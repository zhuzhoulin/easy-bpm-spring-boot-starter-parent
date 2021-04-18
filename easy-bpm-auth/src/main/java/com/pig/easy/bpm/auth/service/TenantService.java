package com.pig.easy.bpm.auth.service;

import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.auth.dto.request.TenantQueryDTO;
import com.pig.easy.bpm.auth.dto.request.TenantSaveOrUpdateDTO;
import com.pig.easy.bpm.auth.dto.response.TenantDTO;
import com.pig.easy.bpm.auth.entity.TenantDO;
import com.pig.easy.bpm.common.generator.BaseService;
import com.pig.easy.bpm.common.utils.Result;

/**
 * <p>
 * 租户表 服务类
 * </p>
 *
 * @author pig
 * @since 2020-09-02
 */
public interface TenantService extends BaseService<TenantDO> {

    Result<PageInfo<TenantDTO>> getListByCondition(TenantQueryDTO param);

    Result<Integer> insertTenant(TenantSaveOrUpdateDTO param);

    Result<Integer> updateTenant(TenantSaveOrUpdateDTO param);

    Result<Integer> deleteTenant(TenantSaveOrUpdateDTO param);

}
