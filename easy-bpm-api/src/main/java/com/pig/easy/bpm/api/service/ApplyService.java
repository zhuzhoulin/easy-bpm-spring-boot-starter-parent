package com.pig.easy.bpm.api.service;

import com.pig.easy.bpm.api.dto.request.ApplyQueryDTO;
import com.pig.easy.bpm.api.dto.request.ApplyReqDTO;
import com.pig.easy.bpm.api.dto.request.ApplyUpdateDTO;
import com.pig.easy.bpm.api.dto.request.LineChartQueryDTO;
import com.pig.easy.bpm.api.dto.response.ApplyDTO;
import com.pig.easy.bpm.api.dto.response.ApproveTaskDTO;
import com.pig.easy.bpm.api.dto.response.ChartDTO;
import com.pig.easy.bpm.api.entity.ApplyDO;
import com.pig.easy.bpm.common.generator.BaseService;
import com.pig.easy.bpm.common.utils.Result;

import java.util.List;

/**
 * <p>
 * 申请表 服务类
 * </p>
 *
 * @author pig
 * @since 2020-05-20
 */
public interface ApplyService extends BaseService<ApplyDO> {

    void beforeStartProcess(ApplyReqDTO applyAddReqDTO);

    Result<Boolean> startProcess(ApplyReqDTO applyAddReqDTO);

    void afterStartProcess(ApplyReqDTO applyAddReqDTO);

    Result<String> generateApplySn(String applySnPrefix);

    Result<ApplyDTO> updateApplyByApplyId(ApplyUpdateDTO applyUpdateDTO);

    Result<ApplyDTO> updateApplyByProcInstId(ApplyUpdateDTO applyUpdateDTO);

    Result<ApplyDTO> getApplyByApplyId(Long applyId);

    Result<ApplyDTO> getApplyByProcInstId(String procInstId);

    Result<Long> saveDraftApply(ApplyReqDTO applyAddReqDTO);

    Result<ApproveTaskDTO> getApproveDetail(Long taskId);

    Result<ApproveTaskDTO> getApplyDetail(Long applyId);

    Result<List<ApplyDTO>> getChildrenListByApplyId(Long applyId);

    Result<List<ApplyDTO>> getListByCondition(ApplyQueryDTO applyQueryDTO);

    Result<ChartDTO> getApplyLineChart(LineChartQueryDTO lineChartQueryDTO);


}
