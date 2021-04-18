package com.pig.easy.bpm.auth.mapper;

import com.pig.easy.bpm.auth.entity.RoleDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.pig.easy.bpm.auth.dto.request.*;
import com.pig.easy.bpm.auth.dto.response.*;

import java.util.List;
/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author pig
 * @since 2021-03-20
 */
@Mapper
public interface RoleMapper extends BaseMapper<RoleDO> {

    List<RoleDTO> getListByCondition(RoleQueryDTO param);

    Integer getCountByCondition(RoleGroupRoleDetailQueryDTO queryDTO);

    List<RoleGroupRoleDetailDTO> getRoleGroupRoleDetailByCondition(RoleGroupRoleDetailQueryDTO queryDTO);

    Integer updateByRoleCode(RoleDO roleDO);
}
