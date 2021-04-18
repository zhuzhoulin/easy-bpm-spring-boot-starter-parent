package com.pig.easy.bpm.auth.service.impl;

import com.pig.easy.bpm.auth.entity.SqlLogDO;
import com.pig.easy.bpm.auth.mapper.SqlLogMapper;
import com.pig.easy.bpm.auth.service.SqlLogService;
import com.pig.easy.bpm.common.annotation.HideLogAspect;
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
import com.pig.easy.bpm.auth.dto.request.*;
import com.pig.easy.bpm.auth.dto.response.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * <p>
 * SQL日志表 服务实现类
 * </p>
 *
 * @author pig
 * @since 2021-03-22
 */
@Service
@HideLogAspect
public class SqlLogServiceImpl extends BaseServiceImpl<SqlLogMapper, SqlLogDO> implements SqlLogService {

    @Autowired
    SqlLogMapper mapper;

    @Override
    public Result<PageInfo<SqlLogDTO>> getListPageByCondition(SqlLogQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        int pageIndex = CommonUtils.evalInt(param.getPageIndex(), DEFAULT_PAGE_INDEX);
        int pageSize = CommonUtils.evalInt(param.getPageSize(), DEFAULT_PAGE_SIZE);

        PageHelper.startPage(pageIndex, pageSize);
        param.setValidState(VALID_STATE);
        List<SqlLogDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        PageInfo<SqlLogDTO> pageInfo = new PageInfo<>(list);
        return Result.responseSuccess(pageInfo);
    }

    @Override
    public Result<List<SqlLogDTO>> getListByCondition(SqlLogQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        param.setValidState(VALID_STATE);
        List<SqlLogDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        return Result.responseSuccess(list);
    }


    @Override
    public Result<Integer> insertSqlLog(SqlLogSaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        SqlLogDO temp = BeanUtils.switchToDO(param, SqlLogDO.class);
        Integer num = mapper.insert(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer>updateSqlLog(SqlLogSaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        SqlLogDO temp = BeanUtils.switchToDO(param, SqlLogDO.class);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer>deleteSqlLog(SqlLogSaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        SqlLogDO temp = BeanUtils.switchToDO(param, SqlLogDO.class);
        temp.setValidState(INVALID_STATE);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<SqlLogDTO> getSqlLogById(Long logId){

        if (logId == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        SqlLogDO query = new SqlLogDO();
        query.setLogId(logId);
        query = mapper.selectOne(new QueryWrapper<>(query));

        if (query == null) {
            return Result.responseError(EntityError.DATA_NOT_FOUND_ERROR);
        }
        SqlLogDTO result = BeanUtils.switchToDTO(query, SqlLogDTO.class);
        return Result.responseSuccess(result);
    }
}
