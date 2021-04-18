package com.pig.easy.bpm.auth.mapper;

import com.pig.easy.bpm.auth.entity.DictItemDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.pig.easy.bpm.auth.dto.request.*;
import com.pig.easy.bpm.auth.dto.response.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * <p>
 * 字典详细表 Mapper 接口
 * </p>
 *
 * @author pig
 * @since 2021-04-06
 */
@Mapper
public interface DictItemMapper extends BaseMapper<DictItemDO> {

    List<DictItemDTO> getListByCondition(DictItemQueryDTO param);

    List<DictItemDTO> getListByDictCode(@Param("dictCode") String dictCode);
}
