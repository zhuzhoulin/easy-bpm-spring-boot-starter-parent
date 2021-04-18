package com.pig.easy.bpm.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.api.core.event.BpmEvent;
import com.pig.easy.bpm.api.core.event.BpmEventDispatcher;
import com.pig.easy.bpm.api.core.event.BpmEventType;
import com.pig.easy.bpm.api.core.event.BpmSourceDTO;
import com.pig.easy.bpm.api.dto.request.*;
import com.pig.easy.bpm.api.dto.response.*;
import com.pig.easy.bpm.api.entity.ApplyDO;
import com.pig.easy.bpm.api.mapper.ApplyMapper;
import com.pig.easy.bpm.api.service.*;
import com.pig.easy.bpm.auth.dto.response.DeptDTO;
import com.pig.easy.bpm.auth.dto.response.FileTemplateDTO;
import com.pig.easy.bpm.auth.dto.response.UserDTO;
import com.pig.easy.bpm.auth.service.*;
import com.pig.easy.bpm.common.constant.BpmConstant;
import com.pig.easy.bpm.common.constant.TaskConstant;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.execption.BpmException;
import com.pig.easy.bpm.common.generator.BaseServiceImpl;
import com.pig.easy.bpm.common.utils.BeanUtils;
import com.pig.easy.bpm.common.utils.CommonUtils;
import com.pig.easy.bpm.common.utils.EasyBpmAsset;
import com.pig.easy.bpm.common.utils.Result;
import com.pig.easy.bpm.common.utils.spring.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * <p>
 * 申请表 服务实现类
 * </p>
 *
 * @author pig
 * @since 2020-05-20
 */
@Service
@Slf4j
public class ApplyServiceImpl extends BaseServiceImpl<ApplyMapper, ApplyDO> implements ApplyService {

    @Autowired
    ConfigService configService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    ProcessService processService;
    @Autowired
    UserService userService;
    @Autowired
    ProcessDetailService processDetailService;
    @Autowired
    DeptService deptService;
    @Autowired
    CompanyService companyService;
    @Autowired
    UserTaskService userTaskService;
    @Autowired
    ApplyMapper applyMapper;
    @Autowired
    FormService formService;
    @Autowired
    FormDataService formDataService;
    @Autowired
    NodeService nodeService;
    @Autowired
    TaskService taskService;
    @Autowired
    HistoryService historyService;
    @Autowired
    NodeUserService nodeUserService;
    @Autowired
    FileTemplateService fileTempleteService;


