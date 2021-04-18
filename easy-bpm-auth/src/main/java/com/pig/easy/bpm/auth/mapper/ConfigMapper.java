package com.pig.easy.bpm.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pig.easy.bpm.auth.dto.request.ConfigQueryDTO;
import com.pig.easy.bpm.auth.dto.response.ConfigDTO;
import com.pig.easy.bpm.auth.entity.ConfigDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
public interface ConfigMapper extends BaseMapper<ConfigDO> {

    List<ConfigDTO> getListByCondition(ConfigQueryDTO param);

    ConfigDO getConfigValue(@Param("tenantId") String tenantId, @Param("configKey") String configKey);
}
