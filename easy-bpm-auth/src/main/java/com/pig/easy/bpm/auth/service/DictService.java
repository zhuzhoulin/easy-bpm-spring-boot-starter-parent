package com.pig.easy.bpm.auth.service;

import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.auth.dto.request.DictQueryDTO;
import com.pig.easy.bpm.auth.dto.request.DictSaveOrUpdateDTO;
import com.pig.easy.bpm.auth.dto.response.DictDTO;
import com.pig.easy.bpm.auth.dto.response.ItemDTO;
import com.pig.easy.bpm.auth.entity.DictDO;
import com.pig.easy.bpm.common.generator.BaseService;
import com.pig.easy.bpm.common.utils.Result;

import java.util.List;
/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @author pig
 * @since 2021-04-06
 */
public interface DictService extends BaseService<DictDO> {

    Result<PageInfo<DictDTO>> getListPageByCondition(DictQueryDTO param);

    Result<Integer> insertDict(DictSaveOrUpdateDTO param);

    Result<Integer> updateDict(DictSaveOrUpdateDTO param);

    Result<Integer> deleteDict(DictSaveOrUpdateDTO param);

    Result<DictDTO> getDictById(Long dictId);

    Result<List<DictDTO>> getListByCondition(DictQueryDTO param);

    Result<List<ItemDTO>> getDictListByDictCode(String dictCode);
}
