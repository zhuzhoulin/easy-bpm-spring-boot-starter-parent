package com.pig.easy.bpm.auth.service.impl;

import com.pig.easy.bpm.auth.entity.OperatorLogDO;
import com.pig.easy.bpm.auth.mapper.OperatorLogMapper;
import com.pig.easy.bpm.auth.service.OperatorLogService;
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
 *  服务实现类
 * </p>
 *
 * @author pig
 * @since 2021-03-22
 */
@Service
public class OperatorLogServiceImpl extends BaseServiceImpl<OperatorLogMapper, OperatorLogDO> implements OperatorLogService {

    @Autowired
    OperatorLogMapper mapper;

    @Override
    public Result<PageInfo<OperatorLogDTO>> getListPageByCondition(OperatorLogQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        int pageIndex = CommonUtils.evalInt(param.getPageIndex(), DEFAULT_PAGE_INDEX);
        int pageSize = CommonUtils.evalInt(param.getPageSize(), DEFAULT_PAGE_SIZE);

        PageHelper.startPage(pageIndex, pageSize);
        param.setValidState(VALID_STATE);
        List<OperatorLogDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        PageInfo<OperatorLogDTO> pageInfo = new PageInfo<>(list);
        return Result.responseSuccess(pageInfo);
    }

    @Override
    public Result<List<OperatorLogDTO>> getListByCondition(OperatorLogQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        param.setValidState(VALID_STATE);
        List<OperatorLogDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        return Result.responseSuccess(list);
    }


    @Override
    public Result<Integer> insertOperatorLog(OperatorLogSaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        OperatorLogDO temp = BeanUtils.switchToDO(param, OperatorLogDO.class);
        Integer num = mapper.insert(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer>updateOperatorLog(OperatorLogSaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        OperatorLogDO temp = BeanUtils.switchToDO(param, OperatorLogDO.class);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer>deleteOperatorLog(OperatorLogSaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        OperatorLogDO temp = BeanUtils.switchToDO(param, OperatorLogDO.class);
        temp.setValidState(INVALID_STATE);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<OperatorLogDTO> getOperatorLogById(Long logId){

        if (logId == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        OperatorLogDO query = new OperatorLogDO();
        query.setLogId(logId);
        query = mapper.selectOne(new QueryWrapper<>(query));

        if (query == null) {
            return Result.responseError(EntityError.DATA_NOT_FOUND_ERROR);
        }
        OperatorLogDTO result = BeanUtils.switchToDTO(query, OperatorLogDTO.class);
        return Result.responseSuccess(result);
    }
}
