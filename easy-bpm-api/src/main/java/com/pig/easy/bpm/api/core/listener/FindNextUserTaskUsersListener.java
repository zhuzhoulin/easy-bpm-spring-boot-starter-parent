package com.pig.easy.bpm.api.core.listener;

import com.pig.easy.bpm.api.dto.response.FlowUserTaskDTO;
import com.pig.easy.bpm.api.service.NodeService;
import com.pig.easy.bpm.auth.service.ConfigService;
import com.pig.easy.bpm.common.constant.BpmConstant;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.execption.BpmException;
import com.pig.easy.bpm.common.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于设置下一个节点人员方法
 *
 * @author pig
 * @date : 2019/7/15 17:16
 */
@Component
@Slf4j
public class FindNextUserTaskUsersListener implements ExecutionListener {

    private static final String DEFAULT_ASSIGNEE_LIST_EXP = "assigneeList";
    private static final String ASSIGNEE_USER_EXP = "assignee";
    private static final long serialVersionUID = -3167479555164375571L;

    @Resource
    NodeService nodeService;
    @Resource
    ConfigService configService;

    @Override
    public void notify(DelegateExecution delegateExecution) {

        List<String> assigneeList = new ArrayList<>();
        Result<FlowUserTaskDTO> result1 = nodeService.calcNodeUsers(delegateExecution.getProcessDefinitionId(), delegateExecution.getCurrentActivityId(), delegateExecution.getVariables());
        if (result1.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            log.error("get calcNodeUsers error {}", result1);
            throw new BpmException(result1.getEntityError());
        }

        FlowUserTaskDTO flowUserTaskDTO = result1.getData();
        if (flowUserTaskDTO.isError()) {
            throw new BpmException(new EntityError(EntityError.SYSTEM_ERROR.getCode(), "节点[" + delegateExecution.getCurrentActivityId() + "]人员找不到，请联系管理员处理！"));
        }
        if(flowUserTaskDTO.isDefaultSetAdmin()){
            Result<Object> result = configService.getConfigValue(delegateExecution.getTenantId(), BpmConstant.FLOW_ADMIN);
            if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                log.error("getConfigValue error {}", result);
                throw new BpmException(result.getEntityError());
            }
            String[] strings = result.getData().toString().split(",");
            for (String string : strings ) {
                if(!StringUtils.isEmpty(string)){
                    assigneeList.add(string);
                }
            }
        }
        if(!flowUserTaskDTO.isSkip()){
            assigneeList.addAll(flowUserTaskDTO.getOwnerUserIds());
        }

        String userTaskAssignee = assigneeList.size() > 0 ? assigneeList.get(0) : null;

        delegateExecution.setVariable(DEFAULT_ASSIGNEE_LIST_EXP, assigneeList);
        delegateExecution.setVariable(ASSIGNEE_USER_EXP, userTaskAssignee);

    }
}
