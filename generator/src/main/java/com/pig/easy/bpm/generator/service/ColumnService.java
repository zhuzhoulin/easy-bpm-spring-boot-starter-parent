package com.pig.easy.bpm.generator.service;

import com.pig.easy.bpm.generator.entity.ColumnDO;
import com.pig.easy.bpm.common.generator.BaseService;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.common.generator.BaseService;
import com.pig.easy.bpm.common.utils.Result;
import com.pig.easy.bpm.generator.dto.request.*;
import com.pig.easy.bpm.generator.dto.response.*;
import java.util.List;
/**
 * <p>
 * 字段表 服务类
 * </p>
 *
 * @author pig
 * @since 2021-03-11
 */
public interface ColumnService extends BaseService<ColumnDO> {

    Result<PageInfo<ColumnDTO>> getListPageByCondition(ColumnQueryDTO param);

    Result<Integer> insertColumn(ColumnSaveOrUpdateDTO param);

    Result<Integer> updateColumn(ColumnSaveOrUpdateDTO param);

    Result<Integer> deleteColumn(ColumnSaveOrUpdateDTO param);

    Result<ColumnDTO> getColumnById(Long columnId);

    Result<List<ColumnDTO>> getListByCondition(ColumnQueryDTO param);
}
