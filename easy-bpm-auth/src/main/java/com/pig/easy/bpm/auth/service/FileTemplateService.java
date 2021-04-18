package com.pig.easy.bpm.auth.service;

import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.auth.dto.request.FileTemplateQueryDTO;
import com.pig.easy.bpm.auth.dto.request.FileTemplateSaveOrUpdateDTO;
import com.pig.easy.bpm.auth.dto.response.FileTemplateDTO;
import com.pig.easy.bpm.auth.entity.FileTemplateDO;
import com.pig.easy.bpm.common.generator.BaseService;
import com.pig.easy.bpm.common.utils.Result;

import java.util.List;
/**
 * <p>
 * 模板文件表 服务类
 * </p>
 *
 * @author pig
 * @since 2021-03-20
 */
public interface FileTemplateService extends BaseService<FileTemplateDO> {

    Result<PageInfo<FileTemplateDTO>> getListPageByCondition(FileTemplateQueryDTO param);

    Result<Integer> insertFileTemplate(FileTemplateSaveOrUpdateDTO param);

    Result<Integer> updateFileTemplate(FileTemplateSaveOrUpdateDTO param);

    Result<Integer> deleteFileTemplate(FileTemplateSaveOrUpdateDTO param);

    Result<FileTemplateDTO> getFileTemplateById(String tempalteId);

    Result<List<FileTemplateDTO>> getListByCondition(FileTemplateQueryDTO param);

    Result<List<FileTemplateDTO>> getListByProcessIdAndTenantId(String tenantId, Long processId);

}
