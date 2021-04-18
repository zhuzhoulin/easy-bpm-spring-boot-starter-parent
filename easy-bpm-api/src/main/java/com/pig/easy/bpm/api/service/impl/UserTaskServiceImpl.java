package com.pig.easy.bpm.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.api.core.flowable.CustomSignUserTaskInProcessInstanceCmd;
import com.pig.easy.bpm.api.core.flowable.GetProcessCmd;
import com.pig.easy.bpm.api.dto.request.*;
import com.pig.easy.bpm.api.dto.response.*;
import com.pig.easy.bpm.api.entity.UserTaskDO;
import com.pig.easy.bpm.api.mapper.UserTaskMapper;
import com.pig.easy.bpm.api.service.*;
import com.pig.easy.bpm.auth.config.VisiableThreadPoolTaskExecutor;
import com.pig.easy.bpm.common.constant.BpmConstant;
import com.pig.easy.bpm.common.constant.TaskConstant;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.execption.BpmException;
import com.pig.easy.bpm.common.generator.BaseServiceImpl;
import com.pig.easy.bpm.common.utils.BeanUtils;
import com.pig.easy.bpm.common.utils.CommonUtils;
import com.pig.easy.bpm.common.utils.EasyBpmAsset;
import com.pig.easy.bpm.common.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.Process;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.impl.dynamic.DynamicUserTaskBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author pig
 * @since 2020-05-20
 */
@Service
@Slf4j
public class UserTaskServiceImpl extends BaseServiceImpl<UserTaskMapper, UserTaskDO> implements UserTaskService {

    @Autowired
    UserTaskMapper userTaskMapper;
    @Autowired
    TaskService taskService;
    @Autowired
    VisiableThreadPoolTaskExecutor visiableThreadPoolTaskExecutor;
    @Autowired
    PlatformTransactionManager platformTransactionManager;
    @Autowired
    HistoryService historyService;
    @Autowired
    @Lazy
    FormDataService formDataService;
    @Autowired
    @Lazy
    ApplyService applyService;
    @Autowired
    NodeUserService nodeUserService;
    @Autowired
    NodeService nodeService;
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    @Lazy
    ProcessEngine processEngine;

    @Override
    public Result<PageInfo> getToDoListByCondition(UserTaskInfoQueryDTO userTaskReqDTO) {

        EasyBpmAsset.isAssetEmpty(userTaskReqDTO);
        EasyBpmAsset.isAssetEmpty(userTaskReqDTO.getTenantId());
        int pageIndex = CommonUtils.evalInt(userTaskReqDTO.getPageIndex(), DEFAULT_PAGE_INDEX);
        int pageSize = CommonUtils.evalInt(userTaskReqDTO.getPageSize(), DEFAULT_PAGE_SIZE);

        if (userTaskReqDTO.getApplyStartDate() != null) {
            userTaskReqDTO.setApplyStartDate(CommonUtils.getDayStart(userTaskReqDTO.getApplyStartDate()));
        }
        if (userTaskReqDTO.getApplyEndDate() != null) {
            userTaskReqDTO.setApplyEndDate(CommonUtils.getDayEnd(userTaskReqDTO.getApplyEndDate()));
        }

        PageHelper.startPage(pageIndex, pageSize);
        List<UserTaskInfoDTO> result = userTaskMapper.getToDoListByCondition(userTaskReqDTO);
        if (result == null) {
            result = new ArrayList<>();
        }
        PageInfo<UserTaskInfoDTO> pageInfo = new PageInfo<>(result);
        return Result.responseSuccess(pageInfo);
    }

    @Override
    public Result<PageInfo> getDraftListByCondition(UserTaskInfoQueryDTO userTaskReqDTO) {

        EasyBpmAsset.isAssetEmpty(userTaskReqDTO);
        EasyBpmAsset.isAssetEmpty(userTaskReqDTO.getTenantId());
        int pageIndex = CommonUtils.evalInt(userTaskReqDTO.getPageIndex(), DEFAULT_PAGE_INDEX);
        int pageSize = CommonUtils.evalInt(userTaskReqDTO.getPageSize(), DEFAULT_PAGE_SIZE);

        if (userTaskReqDTO.getApplyStartDate() != null) {
            userTaskReqDTO.setApplyStartDate(CommonUtils.formatDate(userTaskReqDTO.getApplyStartDate(), CommonUtils.yyyyMMdd));
        }
        if (userTaskReqDTO.getApplyEndDate() != null) {
            userTaskReqDTO.setApplyEndDate(CommonUtils.getDayEnd(userTaskReqDTO.getApplyEndDate()));
        }

        PageHelper.startPage(pageIndex, pageSize);
        List<UserTaskInfoDTO> result = userTaskMapper.getDraftListByCondition(userTaskReqDTO);
        if (result == null) {
            result = new ArrayList<>();
        }
        PageInfo<UserTaskInfoDTO> pageInfo = new PageInfo<>(result);
        return Result.responseSuccess(pageInfo);
    }

    @Override
    public Result<PageInfo> getApplyListByCondition(UserTaskInfoQueryDTO userTaskReqDTO) {
        EasyBpmAsset.isAssetEmpty(userTaskReqDTO);
        EasyBpmAsset.isAssetEmpty(userTaskReqDTO.getTenantId());
        int pageIndex = CommonUtils.evalInt(userTaskReqDTO.getPageIndex(), DEFAULT_PAGE_INDEX);
        int pageSize = CommonUtils.evalInt(userTaskReqDTO.getPageSize(), DEFAULT_PAGE_SIZE);

        if (userTaskReqDTO.getApplyStartDate() != null) {
            userTaskReqDTO.setApplyStartDate(CommonUtils.formatDate(userTaskReqDTO.getApplyStartDate(), CommonUtils.yyyyMMdd));
        }
        if (userTaskReqDTO.getApplyEndDate() != null) {
            userTaskReqDTO.setApplyEndDate(CommonUtils.getDayEnd(userTaskReqDTO.getApplyEndDate()));
        }
        PageHelper.startPage(pageIndex, pageSize);
        List<UserTaskInfoDTO> result = userTaskMapper.getApplyListByCondition(userTaskReqDTO);
        if (result == null) {
            result = new ArrayList<>();
        }
        PageInfo<UserTaskInfoDTO> pageInfo = new PageInfo<>(result);
        return Result.responseSuccess(pageInfo);
    }

