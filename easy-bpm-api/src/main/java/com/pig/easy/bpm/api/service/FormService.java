package com.pig.easy.bpm.api.service;

import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.api.dto.request.FormQueryDTO;
import com.pig.easy.bpm.api.dto.request.FormSaveOrUpdateDTO;
import com.pig.easy.bpm.api.dto.response.DynamicFormDataDTO;
import com.pig.easy.bpm.api.dto.response.FormDTO;
import com.pig.easy.bpm.api.entity.FormDO;
import com.pig.easy.bpm.common.generator.BaseService;
import com.pig.easy.bpm.common.utils.Result;

import java.util.List;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pig
 * @since 2021-04-07
 */
public interface FormService extends BaseService<FormDO> {

    Result<PageInfo<FormDTO>> getListPageByCondition(FormQueryDTO param);

    Result<Integer> insertForm(FormSaveOrUpdateDTO param);

    Result<Integer> updateForm(FormSaveOrUpdateDTO param);

    Result<Integer> deleteForm(FormSaveOrUpdateDTO param);

    Result<FormDTO> getFormById(Long formId);

    Result<List<FormDTO>> getListByCondition(FormQueryDTO param);

    Result<FormDTO> getFormByFormKey(String formKey);

    Result<Integer> updateFormByFormKey(FormQueryDTO param);

    Result<DynamicFormDataDTO> getInitForm(String formKey);
}
