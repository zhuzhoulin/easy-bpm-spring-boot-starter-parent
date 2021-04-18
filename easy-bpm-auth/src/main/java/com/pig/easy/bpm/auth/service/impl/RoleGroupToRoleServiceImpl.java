package com.pig.easy.bpm.auth.service.impl;


import com.pig.easy.bpm.auth.entity.RoleGroupToRoleDO;
import com.pig.easy.bpm.auth.mapper.RoleGroupToRoleMapper;
import com.pig.easy.bpm.auth.service.RoleGroupToRoleService;
import com.pig.easy.bpm.common.generator.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pig
 * @since 2020-06-14
 */
@Service
public class RoleGroupToRoleServiceImpl extends BaseServiceImpl<RoleGroupToRoleMapper, RoleGroupToRoleDO> implements RoleGroupToRoleService {

    @Autowired
    RoleGroupToRoleMapper roleGroupToRoleMapper;
}
