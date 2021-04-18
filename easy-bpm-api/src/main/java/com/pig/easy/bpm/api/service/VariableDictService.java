package com.pig.easy.bpm.api.service;

import com.pig.easy.bpm.api.entity.VariableDictDO;
import com.pig.easy.bpm.common.generator.BaseService;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.common.generator.BaseService;
import com.pig.easy.bpm.common.utils.Result;
import com.pig.easy.bpm.api.dto.request.*;
import com.pig.easy.bpm.api.dto.response.*;
import java.util.List;
/**
 * <p>
 * 变量表 服务类
 * </p>
 *
 * @author pig
 * @since 2021-04-11
 */
public interface VariableDictService extends BaseService<VariableDictDO> {

    Result<PageInfo<VariableDictDTO>> getListPageByCondition(VariableDictQueryDTO param);

    Result<Integer> insertVariableDict(VariableDictSaveOrUpdateDTO param);

    Result<Integer> updateVariableDict(VariableDictSaveOrUpdateDTO param);

    Result<Integer> deleteVariableDict(VariableDictSaveOrUpdateDTO param);

    Result<VariableDictDTO> getVariableDictById(Long variableId);

    Result<List<VariableDictDTO>> getListByCondition(VariableDictQueryDTO param);
}
