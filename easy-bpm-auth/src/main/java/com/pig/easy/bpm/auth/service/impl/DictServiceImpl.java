package com.pig.easy.bpm.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.auth.dto.request.DictQueryDTO;
import com.pig.easy.bpm.auth.dto.request.DictSaveOrUpdateDTO;
import com.pig.easy.bpm.auth.dto.response.DictDTO;
import com.pig.easy.bpm.auth.dto.response.DictItemDTO;
import com.pig.easy.bpm.auth.dto.response.ItemDTO;
import com.pig.easy.bpm.auth.entity.DictDO;
import com.pig.easy.bpm.auth.mapper.DictMapper;
import com.pig.easy.bpm.auth.service.DictItemService;
import com.pig.easy.bpm.auth.service.DictService;
import com.pig.easy.bpm.common.constant.BpmConstant;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.generator.BaseServiceImpl;
import com.pig.easy.bpm.common.utils.BeanUtils;
import com.pig.easy.bpm.common.utils.CommonUtils;
import com.pig.easy.bpm.common.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author pig
 * @since 2021-04-06
 */
@Service
public class DictServiceImpl extends BaseServiceImpl<DictMapper, DictDO> implements DictService {

    @Autowired
    DictMapper mapper;
    @Autowired
    DictItemService dictItemService;

    @Override
    public Result<PageInfo<DictDTO>> getListPageByCondition(DictQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        int pageIndex = CommonUtils.evalInt(param.getPageIndex(), DEFAULT_PAGE_INDEX);
        int pageSize = CommonUtils.evalInt(param.getPageSize(), DEFAULT_PAGE_SIZE);

        PageHelper.startPage(pageIndex, pageSize);
        param.setValidState(VALID_STATE);
        List<DictDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        PageInfo<DictDTO> pageInfo = new PageInfo<>(list);
        return Result.responseSuccess(pageInfo);
    }

    @Override
    public Result<List<DictDTO>> getListByCondition(DictQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        param.setValidState(VALID_STATE);
        List<DictDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        return Result.responseSuccess(list);
    }

    @Override
    public Result<List<ItemDTO>> getDictListByDictCode(String dictCode) {
        if(StringUtils.isEmpty(dictCode)){
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        Result<List<DictItemDTO>> result = dictItemService.getListByDictCode(dictCode);
        if(result.getEntityError().getCode() != EntityError.SUCCESS.getCode()){
            return Result.responseError(result.getEntityError());
        }
        List<DictItemDTO> list = result.getData();

        List<ItemDTO> itemList = new ArrayList<>();
        ItemDTO itemDTO = null;
        for (DictItemDTO dictItem : list) {
            itemDTO = new ItemDTO();
            itemDTO.setLabel(dictItem.getItemText());
            try {
                itemDTO.setValue(Integer.valueOf(dictItem.getItemValue()));
            } catch (Exception e) {
                itemDTO.setValue(dictItem.getItemValue());
            }
            itemList.add(itemDTO);
        }
        return Result.responseSuccess(itemList);
    }


    @Override
    public Result<Integer> insertDict(DictSaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        DictDO temp = BeanUtils.switchToDO(param, DictDO.class);

        // 默认 key 格式   租户 +"-" + key
        if (!temp.getDictCode().startsWith((temp.getTenantId()))) {
            temp.setDictCode(temp.getTenantId()
                    + BpmConstant.COMMON_CODE_SEPARATION_CHARACTER
                    + "dict"
                    + BpmConstant.COMMON_CODE_SEPARATION_CHARACTER
                    + temp.getDictCode());
        }
        Integer num = mapper.insert(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer>updateDict(DictSaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        DictDO temp = BeanUtils.switchToDO(param, DictDO.class);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer>deleteDict(DictSaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        DictDO temp = BeanUtils.switchToDO(param, DictDO.class);
        temp.setValidState(INVALID_STATE);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<DictDTO> getDictById(Long dictId){

        if (dictId == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        DictDO query = new DictDO();
        query.setDictId(dictId);
        query = mapper.selectOne(new QueryWrapper<>(query));

        if (query == null) {
            return Result.responseError(EntityError.DATA_NOT_FOUND_ERROR);
        }
        DictDTO result = BeanUtils.switchToDTO(query, DictDTO.class);
        return Result.responseSuccess(result);
    }
}