    @Override
    public Result<PageInfo> getHaveDoListByCondition(UserTaskInfoQueryDTO userTaskReqDTO) {

        EasyBpmAsset.isAssetEmpty(userTaskReqDTO);
        EasyBpmAsset.isAssetEmpty(userTaskReqDTO.getTenantId());
        int pageIndex = CommonUtils.evalInt(userTaskReqDTO.getPageIndex(), DEFAULT_PAGE_INDEX);
        int pageSize = CommonUtils.evalInt(userTaskReqDTO.getPageSize(), DEFAULT_PAGE_SIZE);

        if (userTaskReqDTO.getApplyStartDate() != null) {
            userTaskReqDTO.setApplyStartDate(CommonUtils.formatDate(userTaskReqDTO.getApplyStartDate(), CommonUtils.yyyyMMdd));
        }
        if (userTaskReqDTO.getApplyEndDate() != null) {
            userTaskReqDTO.setApplyEndDate(CommonUtils.getDayEnd(userTaskReqDTO.getApplyEndDate()));
        }
        PageHelper.startPage(pageIndex, pageSize);
        List<UserTaskInfoDTO> result = userTaskMapper.getHaveDoListByCondition(userTaskReqDTO);
        if (result == null) {
            result = new ArrayList<>();
        }
        PageInfo<UserTaskInfoDTO> pageInfo = new PageInfo<>(result);
        return Result.responseSuccess(pageInfo);
    }

    @Override
    public Result<PageInfo<UserTaskDTO>> getListByCondition(UserTaskReqDTO userTaskReqDTO) {

        EasyBpmAsset.isAssetEmpty(userTaskReqDTO);
        EasyBpmAsset.isAssetEmpty(userTaskReqDTO.getTenantId());
        int pageIndex = CommonUtils.evalInt(userTaskReqDTO.getPageIndex(), DEFAULT_PAGE_INDEX);
        int pageSize = CommonUtils.evalInt(userTaskReqDTO.getPageSize(), DEFAULT_PAGE_SIZE);

        if (userTaskReqDTO.getApplyStartDate() != null) {
            userTaskReqDTO.setApplyStartDate(CommonUtils.formatDate(userTaskReqDTO.getApplyStartDate(), CommonUtils.yyyyMMdd));
        }
        if (userTaskReqDTO.getApplyEndDate() != null) {
            userTaskReqDTO.setApplyEndDate(CommonUtils.getDayEnd(userTaskReqDTO.getApplyEndDate()));
        }
        userTaskReqDTO.setValidState(VALID_STATE);
        PageHelper.startPage(pageIndex, pageSize);
        List<UserTaskDTO> result = userTaskMapper.getListByCondition(userTaskReqDTO);
        if (result == null) {
            result = new ArrayList<>();
        }
        PageInfo<UserTaskDTO> pageInfo = new PageInfo<>(result);
        return Result.responseSuccess(pageInfo);
    }

    @Override
    public Result<UserTaskDTO> getUserTaskByTaskId(Long taskId) {

        EasyBpmAsset.isAssetEmpty(taskId);
        UserTaskDO userTaskDO = userTaskMapper.selectOne(new QueryWrapper<>(UserTaskDO.builder().taskId(taskId).validState(VALID_STATE).build()));
        if (userTaskDO == null) {
            return Result.responseError(EntityError.DATA_NOT_FOUND_ERROR);
        }
        UserTaskDTO userTaskDTO = BeanUtils.switchToDTO(userTaskDO, UserTaskDTO.class);

        return Result.responseSuccess(userTaskDTO);
    }

    @Override
    public Result<List<UserTaskDTO>> getUserTaskByApplyId(Long applyId) {

        EasyBpmAsset.isAssetEmpty(applyId);
        List<UserTaskDO> result = userTaskMapper.selectList(new QueryWrapper<>(UserTaskDO.builder().applyId(applyId).validState(VALID_STATE).build()));
        if (result == null) {
            result = new ArrayList<>();
        }
        List<UserTaskDTO> list = new ArrayList<>();
        for (UserTaskDO userTaskDO : result) {
            list.add(BeanUtils.switchToDTO(userTaskDO, UserTaskDTO.class));
        }

        return Result.responseSuccess(list);
    }

    @Override
    public Result<List<UserTaskDTO>> getUserTaskByProcInstId(String procInstId) {

        EasyBpmAsset.isAssetEmpty(procInstId);
        List<UserTaskDO> result = userTaskMapper.selectList(new QueryWrapper<>(UserTaskDO.builder().procInstId(procInstId).validState(VALID_STATE).build()));
        if (result == null) {
            result = new ArrayList<>();
        }
        List<UserTaskDTO> list = new ArrayList<>();
        for (UserTaskDO userTaskDO : result) {
            list.add(BeanUtils.switchToDTO(userTaskDO, UserTaskDTO.class));
        }

        return Result.responseSuccess(list);
    }

    @Override
    public Result<UserTaskDTO> insertUserTask(UserTaskSaveDTO userTaskSaveDTO) {

        EasyBpmAsset.isAssetEmpty(userTaskSaveDTO);
        userTaskSaveDTO.setValidState(VALID_STATE);
        UserTaskDO userTaskDO = BeanUtils.switchToDO(userTaskSaveDTO, UserTaskDO.class);
        int insert = userTaskMapper.insert(userTaskDO);
        if (insert > 0) {
            UserTaskDTO userTaskDTO = BeanUtils.switchToDTO(userTaskDO, UserTaskDTO.class);
            return Result.responseSuccess(userTaskDTO);
        }
        log.error("insertUserTask error , param {} ", userTaskDO);
        return Result.responseError(EntityError.SYSTEM_ERROR);
    }

    @Override
    public Result<Boolean> updateUserTask(UserTaskUpdateDTO userTaskUpdateDTO) {

        EasyBpmAsset.isAssetEmpty(userTaskUpdateDTO);
        EasyBpmAsset.isAssetEmpty(userTaskUpdateDTO.getTaskId());
        UserTaskDO userTaskDO = BeanUtils.switchToDO(userTaskUpdateDTO, UserTaskDO.class);
        int insert = userTaskMapper.updateById(userTaskDO);
        if (insert > 0) {
            return Result.responseSuccess(true);
        }
        log.error("updateUserTask error , param {} ", userTaskDO);
        return Result.responseError(EntityError.SYSTEM_ERROR);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public Result<Boolean> completeTask(CompleteTaskDTO completeTaskDTO) {

        EasyBpmAsset.isAssetEmpty(completeTaskDTO);
        EasyBpmAsset.isAssetEmpty(completeTaskDTO.getTaskId());
        EasyBpmAsset.isAssetEmpty(completeTaskDTO.getTenantId());
        EasyBpmAsset.isAssetEmpty(completeTaskDTO.getApproveId());
        EasyBpmAsset.isAssetEmpty(completeTaskDTO.getApproveActionCode());
        EasyBpmAsset.isAssetEmpty(completeTaskDTO.getApproveName());
        EasyBpmAsset.isAssetEmpty(completeTaskDTO.getApproveOpinion());

        Result<UserTaskDTO> result = getUserTaskByTaskId(completeTaskDTO.getTaskId());
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result.getEntityError());
        }
        UserTaskDTO userTaskDTO = result.getData();
        if (userTaskDTO.getTaskStatus() > TaskConstant.TASK_CLAIM) {
            return Result.responseError(EntityError.TASK_HAS_COMPLETED);
        }

