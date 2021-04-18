package com.pig.easy.bpm.generator.service;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.pig.easy.bpm.common.generator.BaseService;
import com.pig.easy.bpm.common.utils.Result;
import com.pig.easy.bpm.generator.dto.request.ColumnSaveOrUpdateDTO;
import com.pig.easy.bpm.generator.dto.response.ColumnDTO;
import com.pig.easy.bpm.generator.entity.ColumnDO;

import java.util.List;

/**
 * todo:
 *
 * @author : zhoulin.zhu
 * @date : 2021/2/24 10:09
 */
public interface CodeTableService extends BaseService<ColumnDO> {

    Result<List<TableInfo>> getTableInfoList(DataSourceConfig dataSourceConfig, String[] includeTables, boolean queryTableFiled);

    Result<List<TableInfo>> getTableInfoList(Long dbId, String[] includeTables, boolean queryTableFiled);

    Result<Integer> syncTableIntoColunmTable(Long dbId, String[] includeTables, boolean queryTableFiled);

    Result<List<ColumnDTO>> getTableColumnList(Long dbId, String tableName);

    Result<List<ColumnDTO>> saveOrUpdateListAndGetList(List<ColumnSaveOrUpdateDTO> param,boolean query);

}
