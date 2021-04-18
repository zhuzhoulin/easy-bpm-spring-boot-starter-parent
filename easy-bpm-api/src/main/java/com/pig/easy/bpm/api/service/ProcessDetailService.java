package com.pig.easy.bpm.api.service;

import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.api.dto.request.ProcessDetailQueryDTO;
import com.pig.easy.bpm.api.dto.request.ProcessDetailSaveOrUpdateDTO;
import com.pig.easy.bpm.api.dto.request.ProcessPublishDTO;
import com.pig.easy.bpm.api.dto.response.ProcessDetailDTO;
import com.pig.easy.bpm.api.entity.ProcessDetailDO;
import com.pig.easy.bpm.common.generator.BaseService;
import com.pig.easy.bpm.common.utils.Result;

import java.util.List;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pig
 * @since 2021-04-09
 */
public interface ProcessDetailService extends BaseService<ProcessDetailDO> {

    Result<PageInfo<ProcessDetailDTO>> getListPageByCondition(ProcessDetailQueryDTO param);

    Result<Integer> insertProcessDetail(ProcessDetailSaveOrUpdateDTO param);

    Result<Integer> updateProcessDetail(ProcessDetailSaveOrUpdateDTO param);

    Result<Integer> deleteProcessDetail(ProcessDetailSaveOrUpdateDTO param);

    Result<ProcessDetailDTO> getProcessDetailById(Long processDetailId);

    Result<List<ProcessDetailDTO>> getListByCondition(ProcessDetailQueryDTO param);

    Result<Boolean> checkProcessXml(String processXml);

    Result<Boolean> processPublish(ProcessPublishDTO processPublishDTO);

    Result<Boolean> updateDefaultVersion(Long processId, Long processDetailId, Long operatorId, String operatorName);

    Result<Boolean> saveNodeList(String processXml, String definitionId, String processKey, Long operatorId, String operatorName);

    Result<ProcessDetailDTO> getProcessDetailByProcessId(Long processId);

    Result<ProcessDetailDTO> getProcessDetailByDefinitionId(String definitionId);

}
