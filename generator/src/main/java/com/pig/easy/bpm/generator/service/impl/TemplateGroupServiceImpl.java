package com.pig.easy.bpm.generator.service.impl;

import com.pig.easy.bpm.generator.entity.TemplateGroupDO;
import com.pig.easy.bpm.generator.mapper.TemplateGroupMapper;
import com.pig.easy.bpm.generator.service.TemplateGroupService;
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
 * 模板分组表 服务实现类
 * </p>
 *
 * @author pig
 * @since 2021-03-08
 */
@Service
public class TemplateGroupServiceImpl extends BaseServiceImpl<TemplateGroupMapper, TemplateGroupDO> implements TemplateGroupService {

    @Autowired
    TemplateGroupMapper mapper;

    @Override
    public Result<PageInfo<TemplateGroupDTO>> getListPageByCondition(TemplateGroupQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        int pageIndex = CommonUtils.evalInt(param.getPageIndex(), DEFAULT_PAGE_INDEX);
        int pageSize = CommonUtils.evalInt(param.getPageSize(), DEFAULT_PAGE_SIZE);

        PageHelper.startPage(pageIndex, pageSize);
        param.setValidState(VALID_STATE);
        List<TemplateGroupDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        PageInfo<TemplateGroupDTO> pageInfo = new PageInfo<>(list);
        return Result.responseSuccess(pageInfo);
    }

    @Override
    public Result<List<TemplateGroupDTO>> getListByCondition(TemplateGroupQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        param.setValidState(VALID_STATE);
        List<TemplateGroupDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        return Result.responseSuccess(list);
    }


    @Override
    public Result<Integer> insertTemplateGroup(TemplateGroupSaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        TemplateGroupDO temp = BeanUtils.switchToDO(param, TemplateGroupDO.class);
        Integer num = mapper.insert(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer>updateTemplateGroup(TemplateGroupSaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        TemplateGroupDO temp = BeanUtils.switchToDO(param, TemplateGroupDO.class);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer>deleteTemplateGroup(TemplateGroupSaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        TemplateGroupDO temp = BeanUtils.switchToDO(param, TemplateGroupDO.class);
        temp.setValidState(INVALID_STATE);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<TemplateGroupDTO> getTemplateGroupById(Long templateGroupId){

        if (templateGroupId == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        TemplateGroupDO query = new TemplateGroupDO();
        query.setTemplateGroupId(templateGroupId);
        query = mapper.selectOne(new QueryWrapper<>(query));

        if (query == null) {
            return Result.responseError(EntityError.DATA_NOT_FOUND_ERROR);
        }
        TemplateGroupDTO result = BeanUtils.switchToDTO(query, TemplateGroupDTO.class);
        return Result.responseSuccess(result);
    }
}
