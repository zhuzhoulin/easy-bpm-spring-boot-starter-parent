package com.pig.easy.bpm.auth.service.impl;

import com.pig.easy.bpm.auth.entity.FileTemplateDO;
import com.pig.easy.bpm.auth.mapper.FileTemplateMapper;
import com.pig.easy.bpm.auth.service.FileTemplateService;
import com.pig.easy.bpm.common.generator.BaseServiceImpl;
import com.pig.easy.bpm.common.utils.EasyBpmAsset;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.utils.BeanUtils;
import com.pig.easy.bpm.common.utils.CommonUtils;
import com.pig.easy.bpm.common.utils.Result;
import com.pig.easy.bpm.auth.dto.request.*;
import com.pig.easy.bpm.auth.dto.response.*;

import java.util.ArrayList;
import java.util.List;
/**
 * <p>
 * 模板文件表 服务实现类
 * </p>
 *
 * @author pig
 * @since 2021-03-20
 */
@Service
public class FileTemplateServiceImpl extends BaseServiceImpl<FileTemplateMapper, FileTemplateDO> implements FileTemplateService {

    @Autowired
    FileTemplateMapper mapper;

    @Override
    public Result<PageInfo<FileTemplateDTO>> getListPageByCondition(FileTemplateQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        int pageIndex = CommonUtils.evalInt(param.getPageIndex(), DEFAULT_PAGE_INDEX);
        int pageSize = CommonUtils.evalInt(param.getPageSize(), DEFAULT_PAGE_SIZE);

        PageHelper.startPage(pageIndex, pageSize);
        param.setValidState(VALID_STATE);
        List<FileTemplateDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        PageInfo<FileTemplateDTO> pageInfo = new PageInfo<>(list);
        return Result.responseSuccess(pageInfo);
    }

    @Override
    public Result<List<FileTemplateDTO>> getListByCondition(FileTemplateQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        param.setValidState(VALID_STATE);
        List<FileTemplateDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        return Result.responseSuccess(list);
    }

    @Override
    public Result<List<FileTemplateDTO>> getListByProcessIdAndTenantId(String tenantId, Long processId) {
        EasyBpmAsset.isEmpty(tenantId);
        EasyBpmAsset.isEmpty(processId);

        List<FileTemplateDO> selectList = mapper.selectList(new QueryWrapper(FileTemplateDO.builder().tenantId(tenantId).processId(processId.intValue()).validState(VALID_STATE).build()));
        if(selectList == null){
            selectList = new ArrayList<>();
        }

        List<FileTemplateDTO> result = new ArrayList<>();
        FileTemplateDTO fileTempleteDTO = null;
        for (FileTemplateDO fileTempleteDO : selectList) {
            fileTempleteDTO = BeanUtils.switchToDTO(fileTempleteDO, FileTemplateDTO.class);
            result.add(fileTempleteDTO);
        }
        return Result.responseSuccess(result);
    }


    @Override
    public Result<Integer> insertFileTemplate(FileTemplateSaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        FileTemplateDO temp = BeanUtils.switchToDO(param, FileTemplateDO.class);
        Integer num = mapper.insert(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer>updateFileTemplate(FileTemplateSaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        FileTemplateDO temp = BeanUtils.switchToDO(param, FileTemplateDO.class);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer>deleteFileTemplate(FileTemplateSaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        FileTemplateDO temp = BeanUtils.switchToDO(param, FileTemplateDO.class);
        temp.setValidState(INVALID_STATE);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<FileTemplateDTO> getFileTemplateById(String tempalteId){

        if (tempalteId == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        FileTemplateDO query = new FileTemplateDO();
        query.setTempalteId(tempalteId);
        query = mapper.selectOne(new QueryWrapper<>(query));

        if (query == null) {
            return Result.responseError(EntityError.DATA_NOT_FOUND_ERROR);
        }
        FileTemplateDTO result = BeanUtils.switchToDTO(query, FileTemplateDTO.class);
        return Result.responseSuccess(result);
    }
}
