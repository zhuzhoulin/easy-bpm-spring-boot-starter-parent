package com.pig.easy.bpm.auth.service;

import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.auth.dto.request.RoleGroupRoleDetailQueryDTO;
import com.pig.easy.bpm.auth.dto.request.RoleQueryDTO;
import com.pig.easy.bpm.auth.dto.request.RoleSaveOrUpdateDTO;
import com.pig.easy.bpm.auth.dto.response.ItemDTO;
import com.pig.easy.bpm.auth.dto.response.RoleDTO;
import com.pig.easy.bpm.auth.dto.response.RoleGroupRoleDetailDTO;
import com.pig.easy.bpm.auth.entity.RoleDO;
import com.pig.easy.bpm.common.generator.BaseService;
import com.pig.easy.bpm.common.utils.Result;

import java.util.List;
/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author pig
 * @since 2021-03-20
 */
public interface RoleService extends BaseService<RoleDO> {

    Result<PageInfo<RoleDTO>> getListPageByCondition(RoleQueryDTO param);

    Result<Integer> insertRole(RoleSaveOrUpdateDTO param);

    Result<Integer> updateRole(RoleSaveOrUpdateDTO param);

    Result<Integer> deleteRole(RoleSaveOrUpdateDTO param);

    Result<RoleDTO> getRoleById(Long roleId);

    Result<List<RoleDTO>> getListByCondition(RoleQueryDTO param);

    Result<PageInfo<RoleGroupRoleDetailDTO>> getRoleGroupRoleDetailByCondition(RoleGroupRoleDetailQueryDTO queryDTO);

    Result<List<RoleGroupRoleDetailDTO>> getRoleGroupRoleDetailList(RoleGroupRoleDetailQueryDTO queryDTO);

    Result<List<ItemDTO>> getRoleDictByTenantId(String tenantId);

    Result<Integer> updateRoleByRoleCode(RoleSaveOrUpdateDTO roleAddOrUpdateDTO);
}
