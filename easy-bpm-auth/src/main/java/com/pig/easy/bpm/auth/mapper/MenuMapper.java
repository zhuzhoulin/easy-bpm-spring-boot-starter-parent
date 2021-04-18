package com.pig.easy.bpm.auth.mapper;

import com.pig.easy.bpm.auth.entity.MenuDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.pig.easy.bpm.auth.dto.request.*;
import com.pig.easy.bpm.auth.dto.response.*;

import java.util.List;
/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author pig
 * @since 2021-04-06
 */
@Mapper
public interface MenuMapper extends BaseMapper<MenuDO> {

    List<MenuDTO> getListByCondition(MenuQueryDTO param);

}
