package com.pig.easy.bpm.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.api.dto.request.FormDataQueryDTO;
import com.pig.easy.bpm.api.dto.request.FormDataSaveOrUpdateDTO;
import com.pig.easy.bpm.api.dto.response.*;
import com.pig.easy.bpm.api.entity.FormDataDO;
import com.pig.easy.bpm.api.mapper.FormDataMapper;
import com.pig.easy.bpm.api.service.ApplyService;
import com.pig.easy.bpm.api.service.FormDataService;
import com.pig.easy.bpm.api.service.FormService;
import com.pig.easy.bpm.api.service.UserTaskService;
import com.pig.easy.bpm.common.annotation.DynamicDataSource;
import com.pig.easy.bpm.common.constant.BpmConstant;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.enums.DataSourceType;
import com.pig.easy.bpm.common.generator.BaseServiceImpl;
import com.pig.easy.bpm.common.utils.BeanUtils;
import com.pig.easy.bpm.common.utils.CommonUtils;
import com.pig.easy.bpm.common.utils.EasyBpmAsset;
import com.pig.easy.bpm.common.utils.Result;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pig
 * @since 2021-04-09
 */
@Service
public class FormDataServiceImpl extends BaseServiceImpl<FormDataMapper, FormDataDO> implements FormDataService {

    @Autowired
    FormDataMapper mapper;

    @Autowired
    FormService formService;
    @Autowired
    UserTaskService userTaskService;
    @Autowired
    @Lazy
    ApplyService applyService;
    @Autowired
    TaskService taskService;

    @Override
    public Result<PageInfo<FormDataDTO>> getListPageByCondition(FormDataQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        int pageIndex = CommonUtils.evalInt(param.getPageIndex(), DEFAULT_PAGE_INDEX);
        int pageSize = CommonUtils.evalInt(param.getPageSize(), DEFAULT_PAGE_SIZE);

        PageHelper.startPage(pageIndex, pageSize);
        param.setValidState(VALID_STATE);
        List<FormDataDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        PageInfo<FormDataDTO> pageInfo = new PageInfo<>(list);
        return Result.responseSuccess(pageInfo);
    }

    @Override
    public Result<List<FormDataDTO>> getListByCondition(FormDataQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        param.setValidState(VALID_STATE);
        List<FormDataDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        return Result.responseSuccess(list);
    }