        Long taskUserId = CommonUtils.evalLong(userTaskDTO.getTaskAssigneeUserId()) > 0 ? userTaskDTO.getTaskAssigneeUserId() : userTaskDTO.getTaskOwnerUserId();
        if (!completeTaskDTO.getApproveId().equals(taskUserId)) {
            return Result.responseError(EntityError.TASK_CLAIM_BY_OTHER);
        }

        Result<Integer> result1 = formDataService.batchSaveOrUpdateFormData(userTaskDTO.getApplyId(), userTaskDTO.getTaskId(), userTaskDTO.getTenantId(), userTaskDTO.getFormKey(), completeTaskDTO.getBusinessData());
        if (result1.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.responseError(result1.getEntityError());
        }

        Map<String, Object> actionMap = new ConcurrentHashMap<>();
        List<String> assigneeList = new ArrayList<>();
        String userTaskAssignee = null;
        String targetTaskDefKey = null;
        String[] returnNodeUsers = null;
        List<DynamicUserTaskBuilder> dynamicUserTaskBuilders = new ArrayList<>();
        DynamicUserTaskBuilder dynamicUserTaskBuilder = new DynamicUserTaskBuilder();
        Process process = null;
        String nodeName = null;
        String dynamicId = null;
        AtomicInteger atomicInteger = new AtomicInteger(0);
        Result<ApplyDTO> result2 = Result.responseError(EntityError.SYSTEM_ERROR);
        boolean autoComplete = false;
        boolean selectPath = false;

