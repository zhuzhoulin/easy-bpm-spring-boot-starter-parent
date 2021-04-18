package com.pig.easy.bpm.auth.service;

import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.auth.dto.request.ConfigTemplateQueryDTO;
import com.pig.easy.bpm.auth.dto.request.ConfigTemplateSaveOrUpdateDTO;
import com.pig.easy.bpm.auth.dto.response.ConfigTemplateDTO;
import com.pig.easy.bpm.auth.entity.ConfigTemplateDO;
import com.pig.easy.bpm.common.generator.BaseService;
import com.pig.easy.bpm.common.utils.Result;

import java.util.List;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pig
 * @since 2021-03-20
 */
public interface ConfigTemplateService extends BaseService<ConfigTemplateDO> {

    Result<PageInfo<ConfigTemplateDTO>> getListPageByCondition(ConfigTemplateQueryDTO param);

    Result<Integer> insertConfigTemplate(ConfigTemplateSaveOrUpdateDTO param);

    Result<Integer> updateConfigTemplate(ConfigTemplateSaveOrUpdateDTO param);

    Result<Integer> deleteConfigTemplate(ConfigTemplateSaveOrUpdateDTO param);

    Result<ConfigTemplateDTO> getConfigTemplateById(Long templateId);

    Result<List<ConfigTemplateDTO>> getListByCondition(ConfigTemplateQueryDTO param);

    Result<ConfigTemplateDTO> getConfigTemplateByKey(String configKey);

}
