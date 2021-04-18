package com.pig.easy.bpm.auth.service;

import com.pig.easy.bpm.auth.entity.RoleGroupDO;
import com.pig.easy.bpm.common.generator.BaseService;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.common.generator.BaseService;
import com.pig.easy.bpm.common.utils.Result;
import com.pig.easy.bpm.auth.dto.request.*;
import com.pig.easy.bpm.auth.dto.response.*;
import java.util.List;
/**
 * <p>
 * 角色组 服务类
 * </p>
 *
 * @author pig
 * @since 2021-03-20
 */
public interface RoleGroupService extends BaseService<RoleGroupDO> {

    Result<PageInfo<RoleGroupDTO>> getListPageByCondition(RoleGroupQueryDTO param);

    Result<Integer> insertRoleGroup(RoleGroupSaveOrUpdateDTO param);

    Result<Integer> updateRoleGroup(RoleGroupSaveOrUpdateDTO param);

    Result<Integer> deleteRoleGroup(RoleGroupSaveOrUpdateDTO param);

    Result<RoleGroupDTO> getRoleGroupById(Long roleGroupId);

    Result<List<RoleGroupDTO>> getListByCondition(RoleGroupQueryDTO param);

    Result<List<ItemDTO>> getRoleGroupDict(String tenantId);

}
