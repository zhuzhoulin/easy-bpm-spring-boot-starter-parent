package com.pig.easy.bpm.auth.service;

import com.pig.easy.bpm.auth.entity.SqlLogDO;
import com.pig.easy.bpm.common.generator.BaseService;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.common.generator.BaseService;
import com.pig.easy.bpm.common.utils.Result;
import com.pig.easy.bpm.auth.dto.request.*;
import com.pig.easy.bpm.auth.dto.response.*;
import java.util.List;
/**
 * <p>
 * SQL日志表 服务类
 * </p>
 *
 * @author pig
 * @since 2021-03-22
 */
public interface SqlLogService extends BaseService<SqlLogDO> {

    Result<PageInfo<SqlLogDTO>> getListPageByCondition(SqlLogQueryDTO param);

    Result<Integer> insertSqlLog(SqlLogSaveOrUpdateDTO param);

    Result<Integer> updateSqlLog(SqlLogSaveOrUpdateDTO param);

    Result<Integer> deleteSqlLog(SqlLogSaveOrUpdateDTO param);

    Result<SqlLogDTO> getSqlLogById(Long logId);

    Result<List<SqlLogDTO>> getListByCondition(SqlLogQueryDTO param);
}