    @Override
    public Result<Integer> insertFormData(FormDataSaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        FormDataDO temp = BeanUtils.switchToDO(param, FormDataDO.class);
        Integer num = mapper.insert(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer>updateFormData(FormDataSaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        FormDataDO temp = BeanUtils.switchToDO(param, FormDataDO.class);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer>deleteFormData(FormDataSaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        FormDataDO temp = BeanUtils.switchToDO(param, FormDataDO.class);
        temp.setValidState(INVALID_STATE);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<FormDataDTO> getFormDataById(Long dataId){

        if (dataId == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        FormDataDO query = new FormDataDO();
        query.setDataId(dataId);
        query = mapper.selectOne(new QueryWrapper<>(query));

        if (query == null) {
            return Result.responseError(EntityError.DATA_NOT_FOUND_ERROR);
        }
        FormDataDTO result = BeanUtils.switchToDTO(query, FormDataDTO.class);
        return Result.responseSuccess(result);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public Result<Integer> batchSaveOrUpdateFormData(Long applyId, Long taskId, String tenantId, String formKey, Map<String, Object> dataMap) {

        EasyBpmAsset.isAssetEmpty(applyId);
        EasyBpmAsset.isAssetEmpty(tenantId);

        if (dataMap == null || dataMap.size() == 0) {
            return Result.responseSuccess(0);
        }

        List<FormDataDO> formDataDOS = new ArrayList<>();
        List<FormDataDO> saveFormDataList = new ArrayList<>();
        List<FormDataDO> updateFormDataList = new ArrayList<>();
        UserTaskDTO userTaskDTO = null;
        FormDTO formDTO = null;
        ApplyDTO applyDTO = null;
        if (CommonUtils.evalLong(applyId) > 0) {
            formDataDOS = mapper.selectList(new QueryWrapper<>(FormDataDO.builder().applyId(applyId).validState(VALID_STATE).build()));
            Result<ApplyDTO> result = applyService.getApplyByApplyId(applyId);
            if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                return Result.responseError(result.getEntityError());
            }
            applyDTO=result.getData();
        }
        if (CommonUtils.evalLong(taskId) > 0) {
            Result<UserTaskDTO> result = userTaskService.getUserTaskByTaskId(taskId);
            if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                return Result.responseError(result.getEntityError());
            }
            userTaskDTO = result.getData();
            formDataDOS = mapper.selectList(new QueryWrapper<>(FormDataDO.builder().taskId(taskId).validState(VALID_STATE).build()));
            if(applyDTO == null && CommonUtils.evalLong(userTaskDTO.getApplyId()) > 0){
                Result<ApplyDTO> result2 = applyService.getApplyByApplyId(applyId);
                if (result2.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                    return Result.responseError(result2.getEntityError());
                }
                applyDTO=result2.getData();
            }
        }

        if (!StringUtils.isEmpty(formKey)) {
            Result<FormDTO> result = formService.getFormByFormKey(formKey);
            if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                return Result.responseError(result.getEntityError());
            }
            formDTO = result.getData();
        }

        ConcurrentMap<String, FormDataDO> formDataMap = formDataDOS.stream().collect(Collectors.toConcurrentMap(FormDataDO::getDataKey, a -> a, (newVal, oldVal) -> newVal));

        // 有一部分流程数据，不在FormData 也需要保存
        Map<String, Object> tempMap = new ConcurrentHashMap<>();

        // 后续可以新增权限保存
        FormDataTemplateDTO templateDTO = null;
        boolean updateFlag;
        FormDataDO templateDO = null;
        // 如果有 formKey 则 根据 formKey 保存数据
        if (formDTO != null && !StringUtils.isEmpty(formDTO.getFormData())) {
            JSONObject jsonObject = JSONObject.parseObject(formDTO.getFormData());

            JSONObject tempJsonObject = null;
            if (jsonObject.containsKey(BpmConstant.FORM_DATA_LIST)) {
                JSONArray jsonList = jsonObject.getJSONArray(BpmConstant.FORM_DATA_LIST);

                for (Object obj : jsonList) {

                    if (obj != null && !StringUtils.isEmpty(obj.toString())) {
                        templateDTO = JSON.parseObject(obj.toString(), FormDataTemplateDTO.class);
                        templateDO = new FormDataDO();
                        updateFlag = false;
                        tempJsonObject = null;

                        if (StringUtils.isEmpty(templateDTO.getType())
                                || StringUtils.isEmpty(templateDTO.getKey())
                                || dataMap.get(templateDTO.getKey()) == null) {
                            continue;
                        }
                        tempJsonObject = JSONObject.parseObject(templateDTO.getOptions().toString());
                        if (tempJsonObject != null
                                && Boolean.valueOf(tempJsonObject.getString(BpmConstant.DISABLED))) {
                            continue;
                        }
                        if (formDataMap.get(templateDTO.getKey()) != null) {
                            templateDO = BeanUtils.switchToDTO(formDataMap.get(templateDTO.getKey()), FormDataDO.class);
                            updateFlag = true;
                        }

                        switch (templateDTO.getType().toLowerCase()) {
                            case BpmConstant.FORM_DATA_TYPE_INPUT:
                                templateDO.setStringValue(StringEscapeUtils.unescapeJava(dataMap.getOrDefault(templateDTO.getKey(), "").toString()));
                                break;
                            case BpmConstant.FORM_DATA_TYPE_TEXT:
                            case BpmConstant.FORM_DATA_TYPE_CHILDTABLE:
                                templateDO.setTextValue(dataMap.getOrDefault(templateDTO.getKey(), "").toString());
                                break;
                            case BpmConstant.FORM_DATA_TYPE_NUMBER:
                                templateDO.setStringValue(StringEscapeUtils.unescapeJava(dataMap.getOrDefault(templateDTO.getKey(), "").toString()));
                                templateDO.setNumberValue(new BigDecimal(dataMap.getOrDefault(templateDTO.getKey(), "").toString()));
                                break;
                            case BpmConstant.FORM_DATA_TYPE_SELECT:
                            case BpmConstant.FORM_DATA_TYPE_CASCADER:
                            case BpmConstant.FORM_DATA_TYPE_RADIO:
                            case BpmConstant.FORM_DATA_TYPE_CHECKBOX:
                                templateDO.setStringValue(dataMap.getOrDefault(templateDTO.getKey(), "").toString());
                                if (tempJsonObject != null) {
                                    // true 动态数据 false 静态数据
                                    templateDO.setSelectItem(Boolean.valueOf(tempJsonObject.getString(BpmConstant.DYNAMIC)) ? BpmConstant.DYNAMIC_KEY : BpmConstant.OPTIONS);
                                }
                                break;
                            case BpmConstant.FORM_DATA_TYPE_DATE:
                            case BpmConstant.FORM_DATA_TYPE_TIME:
                                templateDO.setStringValue(StringEscapeUtils.unescapeJava(dataMap.getOrDefault(templateDTO.getKey(), "").toString()));

                                if (tempJsonObject != null
                                        && !StringUtils.isEmpty(tempJsonObject.getString(BpmConstant.FORMAT))
                                        && !StringUtils.isEmpty(tempJsonObject.getString(BpmConstant.IS_CHOOSE_TIMES))
                                        && !StringUtils.isEmpty(tempJsonObject.getString(BpmConstant.IS_CHOOSE_TIMES))
                                        && "1".equals(tempJsonObject.getString(BpmConstant.IS_CHOOSE_TIMES))) {
                                    try {
                                        LocalDateTime localDateTime = CommonUtils.parseLocalDate(dataMap.getOrDefault(templateDTO.getKey(), "").toString(), tempJsonObject.getString(BpmConstant.FORMAT));
                                        templateDO.setDateValue(localDateTime);
                                    } catch (Exception e) {
                                        templateDO.setDateValue(null);
                                    }
                                }
                                break;
                            case BpmConstant.FORM_DATA_TYPE_UPLOADFILE:
                            case BpmConstant.FORM_DATA_TYPE_UPLOADIMG:
                                templateDO.setTextValue(StringEscapeUtils.unescapeJava(JSON.toJSONString(dataMap.getOrDefault(templateDTO.getKey(), ""))));
                                break;
                            case BpmConstant.FORM_DATA_TYPE_SWITCH:
                                templateDO.setStringValue(StringEscapeUtils.unescapeJava(dataMap.getOrDefault(templateDTO.getKey(), "").toString()));
                                templateDO.setBooleanValue(Boolean.valueOf(dataMap.getOrDefault(templateDTO.getKey(), "").toString()));
                                break;
                            case BpmConstant.FORM_DATA_TYPE_SLIDER:
                                templateDO.setStringValue(StringEscapeUtils.unescapeJava(dataMap.getOrDefault(templateDTO.getKey(), "").toString()));
                                templateDO.setNumberValue(new BigDecimal(dataMap.getOrDefault(templateDTO.getKey(), "").toString()));
                                break;
                            case BpmConstant.FORM_DATA_TYPE_P:
                            case BpmConstant.FORM_DATA_TYPE_SUPER:
                            case BpmConstant.FORM_DATA_TYPE_DIVIDER:
                            case BpmConstant.FORM_DATA_TYPE_CARD:
                            case BpmConstant.FORM_DATA_TYPE_TABLE:
                                continue;
                            default:
                                templateDO.setStringValue(StringEscapeUtils.unescapeJava(dataMap.getOrDefault(templateDTO.getKey(), "").toString()));
                                break;
                        }

                        tempMap.put(templateDTO.getKey(), templateDTO.getLabel());
                        templateDO.setDataKey(templateDTO.getKey());
                        templateDO.setDataName(templateDTO.getLabel());
                        templateDO.setDataType(templateDTO.getType().toLowerCase());
                        templateDO.setTenantId(tenantId);
                        templateDO.setApplyId(applyId);
                        templateDO.setFormKey(formDTO.getFormKey());
                        templateDO.setFormId(formDTO.getFormId());

                        if (userTaskDTO != null) {
                            templateDO.setTaskId(taskId);
                            templateDO.setOperatorId(userTaskDTO.getOperatorId());
                            templateDO.setOperatorName(userTaskDTO.getOperatorName());
                        }
                        if(applyDTO != null){
                            templateDO.setProcInstId(applyDTO.getProcInstId());
                            templateDO.setProcessId(applyDTO.getProcessId());
                        }
                        if (updateFlag) {
                            updateFormDataList.add(templateDO);
                        } else {
                            saveFormDataList.add(templateDO);
                        }
                    }
                }
            }
        }

        // 如果没有formKey ，则根据 dataMap 保存数据
        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {

            if (tempMap.get(entry.getKey()) == null) {
                updateFlag = false;
                templateDO = new FormDataDO();

                if (formDataMap.get(entry.getKey()) != null) {
                    templateDO = BeanUtils.switchToDTO(formDataMap.get(entry.getKey()), FormDataDO.class);
                    updateFlag = true;
                }

                switch (entry.getValue().getClass().getName()) {
                    case "java.lang.String":
                        templateDO.setStringValue(StringEscapeUtils.unescapeJava(entry.getValue().toString()));
                        break;
                    case "java.lang.Short":
                    case "java.lang.Long":
                    case "java.lang.Int":
                    case "java.lang.Integer":
                    case "java.lang.Number":
                    case "java.math.BigDecimal":
                        templateDO.setStringValue(StringEscapeUtils.unescapeJava(entry.getValue().toString()));
                        templateDO.setNumberValue(new BigDecimal(entry.getValue().toString()));
                        break;
                    default:
                        templateDO.setTextValue(StringEscapeUtils.unescapeJava(JSON.toJSONString(entry.getValue())));
                        break;
                }
                templateDO.setDataType(entry.getValue().getClass().getName());
                templateDO.setDataKey(entry.getKey());
                templateDO.setDataName("");
                templateDO.setTenantId(tenantId);
                templateDO.setApplyId(applyId);
                templateDO.setFormKey(formDTO != null ? formDTO.getFormKey() : "");
                templateDO.setFormId(formDTO != null ? formDTO.getFormId() : 0);
                if(applyDTO != null){
                    templateDO.setProcInstId(applyDTO.getProcInstId());
                    templateDO.setProcessId(applyDTO.getProcessId());
                }
                if (userTaskDTO != null) {
                    templateDO.setTaskId(taskId);
                    templateDO.setOperatorId(userTaskDTO.getOperatorId());
                    templateDO.setOperatorName(userTaskDTO.getOperatorName());
                }
                if (formDataMap.get(entry.getKey()) != null) {
                    templateDO = BeanUtils.switchToDTO(formDataMap.get(entry.getKey()), FormDataDO.class);
                    updateFlag = true;
                }
                if (updateFlag) {
                    updateFormDataList.add(templateDO);
                } else {
                    saveFormDataList.add(templateDO);
                }
            }
        }

        AtomicInteger result = new AtomicInteger(0);
        if (saveFormDataList.size() > 0) {
//            for(FormDataDO formDataDO : saveFormDataList){
//                result.addAndGet(mapper.insert(formDataDO));
//            }
            result.addAndGet(mapper.batchSave(saveFormDataList));
        }
        if (updateFormDataList.size() > 0) {

            result.addAndGet(mapper.batchUpdate(updateFormDataList));
        }

        // 保存流程变量
        if (userTaskDTO != null) {
            taskService.setVariablesLocal(userTaskDTO.getActTaskId(), dataMap);
        }
        return Result.responseSuccess(result.get());
    }

    @Override
    public Result<Map<String, Object>> getFormDataByApplyId(Long applyId) {

        EasyBpmAsset.isAssetEmpty(applyId);

        List<FormDataDO> formDataDOS = mapper.selectList(new QueryWrapper<>(FormDataDO.builder().applyId(applyId).validState(VALID_STATE).build()));
        if (formDataDOS == null) {
            formDataDOS = new ArrayList<>();
        }

        return Result.responseSuccess(listToMap(formDataDOS));
    }

    private Map<String, Object> listToMap(List<FormDataDO> formDataDOS) {

        Map<String, Object> resultMap = new ConcurrentHashMap<>();
        Object dataValue = null;

        for (FormDataDO formDataDO : formDataDOS) {
            dataValue = null;
            switch (formDataDO.getDataType()) {
                case BpmConstant.FORM_DATA_TYPE_INPUT:
                case "java.lang.String":
                    dataValue = formDataDO.getStringValue();
                    break;
                case BpmConstant.FORM_DATA_TYPE_TEXT:
                case BpmConstant.FORM_DATA_TYPE_CHILDTABLE:

                    if(isJson(formDataDO.getTextValue())){
                        dataValue = JSON.parse(formDataDO.getTextValue());
                    } else {
                        dataValue = formDataDO.getTextValue();
                    }

                    break;
                case BpmConstant.FORM_DATA_TYPE_NUMBER:
                case "java.lang.Short":
                case "java.lang.Long":
                case "java.lang.Int":
                case "java.lang.Integer":
                case "java.lang.Number":
                case "java.math.BigDecimal":
                    dataValue = new BigDecimal(formDataDO.getStringValue());
                    break;
                case BpmConstant.FORM_DATA_TYPE_SELECT:
                case BpmConstant.FORM_DATA_TYPE_CASCADER:
                case BpmConstant.FORM_DATA_TYPE_RADIO:
                case BpmConstant.FORM_DATA_TYPE_CHECKBOX:

                    dataValue = JSON.parse(formDataDO.getStringValue()) != null ? JSON.parse(formDataDO.getStringValue()) : formDataDO.getStringValue();
                    break;
                case BpmConstant.FORM_DATA_TYPE_DATE:
                case BpmConstant.FORM_DATA_TYPE_TIME:
                    dataValue = formDataDO.getStringValue();
                    break;
                case BpmConstant.FORM_DATA_TYPE_UPLOADFILE:
                case BpmConstant.FORM_DATA_TYPE_UPLOADIMG:
                    dataValue = JSON.parse(formDataDO.getTextValue());
                    break;
                case BpmConstant.FORM_DATA_TYPE_SWITCH:
                    dataValue = Boolean.valueOf(formDataDO.getStringValue());
                    break;
                case BpmConstant.FORM_DATA_TYPE_SLIDER:
                    dataValue = new BigDecimal(formDataDO.getStringValue());
                    break;
                case BpmConstant.FORM_DATA_TYPE_P:
                case BpmConstant.FORM_DATA_TYPE_SUPER:
                case BpmConstant.FORM_DATA_TYPE_DIVIDER:
                case BpmConstant.FORM_DATA_TYPE_CARD:
                case BpmConstant.FORM_DATA_TYPE_TABLE:
                    continue;
                default:
                    dataValue = JSON.parse(formDataDO.getTextValue());
                    break;
            }

            if(dataValue == null){
                dataValue = "";
            }
            resultMap.put(formDataDO.getDataKey(), dataValue);
        }

        return resultMap;
    }

    @Override
    public Result<Map<String, Object>> getFormDataByProcInstId(String procInstId) {

        EasyBpmAsset.isAssetEmpty(procInstId);
        List<FormDataDO> formDataDOS = mapper.selectList(new QueryWrapper<>(FormDataDO.builder().procInstId(procInstId).validState(VALID_STATE).build()));
        if (formDataDOS == null) {
            formDataDOS = new ArrayList<>();
        }

        return Result.responseSuccess(listToMap(formDataDOS));
    }

    private static boolean isJson(String content) {
        if(StringUtils.isEmpty(content)){
            return false;
        }
        boolean isJsonObject = true;
        boolean isJsonArray = true;
        try {
            JSONObject.parseObject(content);
        } catch (Exception e) {
            isJsonObject = false;
        }
        try {
            JSONObject.parseArray(content);
        } catch (Exception e) {
            isJsonArray = false;
        }
        if(!isJsonObject && !isJsonArray){
            return false;
        }
        return true;
    }
}
