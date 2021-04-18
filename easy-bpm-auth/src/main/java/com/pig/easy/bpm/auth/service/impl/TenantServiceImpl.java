package com.pig.easy.bpm.auth.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.auth.dto.request.TenantQueryDTO;
import com.pig.easy.bpm.auth.dto.request.TenantSaveOrUpdateDTO;
import com.pig.easy.bpm.auth.dto.response.TenantDTO;
import com.pig.easy.bpm.auth.entity.TenantDO;
import com.pig.easy.bpm.auth.mapper.TenantMapper;
import com.pig.easy.bpm.auth.service.TenantService;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.generator.BaseServiceImpl;
import com.pig.easy.bpm.common.utils.BeanUtils;
import com.pig.easy.bpm.common.utils.CommonUtils;
import com.pig.easy.bpm.common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 租户表 服务实现类
 * </p>
 *
 * @author pig
 * @since 2020-09-02
 */
@Service
public class TenantServiceImpl extends BaseServiceImpl<TenantMapper, TenantDO> implements TenantService {

    @Autowired
    TenantMapper mapper;

    @Override
    public Result<PageInfo<TenantDTO>> getListByCondition(TenantQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        int pageIndex = CommonUtils.evalInt(param.getPageIndex(), DEFAULT_PAGE_INDEX);
        int pageSize = CommonUtils.evalInt(param.getPageSize(), DEFAULT_PAGE_SIZE);

        PageHelper.startPage(pageIndex, pageSize);
        param.setValidState(VALID_STATE);
        List<TenantDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        PageInfo<TenantDTO> pageInfo = new PageInfo<>(list);
        return Result.responseSuccess(pageInfo);
    }


    @Override
    public Result<Integer> insertTenant(TenantSaveOrUpdateDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        TenantDO temp = BeanUtils.switchToDO(param, TenantDO.class);
        Integer num = mapper.insert(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer> updateTenant(TenantSaveOrUpdateDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        TenantDO temp = BeanUtils.switchToDO(param, TenantDO.class);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer> deleteTenant(TenantSaveOrUpdateDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        TenantDO temp = BeanUtils.switchToDO(param, TenantDO.class);
        temp.setValidState(INVALID_STATE);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

}