        switch (completeTaskDTO.getApproveActionCode()) {
            /* 审批拒绝 */
            case TaskConstant.APPROVE_ACTION_REJECT:
                actionMap.put(TaskConstant.APPROVE_ACTION_DESC, TaskConstant.APPROVE_ACTION_REJECT);
                taskService.setVariables(userTaskDTO.getActTaskId(), actionMap);
                if (org.apache.commons.lang.StringUtils.isNotEmpty(userTaskDTO.getProcInstId())) {
                    processEngine.getRuntimeService().deleteProcessInstance(userTaskDTO.getProcInstId(), TaskConstant.APPROVE_ACTION_FAIL);
                }
                autoComplete = true;
                break;
            /*  / 中止流程 */
            case TaskConstant.APPROVE_ACTION_FAIL:
                actionMap.put(TaskConstant.APPROVE_ACTION_DESC, TaskConstant.APPROVE_ACTION_FAIL);
                taskService.setVariables(userTaskDTO.getActTaskId(), actionMap);

                if (org.apache.commons.lang.StringUtils.isNotEmpty(userTaskDTO.getProcInstId())) {
                    processEngine.getRuntimeService().deleteProcessInstance(userTaskDTO.getProcInstId(), TaskConstant.APPROVE_ACTION_FAIL);
                }
                autoComplete = true;
                break;
            /* 审批通过 */
            case TaskConstant.APPROVE_ACTION_PASS:
                actionMap.put(TaskConstant.APPROVE_ACTION_DESC, TaskConstant.APPROVE_ACTION_PASS);

                result2 = applyService.getApplyByApplyId(userTaskDTO.getApplyId());
                if (result2.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.responseError(result2.getEntityError());
                }
                Result<NodeDTO> result4 = nodeService.getNodeInfoByNodeIdAndDefinitionId(userTaskDTO.getTaskNodeCode(), result2.getData().getDefinitionId());
                if (result4.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.responseError(result4.getEntityError());
                }
                NodeDTO nodeInfoDTO = result4.getData();
                /* 如果是选择路径 */
                if (BpmConstant.SELECT_PATCH_TRUE == CommonUtils.evalInt(nodeInfoDTO.getSelectPath())) {
                    selectPath = true;
                    targetTaskDefKey = completeTaskDTO.getBusinessData().get(TaskConstant.NODE_ID).toString();
                    if (StringUtils.isEmpty(targetTaskDefKey)) {
                        throw new BpmException(new EntityError(EntityError.ILLEGAL_ARGUMENT_ERROR.getCode(), "nodeId is not  allow empty ."));
                    }
                }
                break;
            /* 会签 */
            case TaskConstant.APPROVE_ACTION_COUNTER_SIGN:
                actionMap.put(TaskConstant.APPROVE_ACTION_DESC, TaskConstant.APPROVE_ACTION_COUNTER_SIGN);
                taskService.setVariables(userTaskDTO.getActTaskId(), actionMap);
                assigneeList = Arrays.asList((completeTaskDTO.getBusinessData().get(TaskConstant.DEFAULT_ASSIGNEE_LIST_EXP) + "").split(","));
                if (assigneeList == null || assigneeList.size() == 0) {
                    throw new BpmException(new EntityError(EntityError.ILLEGAL_ARGUMENT_ERROR.getCode(), "assigneeList is not  allow empty ."));
                }
                HashMap<String, Object> addApproveMap = null;
                for (String assignee : assigneeList) {
                    addApproveMap = new HashMap<>();
                    addApproveMap.put(TaskConstant.DEFAULT_ASSIGNEE_USER_EXP, assignee);
                    runtimeService.addMultiInstanceExecution(userTaskDTO.getTaskNodeCode(), userTaskDTO.getProcInstId(), addApproveMap);
                }
                break;
            /* 新增临时节点 */
            case TaskConstant.APPROVE_ACTION_ADD_TEMP_NODE:
                actionMap.put(TaskConstant.APPROVE_ACTION_DESC, TaskConstant.APPROVE_ACTION_ADD_TEMP_NODE);
                taskService.setVariables(userTaskDTO.getActTaskId(), actionMap);

                result2 = applyService.getApplyByApplyId(userTaskDTO.getApplyId());
                if (result2.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.responseError(result2.getEntityError());
                }

                EasyBpmAsset.isEmpty(completeTaskDTO.getBusinessData().get(TaskConstant.NODE_USERS), "nodeUsers is not  allow empty .");
                returnNodeUsers = completeTaskDTO.getBusinessData().get(TaskConstant.NODE_USERS).toString().split(",");
                assigneeList.addAll(Arrays.asList(returnNodeUsers));
                if (assigneeList.size() > 0 && !ObjectUtils.isEmpty(assigneeList.get(0))) {
                    userTaskAssignee = assigneeList.get(0);
                } else {
                    throw new BpmException(new EntityError(EntityError.ILLEGAL_ARGUMENT_ERROR.getCode(), "nodeUsers is not  allow empty ."));
                }
                process = processEngine.getManagementService().executeCommand(new GetProcessCmd(result2.getData().getDefinitionId()));
                nodeName = completeTaskDTO.getBusinessData().get(TaskConstant.NODE_NAME) != null ? completeTaskDTO.getBusinessData().get(TaskConstant.NODE_NAME).toString() : "动态节点";

                dynamicId = dynamicUserTaskBuilder.nextTaskId(process.getFlowElementMap());

                dynamicUserTaskBuilder.setId(dynamicId);
                dynamicUserTaskBuilder.setName(nodeName);
                dynamicUserTaskBuilder.setAssignee(String.join(",", assigneeList));

                try {
                    NodeUserSaveOrUpdateDTO nodeUserSaveOrUpdateDTO = new NodeUserSaveOrUpdateDTO();
                    nodeUserSaveOrUpdateDTO.setAssigneeUserIdList(String.join(",", assigneeList));
                    nodeUserSaveOrUpdateDTO.setAssigneeUserNameList("");
                    nodeUserSaveOrUpdateDTO.setNodeId(dynamicId);
                    nodeUserSaveOrUpdateDTO.setNodeName(nodeName);
                    nodeUserSaveOrUpdateDTO.setApplyId(userTaskDTO.getApplyId());
                    nodeUserSaveOrUpdateDTO.setProcInstId(userTaskDTO.getProcInstId());
                    nodeUserSaveOrUpdateDTO.setOperatorId(completeTaskDTO.getCurrentUserId());
                    nodeUserSaveOrUpdateDTO.setOperatorName(completeTaskDTO.getCurrentRealName());
                    nodeUserSaveOrUpdateDTO.setTenantId(completeTaskDTO.getCurrentTenantId());
                    nodeUserSaveOrUpdateDTO.setProcessId(result2.getData().getProcessId());
                    nodeUserSaveOrUpdateDTO.setProcessKey(result2.getData().getProcessKey());
                    nodeUserSaveOrUpdateDTO.setDefinitionId(result2.getData().getDefinitionId());
                    nodeUserSaveOrUpdateDTO.setError(0);
                    nodeUserSaveOrUpdateDTO.setRemarks("");
                    nodeUserSaveOrUpdateDTO.setDefaultSetAdmin(0);
                    nodeUserSaveOrUpdateDTO.setSkip(0);

                    Result<Integer> result3 = nodeUserService.insert(nodeUserSaveOrUpdateDTO);
                    if (result3.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return Result.responseError(result3.getEntityError());
                    }

                    dynamicUserTaskBuilders.add(dynamicUserTaskBuilder);
                    processEngine.getManagementService().executeCommand(new CustomSignUserTaskInProcessInstanceCmd(userTaskDTO.getProcInstId(), dynamicUserTaskBuilders, process.getFlowElement(userTaskDTO.getTaskNodeCode()), true));

                } catch (Exception e) {
                    e.printStackTrace();
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    throw new BpmException(EntityError.SYSTEM_ERROR);
                }
                autoComplete = true;
                break;
            /* 加签  前加签/后加签 */
            case TaskConstant.APPROVE_ACTION_ADD_SIGN:
                /* 前加签 需要回到加签人 */

                /* 前加签 无需回到加签人 */
                actionMap.put(TaskConstant.APPROVE_ACTION_DESC, TaskConstant.APPROVE_ACTION_ADD_SIGN);
                taskService.setVariables(userTaskDTO.getActTaskId(), actionMap);

                EasyBpmAsset.isEmpty(completeTaskDTO.getBusinessData().get(TaskConstant.SIGN_TYPE), "signType is not  allow empty .");

                result2 = applyService.getApplyByApplyId(userTaskDTO.getApplyId());
                if (result2.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.responseError(result2.getEntityError());
                }

                EasyBpmAsset.isEmpty(completeTaskDTO.getBusinessData().get(TaskConstant.NODE_USERS), "nodeUsers is not  allow empty .");
                returnNodeUsers = completeTaskDTO.getBusinessData().get(TaskConstant.NODE_USERS).toString().split(",");
                assigneeList.addAll(Arrays.asList(returnNodeUsers));
                if (assigneeList.size() > 0 && !ObjectUtils.isEmpty(assigneeList.get(0))) {
                    userTaskAssignee = assigneeList.get(0);
                } else {
                    throw new BpmException(new EntityError(EntityError.ILLEGAL_ARGUMENT_ERROR.getCode(), "nodeUsers is not  allow empty ."));
                }
                process = processEngine.getManagementService().executeCommand(new GetProcessCmd(result2.getData().getDefinitionId()));
                nodeName = completeTaskDTO.getBusinessData().get(TaskConstant.NODE_NAME) != null ? completeTaskDTO.getBusinessData().get(TaskConstant.NODE_NAME).toString() : "动态节点";

                dynamicId = dynamicUserTaskBuilder.nextTaskId(process.getFlowElementMap());
                atomicInteger.addAndGet(Integer.valueOf(dynamicId.split("dynamicTask")[1]));
                dynamicUserTaskBuilder.setId(dynamicId);
                dynamicUserTaskBuilder.setName(nodeName);
                dynamicUserTaskBuilder.setAssignee(String.join(",", assigneeList));
                dynamicUserTaskBuilders.add(dynamicUserTaskBuilder);

                List<NodeUserSaveOrUpdateDTO> saveOrUpdateDTOList = new ArrayList<>();
                NodeUserSaveOrUpdateDTO nodeUserSaveOrUpdateDTO = new NodeUserSaveOrUpdateDTO();

                try {
                    nodeUserSaveOrUpdateDTO = new NodeUserSaveOrUpdateDTO();
                    nodeUserSaveOrUpdateDTO.setAssigneeUserIdList(String.join(",", assigneeList));
                    nodeUserSaveOrUpdateDTO.setAssigneeUserNameList("");
                    nodeUserSaveOrUpdateDTO.setNodeId(dynamicId);
                    nodeUserSaveOrUpdateDTO.setNodeName(nodeName);
                    nodeUserSaveOrUpdateDTO.setApplyId(userTaskDTO.getApplyId());
                    nodeUserSaveOrUpdateDTO.setProcInstId(userTaskDTO.getProcInstId());
                    nodeUserSaveOrUpdateDTO.setOperatorId(completeTaskDTO.getCurrentUserId());
                    nodeUserSaveOrUpdateDTO.setOperatorName(completeTaskDTO.getCurrentRealName());
                    nodeUserSaveOrUpdateDTO.setTenantId(completeTaskDTO.getCurrentTenantId());
                    nodeUserSaveOrUpdateDTO.setProcessId(result2.getData().getProcessId());
                    nodeUserSaveOrUpdateDTO.setProcessKey(result2.getData().getProcessKey());
                    nodeUserSaveOrUpdateDTO.setDefinitionId(result2.getData().getDefinitionId());
                    nodeUserSaveOrUpdateDTO.setError(0);
                    nodeUserSaveOrUpdateDTO.setRemarks("");
                    nodeUserSaveOrUpdateDTO.setDefaultSetAdmin(0);
                    nodeUserSaveOrUpdateDTO.setSkip(0);
                    saveOrUpdateDTOList.add(nodeUserSaveOrUpdateDTO);

                    /* 如果是前加签，则加上 当前审批人节点 */
                    if (TaskConstant.SIGN_TYPE_BEFOR.equals(completeTaskDTO.getBusinessData().get(TaskConstant.SIGN_TYPE).toString())) {
                        dynamicUserTaskBuilder = new DynamicUserTaskBuilder();
                        String tempNodeId = "dynamicTask" + atomicInteger.addAndGet(1);
                        String tempNodeName = "加签人" + atomicInteger.get();
                        dynamicUserTaskBuilder.setId(tempNodeId);
                        dynamicUserTaskBuilder.setName(tempNodeName);
                        dynamicUserTaskBuilder.setAssignee(String.valueOf(completeTaskDTO.getApproveId()));
                        dynamicUserTaskBuilders.add(dynamicUserTaskBuilder);

                        nodeUserSaveOrUpdateDTO.setAssigneeUserIdList(String.valueOf(completeTaskDTO.getApproveId()));
                        nodeUserSaveOrUpdateDTO.setAssigneeUserNameList(completeTaskDTO.getApproveName());
                        nodeUserSaveOrUpdateDTO.setNodeId(tempNodeId);
                        nodeUserSaveOrUpdateDTO.setNodeName(nodeName);
                        saveOrUpdateDTOList.add(nodeUserSaveOrUpdateDTO);
                    }

                    Result<Integer> result3 = nodeUserService.batchSave(saveOrUpdateDTOList);
                    if (result3.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return Result.responseError(result3.getEntityError());
                    }

                    processEngine.getManagementService().executeCommand(new CustomSignUserTaskInProcessInstanceCmd(userTaskDTO.getProcInstId(), dynamicUserTaskBuilders, process.getFlowElement(userTaskDTO.getTaskNodeCode()), true));

                } catch (Exception e) {
                    e.printStackTrace();
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    throw new BpmException(EntityError.SYSTEM_ERROR);
                }

                actionMap.put(TaskConstant.APPROVE_ACTION_DESC, TaskConstant.APPROVE_ACTION_ADD_SIGN);
                autoComplete = true;
                break;
            /* 指定节点退回 */
            case TaskConstant.APPROVE_ACTION_RETURN:
                EasyBpmAsset.isEmpty(completeTaskDTO.getBusinessData().get(TaskConstant.NODE_ID), "nodeId is not  allow empty .");
                targetTaskDefKey = completeTaskDTO.getBusinessData().get(TaskConstant.NODE_ID).toString();
                actionMap.put(TaskConstant.APPROVE_ACTION_DESC, TaskConstant.APPROVE_ACTION_RETURN);
                taskService.setVariables(userTaskDTO.getActTaskId(), actionMap);

                actionMap.put(TaskConstant.APPROVE_ACTION_DESC, TaskConstant.APPROVE_ACTION_RANDOM_RETURN);
                log.info("流程申请编号[{}],流程实例[{}],源节点[{}]，目标节点[{}]", userTaskDTO.getApplyId(), userTaskDTO.getProcInstId(), userTaskDTO.getTaskNodeCode(), targetTaskDefKey);
                runtimeService.createChangeActivityStateBuilder().processInstanceId(userTaskDTO.getProcInstId()).moveActivityIdTo(userTaskDTO.getTaskNodeCode(), targetTaskDefKey).changeState();

                autoComplete = true;
                break;
            /* 任意节点退回 */
            case TaskConstant.APPROVE_ACTION_RANDOM_RETURN:

                targetTaskDefKey = completeTaskDTO.getBusinessData().get(TaskConstant.NODE_ID).toString();
                if (StringUtils.isEmpty(targetTaskDefKey)) {
                    throw new BpmException(new EntityError(EntityError.ILLEGAL_ARGUMENT_ERROR.getCode(), "nodeId is not  allow empty ."));
                }
//                returnNodeUsers = completeTaskDTO.getBusinessData().get(TaskConstant.NODE_USERS).toString().split(",");
//                assigneeList.addAll(Arrays.asList(returnNodeUsers));
//                if (assigneeList.size() > 0 && !ObjectUtils.isEmpty(assigneeList.get(0))) {
//                    userTaskAssignee = assigneeList.get(0);
//                } else {
//                    throw new BpmException(new EntityError(EntityError.ILLEGAL_ARGUMENT_ERROR.getCode(), "nodeUser is not  allow empty ."));
//                }
//                taskService.setVariable(userTaskDTO.getActTaskId(), TaskConstant.DEFAULT_ASSIGNEE_LIST_EXP, assigneeList);
//                taskService.setVariable(userTaskDTO.getActTaskId(), TaskConstant.ASSIGNEE_USER_EXP, userTaskAssignee);
                actionMap.put(TaskConstant.APPROVE_ACTION_DESC, TaskConstant.APPROVE_ACTION_RANDOM_RETURN);
                taskService.setVariables(userTaskDTO.getActTaskId(), actionMap);

                log.info("流程申请编号[{}],流程实例[{}],源节点[{}]，目标节点[{}]", userTaskDTO.getApplyId(), userTaskDTO.getProcInstId(), userTaskDTO.getTaskNodeCode(), targetTaskDefKey);
                runtimeService.createChangeActivityStateBuilder().processInstanceId(userTaskDTO.getProcInstId()).moveActivityIdTo(userTaskDTO.getTaskNodeCode(), targetTaskDefKey).changeState();
                autoComplete = true;
                break;

            /* 撤销 */
            case TaskConstant.APPROVE_ACTION_CANCEL:
                actionMap.put(TaskConstant.APPROVE_ACTION_DESC, TaskConstant.APPROVE_ACTION_CANCEL);
                break;
            /* 默认审批通过 */
            default:
                actionMap.put(TaskConstant.APPROVE_ACTION_DESC, TaskConstant.APPROVE_ACTION_PASS);
                break;
        }


