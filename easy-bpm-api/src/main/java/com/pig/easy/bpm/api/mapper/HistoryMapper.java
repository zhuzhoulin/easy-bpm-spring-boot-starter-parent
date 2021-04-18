package com.pig.easy.bpm.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pig.easy.bpm.api.entity.HistoryDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 审批历史表 Mapper 接口
 * </p>
 *
 * @author pig
 * @since 2020-07-01
 */
@Mapper
public interface HistoryMapper extends BaseMapper<HistoryDO> {

}
