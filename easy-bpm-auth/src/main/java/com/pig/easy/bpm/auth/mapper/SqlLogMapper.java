package com.pig.easy.bpm.auth.mapper;

import com.pig.easy.bpm.auth.entity.SqlLogDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.pig.easy.bpm.auth.dto.request.*;
import com.pig.easy.bpm.auth.dto.response.*;

import java.util.List;
/**
 * <p>
 * SQL日志表 Mapper 接口
 * </p>
 *
 * @author pig
 * @since 2021-03-22
 */
@Mapper
public interface SqlLogMapper extends BaseMapper<SqlLogDO> {

    List<SqlLogDTO> getListByCondition(SqlLogQueryDTO param);

}