    @Override
    public void beforeStartProcess(ApplyReqDTO applyReqDTO) {

        BpmEventDispatcher bpmEventDispatcher = SpringUtils.getBean(BpmEventDispatcher.class);
        BpmEvent bestBpmEvent = new BpmEvent(new BpmSourceDTO(BpmEventType.BEFOR_START_PROCESS, applyReqDTO,applyReqDTO.getClass()));
        bpmEventDispatcher.dispatchEvent(bestBpmEvent);
    }


    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public Result<Boolean> startProcess(ApplyReqDTO applyReqDTO) {

        if (applyReqDTO == null
                || StringUtils.isEmpty(applyReqDTO.getTenantId())
                || StringUtils.isEmpty(applyReqDTO.getProcessKey())
                || CommonUtils.evalLong(applyReqDTO.getStartUserId()) < 0) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        if (StringUtils.isEmpty(applyReqDTO.getSystem())) {
            Result<Object> configValue = configService.getConfigValue(applyReqDTO.getTenantId(), BpmConstant.SYSTEM);
            applyReqDTO.setSystem(configValue.getData().toString());
        }
        if (StringUtils.isEmpty(applyReqDTO.getCurrentPlatform())) {
            Result<Object> configValue = configService.getConfigValue(applyReqDTO.getTenantId(), BpmConstant.PLATFORM);
            applyReqDTO.setCurrentPlatform(configValue.getData().toString());
        }

        Result<ProcessInfoDTO> result = processService.getProcessWithDetailByProcessKey(applyReqDTO.getProcessKey());
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result.getEntityError());
        }
        ProcessInfoDTO processInfo = result.getData();

        Result<UserDTO> result2 = userService.getUserInfoById(applyReqDTO.getStartUserId());
        if (result2.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result2.getEntityError());
        }
        UserDTO applyUserInfo = result2.getData();
        // 校验数据
        if (StringUtils.isNotEmpty(applyReqDTO.getFormKey())) {
            Result<FormDTO> result3 = formService.getFormByFormKey(applyReqDTO.getFormKey());
            if (result3.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                return Result.responseError(result3.getEntityError());
            }
        }
        Result<DeptDTO> result3 = deptService.getDeptById(applyUserInfo.getDeptId());
        if (result3.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result3.getEntityError());
        }
        DeptDTO applyDept = result3.getData();

        beforeStartProcess(applyReqDTO);

        Map<String, Object> businessDataMap = applyReqDTO.getData();
        if (businessDataMap == null) {
            businessDataMap = new HashMap<>();
        }

        businessDataMap.put(BpmConstant.SYSTEM, applyReqDTO.getSystem());
        businessDataMap.put(BpmConstant.PLATFORM, applyReqDTO.getCurrentPlatform());
        businessDataMap.put(BpmConstant.APPLY_ID, CommonUtils.evalInt(applyReqDTO.getApplyId(), 0));
        businessDataMap.put(BpmConstant.TENANT_ID, applyReqDTO.getTenantId());
        businessDataMap.put(BpmConstant.APPLY_USER_INFO, applyUserInfo);
        businessDataMap.put(BpmConstant.APPLY_COMPANY_ID, applyUserInfo.getCompanyId());
        businessDataMap.put(BpmConstant.APPLY_ROLE_ID, applyUserInfo.getRoleList().get(0).getId());
        businessDataMap.put(BpmConstant.APPLY_ROLE_NAME, applyUserInfo.getRoleList().get(0).getName());
        businessDataMap.put(BpmConstant.APPLY_ROLE_CODE, applyUserInfo.getRoleList().get(0).getCode());
        businessDataMap.put(BpmConstant.BUSINESS_LINE, applyDept.getBusinessLine());
        businessDataMap.put(BpmConstant.APPLY_DEPT, applyDept);

        boolean firstSubmit;
        ApplyDO applyDO = new ApplyDO();
        UserTaskUpdateDTO userTaskUpdateDTO = null;
        UserTaskUpdateDTO firstUserTask = null;
        String applySn = null;
        if (CommonUtils.evalLong(applyReqDTO.getApplyId()) > 0) {
            applyDO = applyMapper.selectById(applyReqDTO.getApplyId());
            if (applyDO == null) {
                log.error("startProcess applyDO is null ");
                throw BpmException.builder().entityError(EntityError.DATA_NOT_FOUND_ERROR).build();
            }
            applySn = applyDO.getApplySn();
            if (applyDO.getApplyStatus() != BpmConstant.APPLY_STATUS_DRAFT) {
                return Result.responseError(EntityError.SUBMIT_REPET_APPLY_ERROR);
            }
        }


        if (StringUtils.isEmpty(applyDO.getProcInstId())) {

            firstSubmit = true;
            applyDO = gengrateApply(applyUserInfo, processInfo, applyReqDTO, "");

            if (CommonUtils.evalLong(applyReqDTO.getApplyId()) > 0) {
                applyDO.setApplyId(applyReqDTO.getApplyId());
                applyMapper.updateById(applyDO);
            } else {
                applyMapper.insert(applyDO);
            }
            applyDO.setApplyStatus(BpmConstant.APPLY_STATUS_APPROVING);

            businessDataMap.put(BpmConstant.APPLY_ID, applyDO.getApplyId());
            // 第一次提交
            Authentication.setAuthenticatedUserId(String.valueOf(applyReqDTO.getStartUserId()));
            /* 以Builder 方式启动流程，解决 无 租户 和 Id 的启动方式 使用租户的前提时，部署时 也需要设置 租户编号！ */
            ProcessInstance processInstance = runtimeService.createProcessInstanceBuilder()
                    .tenantId(applyReqDTO.getTenantId())
                    .processDefinitionId(processInfo.getDefinitionId())
                    .variables(businessDataMap)
                    .start();
            /* 防止多线程的时候出问题 */
            Authentication.setAuthenticatedUserId(null);
            applyDO.setProcInstId(processInstance.getProcessInstanceId());
            applyMapper.updateById(applyDO);
        } else {
            firstSubmit = false;
            // 非第一次提交
            ApplyDO tempApply = gengrateApply(applyUserInfo, processInfo, applyReqDTO, applyDO.getProcInstId());
            tempApply.setApplyId(applyDO.getApplyId());
            tempApply.setApplySn(applyDO.getApplySn());
            tempApply.setApplyStatus(BpmConstant.APPLY_STATUS_APPROVING);
            applyMapper.updateById(tempApply);
        }

        // find firstUserTask
        Result<List<UserTaskDTO>> result1 = userTaskService.getUserTaskByProcInstId(applyDO.getProcInstId());
        if (result1.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            log.error("startProcess error ,message {}", result1.getEntityError());
            throw BpmException.builder().entityError(result1.getEntityError()).build();
        }

        for (UserTaskDTO userTaskDTO : result1.getData()) {
            userTaskUpdateDTO = BeanUtils.switchToDTO(userTaskDTO, UserTaskUpdateDTO.class);
            if (BpmConstant.TASK_TYPE_START.equals(userTaskUpdateDTO.getTaskType())
                    && userTaskUpdateDTO.getTaskStatus() < TaskConstant.TASK_COMPLETED) {
                firstUserTask = userTaskUpdateDTO;
            }
        }

        businessDataMap.put(BpmConstant.APPLY_SN, applyDO.getApplySn());

        // 保存数据
        Result<Integer> result4 = formDataService.batchSaveOrUpdateFormData(applyDO.getApplyId(),
                firstUserTask != null ? firstUserTask.getTaskId() : null, applyReqDTO.getTenantId(), applyReqDTO.getFormKey(), businessDataMap);
        if (result4.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result4.getEntityError());
        }

        /* 自动完成首个任务*/
        if (BpmConstant.AUTO_COMPLETE_FIRST_NODE.equals(processInfo.getAutoCompleteFirstNode())
                && firstUserTask != null) {
            taskService.complete(firstUserTask.getActTaskId());
            firstUserTask.setTaskStatus(TaskConstant.TASK_COMPLETED);
            firstUserTask.setApproveTime(LocalDateTime.now());

            userTaskService.updateUserTask(firstUserTask);
        }
        /* 记录审批历史 */
        historyService.insertHistory(applyDO.getApplyId(), applyReqDTO.getTenantId(), firstUserTask != null ? firstUserTask.getTaskId() : null,
                firstUserTask != null ? firstUserTask.getTaskName() : null, applyUserInfo.getUserId(), applyUserInfo.getRealName(),
                BpmConstant.TASK_TYPE_START, BpmConstant.HISTORY_START,applyReqDTO.getSystem(),applyReqDTO.getCurrentPlatform());


        // 演算审批链
        if (firstUserTask != null) {
            Result<List<FlowUserTaskDTO>> result5 = nodeService.getNextNodeList(processInfo.getDefinitionId(), null, businessDataMap, true);
            if (result5.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.responseError(result5.getEntityError());
            }
            List<NodeUserSaveOrUpdateDTO> nodeUserSaveList = new ArrayList<>();
            NodeUserSaveOrUpdateDTO nodeUserSaveOrUpdateDTO = null;
            for (FlowUserTaskDTO flowUserTaskDTO : result5.getData()) {
                nodeUserSaveOrUpdateDTO = new NodeUserSaveOrUpdateDTO();
                nodeUserSaveOrUpdateDTO.setApplyId(applyDO.getApplyId());
                nodeUserSaveOrUpdateDTO.setProcessId(applyDO.getProcessId());
                nodeUserSaveOrUpdateDTO.setProcessKey(applyDO.getProcessKey());
                nodeUserSaveOrUpdateDTO.setProcInstId(applyDO.getProcInstId());
                nodeUserSaveOrUpdateDTO.setNodeId(flowUserTaskDTO.getNodeId());
                nodeUserSaveOrUpdateDTO.setNodeName(flowUserTaskDTO.getNodeName());
                nodeUserSaveOrUpdateDTO.setParentNodeId(flowUserTaskDTO.getParentNodeId());
                nodeUserSaveOrUpdateDTO.setParentNodeName(flowUserTaskDTO.getParentNodeName());
                nodeUserSaveOrUpdateDTO.setTenantId(applyDO.getTenantId());
                nodeUserSaveOrUpdateDTO.setOperatorId(applyDO.getOperatorId());
                nodeUserSaveOrUpdateDTO.setOperatorName(applyDO.getOperatorName());
                nodeUserSaveOrUpdateDTO.setDefinitionId(applyDO.getDefinitionId());
                nodeUserSaveOrUpdateDTO.setAssigneeUserIdList(String.join(",", flowUserTaskDTO.getOwnerUserIds()));
                nodeUserSaveOrUpdateDTO.setAssigneeUserNameList(String.join(",", flowUserTaskDTO.getOwnerNames()));
                nodeUserSaveOrUpdateDTO.setError(flowUserTaskDTO.isError() ? 1 : 0);
                nodeUserSaveOrUpdateDTO.setRemarks("");
                nodeUserSaveOrUpdateDTO.setDefaultSetAdmin(flowUserTaskDTO.isDefaultSetAdmin() ? 1 : 0);
                nodeUserSaveOrUpdateDTO.setSkip(flowUserTaskDTO.isSkip() ? 1 : 0);
                nodeUserSaveList.add(nodeUserSaveOrUpdateDTO);
            }
            Result<Integer> result6 = nodeUserService.batchSave(nodeUserSaveList);
            if (result6.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.responseError(result6.getEntityError());
            }
        }

        return Result.responseSuccess(true);
    }

    @Override
    public void afterStartProcess(ApplyReqDTO applyReqDTO) {
        BpmEventDispatcher bpmEventDispatcher = SpringUtils.getBean(BpmEventDispatcher.class);
        BpmEvent bestBpmEvent = new BpmEvent(new BpmSourceDTO(BpmEventType.AFTER_START_PROCESS, applyReqDTO,applyReqDTO.getClass()));
        bpmEventDispatcher.dispatchEvent(bestBpmEvent);
    }

    private ApplyDO gengrateApply(UserDTO applyUserInfo, ProcessInfoDTO processDTO, ApplyReqDTO applyReqDTO, String processInstanceId) {

        /* 单据编号默认规则 流程简称 + 用户工号 + 当前时间mmssSSSS  */
        StringBuffer applySn = new StringBuffer();
        Result<String> stringResult = generateApplySn(processDTO.getProcessAbbr() + applyUserInfo.getUserId());
        if (stringResult.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            applySn.append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("mmssSSSS")));
        } else {
            applySn.append(stringResult.getData());
        }

