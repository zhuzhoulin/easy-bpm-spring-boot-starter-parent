package com.pig.easy.bpm.api.service;


import com.pig.easy.bpm.api.dto.response.ProcessDiagramDTO;
import com.pig.easy.bpm.api.entity.ProcessDetailDO;
import com.pig.easy.bpm.common.generator.BaseService;
import com.pig.easy.bpm.common.utils.Result;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/7/4 14:16
 */
public interface ProcessDiagramService {

    Result<ProcessDiagramDTO> getProcessDiagramByApplyId(Long applyId);

    Result<ProcessDiagramDTO> getProcessDiagramByProInstId(String procInstId);
}
