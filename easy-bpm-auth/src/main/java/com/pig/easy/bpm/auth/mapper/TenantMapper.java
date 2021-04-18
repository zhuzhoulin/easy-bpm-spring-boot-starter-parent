package com.pig.easy.bpm.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pig.easy.bpm.auth.dto.request.TenantQueryDTO;
import com.pig.easy.bpm.auth.dto.response.TenantDTO;
import com.pig.easy.bpm.auth.entity.TenantDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 租户表 Mapper 接口
 * </p>
 *
 * @author pig
 * @since 2020-09-02
 */
@Mapper
public interface TenantMapper extends BaseMapper<TenantDO> {

        List<TenantDTO> getListByCondition(TenantQueryDTO param);
}
