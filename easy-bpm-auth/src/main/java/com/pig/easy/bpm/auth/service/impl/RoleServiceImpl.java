package com.pig.easy.bpm.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.auth.dto.request.RoleGroupRoleDetailQueryDTO;
import com.pig.easy.bpm.auth.dto.request.RoleQueryDTO;
import com.pig.easy.bpm.auth.dto.request.RoleSaveOrUpdateDTO;
import com.pig.easy.bpm.auth.dto.response.ItemDTO;
import com.pig.easy.bpm.auth.dto.response.RoleDTO;
import com.pig.easy.bpm.auth.dto.response.RoleGroupRoleDetailDTO;
import com.pig.easy.bpm.auth.entity.RoleDO;
import com.pig.easy.bpm.auth.mapper.RoleMapper;
import com.pig.easy.bpm.auth.service.RoleService;
import com.pig.easy.bpm.common.constant.BpmConstant;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.generator.BaseServiceImpl;
import com.pig.easy.bpm.common.utils.BeanUtils;
import com.pig.easy.bpm.common.utils.CommonUtils;
import com.pig.easy.bpm.common.utils.EasyBpmAsset;
import com.pig.easy.bpm.common.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author pig
 * @since 2021-03-20
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, RoleDO> implements RoleService {

    @Autowired
    RoleMapper mapper;

    @Override
    public Result<PageInfo<RoleDTO>> getListPageByCondition(RoleQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        int pageIndex = CommonUtils.evalInt(param.getPageIndex(), DEFAULT_PAGE_INDEX);
        int pageSize = CommonUtils.evalInt(param.getPageSize(), DEFAULT_PAGE_SIZE);

        PageHelper.startPage(pageIndex, pageSize);
        param.setValidState(VALID_STATE);
        List<RoleDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        PageInfo<RoleDTO> pageInfo = new PageInfo<>(list);
        return Result.responseSuccess(pageInfo);
    }

    @Override
    public Result<List<RoleDTO>> getListByCondition(RoleQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        param.setValidState(VALID_STATE);
        List<RoleDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        return Result.responseSuccess(list);
    }

    @Override
    public Result<PageInfo<RoleGroupRoleDetailDTO>> getRoleGroupRoleDetailByCondition(RoleGroupRoleDetailQueryDTO queryDTO) {
        if (queryDTO == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        int pageIndex = CommonUtils.evalInt(queryDTO.getPageIndex(), DEFAULT_PAGE_INDEX);
        int pageSize = CommonUtils.evalInt(queryDTO.getPageSize(), DEFAULT_PAGE_SIZE);
        queryDTO.setPageIndex(pageIndex);
        queryDTO.setPageSize(pageSize);
        List<RoleGroupRoleDetailDTO> result = new ArrayList<>();
        Integer count = mapper.getCountByCondition(queryDTO);
        if (count > 0) {
            result = mapper.getRoleGroupRoleDetailByCondition(queryDTO);
        }
        PageInfo<RoleGroupRoleDetailDTO> pageInfo = new PageInfo<>(result);
        pageInfo.setSize(count);
        return Result.responseSuccess(pageInfo);
    }

    @Override
    public Result<List<RoleGroupRoleDetailDTO>> getRoleGroupRoleDetailList(RoleGroupRoleDetailQueryDTO queryDTO) {

        int pageIndex = CommonUtils.evalInt(queryDTO.getPageIndex(), DEFAULT_PAGE_INDEX);
        int pageSize = CommonUtils.evalInt(queryDTO.getPageSize(), MAX_PAGE_SIZE);
        queryDTO.setPageIndex(pageIndex);
        queryDTO.setPageSize(pageSize);
        Result<PageInfo<RoleGroupRoleDetailDTO>> result = getRoleGroupRoleDetailByCondition(queryDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result.getEntityError());
        }
        return Result.responseSuccess(result.getData().getList());
    }

    @Override
    public Result<List<ItemDTO>> getRoleDictByTenantId(String tenantId) {

        EasyBpmAsset.isAssetEmptyByDefault(tenantId);
        RoleQueryDTO param = new RoleQueryDTO();
        param.setTenantId(tenantId);
        Result<List<RoleDTO>> result = getListByCondition(param);

        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result.getEntityError());
        }
        List<ItemDTO> list = new ArrayList<>();
        ItemDTO itemDTO = null;
        for (RoleDTO roleDTO : result.getData()) {
            itemDTO = new ItemDTO();
            itemDTO.setValue(roleDTO.getRoleCode());
            itemDTO.setLabel(roleDTO.getRoleName());
            list.add(itemDTO);
        }
        itemDTO = null;
        return Result.responseSuccess(list);
    }

    @Override
    public Result<Integer> updateRoleByRoleCode(RoleSaveOrUpdateDTO roleAddOrUpdateDTO) {

        if (roleAddOrUpdateDTO == null
                || StringUtils.isEmpty(roleAddOrUpdateDTO.getRoleCode())) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        RoleDO roleDO = BeanUtils.switchToDO(roleAddOrUpdateDTO, RoleDO.class);
        Integer num = mapper.updateByRoleCode(roleDO);
        return Result.responseSuccess(num);
    }


    @Override
    public Result<Integer> insertRole(RoleSaveOrUpdateDTO param) {

        if (param == null
                || StringUtils.isEmpty(param.getTenantId())
                || CommonUtils.evalLong(param.getDeptId()) < 0
                || CommonUtils.evalLong(param.getCompanyId()) < 0
                || StringUtils.isEmpty(param.getRoleCode())) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        // 默认 key 格式   租户 +":" + key
        if (!param.getRoleCode().startsWith((param.getTenantId()))) {
            param.setRoleCode(param.getTenantId()
                    + BpmConstant.COMMON_CODE_SEPARATION_CHARACTER
                    + "role"
                    + BpmConstant.COMMON_CODE_SEPARATION_CHARACTER
                    + param.getRoleCode());
        }

        RoleDO temp = BeanUtils.switchToDO(param, RoleDO.class);
        Integer num = mapper.insert(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer> updateRole(RoleSaveOrUpdateDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        RoleDO temp = BeanUtils.switchToDO(param, RoleDO.class);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer> deleteRole(RoleSaveOrUpdateDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        RoleDO temp = BeanUtils.switchToDO(param, RoleDO.class);
        temp.setValidState(INVALID_STATE);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<RoleDTO> getRoleById(Long roleId) {

        if (roleId == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        RoleDO query = new RoleDO();
        query.setRoleId(roleId);
        query = mapper.selectOne(new QueryWrapper<>(query));

        if (query == null) {
            return Result.responseError(EntityError.DATA_NOT_FOUND_ERROR);
        }
        RoleDTO result = BeanUtils.switchToDTO(query, RoleDTO.class);
        return Result.responseSuccess(result);
    }
}
