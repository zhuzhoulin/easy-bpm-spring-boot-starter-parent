package com.pig.easy.bpm.auth.service;

import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.auth.dto.request.DictItemQueryDTO;
import com.pig.easy.bpm.auth.dto.request.DictItemSaveOrUpdateDTO;
import com.pig.easy.bpm.auth.dto.response.DictItemDTO;
import com.pig.easy.bpm.auth.entity.DictItemDO;
import com.pig.easy.bpm.common.generator.BaseService;
import com.pig.easy.bpm.common.utils.Result;

import java.util.List;
/**
 * <p>
 * 字典详细表 服务类
 * </p>
 *
 * @author pig
 * @since 2021-04-06
 */
public interface DictItemService extends BaseService<DictItemDO> {

    Result<PageInfo<DictItemDTO>> getListPageByCondition(DictItemQueryDTO param);

    Result<Integer> insertDictItem(DictItemSaveOrUpdateDTO param);

    Result<Integer> updateDictItem(DictItemSaveOrUpdateDTO param);

    Result<Integer> deleteDictItem(DictItemSaveOrUpdateDTO param);

    Result<DictItemDTO> getDictItemById(Long itemId);

    Result<List<DictItemDTO>> getListByCondition(DictItemQueryDTO param);

    Result<List<DictItemDTO>> getListByDictCode(String dictCode);
}
