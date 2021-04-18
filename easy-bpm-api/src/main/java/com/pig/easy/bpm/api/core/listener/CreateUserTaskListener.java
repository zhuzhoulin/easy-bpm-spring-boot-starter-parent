package com.pig.easy.bpm.api.core.listener;

import com.pig.easy.bpm.api.dto.request.UserTaskSaveDTO;
import com.pig.easy.bpm.api.dto.response.NodeDTO;
import com.pig.easy.bpm.api.service.NodeService;
import com.pig.easy.bpm.api.service.UserTaskService;
import com.pig.easy.bpm.auth.dto.response.UserDTO;
import com.pig.easy.bpm.auth.service.ConfigService;
import com.pig.easy.bpm.auth.service.UserService;
import com.pig.easy.bpm.common.constant.BpmConstant;
import com.pig.easy.bpm.common.constant.TaskConstant;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.execption.BpmException;
import com.pig.easy.bpm.common.utils.CommonUtils;
import com.pig.easy.bpm.common.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.engine.impl.context.Context;
import org.flowable.engine.impl.history.HistoryManager;
import org.flowable.task.service.delegate.DelegateTask;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/5/20 10:47
 */
@Component
@Slf4j
public class CreateUserTaskListener implements TaskListener {

    private static final long serialVersionUID = 5464439119647631245L;

    private static final String ASSIGNEE_USER_EXP = "assignee";
    private static final String DEFAULT_ASSIGNEE_USER_EXP = "assigneeExp";

    @Resource
    UserTaskService userTaskService;
    @Resource
    NodeService nodeService;
    @Resource
    UserService userService;
    @Resource
    ConfigService configService;

    @Override
    public void notify(DelegateTask delegateTask) {

        log.info("#####CreateUserTaskListener#####[{}][{}]###start###", delegateTask.getId(), delegateTask.getName());
        Map<String, Object> param = delegateTask.getVariables();
        /* 如果是 加签，则直接优先获取 加签人员 */
        String userTaskAssignee = param.get(DEFAULT_ASSIGNEE_USER_EXP) != null ? param.get(DEFAULT_ASSIGNEE_USER_EXP).toString() : (String) param.get(ASSIGNEE_USER_EXP);
        String currentApproveAction = param.get(TaskConstant.APPROVE_ACTION_DESC) != null ? param.get(TaskConstant.APPROVE_ACTION_DESC).toString() : TaskConstant.APPROVE_ACTION_PASS;

        Result<NodeDTO> result = nodeService.getNodeInfoByNodeIdAndDefinitionId(delegateTask.getTaskDefinitionKey(), delegateTask.getProcessDefinitionId());
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            log.error("get node error {}", result);
            throw new BpmException(result.getEntityError());
        }

        NodeDTO nodeInfoDTO = result.getData();
        UserTaskSaveDTO userTaskSaveDTO = new UserTaskSaveDTO();
        userTaskSaveDTO.setActTaskId(delegateTask.getId());
        userTaskSaveDTO.setCreateTime(LocalDateTime.now());
        userTaskSaveDTO.setTaskOwnerUserId(Long.valueOf(userTaskAssignee));
        userTaskSaveDTO.setFormKey(nodeInfoDTO.getFormKey());
        userTaskSaveDTO.setTenantId(delegateTask.getTenantId());
        userTaskSaveDTO.setProcInstId(delegateTask.getProcessInstanceId());
        userTaskSaveDTO.setTaskName(delegateTask.getName());
        userTaskSaveDTO.setTaskNodeCode(delegateTask.getTaskDefinitionKey());
        userTaskSaveDTO.setTaskPriority(nodeInfoDTO.getPriority());
        userTaskSaveDTO.setOperatorId(Long.valueOf(userTaskAssignee));
        userTaskSaveDTO.setSystem(param.getOrDefault(BpmConstant.SYSTEM, "").toString());
        userTaskSaveDTO.setPlatform(param.getOrDefault(BpmConstant.PLATFORM, "").toString());
        userTaskSaveDTO.setProcessId(nodeInfoDTO.getProcessId());
        userTaskSaveDTO.setTaskStatus(TaskConstant.TASK_UN_CLAIM);
        userTaskSaveDTO.setApplyId(CommonUtils.evalLong(param.get(BpmConstant.APPLY_ID), 0));
        userTaskSaveDTO.setTaskType(nodeInfoDTO.getTaskType());

        if (nodeInfoDTO.getFindUserType() == BpmConstant.FIND_USER_TYPE_BY_ROLE_GROUP) {
            userTaskSaveDTO.setRoleGroupCode(nodeInfoDTO.getRoleGroupCode());
            // 还需设置 role 信息
        }
        if (nodeInfoDTO.getFindUserType() == BpmConstant.FIND_USER_TYPE_BY_ROLE) {
            userTaskSaveDTO.setRoleCode(nodeInfoDTO.getRoleCode());
            userTaskSaveDTO.setRoleName(nodeInfoDTO.getRoleName());
        }

        if (StringUtils.isEmpty(userTaskSaveDTO.getSystem())) {
            Result<Object> configValue = configService.getConfigValue(userTaskSaveDTO.getTenantId(), BpmConstant.SYSTEM);
            userTaskSaveDTO.setSystem(configValue.getData().toString());
        }
        if (StringUtils.isEmpty(userTaskSaveDTO.getPlatform())) {
            Result<Object> configValue = configService.getConfigValue(userTaskSaveDTO.getTenantId(), BpmConstant.SYSTEM);
            userTaskSaveDTO.setPlatform(configValue.getData().toString());
        }

        if (!StringUtils.isEmpty(userTaskAssignee)) {
            Result<UserDTO> result1 = userService.getUserInfoById(Long.valueOf(userTaskAssignee));
            if (result1.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                log.error("get user error {}", result1);
                throw new BpmException(result1.getEntityError());
            }
            UserDTO userInfoDTO = result1.getData();
            userTaskSaveDTO.setTaskOwnerRealName(userInfoDTO.getRealName());
            userTaskSaveDTO.setOperatorName(userInfoDTO.getRealName());
        } else {
            userTaskSaveDTO.setTaskStatus(TaskConstant.TASK_AUTO_COMPLETE);
        }

        userTaskService.insertUserTask(userTaskSaveDTO);

        delegateTask.addCandidateUser(userTaskAssignee);
        delegateTask.setAssignee(userTaskAssignee);
        delegateTask.setOwner(userTaskAssignee);
        TaskEntity taskEntity = (TaskEntity) delegateTask;

        // 修复历史记录表 不会设置assignee 及 owner 字段值,还有BUG，后续维护
        HistoryManager historyManager = Context.getProcessEngineConfiguration().getHistoryManager();
        taskEntity.setAssignee(userTaskAssignee);
        taskEntity.setOwner(userTaskAssignee);
        historyManager.recordTaskInfoChange(taskEntity, taskEntity.getProcessInstanceId());

        log.info("#####CreateUserTaskListener#####[{}][{}]###end###", delegateTask.getId(), delegateTask.getName());

    }
}