        /* 如果是退回 / 任意节点退回/中止流程，则需要取消 其余活动多实例任务 */
        if (TaskConstant.APPROVE_ACTION_RETURN.equals(completeTaskDTO.getApproveActionCode())
                || TaskConstant.APPROVE_ACTION_RANDOM_RETURN.equals(completeTaskDTO.getApproveActionCode())
                || TaskConstant.APPROVE_ACTION_FAIL.equals(completeTaskDTO.getApproveActionCode())
                || TaskConstant.APPROVE_ACTION_ADD_TEMP_NODE.equals(completeTaskDTO.getApproveActionCode())
                || TaskConstant.APPROVE_ACTION_ADD_SIGN.equals(completeTaskDTO.getApproveActionCode())) {
            Result<List<UserTaskDTO>> result3 = getUserTaskByApplyId(userTaskDTO.getApplyId());
            if (result3.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.responseError(result3.getEntityError());
            }

            /* 如果 退回 / 任意节点退回  目标节点为发起节点。则退回至草稿箱中！*/
            if (TaskConstant.APPROVE_ACTION_RETURN.equals(completeTaskDTO.getApproveActionCode())
                    || TaskConstant.APPROVE_ACTION_RANDOM_RETURN.equals(completeTaskDTO.getApproveActionCode())) {

                result2 = applyService.getApplyByApplyId(userTaskDTO.getApplyId());
                if (result2.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.responseError(result2.getEntityError());
                }

                Result<NodeDTO> result4 = nodeService.getNodeInfoByNodeIdAndDefinitionId(targetTaskDefKey, result2.getData().getDefinitionId());
                if (result4.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.responseError(result4.getEntityError());
                }

                if (TaskConstant.TASK_ACTION_START.equals(result4.getData().getTaskType())) {
                    log.info("流程申请编号[{}],流程实例[{}],源节点[{}]，目标节点[{}]，退回至发起人节点", userTaskDTO.getApplyId(), userTaskDTO.getProcInstId(), userTaskDTO.getTaskNodeCode(), targetTaskDefKey);
                    ApplyUpdateDTO applyUpdateDTO = new ApplyUpdateDTO();
                    applyUpdateDTO.setApplyId(userTaskDTO.getApplyId());
                    applyUpdateDTO.setUpdateTime(LocalDateTime.now());
                    applyUpdateDTO.setApplyStatus(BpmConstant.APPLY_STATUS_DRAFT);
                    applyService.updateApplyByApplyId(applyUpdateDTO);
                }
            }

            result3.getData().stream().filter(temp ->
                    temp.getTaskStatus() < TaskConstant.TASK_COMPLETED && !temp.getTaskId().equals(userTaskDTO.getTaskId())).forEach(
                    userTask -> {
                        /* 如果是 新增临时节点 / 前加签/后加签 则只需要取消当前 剩余并行节点 */
                        boolean needDisable = true;
                        if (TaskConstant.APPROVE_ACTION_ADD_TEMP_NODE.equals(completeTaskDTO.getApproveActionCode())
                                || TaskConstant.APPROVE_ACTION_ADD_SIGN.equals(completeTaskDTO.getApproveActionCode())
                                || TaskConstant.APPROVE_ACTION_RETURN.equals(completeTaskDTO.getApproveActionCode())
                                || TaskConstant.APPROVE_ACTION_RANDOM_RETURN.equals(completeTaskDTO.getApproveActionCode())) {
                            if (!userTaskDTO.getTaskNodeCode().equals(userTask.getTaskNodeCode())) {
                                needDisable = false;
                            }
                        }
                        if (needDisable) {
                            UserTaskUpdateDTO disableUserTask = BeanUtils.switchToDTO(userTask, UserTaskUpdateDTO.class);
                            disableUserTask.setUpdateTime(LocalDateTime.now());
                            disableUserTask.setTaskStatus(TaskConstant.TASK_DISABLE);
                            disableUserTask.setApproveTime(LocalDateTime.now());
                            updateUserTask(disableUserTask);
                        }
                    }
            );
        }

