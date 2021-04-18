package com.pig.easy.bpm.auth.service;

import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.auth.dto.request.UserRoleDetailQueryDTO;
import com.pig.easy.bpm.auth.dto.request.UserRoleReqDTO;
import com.pig.easy.bpm.auth.dto.response.UserRoleDTO;
import com.pig.easy.bpm.auth.dto.response.UserRoleDetailDTO;
import com.pig.easy.bpm.auth.entity.UserRoleDO;
import com.pig.easy.bpm.common.generator.BaseService;
import com.pig.easy.bpm.common.utils.Result;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pig
 * @since 2020-06-14
 */
public interface UserRoleService extends BaseService<UserRoleDO> {

    Result<List<UserRoleDTO>> getUserRoleListByUserId(Long userId);

    Result<List<UserRoleDTO>> getUserRoleListByUserIdAndTenantId(Long userId, String tenantId);

    Result<List<UserRoleDTO>> getUserRoleListByRoleId(Long roleId);

    Result<List<UserRoleDTO>> getUserRoleListByRoleIdAndTenantId(Long roleId, String tenantId);

    Result<PageInfo> getListByCondiction(UserRoleReqDTO userRoleReqDTO);

    Result<UserRoleDTO> getUserRoleById(Long id);

    Result<Integer> insertUserRole(UserRoleReqDTO userRoleReqDTO);

    Result<Integer> updateUserRoleById(UserRoleReqDTO userRoleReqDTO);

    Result<List<UserRoleDetailDTO>> getUserRoleDetailByRoleId(Long roleId);

    Result<List<UserRoleDetailDTO>> getUserRoleDetailByUserId(Long userId);

    Result<List<UserRoleDetailDTO>> getUserRoleDetailByRoleIdAndUserId(Long roleId, Long userId);

    Result<List<UserRoleDetailDTO>> getUserRoleDetailByCondition(UserRoleDetailQueryDTO userRoleDetailQueryDTO);
}
