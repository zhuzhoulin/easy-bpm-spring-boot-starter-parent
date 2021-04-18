package com.pig.easy.bpm.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.api.dto.request.NodeQueryDTO;
import com.pig.easy.bpm.api.dto.request.ProcessDetailSaveOrUpdateDTO;
import com.pig.easy.bpm.api.dto.request.ProcessQueryDTO;
import com.pig.easy.bpm.api.dto.request.ProcessSaveOrUpdateDTO;
import com.pig.easy.bpm.api.dto.response.*;
import com.pig.easy.bpm.api.entity.ProcessDO;
import com.pig.easy.bpm.api.mapper.ProcessMapper;
import com.pig.easy.bpm.api.service.FormService;
import com.pig.easy.bpm.api.service.NodeService;
import com.pig.easy.bpm.api.service.ProcessDetailService;
import com.pig.easy.bpm.api.service.ProcessService;
import com.pig.easy.bpm.auth.dto.response.ItemDTO;
import com.pig.easy.bpm.auth.dto.response.TreeItemDTO;
import com.pig.easy.bpm.auth.service.DictItemService;
import com.pig.easy.bpm.common.constant.BpmConstant;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.generator.BaseServiceImpl;
import com.pig.easy.bpm.common.utils.BeanUtils;
import com.pig.easy.bpm.common.utils.CommonUtils;
import com.pig.easy.bpm.common.utils.EasyBpmAsset;
import com.pig.easy.bpm.common.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 流程表 服务实现类
 * </p>
 *
 * @author pig
 * @since 2021-04-09
 */
@Service
public class ProcessServiceImpl extends BaseServiceImpl<ProcessMapper, ProcessDO> implements ProcessService {

    @Autowired
    ProcessMapper mapper;
    @Autowired
    @Lazy
    ProcessDetailService processDetailService;
    @Autowired
    NodeService nodeService;
    @Autowired
    FormService formService;
    @Autowired
    DictItemService dictItemService;

    @Override
    public Result<PageInfo<ProcessInfoDTO>> getListPageByCondition(ProcessQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        int pageIndex = CommonUtils.evalInt(param.getPageIndex(), DEFAULT_PAGE_INDEX);
        int pageSize = CommonUtils.evalInt(param.getPageSize(), DEFAULT_PAGE_SIZE);

        PageHelper.startPage(pageIndex, pageSize);
        param.setValidState(VALID_STATE);
        List<ProcessInfoDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        PageInfo<ProcessInfoDTO> pageInfo = new PageInfo<>(list);
        return Result.responseSuccess(pageInfo);
    }

    @Override
    public Result<List<ProcessInfoDTO>> getListByCondition(ProcessQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        param.setValidState(VALID_STATE);
        List<ProcessInfoDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        return Result.responseSuccess(list);
    }

