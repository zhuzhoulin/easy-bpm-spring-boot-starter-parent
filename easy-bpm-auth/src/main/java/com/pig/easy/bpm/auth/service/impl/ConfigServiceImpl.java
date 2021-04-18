package com.pig.easy.bpm.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.auth.dto.request.ConfigQueryDTO;
import com.pig.easy.bpm.auth.dto.request.ConfigSaveOrUpdateDTO;
import com.pig.easy.bpm.auth.dto.response.ConfigDTO;
import com.pig.easy.bpm.auth.dto.response.ConfigTemplateDTO;
import com.pig.easy.bpm.auth.entity.ConfigDO;
import com.pig.easy.bpm.auth.mapper.ConfigMapper;
import com.pig.easy.bpm.auth.service.ConfigService;
import com.pig.easy.bpm.auth.service.ConfigTemplateService;
import com.pig.easy.bpm.auth.utils.TreeUtils;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.generator.BaseServiceImpl;
import com.pig.easy.bpm.common.utils.BeanUtils;
import com.pig.easy.bpm.common.utils.CommonUtils;
import com.pig.easy.bpm.common.utils.EasyBpmAsset;
import com.pig.easy.bpm.common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author pig
 * @since 2021-03-20
 */
@Service
public class ConfigServiceImpl extends BaseServiceImpl<ConfigMapper, ConfigDO> implements ConfigService {

    @Autowired
    ConfigMapper mapper;

    @Autowired
    ConfigTemplateService configTemplateService;


    @Override
    public Result<PageInfo<ConfigDTO>> getListPageByCondition(ConfigQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        int pageIndex = CommonUtils.evalInt(param.getPageIndex(), DEFAULT_PAGE_INDEX);
        int pageSize = CommonUtils.evalInt(param.getPageSize(), DEFAULT_PAGE_SIZE);

        PageHelper.startPage(pageIndex, pageSize);
        param.setValidState(VALID_STATE);
        List<ConfigDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        PageInfo<ConfigDTO> pageInfo = new PageInfo<>(list);
        return Result.responseSuccess(pageInfo);
    }

    @Override
    public Result<List<ConfigDTO>> getListByCondition(ConfigQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        param.setValidState(VALID_STATE);
        List<ConfigDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        return Result.responseSuccess(list);
    }

    @Cacheable(value = "config", key = "#p0.concat('-').concat(#p1)", unless = "#result == null || #result.entityError == null || #result.entityError.code != 200 || #result.data == null ")
    @Override
    public Result<Object> getConfigValue(String tenantId, String configKey) {

        EasyBpmAsset.isAssetEmpty(tenantId);
        EasyBpmAsset.isAssetEmpty(configKey);

        ConfigDO config = mapper.getConfigValue(tenantId, configKey);
        if (config == null) {
            Result<ConfigTemplateDTO> result = configTemplateService.getConfigTemplateByKey(configKey);
            if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                return Result.responseError(result.getEntityError());
            }
            config = BeanUtils.objectToBean(result.getData(), ConfigDO.class);
            if (config != null) {
                config.setConfigKey(result.getData().getTemplateKey());
                config.setConfigValue(result.getData().getTemplateValue());
                config.setConfigType(result.getData().getTemplateType());
            }
        }

        return Result.responseSuccess(TreeUtils.getConfigValueByKey(config));
    }


    @Override
    public Result<Integer> insertConfig(ConfigSaveOrUpdateDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        ConfigDO temp = BeanUtils.switchToDO(param, ConfigDO.class);
        Integer num = mapper.insert(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer> updateConfig(ConfigSaveOrUpdateDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        ConfigDO temp = BeanUtils.switchToDO(param, ConfigDO.class);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer> deleteConfig(ConfigSaveOrUpdateDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        ConfigDO temp = BeanUtils.switchToDO(param, ConfigDO.class);
        temp.setValidState(INVALID_STATE);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<ConfigDTO> getConfigById(Long configId) {

        if (configId == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        ConfigDO query = new ConfigDO();
        query.setConfigId(configId);
        query = mapper.selectOne(new QueryWrapper<>(query));

        if (query == null) {
            return Result.responseError(EntityError.DATA_NOT_FOUND_ERROR);
        }
        ConfigDTO result = BeanUtils.switchToDTO(query, ConfigDTO.class);
        return Result.responseSuccess(result);
    }
}