        if (!autoComplete) {
            /* 如果是选择路径，则 直接跳转至对应节点,否则 调用完成任务接口 */
            if (selectPath) {
                runtimeService.createChangeActivityStateBuilder().processInstanceId(userTaskDTO.getProcInstId()).moveActivityIdTo(userTaskDTO.getTaskNodeCode(), targetTaskDefKey).changeState();
            } else {
                taskService.complete(userTaskDTO.getActTaskId(), actionMap);
            }
        }
        UserTaskUpdateDTO userTaskUpdateDTO = BeanUtils.switchToDTO(userTaskDTO, UserTaskUpdateDTO.class);
        userTaskUpdateDTO.setUpdateTime(LocalDateTime.now());
        userTaskUpdateDTO.setTaskAssigneeUserId(completeTaskDTO.getApproveId());
        userTaskUpdateDTO.setTaskAssigneeRealName(completeTaskDTO.getApproveName());
        userTaskUpdateDTO.setTaskStatus(TaskConstant.TASK_COMPLETED);
        userTaskUpdateDTO.setApproveTime(LocalDateTime.now());

        Result<Boolean> result5 = updateUserTask(userTaskUpdateDTO);
        if (result5.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.responseError(result5.getEntityError());
        }


        historyService.insertHistory(userTaskDTO.getApplyId(), completeTaskDTO.getTenantId(), userTaskDTO.getTaskId(),
                userTaskDTO.getTaskName(), completeTaskDTO.getApproveId(), completeTaskDTO.getApproveName(),
                completeTaskDTO.getApproveActionCode(), completeTaskDTO.getApproveOpinion(),
                completeTaskDTO.getCurrentSystem(), completeTaskDTO.getCurrentPlatform());