//        Result<CompanyDTO> result = companyService.getCompanyById(applyUserInfo.getCompanyId());
//        CompanyDTO companyDTO = new CompanyDTO();
//        if(result.getEntityError().getCode() != EntityError.SUCCESS.getCode()){
//            log.error(result.getEntityError().toString());
//        } else {
//            companyDTO = result.getData();
//        }

        return ApplyDO.builder()
                .tenantId(applyReqDTO.getTenantId())
                .system(applyReqDTO.getSystem())
                .platform(applyReqDTO.getCurrentPlatform())
                .applyCompanyId(applyUserInfo.getCompanyId())
                .applyCompanyCode(applyUserInfo.getCompanyCode())
                .applyCompanyName(applyUserInfo.getCompanyName())
                .applyDeptId(applyUserInfo.getDeptId())
                .applyDeptCode(applyUserInfo.getDeptCode())
                .applyDeptName(applyUserInfo.getDeptName())
                .applyUserId(applyUserInfo.getUserId())
                .procInstId(processInstanceId)
                .formKey(!StringUtils.isEmpty(applyReqDTO.getFormKey()) ? applyReqDTO.getFormKey() : "")
                .processId(processDTO.getProcessId())
                .processKey(processDTO.getProcessKey())
                .processName(processDTO.getProcessName())
                .definitionId(processDTO.getDefinitionId())
                .applyRealName(applyUserInfo.getRealName())
                .operatorId(applyReqDTO.getCreateUserId())
                .operatorName(applyReqDTO.getCreaterRealName())
                .applySn(applySn.toString())
                .applyTitle(generateApplyTitle(processDTO, applyUserInfo.getRealName(), applyUserInfo.getDeptName()))
                .createTime(LocalDateTime.now())
                .build();
    }

    /**
     * 功能描述:  统一获取 申请标题
     *
     * @param processInfo   流程基本信息
     * @param applyRealName 申请人姓名
     * @param deptName      部门姓名
     * @return : java.lang.String
     * @author : pig
     * @date : 2020/6/8 17:38
     */
    private String generateApplyTitle(ProcessInfoDTO processInfo, String applyRealName, String deptName) {

        if (!org.apache.commons.lang.StringUtils.isEmpty(processInfo.getApplyTitleRule())) {
            return processInfo.getApplyTitleRule().trim()
                    .replaceAll("\\$", "")
                    .replaceAll("\\{applyRealName\\}", applyRealName)
                    .replaceAll("\\{processName\\}", processInfo.getProcessName())
                    .replaceAll("\\{startDate\\}", CommonUtils.formatNow("yyyy-MM-dd"))
                    .replaceAll("\\{deptName\\}", deptName);
        } else {
            return new StringBuffer()
                    .append(deptName)
                    .append(applyRealName)
                    .append("在")
                    .append(CommonUtils.formatNow("yyyy-MM-dd"))
                    .append("发起")
                    .append(processInfo.getProcessName()).toString();
        }
    }


    @Override
    public Result<String> generateApplySn(String applySnPrefix) {

        if (applySnPrefix == null) {
            applySnPrefix = "";
        }
        return Result.responseSuccess(applySnPrefix + LocalDateTime.now().format(DateTimeFormatter.ofPattern("mmssSSSS")));
    }

    @Override
    public Result<ApplyDTO> updateApplyByApplyId(ApplyUpdateDTO applyUpdateDTO) {

        EasyBpmAsset.isAssetEmpty(applyUpdateDTO);
        EasyBpmAsset.isAssetEmpty(applyUpdateDTO.getApplyId());

        ApplyDO applyDO = BeanUtils.switchToDO(applyUpdateDTO, ApplyDO.class);
        int i = applyMapper.updateById(applyDO);
        ApplyDTO applyDTO = BeanUtils.switchToDTO(applyDO, ApplyDTO.class);
        return Result.responseSuccess(applyDTO);
    }

    @Override
    public Result<ApplyDTO> updateApplyByProcInstId(ApplyUpdateDTO applyUpdateDTO) {

        EasyBpmAsset.isAssetEmpty(applyUpdateDTO);
        EasyBpmAsset.isAssetEmpty(applyUpdateDTO.getProcInstId());
        Result<ApplyDTO> result = getApplyByProcInstId(applyUpdateDTO.getProcInstId());
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result.getEntityError());
        }
        ApplyDO applyDO = BeanUtils.switchToDO(applyUpdateDTO, ApplyDO.class);
        applyDO.setApplyId(result.getData().getApplyId());
        int i = applyMapper.updateById(applyDO);
        ApplyDTO applyDTO = BeanUtils.switchToDTO(applyDO, ApplyDTO.class);
        return Result.responseSuccess(applyDTO);
    }

    @Override
    public Result<ApplyDTO> getApplyByApplyId(Long applyId) {

        EasyBpmAsset.isAssetEmpty(applyId);
        ApplyDO applyDO = applyMapper.selectById(applyId);
        if (applyDO == null) {
            return Result.responseError(EntityError.DATA_NOT_FOUND_ERROR);
        }
        ApplyDTO applyDTO = BeanUtils.switchToDTO(applyDO, ApplyDTO.class);
        return Result.responseSuccess(applyDTO);
    }

    @Override
    public Result<ApplyDTO> getApplyByProcInstId(String procInstId) {

        EasyBpmAsset.isAssetEmpty(procInstId);
        ApplyDO applyDO = applyMapper.selectOne(new QueryWrapper<>(ApplyDO.builder().procInstId(procInstId).build()));
        if (applyDO == null) {
            return Result.responseError(EntityError.DATA_NOT_FOUND_ERROR);
        }
        ApplyDTO applyDTO = BeanUtils.switchToDTO(applyDO, ApplyDTO.class);
        return Result.responseSuccess(applyDTO);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public Result<Long> saveDraftApply(ApplyReqDTO applyReqDTO) {

        if (applyReqDTO == null
                || StringUtils.isEmpty(applyReqDTO.getTenantId())
                || StringUtils.isEmpty(applyReqDTO.getProcessKey())
                || CommonUtils.evalLong(applyReqDTO.getStartUserId()) < 0) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        if (StringUtils.isEmpty(applyReqDTO.getSystem())) {
            Result<Object> configValue = configService.getConfigValue(applyReqDTO.getTenantId(), BpmConstant.SYSTEM);
            applyReqDTO.setSystem(configValue.getData().toString());
        }
        if (StringUtils.isEmpty(applyReqDTO.getCurrentPlatform())) {
            Result<Object> configValue = configService.getConfigValue(applyReqDTO.getTenantId(), BpmConstant.PLATFORM);
            applyReqDTO.setCurrentPlatform(configValue.getData().toString());
        }

        Result<ProcessInfoDTO> result = processService.getProcessWithDetailByProcessKey(applyReqDTO.getProcessKey());
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result.getEntityError());
        }
        ProcessInfoDTO processInfo = result.getData();

        Result<UserDTO> result2 = userService.getUserInfoById(applyReqDTO.getStartUserId());
        if (result2.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result2.getEntityError());
        }
        UserDTO applyUserInfo = result2.getData();
        // 校验数据
        if (StringUtils.isNotEmpty(applyReqDTO.getFormKey())) {
            Result<FormDTO> result3 = formService.getFormByFormKey(applyReqDTO.getFormKey());
            if (result3.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                return Result.responseError(result3.getEntityError());
            }
        }
        Result<DeptDTO> result3 = deptService.getDeptById(applyUserInfo.getDeptId());
        if (result3.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result3.getEntityError());
        }
        DeptDTO applyDept = result3.getData();

        Map<String, Object> businessDataMap = applyReqDTO.getData();
        if (businessDataMap == null) {
            businessDataMap = new HashMap<>();
        }

        businessDataMap.put(BpmConstant.SYSTEM, applyReqDTO.getSystem());
        businessDataMap.put(BpmConstant.PLATFORM, applyReqDTO.getCurrentPlatform());
        businessDataMap.put(BpmConstant.APPLY_ID, CommonUtils.evalInt(applyReqDTO.getApplyId(), 0));
        businessDataMap.put(BpmConstant.TENANT_ID, applyReqDTO.getTenantId());
        businessDataMap.put(BpmConstant.APPLY_USER_INFO, applyUserInfo);
        businessDataMap.put(BpmConstant.APPLY_COMPANY_ID, applyUserInfo.getCompanyId());
        businessDataMap.put(BpmConstant.APPLY_ROLE_ID, applyUserInfo.getRoleList().get(0).getId());
        businessDataMap.put(BpmConstant.APPLY_ROLE_NAME, applyUserInfo.getRoleList().get(0).getName());
        businessDataMap.put(BpmConstant.APPLY_ROLE_CODE, applyUserInfo.getRoleList().get(0).getCode());
        businessDataMap.put(BpmConstant.BUSINESS_LINE, applyDept.getBusinessLine());
        businessDataMap.put(BpmConstant.APPLY_DEPT, applyDept);
        businessDataMap.put(BpmConstant.APPLY_ROLE_NAME, applyUserInfo.getRoleList().get(0).getName());


        ApplyDO applyDO = null;
        boolean firstSubmit;
        if (CommonUtils.evalLong(applyReqDTO.getApplyId()) <= 0) {
            firstSubmit = true;
            applyDO = gengrateApply(applyUserInfo, processInfo, applyReqDTO, null);
            int applyId = applyMapper.insert(applyDO);
            businessDataMap.put(BpmConstant.APPLY_ID, applyId);

        } else {
            firstSubmit = false;
            // 非第一次提交
            applyDO = applyMapper.selectById(applyReqDTO.getApplyId());
            if (applyDO == null) {
                log.error("startProcess applyDO is null ");
                throw BpmException.builder().entityError(EntityError.DATA_NOT_FOUND_ERROR).build();
            }
            ApplyDO tempApply = gengrateApply(applyUserInfo, processInfo, applyReqDTO, applyDO.getProcInstId());
            tempApply.setApplyId(applyDO.getApplyId());
            tempApply.setApplySn(applyDO.getApplySn());
            applyMapper.updateById(tempApply);
        }

        businessDataMap.put(BpmConstant.APPLY_SN, applyDO.getApplySn());

        // 保存数据
        Result<Integer> result1 = formDataService.batchSaveOrUpdateFormData(applyDO.getApplyId(),
                null, applyReqDTO.getTenantId(), applyReqDTO.getFormKey(), businessDataMap);
        if (result1.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result1.getEntityError());
        }

        return Result.responseSuccess(applyDO.getApplyId());
    }

    @Override
    public Result<ApproveTaskDTO> getApproveDetail(Long taskId) {

        EasyBpmAsset.isAssetEmpty(taskId);
        ApproveTaskDTO approveTaskDTO = new ApproveTaskDTO();
        Result<UserTaskDTO> result = userTaskService.getUserTaskByTaskId(taskId);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result.getEntityError());
        }
        UserTaskDTO userTaskDTO = result.getData();
        approveTaskDTO.setUserTaskInfo(userTaskDTO);

        Result<ApplyDTO> result1 = getApplyByApplyId(userTaskDTO.getApplyId());
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result.getEntityError());
        }
        ApplyDTO applyDTO = result1.getData();
        approveTaskDTO.setApplyInfo(applyDTO);

        Result<UserDTO> result2 = userService.getUserInfoById(applyDTO.getApplyUserId());
        if (result2.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result2.getEntityError());
        }
        approveTaskDTO.setApplyUserInfo(result2.getData());

        Result<Map<String, Object>> result3 = formDataService.getFormDataByApplyId(userTaskDTO.getApplyId());
        if (result3.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result3.getEntityError());
        }
        approveTaskDTO.setBusinessData(result3.getData());

        Result<DynamicFormDataDTO> result4 = formService.getInitForm(StringUtils.isEmpty(userTaskDTO.getFormKey()) ? applyDTO.getFormKey() : userTaskDTO.getFormKey());
        if (result4.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result4.getEntityError());
        }
        approveTaskDTO.setDynamicFormData(result4.getData());

        NodeQueryDTO nodeInfoReqDTO = new NodeQueryDTO();
        nodeInfoReqDTO.setDefinitionId(applyDTO.getDefinitionId());
        nodeInfoReqDTO.setNodeId(userTaskDTO.getTaskNodeCode());
        nodeInfoReqDTO.setValidState(VALID_STATE);
        Result<List<NodeDTO>> result5 = nodeService.getListByCondition(nodeInfoReqDTO);
        if (result1.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result1.getEntityError());
        }

        NodeDTO nodeInfoDTO = result5.getData().get(0);
        approveTaskDTO.setNodeInfo(nodeInfoDTO);

        Result<List<HistoryDTO>> result6 = historyService.getListByApplyId(userTaskDTO.getApplyId());
        if (result6.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result6.getEntityError());
        }
        approveTaskDTO.setHistoryList(result6.getData());

        NodeUserQueryDTO nodeUserQueryDTO = new NodeUserQueryDTO();
        nodeUserQueryDTO.setApplyId(applyDTO.getApplyId());
        nodeUserQueryDTO.setParentNodeId(nodeInfoDTO.getNodeId());
        nodeUserQueryDTO.setValidState(VALID_STATE);
        Result<List<NodeUserDTO>> result7 = nodeUserService.getListByCondition(nodeUserQueryDTO);
        if (result7.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result7.getEntityError());
        }
        approveTaskDTO.setSelectNodeUsers(result7.getData());

        Result<List<FileTemplateDTO>> result8 = fileTempleteService.getListByProcessIdAndTenantId(applyDTO.getTenantId(), applyDTO.getProcessId());
        if (result8.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result8.getEntityError());
        }
        approveTaskDTO.setFileTempleteList(result8.getData());

        return Result.responseSuccess(approveTaskDTO);
    }

    @Override
    public Result<ApproveTaskDTO> getApplyDetail(Long applyId) {

        EasyBpmAsset.isAssetEmpty(applyId);

        ApproveTaskDTO approveTaskDTO = new ApproveTaskDTO();
        Result<ApplyDTO> result = getApplyByApplyId(applyId);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result.getEntityError());
        }
        ApplyDTO applyDTO = result.getData();
        approveTaskDTO.setApplyInfo(applyDTO);

        Result<UserDTO> result1 = userService.getUserInfoById(applyDTO.getApplyUserId());
        if (result1.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result1.getEntityError());
        }
        approveTaskDTO.setApplyUserInfo(result1.getData());

        Result<Map<String, Object>> result2 = formDataService.getFormDataByApplyId(applyId);
        if (result2.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result2.getEntityError());
        }
        approveTaskDTO.setBusinessData(result2.getData());

        Result<DynamicFormDataDTO> result3 = formService.getInitForm(applyDTO.getFormKey());
        if (result3.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result3.getEntityError());
        }
        approveTaskDTO.setDynamicFormData(result3.getData());

        NodeQueryDTO nodeInfoReqDTO = new NodeQueryDTO();
        nodeInfoReqDTO.setDefinitionId(applyDTO.getDefinitionId());
        nodeInfoReqDTO.setTaskType(BpmConstant.TASK_TYPE_START);
        nodeInfoReqDTO.setNodeType(BpmConstant.NODE_TYPE_USER_TASK);
        nodeInfoReqDTO.setValidState(VALID_STATE);
        Result<List<NodeDTO>> result4 = nodeService.getListByCondition(nodeInfoReqDTO);
        if (result1.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result1.getEntityError());
        }

        NodeDTO nodeInfoDTO = result4.getData().get(0);
        approveTaskDTO.setNodeInfo(nodeInfoDTO);

        UserTaskReqDTO userTaskReqDTO = new UserTaskReqDTO();
        userTaskReqDTO.setTenantId(applyDTO.getTenantId());
        userTaskReqDTO.setValidState(VALID_STATE);
        userTaskReqDTO.setTaskAssigneeUserId(applyDTO.getApplyUserId());
        userTaskReqDTO.setTaskNodeCode(nodeInfoDTO.getNodeId());
        userTaskReqDTO.setTaskStatusList(new ArrayList<>(Arrays.asList(1, 2)));
        Result<PageInfo<UserTaskDTO>> result5 = userTaskService.getListByCondition(userTaskReqDTO);
        if (result5.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result5.getEntityError());
        }
        if (result5.getData().getList().size() > 0) {
            approveTaskDTO.setUserTaskInfo(result5.getData().getList().get(0));
        } else {
            approveTaskDTO.setUserTaskInfo(new UserTaskDTO());
        }
        Result<List<HistoryDTO>> result6 = historyService.getListByApplyId(applyId);
        if (result6.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result6.getEntityError());
        }
        approveTaskDTO.setHistoryList(result6.getData());

        Result<List<FileTemplateDTO>> result7 = fileTempleteService.getListByProcessIdAndTenantId(applyDTO.getTenantId(), applyDTO.getProcessId());
        if (result7.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result7.getEntityError());
        }
        approveTaskDTO.setFileTempleteList(result7.getData());
        return Result.responseSuccess(approveTaskDTO);
    }

    @Override
    public Result<List<ApplyDTO>> getChildrenListByApplyId(Long applyId) {

        if (CommonUtils.evalLong(applyId) <= 0) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        ApplyQueryDTO applyQueryDTO = new ApplyQueryDTO();
        applyQueryDTO.setValidState(VALID_STATE);
        applyQueryDTO.setParentApplyId(applyId);
        Result<List<ApplyDTO>> result = getListByCondition(applyQueryDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result.getEntityError());
        }
        return Result.responseSuccess(result.getData());
    }

    @Override
    public Result<List<ApplyDTO>> getListByCondition(ApplyQueryDTO applyQueryDTO) {

        if (applyQueryDTO == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        int pageIndex = CommonUtils.evalInt(applyQueryDTO.getPageIndex(), DEFAULT_PAGE_INDEX);
        int pageSize = CommonUtils.evalInt(applyQueryDTO.getPageSize(), DEFAULT_PAGE_SIZE);

        PageHelper.startPage(pageIndex, pageSize);
        List<ApplyDTO> list = applyMapper.getListByCondition(applyQueryDTO);
        if (list == null) {
            list = new ArrayList<>();
        }
        return Result.responseSuccess(list);
    }

    @Override
    public Result<ChartDTO> getApplyLineChart(LineChartQueryDTO lineChartQueryDTO) {

        if (lineChartQueryDTO == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        ChartDTO chartDTO = new ChartDTO();
        chartDTO.setTitleText("流程引擎活动实例数据");

        List<LineCharDTO> list = applyMapper.getApplyLineChart(lineChartQueryDTO);
        if (list == null) {
            list = new ArrayList<>();
        }
        List<Object> seriesData = new ArrayList<>();
        List<String> legendDataList = new ArrayList<>();
        List<String> xAxisDataList = new ArrayList<>();

        Integer maxNum = 0;
        for (LineCharDTO applyLineChartDTO : list) {
            if (!xAxisDataList.contains(applyLineChartDTO.getApplyMonth())) {
                xAxisDataList.add(applyLineChartDTO.getApplyMonth());
            }
            if (!legendDataList.contains(applyLineChartDTO.getProcessName())) {
                legendDataList.add(applyLineChartDTO.getProcessName());
            }
            if (applyLineChartDTO.getNum() > maxNum) {
                maxNum = applyLineChartDTO.getNum();
            }
        }
        chartDTO.setLegendDataList(legendDataList);
        chartDTO.setYAxisMax(maxNum);
        chartDTO.setXAxisDataList(xAxisDataList);

        StringBuffer stringBuffer = new StringBuffer();
        if (xAxisDataList.size() > 0) {
            stringBuffer.append(xAxisDataList.get(0)).append(" - ").append(xAxisDataList.get(xAxisDataList.size() - 1));
        }
        chartDTO.setSubtext(stringBuffer.toString());

        List<Integer> data = new ArrayList<>();
        Integer data1 = 0;
        for (String legendData : legendDataList) {
            data = new ArrayList<>();
            for (String xAxis : xAxisDataList) {
                data1 = 0;
                for (LineCharDTO lineCharDTO : list) {
                    if (xAxis.equals(lineCharDTO.getApplyMonth())
                            && legendData.equals(lineCharDTO.getProcessName())) {
                        data1 = lineCharDTO.getNum();
                    }
                }
                data.add(data1);
            }
            seriesData.add(data);
        }
        chartDTO.setDataList(seriesData);

        return Result.responseSuccess(chartDTO);
    }
}
