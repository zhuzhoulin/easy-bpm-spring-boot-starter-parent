package com.pig.easy.bpm.generator.mapper;

import com.pig.easy.bpm.generator.entity.DbConfigDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.pig.easy.bpm.generator.dto.request.*;
import com.pig.easy.bpm.generator.dto.response.*;

import java.util.List;
/**
 * <p>
 * 数据源表 Mapper 接口
 * </p>
 *
 * @author pig
 * @since 2021-03-12
 */
@Mapper
public interface DbConfigMapper extends BaseMapper<DbConfigDO> {

    List<DbConfigDTO> getListByCondition(DbConfigQueryDTO param);

}
