package com.pig.easy.bpm.api.service.impl;

import com.pig.easy.bpm.api.entity.ProcessRuleDO;
import com.pig.easy.bpm.api.mapper.ProcessRuleMapper;
import com.pig.easy.bpm.api.service.ProcessRuleService;
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
import com.pig.easy.bpm.api.dto.request.*;
import com.pig.easy.bpm.api.dto.response.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pig
 * @since 2021-04-09
 */
@Service
public class ProcessRuleServiceImpl extends BaseServiceImpl<ProcessRuleMapper, ProcessRuleDO> implements ProcessRuleService {

    @Autowired
    ProcessRuleMapper mapper;

    @Override
    public Result<PageInfo<ProcessRuleDTO>> getListPageByCondition(ProcessRuleQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        int pageIndex = CommonUtils.evalInt(param.getPageIndex(), DEFAULT_PAGE_INDEX);
        int pageSize = CommonUtils.evalInt(param.getPageSize(), DEFAULT_PAGE_SIZE);

        PageHelper.startPage(pageIndex, pageSize);
        param.setValidState(VALID_STATE);
        List<ProcessRuleDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        PageInfo<ProcessRuleDTO> pageInfo = new PageInfo<>(list);
        return Result.responseSuccess(pageInfo);
    }

    @Override
    public Result<List<ProcessRuleDTO>> getListByCondition(ProcessRuleQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        param.setValidState(VALID_STATE);
        List<ProcessRuleDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        return Result.responseSuccess(list);
    }


    @Override
    public Result<Integer> insertProcessRule(ProcessRuleSaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        ProcessRuleDO temp = BeanUtils.switchToDO(param, ProcessRuleDO.class);
        Integer num = mapper.insert(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer>updateProcessRule(ProcessRuleSaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        ProcessRuleDO temp = BeanUtils.switchToDO(param, ProcessRuleDO.class);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer>deleteProcessRule(ProcessRuleSaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        ProcessRuleDO temp = BeanUtils.switchToDO(param, ProcessRuleDO.class);
        temp.setValidState(INVALID_STATE);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<ProcessRuleDTO> getProcessRuleById(String ruleId){

        if (ruleId == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        ProcessRuleDO query = new ProcessRuleDO();
        query.setRuleId(ruleId);
        query = mapper.selectOne(new QueryWrapper<>(query));

        if (query == null) {
            return Result.responseError(EntityError.DATA_NOT_FOUND_ERROR);
        }
        ProcessRuleDTO result = BeanUtils.switchToDTO(query, ProcessRuleDTO.class);
        return Result.responseSuccess(result);
    }
}