        return Result.responseSuccess(Boolean.TRUE);
    }

    @Override
    public Result<Boolean> batchCompleteTask(CompleteTaskDTO completeTaskDTO) {

        EasyBpmAsset.isAssetEmpty(completeTaskDTO);
        EasyBpmAsset.isAssetEmpty(completeTaskDTO.getTaskIds());
        EasyBpmAsset.isAssetEmpty(completeTaskDTO.getTenantId());
        EasyBpmAsset.isAssetEmpty(completeTaskDTO.getApproveId());
        EasyBpmAsset.isAssetEmpty(completeTaskDTO.getApproveActionCode());
        EasyBpmAsset.isAssetEmpty(completeTaskDTO.getApproveName());
        EasyBpmAsset.isAssetEmpty(completeTaskDTO.getApproveOpinion());

        if (completeTaskDTO.getTaskIds().size() == 0) {
            return Result.responseSuccess(Boolean.TRUE);
        }

        CountDownLatch countDownLatch = new CountDownLatch(completeTaskDTO.getTaskIds().size());
        // 因为事务在call方法中，所以如果线程这个方法被中断，可能导致当前任务的事务不会回滚
        List<Future<Result<Boolean>>> futures = new ArrayList<>();
        Callable<Result<Boolean>> callable = null;

        for (Long taskId : completeTaskDTO.getTaskIds()) {
            try {
                callable = new CompleteUserTaskAsync(taskId, completeTaskDTO.getTenantId(), new HashMap<>(),
                        formDataService, historyService, this, taskService,
                        completeTaskDTO.getApproveId(), completeTaskDTO.getApproveName(),
                        completeTaskDTO.getApproveOpinion(), completeTaskDTO.getApproveActionCode(),
                        completeTaskDTO.getSystem(), completeTaskDTO.getCurrentPlatform());
                Future<Result<Boolean>> submit = visiableThreadPoolTaskExecutor.submit(callable);
                futures.add(submit);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }
        }

        System.out.println(" ===============" + Thread.currentThread().getId() + "====end========== ");

        List<Result<Boolean>> responseResults = new ArrayList<>();
        for (Future<Result<Boolean>> result : futures) {
            while (true) {
                //CPU高速轮询：每个future都并发轮循，判断完成状态然后获取结果，这一行，是本实现方案的精髓所在。即有10个future在高速轮询，完成一个future的获取结果，就关闭一个轮询
                if (result.isDone() && !result.isCancelled()) {
                    //获取future成功完成状态，如果想要限制每个任务的超时时间，取消本行的状态判断+future.get(1000*1, TimeUnit.MILLISECONDS)+catch超时异常使用即可。
                    try {
                        Result<Boolean> responseResult = result.get();//获取结果
                        responseResults.add(responseResult);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    //当前future获取结果完毕，跳出while
                    break;
                } else {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        try {
            countDownLatch.await();
            System.out.print("all thread task have finished,main thread will continue run");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("responseResults = " + responseResults);

        return Result.responseSuccess(Boolean.TRUE);
    }

    @Override
    public Result<List<ReturnNodeDTO>> getRandomJumpNode(Long taskId) {

        EasyBpmAsset.isAssetEmpty(taskId);
        Result<UserTaskDTO> result = getUserTaskByTaskId(taskId);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result.getEntityError());
        }
        UserTaskDTO userTaskDTO = result.getData();

        Result<List<NodeUserDTO>> result1 = nodeUserService.getListByApplyId(userTaskDTO.getApplyId());
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result.getEntityError());
        }

        List<ReturnNodeDTO> returnNodeDTOS = new ArrayList<>();
        List<Long> userIds = null;
        List<String> userNames = null;
        List<String> strings = null;
        ReturnNodeDTO returnNodeDTO = null;

        /* 不能退回到当前节点 */
        for (NodeUserDTO nodeUserDTO : result1.getData()) {
            if (nodeUserDTO.getNodeId().equals(userTaskDTO.getTaskNodeCode())) {
                continue;
            }
            returnNodeDTO = BeanUtils.switchToDTO(nodeUserDTO, ReturnNodeDTO.class);
            userIds = new CopyOnWriteArrayList<>();
            userNames = new CopyOnWriteArrayList<>();
            if (!StringUtils.isEmpty(returnNodeDTO.getAssigneeUserIdList())) {
                strings = Arrays.asList(returnNodeDTO.getAssigneeUserIdList().split(","));
                for (String string : strings) {
                    userIds.add(Long.valueOf(string));
                }
            }
            if (!StringUtils.isEmpty(returnNodeDTO.getAssigneeUserNameList())) {
                userNames = Arrays.asList(returnNodeDTO.getAssigneeUserNameList().split(","));
            }
            returnNodeDTO.setAssigneeUserIds(userIds);
            returnNodeDTO.setAssigneeUserNames(userNames);
            returnNodeDTOS.add(returnNodeDTO);
        }
        return Result.responseSuccess(returnNodeDTOS);
    }

    @Override
    public Result<List<ReturnNodeDTO>> getReturnNode(Long taskId) {

        EasyBpmAsset.isAssetEmpty(taskId);
        Result<UserTaskDTO> result = getUserTaskByTaskId(taskId);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result.getEntityError());
        }
        UserTaskDTO userTaskDTO = result.getData();

        List<ReturnNodeDTO> returnNodeDTOS = new ArrayList<>();
        List<Long> userIds = null;
        List<String> userNames = null;
        List<String> strings = null;
        ReturnNodeDTO returnNodeDTO = null;

        Result<List<NodeUserDTO>> result1 = nodeUserService.getListByApplyId(userTaskDTO.getApplyId());
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result.getEntityError());
        }

        List<NodeUserDTO> tempNodeUserList = result1.getData().stream().filter(x -> userTaskDTO.getTaskNodeCode().equals(x.getNodeId())).collect(Collectors.toList());

        Long currentNodeId = tempNodeUserList.get(0).getNodeUserId();
        for (NodeUserDTO nodeUserDTO : result1.getData()) {
            if (nodeUserDTO.getNodeUserId() >= currentNodeId) {
                continue;
            }
            returnNodeDTO = BeanUtils.switchToDTO(nodeUserDTO, ReturnNodeDTO.class);
            userIds = new CopyOnWriteArrayList<>();
            userNames = new CopyOnWriteArrayList<>();
            if (!StringUtils.isEmpty(returnNodeDTO.getAssigneeUserIdList())) {
                strings = Arrays.asList(returnNodeDTO.getAssigneeUserIdList().split(","));
                for (String string : strings) {
                    userIds.add(Long.valueOf(string));
                }
            }
            if (!StringUtils.isEmpty(returnNodeDTO.getAssigneeUserNameList())) {
                userNames = Arrays.asList(returnNodeDTO.getAssigneeUserNameList().split(","));
            }
            returnNodeDTO.setAssigneeUserIds(userIds);
            returnNodeDTO.setAssigneeUserNames(userNames);
            returnNodeDTOS.add(returnNodeDTO);
        }
        return Result.responseSuccess(returnNodeDTOS);
    }

    @Override
    public Result<Map<String, Object>> getCountListByUserType(Long userId, String tenantId, String userType) {

        EasyBpmAsset.isAssetEmptyByDefault(userId);
        EasyBpmAsset.isAssetEmptyByDefault(tenantId);
        EasyBpmAsset.isAssetEmptyByDefault(userType);

        Map<String, Object> countMap = new ConcurrentHashMap<>();

        int toDoNum = 0, haveDoNum = 0, draftNum = 0, applyNum = 0;

        UserTaskInfoQueryDTO queryUserTaskCountDTO = new UserTaskInfoQueryDTO();
        queryUserTaskCountDTO.setTenantId(tenantId);
        queryUserTaskCountDTO.setCurrentUserId(userId);
        Result<PageInfo> result = null;
        switch (userType) {

            case "all":
                result = getDraftListByCondition(queryUserTaskCountDTO);
                if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                    return Result.responseError(result.getEntityError());
                }
                draftNum = result.getData().getSize();
                result = getApplyListByCondition(queryUserTaskCountDTO);
                if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                    return Result.responseError(result.getEntityError());
                }
                applyNum = result.getData().getSize();
                result = getToDoListByCondition(queryUserTaskCountDTO);
                if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                    return Result.responseError(result.getEntityError());
                }
                toDoNum = result.getData().getSize();
                break;
            case "toDo":
                result = getToDoListByCondition(queryUserTaskCountDTO);
                if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                    return Result.responseError(result.getEntityError());
                }
                toDoNum = result.getData().getSize();
                break;
            case "haveDo":
                result = getHaveDoListByCondition(queryUserTaskCountDTO);
                if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                    return Result.responseError(result.getEntityError());
                }
                haveDoNum = result.getData().getSize();
                break;
            case "draft":
                result = getDraftListByCondition(queryUserTaskCountDTO);
                if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                    return Result.responseError(result.getEntityError());
                }
                draftNum = result.getData().getSize();
                break;
            case "apply":
                result = getApplyListByCondition(queryUserTaskCountDTO);
                if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                    return Result.responseError(result.getEntityError());
                }
                applyNum = result.getData().getSize();
                break;
            default:
                result = getToDoListByCondition(queryUserTaskCountDTO);
                if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                    return Result.responseError(result.getEntityError());
                }
                toDoNum = result.getData().getSize();
                break;
        }

        countMap.put("toDo", toDoNum);
        countMap.put("haveDo", haveDoNum);
        countMap.put("draft", draftNum);
        countMap.put("apply", applyNum);

        return Result.responseSuccess(countMap);
    }


    class CompleteUserTaskAsync implements Callable<Result<Boolean>> {

        private Long taskId;
        private FormDataService formDataService;
        private HistoryService historyService;
        private UserTaskService userTaskService;
        private TaskService taskService;
        private String tenantId;
        private Long approveId;
        private String approveName;
        private String approveOpinion;
        private String approveActionCode;
        private Map<String, Object> businessData;
        private String systemCode;
        private String platform;

        private CompleteUserTaskAsync(Long taskId, String tenantId, Map<String, Object> businessData, FormDataService formDataService, HistoryService historyService, UserTaskService userTaskService, TaskService taskService,
                                      Long approveId, String approveName, String approveOpinion, String approveActionCode, String systemCode, String platform) {
            this.taskId = taskId;
            this.formDataService = formDataService;
            this.historyService = historyService;
            this.userTaskService = userTaskService;
            this.taskService = taskService;
            this.approveId = approveId;
            this.approveName = approveName;
            this.approveActionCode = approveActionCode;
            this.approveOpinion = approveOpinion;
            this.tenantId = tenantId;
            this.systemCode = systemCode;
            this.platform = platform;
        }

        @Override
        public Result<Boolean> call() throws Exception {

            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
            TransactionStatus transactionStatus = platformTransactionManager.getTransaction(def);

            try {

                Result<UserTaskDTO> result = getUserTaskByTaskId(taskId);
                if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                    return Result.responseError(result.getEntityError());
                }
                UserTaskDTO userTaskDTO = result.getData();
                if (userTaskDTO.getTaskStatus() > TaskConstant.TASK_CLAIM) {
                    return Result.responseError(EntityError.TASK_HAS_COMPLETED);
                }

                Long taskUserId = CommonUtils.evalLong(userTaskDTO.getTaskAssigneeUserId()) > 0 ? userTaskDTO.getTaskAssigneeUserId() : userTaskDTO.getTaskOwnerUserId();
                if (!approveId.equals(taskUserId)) {
                    return Result.responseError(EntityError.TASK_CLAIM_BY_OTHER);
                }

                Result<Integer> result1 = formDataService.batchSaveOrUpdateFormData(userTaskDTO.getApplyId(), userTaskDTO.getTaskId(), userTaskDTO.getTenantId(), userTaskDTO.getFormKey(), businessData);
                if (result1.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.responseError(result1.getEntityError());
                }
                Map<String, Object> actionMap = new ConcurrentHashMap<>();
                if (approveActionCode.equals(TaskConstant.APPROVE_ACTION_FAIL)) {
                    actionMap.put(TaskConstant.APPROVE_ACTION_DESC, TaskConstant.APPROVE_ACTION_FAIL);
                } else {
                    actionMap.put(TaskConstant.APPROVE_ACTION_DESC, TaskConstant.APPROVE_ACTION_PASS);
                }

                taskService.complete(userTaskDTO.getActTaskId(), actionMap);

                UserTaskUpdateDTO userTaskUpdateDTO = BeanUtils.switchToDTO(userTaskDTO, UserTaskUpdateDTO.class);
                userTaskUpdateDTO.setUpdateTime(LocalDateTime.now());
                userTaskUpdateDTO.setTaskAssigneeUserId(approveId);
                userTaskUpdateDTO.setTaskAssigneeRealName(approveName);
                userTaskUpdateDTO.setTaskStatus(TaskConstant.TASK_COMPLETED);
                userTaskUpdateDTO.setApproveTime(LocalDateTime.now());

                Result<Boolean> result2 = updateUserTask(userTaskUpdateDTO);
                if (result2.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.responseError(result2.getEntityError());
                }
                historyService.insertHistory(userTaskDTO.getApplyId(), tenantId, userTaskDTO.getTaskId(),
                        userTaskDTO.getTaskName(), approveId, approveName, approveActionCode, approveOpinion, systemCode, platform);

                System.out.println("result.getData() = " + result.getData());
                platformTransactionManager.commit(transactionStatus);
            } catch (Exception e) {
                log.error("异常:{}", e);
                platformTransactionManager.rollback(transactionStatus);
                return Result.responseError(EntityError.SYSTEM_ERROR);
            }

            return Result.responseSuccess(Boolean.TRUE);
        }
    }
}
