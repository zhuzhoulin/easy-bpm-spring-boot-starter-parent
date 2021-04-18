package com.pig.easy.bpm.auth.service.impl;

import com.pig.easy.bpm.auth.entity.RoleGroupDO;
import com.pig.easy.bpm.auth.mapper.RoleGroupMapper;
import com.pig.easy.bpm.auth.service.RoleGroupService;
import com.pig.easy.bpm.common.generator.BaseServiceImpl;
import com.pig.easy.bpm.common.utils.EasyBpmAsset;
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
 * 角色组 服务实现类
 * </p>
 *
 * @author pig
 * @since 2021-03-20
 */
@Service
public class RoleGroupServiceImpl extends BaseServiceImpl<RoleGroupMapper, RoleGroupDO> implements RoleGroupService {

    @Autowired
    RoleGroupMapper mapper;

    @Override
    public Result<PageInfo<RoleGroupDTO>> getListPageByCondition(RoleGroupQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        int pageIndex = CommonUtils.evalInt(param.getPageIndex(), DEFAULT_PAGE_INDEX);
        int pageSize = CommonUtils.evalInt(param.getPageSize(), DEFAULT_PAGE_SIZE);

        PageHelper.startPage(pageIndex, pageSize);
        param.setValidState(VALID_STATE);
        List<RoleGroupDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        PageInfo<RoleGroupDTO> pageInfo = new PageInfo<>(list);
        return Result.responseSuccess(pageInfo);
    }

    @Override
    public Result<List<RoleGroupDTO>> getListByCondition(RoleGroupQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        param.setValidState(VALID_STATE);
        List<RoleGroupDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        return Result.responseSuccess(list);
    }

    @Override
    public Result<List<ItemDTO>> getRoleGroupDict(String tenantId) {

        EasyBpmAsset.isEmpty(tenantId,"tenantId is not allow empty");
        List<RoleGroupDO> roleGroupDOList = mapper.selectList(new QueryWrapper<>(RoleGroupDO.builder().tenantId(tenantId).validState(VALID_STATE).build()));
        List<ItemDTO> result = new ArrayList<>();
        ItemDTO item = null;
        for (RoleGroupDO roleGroupDO : roleGroupDOList) {
            item = new ItemDTO();
            item.setLabel(roleGroupDO.getRoleGroupName());
            item.setValue(roleGroupDO.getRoleGroupCode());
            result.add(item);
        }

        return Result.responseSuccess(result);
    }


    @Override
    public Result<Integer> insertRoleGroup(RoleGroupSaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        RoleGroupDO temp = BeanUtils.switchToDO(param, RoleGroupDO.class);
        Integer num = mapper.insert(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer>updateRoleGroup(RoleGroupSaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        RoleGroupDO temp = BeanUtils.switchToDO(param, RoleGroupDO.class);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer>deleteRoleGroup(RoleGroupSaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        RoleGroupDO temp = BeanUtils.switchToDO(param, RoleGroupDO.class);
        temp.setValidState(INVALID_STATE);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<RoleGroupDTO> getRoleGroupById(Long roleGroupId){

        if (roleGroupId == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        RoleGroupDO query = new RoleGroupDO();
        query.setRoleGroupId(roleGroupId);
        query = mapper.selectOne(new QueryWrapper<>(query));

        if (query == null) {
            return Result.responseError(EntityError.DATA_NOT_FOUND_ERROR);
        }
        RoleGroupDTO result = BeanUtils.switchToDTO(query, RoleGroupDTO.class);
        return Result.responseSuccess(result);
    }
}
