package com.pig.easy.bpm.generator.service;

import com.pig.easy.bpm.generator.entity.TemplateGroupDO;
import com.pig.easy.bpm.common.generator.BaseService;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.common.generator.BaseService;
import com.pig.easy.bpm.common.utils.Result;
import com.pig.easy.bpm.generator.dto.request.*;
import com.pig.easy.bpm.generator.dto.response.*;
import java.util.List;
/**
 * <p>
 * 模板分组表 服务类
 * </p>
 *
 * @author pig
 * @since 2021-03-08
 */
public interface TemplateGroupService extends BaseService<TemplateGroupDO> {

    Result<PageInfo<TemplateGroupDTO>> getListPageByCondition(TemplateGroupQueryDTO param);

    Result<Integer> insertTemplateGroup(TemplateGroupSaveOrUpdateDTO param);

    Result<Integer> updateTemplateGroup(TemplateGroupSaveOrUpdateDTO param);

    Result<Integer> deleteTemplateGroup(TemplateGroupSaveOrUpdateDTO param);

    Result<TemplateGroupDTO> getTemplateGroupById(Long templateGroupId);

    Result<List<TemplateGroupDTO>> getListByCondition(TemplateGroupQueryDTO param);
}
