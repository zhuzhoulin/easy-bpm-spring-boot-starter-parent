package com.pig.easy.bpm.generator.service.impl;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.generator.BaseServiceImpl;
import com.pig.easy.bpm.common.utils.BeanUtils;
import com.pig.easy.bpm.common.utils.EasyBpmAsset;
import com.pig.easy.bpm.common.utils.Result;
import com.pig.easy.bpm.generator.dto.request.ColumnSaveOrUpdateDTO;
import com.pig.easy.bpm.generator.dto.response.ColumnDTO;
import com.pig.easy.bpm.generator.dto.response.DbConfigDTO;
import com.pig.easy.bpm.generator.entity.ColumnDO;
import com.pig.easy.bpm.generator.generator.CustomDbQuery;
import com.pig.easy.bpm.generator.generator.MySqlCustomDbQuery;
import com.pig.easy.bpm.generator.mapper.ColumnMapper;
import com.pig.easy.bpm.generator.service.CodeTableService;
import com.pig.easy.bpm.generator.service.ColumnService;
import com.pig.easy.bpm.generator.service.DbConfigService;
import com.pig.easy.bpm.generator.utils.DbUtils;
import com.pig.easy.bpm.generator.utils.GeneratorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * todo:
 *
 * @author : zhoulin.zhu
 * @date : 2021/2/24 10:11
 */
@Service
public class CodeTableServiceImpl extends BaseServiceImpl<ColumnMapper, ColumnDO> implements CodeTableService {

    @Autowired
    DbConfigService codeDbConfigService;

    @Autowired
    ColumnService codeColumnService;
    @Autowired
    ColumnMapper mapper;

    @Override
    public Result<List<TableInfo>> getTableInfoList(DataSourceConfig dataSourceConfig, String[] includeTables, boolean queryTableFiled) {
        return Result.responseSuccess(DbUtils.getTablesInfo(dataSourceConfig, includeTables, queryTableFiled));
    }

