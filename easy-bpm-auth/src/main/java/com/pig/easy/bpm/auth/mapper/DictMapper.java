package com.pig.easy.bpm.auth.mapper;

import com.pig.easy.bpm.auth.entity.DictDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.pig.easy.bpm.auth.dto.request.*;
import com.pig.easy.bpm.auth.dto.response.*;

import java.util.List;
/**
 * <p>
 * 字典表 Mapper 接口
 * </p>
 *
 * @author pig
 * @since 2021-04-06
 */
@Mapper
public interface DictMapper extends BaseMapper<DictDO> {

    List<DictDTO> getListByCondition(DictQueryDTO param);

}
