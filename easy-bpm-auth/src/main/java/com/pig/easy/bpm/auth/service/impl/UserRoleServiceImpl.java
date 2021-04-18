package com.pig.easy.bpm.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.auth.dto.request.UserRoleDetailQueryDTO;
import com.pig.easy.bpm.auth.dto.request.UserRoleReqDTO;
import com.pig.easy.bpm.auth.dto.response.UserRoleDTO;
import com.pig.easy.bpm.auth.dto.response.UserRoleDetailDTO;
import com.pig.easy.bpm.auth.entity.UserRoleDO;
import com.pig.easy.bpm.auth.mapper.UserRoleMapper;
import com.pig.easy.bpm.auth.service.UserRoleService;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.generator.BaseServiceImpl;
import com.pig.easy.bpm.common.utils.BeanUtils;
import com.pig.easy.bpm.common.utils.CommonUtils;
import com.pig.easy.bpm.common.utils.EasyBpmAsset;
import com.pig.easy.bpm.common.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author pig
 * @since 2020-06-14
 */
@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRoleMapper, UserRoleDO> implements UserRoleService {

    @Autowired
    UserRoleMapper userRoleMapper;

    @Override
    public Result<List<UserRoleDTO>> getUserRoleListByUserId(Long userId) {

        if (CommonUtils.evalLong(userId) < 0) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        UserRoleDO userRoleDO = new UserRoleDO();
        userRoleDO.setUserId(userId);
        userRoleDO.setValidState(VALID_STATE);

        QueryWrapper<UserRoleDO> queryWrapper = new QueryWrapper<>(userRoleDO).orderByAsc("userId");

        List<UserRoleDO> roleDOList = userRoleMapper.selectList(queryWrapper);
        List<UserRoleDTO> result = switchDoToDtoList(roleDOList);

        return Result.responseSuccess(result);
    }


    @Override
    public Result<List<UserRoleDTO>> getUserRoleListByUserIdAndTenantId(Long userId, String tenantId) {

        if (CommonUtils.evalLong(userId) < 0
                || StringUtils.isEmpty(tenantId)) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        UserRoleDO userRoleDO = new UserRoleDO();
        userRoleDO.setUserId(userId);
        userRoleDO.setValidState(VALID_STATE);
        userRoleDO.setTenantId(tenantId);

        QueryWrapper<UserRoleDO> queryWrapper = new QueryWrapper<>(userRoleDO).orderByAsc("userId");
        ;

        List<UserRoleDO> roleDOList = userRoleMapper.selectList(queryWrapper);
        List<UserRoleDTO> result = switchDoToDtoList(roleDOList);
        return Result.responseSuccess(result);
    }

    @Override
    public Result<List<UserRoleDTO>> getUserRoleListByRoleId(Long roleId) {

        if (CommonUtils.evalLong(roleId) < 0) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        UserRoleDO userRoleDO = new UserRoleDO();
        userRoleDO.setRoleId(roleId);
        userRoleDO.setValidState(VALID_STATE);

        QueryWrapper<UserRoleDO> queryWrapper = new QueryWrapper<>(userRoleDO);

        List<UserRoleDO> roleDOList = userRoleMapper.selectList(queryWrapper);
        List<UserRoleDTO> result = switchDoToDtoList(roleDOList);

        return Result.responseSuccess(result);
    }

    @Override
    public Result<List<UserRoleDTO>> getUserRoleListByRoleIdAndTenantId(Long roleId, String tenantId) {
        if (CommonUtils.evalLong(roleId) < 0
                || StringUtils.isEmpty(tenantId)) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        UserRoleDO userRoleDO = new UserRoleDO();
        userRoleDO.setRoleId(roleId);
        userRoleDO.setValidState(VALID_STATE);
        userRoleDO.setTenantId(tenantId);

        QueryWrapper<UserRoleDO> queryWrapper = new QueryWrapper<>(userRoleDO).orderByAsc("userId");
        ;

        List<UserRoleDO> roleDOList = userRoleMapper.selectList(queryWrapper);
        List<UserRoleDTO> result = switchDoToDtoList(roleDOList);
        return Result.responseSuccess(result);
    }

    @Override
    public Result<PageInfo> getListByCondiction(UserRoleReqDTO userRoleReqDTO) {

        if (userRoleReqDTO == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        int pageIndex = CommonUtils.evalInt(userRoleReqDTO.getPageIndex(), DEFAULT_PAGE_INDEX);
        int pageSize = CommonUtils.evalInt(userRoleReqDTO.getPageSize(), DEFAULT_PAGE_SIZE);

        UserRoleDO userRoleDO = BeanUtils.switchToDO(userRoleReqDTO, UserRoleDO.class);
        userRoleDO.setValidState(VALID_STATE);
        PageHelper.startPage(pageIndex, pageSize);
        List<UserRoleDTO> userRoleDTOList = userRoleMapper.getListByCondiction(userRoleDO);
        if (userRoleDTOList == null) {
            userRoleDTOList = new ArrayList<>();
        }
        PageInfo<UserRoleDTO> pageInfo = new PageInfo<>(userRoleDTOList);
        return Result.responseSuccess(pageInfo);
    }

    @Override
    public Result<UserRoleDTO> getUserRoleById(Long id) {

        if (CommonUtils.evalLong(id) < 0) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        UserRoleDO userRoleDO = userRoleMapper.selectById(id);
        if (userRoleDO == null) {
            return Result.responseError(EntityError.DATA_NOT_FOUND_ERROR);
        }
        UserRoleDTO userRoleDTO = BeanUtils.switchToDTO(userRoleDO, UserRoleDTO.class);
        return Result.responseSuccess(userRoleDTO);
    }

    @Override
    public Result<Integer> insertUserRole(UserRoleReqDTO userRoleReqDTO) {

        if (userRoleReqDTO == null
                || CommonUtils.evalLong(userRoleReqDTO.getUserId()) < 0
                || CommonUtils.evalLong(userRoleReqDTO.getRoleId()) < 0
                || StringUtils.isEmpty(userRoleReqDTO.getTenantId())
                ) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        UserRoleDO userRoleDO = BeanUtils.switchToDO(userRoleReqDTO, UserRoleDO.class);
        return Result.responseSuccess(userRoleMapper.insert(userRoleDO));
    }

    @Override
    public Result<Integer> updateUserRoleById(UserRoleReqDTO userRoleReqDTO) {

        if (userRoleReqDTO == null
                || CommonUtils.evalLong(userRoleReqDTO.getUserId()) < 0
                || CommonUtils.evalLong(userRoleReqDTO.getRoleId()) < 0
                || CommonUtils.evalLong(userRoleReqDTO.getId()) < 0
                || StringUtils.isEmpty(userRoleReqDTO.getTenantId())
                ) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        UserRoleDO userRoleDO = BeanUtils.switchToDO(userRoleReqDTO, UserRoleDO.class);
        return Result.responseSuccess(userRoleMapper.updateById(userRoleDO));
    }

    @Override
    public Result<List<UserRoleDetailDTO>> getUserRoleDetailByRoleId(Long roleId) {

        EasyBpmAsset.isAssetEmpty(roleId);

        List<UserRoleDetailDTO> result = userRoleMapper.getUserRoleDetailByRoleIdOrUserId(roleId, null);

        if (result == null) {
            result = new ArrayList<>();
        }
        return Result.responseSuccess(distinctByKey(result));
    }

    @Override
    public Result<List<UserRoleDetailDTO>> getUserRoleDetailByUserId(Long userId) {

        EasyBpmAsset.isAssetEmpty(userId);

        List<UserRoleDetailDTO> result = userRoleMapper.getUserRoleDetailByRoleIdOrUserId(null, userId);
        if (result == null) {
            result = new ArrayList<>();
        }
        return Result.responseSuccess(distinctByKey(result));
    }

    @Override
    public Result<List<UserRoleDetailDTO>> getUserRoleDetailByRoleIdAndUserId(Long roleId, Long userId) {
        EasyBpmAsset.isAssetEmpty(userId);
        EasyBpmAsset.isAssetEmpty(roleId);
        List<UserRoleDetailDTO> result = userRoleMapper.getUserRoleDetailByRoleIdOrUserId(null, userId);
        if (result == null) {
            result = new ArrayList<>();
        }

        return Result.responseSuccess(distinctByKey(result));
    }

    @Override
    public Result<List<UserRoleDetailDTO>> getUserRoleDetailByCondition(UserRoleDetailQueryDTO userRoleDetailQueryDTO) {

        EasyBpmAsset.isAssetEmpty(userRoleDetailQueryDTO);
        List<UserRoleDetailDTO> result = userRoleMapper.getUserRoleDetailByCondition(userRoleDetailQueryDTO);

        if (result == null) {
            result = new ArrayList<>();
        }

        return Result.responseSuccess(distinctByKey(result));
    }

    private List<UserRoleDTO> switchDoToDtoList(List<UserRoleDO> roleDOList) {

        List<UserRoleDTO> result = new ArrayList<>();

        if (roleDOList != null) {
            UserRoleDTO userRoleDTO = new UserRoleDTO();
            for (UserRoleDO temp : roleDOList) {
                userRoleDTO = BeanUtils.switchToDTO(temp, UserRoleDTO.class);
                result.add(userRoleDTO);
            }
            userRoleDTO = null;
        }
        return result;
    }

    private List<UserRoleDetailDTO> distinctByKey(List<UserRoleDetailDTO> list) {
        if (list == null) {
            return new ArrayList<>();
        }
        // 根据 userId 和 roleId 去重
        List<UserRoleDetailDTO> collect = list.stream().filter(distinctByKey((p) -> (p.getUserId()))).filter(distinctByKey((p) -> (p.getRoleId()))).collect(Collectors.toList());
        return collect;
    }
}