    @Cacheable(value = "processWithDetail", key = "#p0", unless = "#result == null || #result.entityError == null || #result.entityError.code != 200")
    @Override
    public Result<ProcessInfoDTO> getProcessWithDetailByProcessKey(String processKey) {

        Result<ProcessDTO> result = getProcessByProcessKey(processKey);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result.getEntityError());
        }
        /* 加上一个 流程详细信息 */
        ProcessInfoDTO processInfoDTO = BeanUtils.switchToDTO(result.getData(), ProcessInfoDTO.class);
        Result<ProcessDetailDTO> result2 = processDetailService.getProcessDetailByProcessId(processInfoDTO.getProcessId());
        if (result2.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result2.getEntityError());
        }
        org.springframework.beans.BeanUtils.copyProperties(result2.getData(), processInfoDTO);

        return Result.responseSuccess(processInfoDTO);
    }

    @Cacheable(value = "processDetail", key = "#p0", unless = "#result == null || #result.entityError == null || #result.entityError.code != 200")
    @Override
    public Result<ProcessDTO> getProcessByProcessKey(String processKey) {

        EasyBpmAsset.isAssetEmptyByDefault(processKey);
        ProcessDO process = new ProcessDO();
        process.setProcessKey(processKey);
        process.setValidState(VALID_STATE);
        process = mapper.selectOne(new QueryWrapper<>(process));

        if (process == null) {
            return Result.responseError(EntityError.DATA_NOT_FOUND_ERROR);
        }
        ProcessDTO processDTO = BeanUtils.switchToDTO(process, ProcessDTO.class);

        return Result.responseSuccess(processDTO);
    }




    @Caching(
            put = {
                    @CachePut(value = "processDetail", key = "#p0", unless = "#result == null || #result.entityError == null || #result.entityError.code != 200"),
                    @CachePut(value = "processDetail", key = "#p0", unless = "#result == null || #result.entityError == null || #result.entityError.code != 200"),
                    @CachePut(value = "processWithDetail", key = "#p0", unless = "#result == null || #result.entityError == null || #result.entityError.code != 200")
            },
            evict = {
                    @CacheEvict(value = "processDetail", key = "#p0.processKey")
            }
    )

    @Override
    public Result<Integer> updateProcessByProcessKey(ProcessQueryDTO param) {

        if (param == null
                || StringUtils.isEmpty(param.getProcessKey())) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        ProcessDO process = BeanUtils.switchToDO(param, ProcessDO.class);
        Integer num = mapper.updateProcessByProcessKey(process);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<DynamicFormDataDTO> getInitStartFormData(String processKey) {

        EasyBpmAsset.isAssetEmpty(processKey);

        Result<ProcessInfoDTO> result = getProcessWithDetailByProcessKey(processKey);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result.getEntityError());
        }
        ProcessInfoDTO processInfoDTO = result.getData();
        if (StringUtils.isEmpty(processInfoDTO.getDefinitionId())) {
            return Result.responseError(EntityError.UNPUBLISH_PROCESS_START_ERROR);
        }
        // 根据 流程定义 获取 发起类型为 start 的 用户任务节点，从这里获取发起表单
        NodeQueryDTO nodeInfoReqDTO = new NodeQueryDTO();
        nodeInfoReqDTO.setDefinitionId(processInfoDTO.getDefinitionId());
        nodeInfoReqDTO.setTaskType(BpmConstant.TASK_TYPE_START);
        nodeInfoReqDTO.setNodeType(BpmConstant.NODE_TYPE_USER_TASK);
        nodeInfoReqDTO.setValidState(VALID_STATE);
        Result<List<NodeDTO>> result1 = nodeService.getListByCondition(nodeInfoReqDTO);
        if (result1.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result1.getEntityError());
        }

        NodeDTO nodeInfoDTO = result1.getData().get(0);
        DynamicFormDataDTO dynamicFormDataDTO = new DynamicFormDataDTO();
        Map<String, List<TreeItemDTO>> dynamicDataMap = new HashMap<>();

        if (StringUtils.isEmpty(nodeInfoDTO.getFormKey())) {
            dynamicFormDataDTO.setDynamicDataMap(dynamicDataMap);
            return Result.responseSuccess(dynamicFormDataDTO);
        }

        Result<DynamicFormDataDTO> result2 = formService.getInitForm(nodeInfoDTO.getFormKey());
        if (result2.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result2.getEntityError());
        }
        dynamicFormDataDTO = result2.getData();
        /* 设置 formKey */
        dynamicFormDataDTO.setFormKey(nodeInfoDTO.getFormKey());
        dynamicFormDataDTO.setProcessId(processInfoDTO.getProcessId());
        dynamicFormDataDTO.setProcessKey(processInfoDTO.getProcessKey());
        dynamicFormDataDTO.setProcessName(processInfoDTO.getProcessName());
        return Result.responseSuccess(dynamicFormDataDTO);
    }

    @Override
    public Result<List<ItemDTO>> getProcessDict(String tenantId) {
        ProcessQueryDTO param = new ProcessQueryDTO();
        param.setTenantId(tenantId);
        param.setValidState(VALID_STATE);
        List<ProcessInfoDTO> processList = mapper.getListByCondition(param);

        List<ItemDTO> list = new ArrayList<>();
        ItemDTO itemDTO = null;
        if (processList != null) {
            for (ProcessInfoDTO processInfoDTO : processList) {
                itemDTO = new ItemDTO();
                itemDTO.setLabel(processInfoDTO.getProcessName());
                itemDTO.setValue(processInfoDTO.getProcessId());
                list.add(itemDTO);
            }
        }
        return Result.responseSuccess(list);
    }


    @Transactional
    @Override
    public Result<Integer> insertProcess(ProcessSaveOrUpdateDTO param) {

        EasyBpmAsset.isAssetEmptyByDefault(param);
        EasyBpmAsset.isAssetEmptyByDefault(param.getTenantId());
        EasyBpmAsset.isAssetEmptyByDefault(param.getProcessKey());

        // 默认 key 格式   租户 +"_" + key  , 不能包含 :  以字母或下划线 (_) 字符开头，后接 XML 规范中允许的任意字母、数字、重音字符、变音符号、句点 (.)、连字符 (-) 和下划线 (_) 的组合。
        if (!param.getProcessKey().startsWith((param.getTenantId()))) {
            param.setProcessKey(param.getTenantId() + "-" + "process" + "-" + param.getProcessKey());
        }
        ProcessDO process = BeanUtils.switchToDO(param, ProcessDO.class);
        process.setCreateTime(LocalDateTime.now());
        Integer num = mapper.insert(process);

        // 默认添加一个 流程详细信息
        ProcessDetailSaveOrUpdateDTO processDetailReqDTO = BeanUtils.switchToDTO(process, ProcessDetailSaveOrUpdateDTO.class);
        Result<Integer> result = processDetailService.insertProcessDetail(processDetailReqDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result.getEntityError());
        }

        process.setProcessDetailId(result.getData().longValue());
        mapper.updateById(process);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer> updateProcess(ProcessSaveOrUpdateDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        ProcessDO temp = BeanUtils.switchToDO(param, ProcessDO.class);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer> deleteProcess(ProcessSaveOrUpdateDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        ProcessDO temp = BeanUtils.switchToDO(param, ProcessDO.class);
        temp.setValidState(INVALID_STATE);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<ProcessDTO> getProcessById(Long processId) {

        if (processId == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        ProcessDO query = new ProcessDO();
        query.setProcessId(processId);
        query = mapper.selectOne(new QueryWrapper<>(query));

        if (query == null) {
            return Result.responseError(EntityError.DATA_NOT_FOUND_ERROR);
        }
        ProcessDTO result = BeanUtils.switchToDTO(query, ProcessDTO.class);
        return Result.responseSuccess(result);
    }
}
