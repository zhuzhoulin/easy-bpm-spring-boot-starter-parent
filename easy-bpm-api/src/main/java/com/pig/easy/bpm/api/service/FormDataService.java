package com.pig.easy.bpm.api.service;

import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.api.dto.request.FormDataQueryDTO;
import com.pig.easy.bpm.api.dto.request.FormDataSaveOrUpdateDTO;
import com.pig.easy.bpm.api.dto.response.FormDataDTO;
import com.pig.easy.bpm.api.entity.FormDataDO;
import com.pig.easy.bpm.common.generator.BaseService;
import com.pig.easy.bpm.common.utils.Result;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pig
 * @since 2021-04-09
 */
public interface FormDataService extends BaseService<FormDataDO> {

    Result<PageInfo<FormDataDTO>> getListPageByCondition(FormDataQueryDTO param);

    Result<Integer> insertFormData(FormDataSaveOrUpdateDTO param);

    Result<Integer> updateFormData(FormDataSaveOrUpdateDTO param);

    Result<Integer> deleteFormData(FormDataSaveOrUpdateDTO param);

    Result<FormDataDTO> getFormDataById(Long dataId);

    Result<List<FormDataDTO>> getListByCondition(FormDataQueryDTO param);

    Result<Integer> batchSaveOrUpdateFormData(Long applyId, Long taskId, String tenantId, String formKey, Map<String, Object> dataMap);

    Result<Map<String,Object>> getFormDataByApplyId(Long applyId);

    Result<Map<String,Object>> getFormDataByProcInstId(String procInstId);

}
