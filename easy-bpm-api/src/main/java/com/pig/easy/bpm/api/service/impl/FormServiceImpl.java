package com.pig.easy.bpm.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.api.dto.request.FormQueryDTO;
import com.pig.easy.bpm.api.dto.request.FormSaveOrUpdateDTO;
import com.pig.easy.bpm.api.dto.response.DynamicFormDataDTO;
import com.pig.easy.bpm.api.dto.response.FormDTO;
import com.pig.easy.bpm.api.entity.FormDO;
import com.pig.easy.bpm.api.mapper.FormMapper;
import com.pig.easy.bpm.api.service.FormService;
import com.pig.easy.bpm.auth.dto.response.DictItemDTO;
import com.pig.easy.bpm.auth.dto.response.TreeItemDTO;
import com.pig.easy.bpm.auth.service.DictItemService;
import com.pig.easy.bpm.common.constant.BpmConstant;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.generator.BaseServiceImpl;
import com.pig.easy.bpm.common.utils.BeanUtils;
import com.pig.easy.bpm.common.utils.CommonUtils;
import com.pig.easy.bpm.common.utils.EasyBpmAsset;
import com.pig.easy.bpm.common.utils.Result;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author pig
 * @since 2021-04-07
 */
@Service
public class FormServiceImpl extends BaseServiceImpl<FormMapper, FormDO> implements FormService {

    @Autowired
    FormMapper mapper;
    @Autowired
    DictItemService dictItemService;

    @Override
    public Result<PageInfo<FormDTO>> getListPageByCondition(FormQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        int pageIndex = CommonUtils.evalInt(param.getPageIndex(), DEFAULT_PAGE_INDEX);
        int pageSize = CommonUtils.evalInt(param.getPageSize(), DEFAULT_PAGE_SIZE);

        PageHelper.startPage(pageIndex, pageSize);
        param.setValidState(VALID_STATE);
        List<FormDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        PageInfo<FormDTO> pageInfo = new PageInfo<>(list);
        return Result.responseSuccess(pageInfo);
    }

    @Override
    public Result<List<FormDTO>> getListByCondition(FormQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        param.setValidState(VALID_STATE);
        List<FormDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        return Result.responseSuccess(list);
    }

    @Override
    public Result<FormDTO> getFormByFormKey(String formKey) {

        EasyBpmAsset.isAssetEmptyByDefault(formKey);

        FormDO formDO = mapper.selectOne(new QueryWrapper<>(FormDO.builder().formKey(formKey).build()));
        if (formDO == null) {
            return Result.responseError(EntityError.DATA_NOT_FOUND_ERROR);
        }
        FormDTO formDTO = BeanUtils.switchToDTO(formDO, FormDTO.class);
        return Result.responseSuccess(formDTO);
    }

    @Override
    public Result<Integer> updateFormByFormKey(FormQueryDTO param) {
        if (param == null
                || StringUtils.isEmpty(param.getFormKey())) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        FormDO form = BeanUtils.switchToDO(param, FormDO.class);
        Integer num = mapper.updateFormByFormKey(form);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<DynamicFormDataDTO> getInitForm(String formKey) {

        DynamicFormDataDTO dynamicFormDataDTO = new DynamicFormDataDTO();
        Map<String, List<TreeItemDTO>> dynamicDataMap = new HashMap<>();

        if (StringUtils.isEmpty(formKey)) {
            dynamicFormDataDTO.setDynamicDataMap(dynamicDataMap);
            return Result.responseSuccess(dynamicFormDataDTO);
        }

        Result<FormDTO> result2 = getFormByFormKey(formKey);
        if (result2.getEntityError().getCode() == EntityError.ILLEGAL_ARGUMENT_ERROR.getCode()) {
            FormDTO defaultForm = new FormDTO();
            defaultForm.setFormKey(formKey);
            defaultForm.setFormData("{\"config\":{\"labelPosition\":\"left\",\"labelWidth\":15,\"autoLabelWidth\":false},\"list\":[],\"dynamicKeyList\":[]}");
            result2=Result.responseSuccess(defaultForm);
        }

        dynamicFormDataDTO.setFormJsonData(StringEscapeUtils.unescapeJava(result2.getData().getFormData()));
        List<TreeItemDTO> dynamicKeyList = null;
        Result<List<DictItemDTO>> result3 = null;
        TreeItemDTO treeItemDTO = null;
        JSONObject jsonObject = JSON.parseObject(result2.getData().getFormData());
        if (jsonObject.containsKey(BpmConstant.DYNAMIC_KEY_LIST)) {
            JSONArray jsonArray = jsonObject.getJSONArray(BpmConstant.DYNAMIC_KEY_LIST);
            for (Object obj : jsonArray) {

                if (obj != null && !StringUtils.isEmpty(obj.toString())) {
                    dynamicKeyList = new ArrayList<>();
                    result3 = dictItemService.getListByDictCode(obj.toString());
                    if (result3.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                        log.error("getInitForm getListByDictCode error , message is " + result3.getEntityError().getMessage());
                        continue;
                    }

                    for (DictItemDTO dictItem : result3.getData()) {
                        treeItemDTO = new TreeItemDTO();
                        treeItemDTO.setLabel(dictItem.getItemText());
                        try {
                            treeItemDTO.setValue(Integer.valueOf(dictItem.getItemValue()));
                        } catch (Exception e) {
                            treeItemDTO.setValue(dictItem.getItemValue());
                        }
                        dynamicKeyList.add(treeItemDTO);
                    }

                    dynamicDataMap.put(obj.toString(), dynamicKeyList);
                }
                dynamicFormDataDTO.setDynamicDataMap(dynamicDataMap);
            }
        }
        return Result.responseSuccess(dynamicFormDataDTO);


    }


    @Override
    public Result<Integer> insertForm(FormSaveOrUpdateDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        EasyBpmAsset.isAssetEmptyByDefault(param.getTenantId());
        EasyBpmAsset.isAssetEmptyByDefault(param.getFormKey());
        // 默认 key 格式   租户 +":" + key
        if (!param.getFormKey().startsWith((param.getTenantId()))) {
            param.setFormKey(param.getTenantId()
                    + BpmConstant.COMMON_CODE_SEPARATION_CHARACTER
                    + "form"
                    + BpmConstant.COMMON_CODE_SEPARATION_CHARACTER
                    + param.getFormKey());
        }
        FormDO temp = BeanUtils.switchToDO(param, FormDO.class);
        Integer num = mapper.insert(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer> updateForm(FormSaveOrUpdateDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        FormDO temp = BeanUtils.switchToDO(param, FormDO.class);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer> deleteForm(FormSaveOrUpdateDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        FormDO temp = BeanUtils.switchToDO(param, FormDO.class);
        temp.setValidState(INVALID_STATE);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<FormDTO> getFormById(Long formId) {

        if (formId == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        FormDO query = new FormDO();
        query.setFormId(formId);
        query = mapper.selectOne(new QueryWrapper<>(query));

        if (query == null) {
            return Result.responseError(EntityError.DATA_NOT_FOUND_ERROR);
        }
        FormDTO result = BeanUtils.switchToDTO(query, FormDTO.class);
        return Result.responseSuccess(result);
    }
}
