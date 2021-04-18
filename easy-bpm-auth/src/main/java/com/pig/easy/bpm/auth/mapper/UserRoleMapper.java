package com.pig.easy.bpm.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pig.easy.bpm.auth.dto.request.UserRoleDetailQueryDTO;
import com.pig.easy.bpm.auth.dto.response.UserRoleDTO;
import com.pig.easy.bpm.auth.dto.response.UserRoleDetailDTO;
import com.pig.easy.bpm.auth.entity.UserRoleDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author pig
 * @since 2020-06-14
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRoleDO> {

    List<UserRoleDTO> getListByCondiction(UserRoleDO userRoleDO);

    List<UserRoleDetailDTO> getUserRoleDetailByRoleIdOrUserId(@Param("roleId") Long roleId, @Param("userId") Long userId);

    List<UserRoleDetailDTO> getUserRoleDetailByCondition(UserRoleDetailQueryDTO userRoleDetailQueryDTO);
}
