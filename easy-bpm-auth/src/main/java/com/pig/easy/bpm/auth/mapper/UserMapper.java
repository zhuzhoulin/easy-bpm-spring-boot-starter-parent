package com.pig.easy.bpm.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pig.easy.bpm.auth.dto.request.UserQueryDTO;
import com.pig.easy.bpm.auth.dto.response.UserDTO;
import com.pig.easy.bpm.auth.entity.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author pig
 * @since 2021-03-20
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {

    @Select("select count(1)+1 from bpm_user")
    Long getUserId();

    List<UserDTO> getListByCondition(UserQueryDTO param);

    UserDO login(String userName);
}
