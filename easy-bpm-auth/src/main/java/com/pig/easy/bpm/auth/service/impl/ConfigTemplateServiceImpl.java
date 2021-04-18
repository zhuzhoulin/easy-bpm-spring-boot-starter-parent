package com.pig.easy.bpm.auth.service.impl;

import com.pig.easy.bpm.auth.entity.ConfigTemplateDO;
import com.pig.easy.bpm.auth.mapper.ConfigTemplateMapper;
import com.pig.easy.bpm.auth.service.ConfigTemplateService;
import com.pig.easy.bpm.common.generator.BaseServiceImpl;
import com.pig.easy.bpm.common.utils.EasyBpmAsset;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.generator.BaseServiceImpl;
import com.pig.easy.bpm.common.utils.BeanUtils;
import com.pig.easy.bpm.common.utils.CommonUtils;
import com.pig.easy.bpm.common.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.pig.easy.bpm.auth.dto.request.*;
import com.pig.easy.bpm.auth.dto.response.*;

import java.util.ArrayList;
import java.util.Date;
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
public class ConfigTemplateServiceImpl extends BaseServiceImpl<ConfigTemplateMapper, ConfigTemplateDO> implements ConfigTemplateService {

    @Autowired
    ConfigTemplateMapper mapper;

    @Override
    public Result<PageInfo<ConfigTemplateDTO>> getListPageByCondition(ConfigTemplateQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        int pageIndex = CommonUtils.evalInt(param.getPageIndex(), DEFAULT_PAGE_INDEX);
        int pageSize = CommonUtils.evalInt(param.getPageSize(), DEFAULT_PAGE_SIZE);

        PageHelper.startPage(pageIndex, pageSize);
        param.setValidState(VALID_STATE);
        List<ConfigTemplateDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        PageInfo<ConfigTemplateDTO> pageInfo = new PageInfo<>(list);
        return Result.responseSuccess(pageInfo);
    }

    @Override
    public Result<List<ConfigTemplateDTO>> getListByCondition(ConfigTemplateQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        param.setValidState(VALID_STATE);
        List<ConfigTemplateDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        return Result.responseSuccess(list);
    }

    @Override
    public Result<ConfigTemplateDTO> getConfigTemplateByKey(String configKey) {

        EasyBpmAsset.isAssetEmpty(configKey);
        ConfigTemplateDO configTemplate = new ConfigTemplateDO();
        configTemplate.setTemplateKey(configKey);
        configTemplate.setValidState(VALID_STATE);
        QueryWrapper<ConfigTemplateDO> entityWrapper = new QueryWrapper<>(configTemplate);
        ConfigTemplateDO configTemplate1 = mapper.selectOne(entityWrapper);
        EasyBpmAsset.isEmpty(configTemplate1, "templatekey [" + configKey + "] no configTemplate in database");
        ConfigTemplateDTO configTemplateDTO = BeanUtils.switchToDTO(configTemplate1, ConfigTemplateDTO.class);
        return Result.responseSuccess(configTemplateDTO);
    }

    @Override
    public Result<Integer> insertConfigTemplate(ConfigTemplateSaveOrUpdateDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        ConfigTemplateDO temp = BeanUtils.switchToDO(param, ConfigTemplateDO.class);
        Integer num = mapper.insert(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer> updateConfigTemplate(ConfigTemplateSaveOrUpdateDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        ConfigTemplateDO temp = BeanUtils.switchToDO(param, ConfigTemplateDO.class);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer> deleteConfigTemplate(ConfigTemplateSaveOrUpdateDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        ConfigTemplateDO temp = BeanUtils.switchToDO(param, ConfigTemplateDO.class);
        temp.setValidState(INVALID_STATE);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<ConfigTemplateDTO> getConfigTemplateById(Long templateId) {

        if (templateId == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        ConfigTemplateDO query = new ConfigTemplateDO();
        query.setTemplateId(templateId);
        query = mapper.selectOne(new QueryWrapper<>(query));

        if (query == null) {
            return Result.responseError(EntityError.DATA_NOT_FOUND_ERROR);
        }
        ConfigTemplateDTO result = BeanUtils.switchToDTO(query, ConfigTemplateDTO.class);
        return Result.responseSuccess(result);
    }
}
