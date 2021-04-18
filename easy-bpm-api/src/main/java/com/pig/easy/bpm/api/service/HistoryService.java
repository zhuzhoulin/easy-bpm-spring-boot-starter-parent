package com.pig.easy.bpm.api.service;


import com.pig.easy.bpm.api.dto.request.HistorySaveDTO;
import com.pig.easy.bpm.api.dto.response.HistoryDTO;
import com.pig.easy.bpm.api.entity.HistoryDO;
import com.pig.easy.bpm.common.generator.BaseService;
import com.pig.easy.bpm.common.utils.Result;

import java.util.List;

/**
 * <p>
 * 审批历史表 服务类
 * </p>
 *
 * @author pig
 * @since 2020-07-01
 */
public interface HistoryService  extends BaseService<HistoryDO> {

    Result<List<HistoryDTO>> getListByApplyId(Long applyId);

    Result<HistoryDTO> getHistoryByTaskId(Long taskId);

    Result<Integer> addHistory(HistorySaveDTO historySaveDTO);

    Result<Integer> updateHistoryById(HistorySaveDTO historySaveDTO);

    Result<Integer> insertHistory(Long applyId, String tenantId, Long taskId, String taskName, Long approverUserId, String approveRealName, String approveActionCode, String approveOpinion, String systemCode, String platform);

}
