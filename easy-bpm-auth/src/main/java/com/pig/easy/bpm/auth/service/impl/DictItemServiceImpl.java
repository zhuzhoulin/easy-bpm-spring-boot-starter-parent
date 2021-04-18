package com.pig.easy.bpm.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.auth.dto.request.DictItemQueryDTO;
import com.pig.easy.bpm.auth.dto.request.DictItemSaveOrUpdateDTO;
import com.pig.easy.bpm.auth.dto.response.DictItemDTO;
import com.pig.easy.bpm.auth.entity.DictItemDO;
import com.pig.easy.bpm.auth.mapper.DictItemMapper;
import com.pig.easy.bpm.auth.service.DictItemService;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.generator.BaseServiceImpl;
import com.pig.easy.bpm.common.utils.BeanUtils;
import com.pig.easy.bpm.common.utils.CommonUtils;
import com.pig.easy.bpm.common.utils.EasyBpmAsset;
import com.pig.easy.bpm.common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/**
 * <p>
 * 字典详细表 服务实现类
 * </p>
 *
 * @author pig
 * @since 2021-04-06
 */
@Service
public class DictItemServiceImpl extends BaseServiceImpl<DictItemMapper, DictItemDO> implements DictItemService {

    @Autowired
    DictItemMapper mapper;

    @Override
    public Result<PageInfo<DictItemDTO>> getListPageByCondition(DictItemQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        int pageIndex = CommonUtils.evalInt(param.getPageIndex(), DEFAULT_PAGE_INDEX);
        int pageSize = CommonUtils.evalInt(param.getPageSize(), DEFAULT_PAGE_SIZE);

        PageHelper.startPage(pageIndex, pageSize);
        param.setValidState(VALID_STATE);
        List<DictItemDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        PageInfo<DictItemDTO> pageInfo = new PageInfo<>(list);
        return Result.responseSuccess(pageInfo);
    }

    @Override
    public Result<List<DictItemDTO>> getListByCondition(DictItemQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        param.setValidState(VALID_STATE);
        List<DictItemDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        return Result.responseSuccess(list);
    }

    @Override
    public Result<List<DictItemDTO>> getListByDictCode(String dictCode) {

        EasyBpmAsset.isAssetEmpty(dictCode);
        List<DictItemDTO> result = mapper.getListByDictCode(dictCode);
        if(result == null){
            result = new ArrayList<>();
        }
        return Result.responseSuccess(result);
    }


    @Override
    public Result<Integer> insertDictItem(DictItemSaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        DictItemDO temp = BeanUtils.switchToDO(param, DictItemDO.class);
        temp.setCreateTime(LocalDateTime.now());
        Integer num = mapper.insert(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer>updateDictItem(DictItemSaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        DictItemDO temp = BeanUtils.switchToDO(param, DictItemDO.class);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer>deleteDictItem(DictItemSaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        DictItemDO temp = BeanUtils.switchToDO(param, DictItemDO.class);
        temp.setValidState(INVALID_STATE);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<DictItemDTO> getDictItemById(Long itemId){

        if (itemId == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        DictItemDO query = new DictItemDO();
        query.setItemId(itemId);
        query = mapper.selectOne(new QueryWrapper<>(query));

        if (query == null) {
            return Result.responseError(EntityError.DATA_NOT_FOUND_ERROR);
        }
        DictItemDTO result = BeanUtils.switchToDTO(query, DictItemDTO.class);
        return Result.responseSuccess(result);
    }
}