    @Override
    public Result<List<TableInfo>> getTableInfoList(Long dbId, String[] includeTables, boolean queryTableFiled) {

        Result<DbConfigDTO> result = codeDbConfigService.getDbConfigById(dbId);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result.getEntityError());
        }
        DbConfigDTO DbConfigDTO = result.getData();

        DataSourceConfig dataSourceConfig = DbUtils.buildDataSourceConfig(DbConfigDTO.getDbDriverName(), DbConfigDTO.getDbUrl(), DbConfigDTO.getDbUsername(), DbConfigDTO.getDbPassword());
        try {
            List<TableInfo> tablesInfo = DbUtils.getTablesInfo(dataSourceConfig, includeTables, queryTableFiled);
            return Result.responseSuccess(tablesInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.responseError(new EntityError(EntityError.SYSTEM_ERROR.getCode(), e.getMessage()));
        }
    }


    @Override
    public Result<Integer> syncTableIntoColunmTable(Long dbId, String[] includeTables, boolean queryTableFiled) {

        EasyBpmAsset.isAssetEmptyByDefault(dbId);
        EasyBpmAsset.isAssetEmptyByDefault(includeTables);

        for (String tableName : includeTables) {
            getTableColumnList(dbId, tableName);
        }

        return Result.responseSuccess(includeTables.length);
    }

    @Override
    public Result<List<ColumnDTO>> getTableColumnList(Long dbId, String tableName) {
        Result<DbConfigDTO> result = codeDbConfigService.getDbConfigById(dbId);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result.getEntityError());
        }
        DbConfigDTO DbConfigDTO = result.getData();

        DataSourceConfig dataSourceConfig = DbUtils.buildDataSourceConfig(DbConfigDTO.getDbDriverName(), DbConfigDTO.getDbUrl(), DbConfigDTO.getDbUsername(), DbConfigDTO.getDbPassword());
        List<TableInfo> tablesInfo = DbUtils.getTablesInfo(dataSourceConfig, new String[]{tableName}, true);

        List<TableField> fields = tablesInfo.get(0).getFields();
        List<ColumnSaveOrUpdateDTO> list = switchToColumnList(dbId, tableName, dataSourceConfig, fields);

        Result<List<ColumnDTO>> result1 = saveOrUpdateListAndGetList(list, true);
        if (result1.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result1.getEntityError());
        }
        List<ColumnDTO> data = result1.getData();
        return Result.responseSuccess(data);
    }

    @Transactional
    @Override
    public Result<List<ColumnDTO>> saveOrUpdateListAndGetList(List<ColumnSaveOrUpdateDTO> param, boolean query) {

        if (param == null || param.size() == 0) {
            return Result.responseSuccess(new ArrayList<>());
        }
        Long dbId = param.get(0).getDbId();
        String tableName = param.get(0).getTableName();
        EasyBpmAsset.isEmpty(dbId);
        EasyBpmAsset.isEmpty(tableName);

        ColumnDO columnDO = new ColumnDO();
        columnDO.setDbId(dbId);
        columnDO.setValidState(1);
        columnDO.setTableName(tableName);
        List<ColumnDO> codeColumnDOS = mapper.selectList(new QueryWrapper<>(columnDO));

        List<Long> existIds = new ArrayList<>();
        List<Long> needUpdateIds = new ArrayList<>();
        ConcurrentMap<String, ColumnDO> columnMap = new ConcurrentHashMap<>();
        if (codeColumnDOS != null) {
            existIds = codeColumnDOS.stream().map(ColumnDO::getColumnId).collect(Collectors.toList());
            columnMap = codeColumnDOS.stream().collect(Collectors.toConcurrentMap(ColumnDO::getColumnName, a -> a, (k1, k2) -> k2));
        }

        List<ColumnDO> insertList = new ArrayList<>();
        List<ColumnDO> updateList = new ArrayList<>();

        ColumnDO codeColumnDO = null;
        for (ColumnSaveOrUpdateDTO columnSaveOrUpdateDTO : param) {

            codeColumnDO = BeanUtils.switchToDO(columnSaveOrUpdateDTO, ColumnDO.class);
            ColumnDO oldColumn = columnMap.get(columnSaveOrUpdateDTO.getColumnName());

            if (oldColumn != null) {
                needUpdateIds.add(oldColumn.getColumnId());
                codeColumnDO.setColumnId(oldColumn.getColumnId());
                // 如果是查询的话，则不更新数据
                if (!query) {
                    updateList.add(codeColumnDO);
                }
            } else {
                String formType = codeColumnDO.getFormType();
                if (StringUtils.isEmpty(formType)) {
                    codeColumnDO.setFormType(formTypeConvert(codeColumnDO));
                }
                if (StringUtils.isEmpty(codeColumnDO.getQueryMethod())) {
                    codeColumnDO.setQueryMethod("EQ");
                }
                insertList.add(codeColumnDO);
            }
        }

        for (ColumnDO temp : updateList) {
            mapper.updateById(temp);
        }
        for (ColumnDO temp : insertList) {
            mapper.insert(temp);
        }

        List<Long> needDeleteIds = existIds.stream().filter(x -> !needUpdateIds.contains(x)).collect(Collectors.toList());
        if (needDeleteIds != null
                && needDeleteIds.size() > 0) {
            ColumnDO temp = new ColumnDO();
            temp.setValidState(INVALID_STATE);

            mapper.update(temp, new QueryWrapper<>(temp).in("column_id", needDeleteIds));
        }

        codeColumnDOS = mapper.selectList(new QueryWrapper<>(columnDO));
        if (codeColumnDOS == null) {
            codeColumnDOS = new ArrayList<>();
        }

        List<ColumnDTO> result = new ArrayList<>();
        for (ColumnDO temp : codeColumnDOS) {
            // set default value
            if (StringUtils.isEmpty(temp.getFormType())) {
                temp.setFormType(formTypeConvert(temp));
            }
            if (StringUtils.isEmpty(temp.getQueryMethod())) {
                temp.setQueryMethod("==");
            }
            result.add(BeanUtils.switchToDO(temp, ColumnDTO.class));
        }
        return Result.responseSuccess(result);
    }


    /**
     * 功能描述:  设置 formType
     * 
     * 
     * @param codeColumnDO
     * @return : 
     * @author : zhoulin.zhu
     * @date : 2021/4/7 19:39
     */
    private String formTypeConvert(ColumnDO codeColumnDO){

        String formType = "input";

        switch (codeColumnDO.getPropertyType()) {
            case "String":
                formType = "input";
                break;
            case "Integer":
                formType = "number";
                break;
            case "boolean":
            case "Boolean":
                formType = "switch";
                break;
            default:
                formType = "input";
                break;
        }
        //特殊处理
        if (codeColumnDO.getColumnType().toLowerCase().contains("text")) {
            formType = "textarea";
        }
        if (codeColumnDO.getColumnType().toLowerCase().contains("date")) {
            formType = "date";
        }
        if(codeColumnDO.getColumnType().toLowerCase().contains("tinyint")){
            formType = "switch(0,1)";
        }

        return formType;
    }

    private List<ColumnSaveOrUpdateDTO> switchToColumnList(Long dbId, String tableName, DataSourceConfig dataSourceConfig, List<TableField> fieldList) {

        if (fieldList == null) {
            return new ArrayList<>();
        }

        List<ColumnSaveOrUpdateDTO> list = new ArrayList<>();
        ColumnSaveOrUpdateDTO codeColumnSaveOrUpdateDTO = null;
        for (TableField tableField : fieldList) {
            codeColumnSaveOrUpdateDTO = new ColumnSaveOrUpdateDTO();
            codeColumnSaveOrUpdateDTO.setColumnName(tableField.getName());
            codeColumnSaveOrUpdateDTO.setColumnComment(tableField.getComment());
            codeColumnSaveOrUpdateDTO.setFormLabel(tableField.getComment());
            codeColumnSaveOrUpdateDTO.setColumnType(tableField.getType());
            codeColumnSaveOrUpdateDTO.setPropertyType(tableField.getPropertyType());
            codeColumnSaveOrUpdateDTO.setPropertyName(GeneratorUtils.toCamelCase(tableField.getPropertyName()));
            codeColumnSaveOrUpdateDTO.setDbId(dbId);
            codeColumnSaveOrUpdateDTO.setKeyFlag(tableField.isKeyFlag() ? 1 : 0);
            codeColumnSaveOrUpdateDTO.setKeyIdentityFlag(tableField.isKeyIdentityFlag() ? 1 : 0);
            codeColumnSaveOrUpdateDTO.setTableName(tableName);

            if (tableField.getCustomMap() != null) {

                String[] fieldCustom = dataSourceConfig.getDbQuery().fieldCustom();
                if (fieldCustom != null) {
                    Map<String, Object> customMap = tableField.getCustomMap();

                    CustomDbQuery customDbQuery = getCustomDbQuery(dataSourceConfig.getDbType());
                    if (customMap.get(customDbQuery.defaultValue()) != null) {
                        codeColumnSaveOrUpdateDTO.setDefaultValue(customMap.get((customDbQuery.defaultValue())).toString());
                    }
                    if (customMap.get(customDbQuery.extra()) != null) {
                        codeColumnSaveOrUpdateDTO.setExtra(customMap.get((customDbQuery.extra())).toString());
                    }
                    if (customMap.get(customDbQuery.privileges()) != null) {
                        codeColumnSaveOrUpdateDTO.setPrivileges(customMap.get((customDbQuery.privileges())).toString());
                    }
                    if (customMap.get(customDbQuery.collation()) != null) {
                        codeColumnSaveOrUpdateDTO.setCollation(customMap.get((customDbQuery.collation())).toString());
                    }
                }
            }
            list.add(codeColumnSaveOrUpdateDTO);
        }
        codeColumnSaveOrUpdateDTO = null;
        return list;
    }

    private CustomDbQuery getCustomDbQuery(DbType dbType) {

        CustomDbQuery customDbQuery = null;
        switch (dbType) {
            default:
                // 默认 MYSQL
                customDbQuery = new MySqlCustomDbQuery();
                break;
        }

        return customDbQuery;
    }
}
