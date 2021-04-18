package com.pig.easy.bpm.generator.mapper;

import com.pig.easy.bpm.generator.entity.VersionDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.pig.easy.bpm.generator.dto.request.*;
import com.pig.easy.bpm.generator.dto.response.*;

import java.util.List;
/**
 * <p>
 * 版本表 Mapper 接口
 * </p>
 *
 * @author pig
 * @since 2021-03-10
 */
@Mapper
public interface VersionMapper extends BaseMapper<VersionDO> {

    List<VersionDTO> getListByCondition(VersionQueryDTO param);

}
