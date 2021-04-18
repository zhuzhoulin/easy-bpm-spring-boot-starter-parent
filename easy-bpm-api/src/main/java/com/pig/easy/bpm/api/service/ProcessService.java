package com.pig.easy.bpm.api.service;

import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.api.dto.request.ProcessQueryDTO;
import com.pig.easy.bpm.api.dto.request.ProcessSaveOrUpdateDTO;
import com.pig.easy.bpm.api.dto.response.DynamicFormDataDTO;
import com.pig.easy.bpm.api.dto.response.ProcessDTO;
import com.pig.easy.bpm.api.dto.response.ProcessInfoDTO;
import com.pig.easy.bpm.api.entity.ProcessDO;
import com.pig.easy.bpm.auth.dto.response.ItemDTO;
import com.pig.easy.bpm.common.generator.BaseService;
import com.pig.easy.bpm.common.utils.Result;

import java.util.List;
/**
 * <p>
 * 流程表 服务类
 * </p>
 *
 * @author pig
 * @since 2021-04-09
 */
public interface ProcessService extends BaseService<ProcessDO> {

    Result<PageInfo<ProcessInfoDTO>> getListPageByCondition(ProcessQueryDTO param);

    Result<Integer> insertProcess(ProcessSaveOrUpdateDTO param);

    Result<Integer> updateProcess(ProcessSaveOrUpdateDTO param);

    Result<Integer> deleteProcess(ProcessSaveOrUpdateDTO param);

    Result<ProcessDTO> getProcessById(Long processId);

    Result<List<ProcessInfoDTO>> getListByCondition(ProcessQueryDTO param);

    Result<ProcessInfoDTO> getProcessWithDetailByProcessKey(String key);

    Result<ProcessDTO> getProcessByProcessKey(String processKey);

    Result<Integer> updateProcessByProcessKey(ProcessQueryDTO param);

    Result<DynamicFormDataDTO> getInitStartFormData(String processKey);

    Result<List<ItemDTO>> getProcessDict(String tenantId);
}
