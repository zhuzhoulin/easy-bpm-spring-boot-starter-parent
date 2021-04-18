package com.pig.easy.bpm.generator.service;

import com.pig.easy.bpm.generator.entity.TemplateDO;
import com.pig.easy.bpm.common.generator.BaseService;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.common.generator.BaseService;
import com.pig.easy.bpm.common.utils.Result;
import com.pig.easy.bpm.generator.dto.request.*;
import com.pig.easy.bpm.generator.dto.response.*;
import java.util.List;
/**
 * <p>
 * 模板表 服务类
 * </p>
 *
 * @author pig
 * @since 2021-03-11
 */
public interface TemplateService extends BaseService<TemplateDO> {

    Result<PageInfo<TemplateDTO>> getListPageByCondition(TemplateQueryDTO param);

    Result<Integer> insertTemplate(TemplateSaveOrUpdateDTO param);

    Result<Integer> updateTemplate(TemplateSaveOrUpdateDTO param);

    Result<Integer> deleteTemplate(TemplateSaveOrUpdateDTO param);

    Result<TemplateDTO> getTemplateById(Long templateId);

    Result<List<TemplateDTO>> getListByCondition(TemplateQueryDTO param);
}
