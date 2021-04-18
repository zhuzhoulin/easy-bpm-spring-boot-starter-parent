package com.pig.easy.bpm.generator.service.impl;

import com.pig.easy.bpm.generator.entity.TableStrategyConfigDO;
import com.pig.easy.bpm.generator.mapper.TableStrategyConfigMapper;
import com.pig.easy.bpm.generator.service.TableStrategyConfigService;
import com.pig.easy.bpm.common.generator.BaseServiceImpl;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.generator.BaseServiceImpl;
import com.pig.easy.bpm.common.utils.BeanUtils;
import com.pig.easy.bpm.common.utils.CommonUtils;
import com.pig.easy.bpm.common.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.pig.easy.bpm.generator.dto.request.*;
import com.pig.easy.bpm.generator.dto.response.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * <p>
 * 配置策略表 服务实现类
 * </p>
 *
 * @author pig
 * @since 2021-03-11
 */
@Service
public class TableStrategyConfigServiceImpl extends BaseServiceImpl<TableStrategyConfigMapper, TableStrategyConfigDO> implements TableStrategyConfigService {

    @Autowired
    TableStrategyConfigMapper mapper;

    @Override
    public Result<PageInfo<TableStrategyConfigDTO>> getListPageByCondition(TableStrategyConfigQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        int pageIndex = CommonUtils.evalInt(param.getPageIndex(), DEFAULT_PAGE_INDEX);
        int pageSize = CommonUtils.evalInt(param.getPageSize(), DEFAULT_PAGE_SIZE);

        PageHelper.startPage(pageIndex, pageSize);
        param.setValidState(VALID_STATE);
        List<TableStrategyConfigDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        PageInfo<TableStrategyConfigDTO> pageInfo = new PageInfo<>(list);
        return Result.responseSuccess(pageInfo);
    }

    @Override
    public Result<List<TableStrategyConfigDTO>> getListByCondition(TableStrategyConfigQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        param.setValidState(VALID_STATE);
        List<TableStrategyConfigDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        return Result.responseSuccess(list);
    }


    @Override
    public Result<Integer> insertTableStrategyConfig(TableStrategyConfigSaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        TableStrategyConfigDO temp = BeanUtils.switchToDO(param, TableStrategyConfigDO.class);
        Integer num = mapper.insert(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer>updateTableStrategyConfig(TableStrategyConfigSaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        TableStrategyConfigDO temp = BeanUtils.switchToDO(param, TableStrategyConfigDO.class);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer>deleteTableStrategyConfig(TableStrategyConfigSaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        TableStrategyConfigDO temp = BeanUtils.switchToDO(param, TableStrategyConfigDO.class);
        temp.setValidState(INVALID_STATE);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<TableStrategyConfigDTO> getTableStrategyConfigById(Long configId){

        if (configId == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        TableStrategyConfigDO query = new TableStrategyConfigDO();
        query.setConfigId(configId);
        query = mapper.selectOne(new QueryWrapper<>(query));

        if (query == null) {
            return Result.responseError(EntityError.DATA_NOT_FOUND_ERROR);
        }
        TableStrategyConfigDTO result = BeanUtils.switchToDTO(query, TableStrategyConfigDTO.class);
        return Result.responseSuccess(result);
    }
}
