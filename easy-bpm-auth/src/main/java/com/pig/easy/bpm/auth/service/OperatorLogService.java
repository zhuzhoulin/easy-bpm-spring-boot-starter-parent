package com.pig.easy.bpm.auth.service;

import com.pig.easy.bpm.auth.entity.OperatorLogDO;
import com.pig.easy.bpm.common.generator.BaseService;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.common.generator.BaseService;
import com.pig.easy.bpm.common.utils.Result;
import com.pig.easy.bpm.auth.dto.request.*;
import com.pig.easy.bpm.auth.dto.response.*;
import java.util.List;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pig
 * @since 2021-03-22
 */
public interface OperatorLogService extends BaseService<OperatorLogDO> {

    Result<PageInfo<OperatorLogDTO>> getListPageByCondition(OperatorLogQueryDTO param);

    Result<Integer> insertOperatorLog(OperatorLogSaveOrUpdateDTO param);

    Result<Integer> updateOperatorLog(OperatorLogSaveOrUpdateDTO param);

    Result<Integer> deleteOperatorLog(OperatorLogSaveOrUpdateDTO param);

    Result<OperatorLogDTO> getOperatorLogById(Long logId);

    Result<List<OperatorLogDTO>> getListByCondition(OperatorLogQueryDTO param);
}
