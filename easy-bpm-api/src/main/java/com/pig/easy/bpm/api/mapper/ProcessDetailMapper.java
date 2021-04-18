package com.pig.easy.bpm.api.mapper;

import com.pig.easy.bpm.api.entity.ProcessDetailDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.pig.easy.bpm.api.dto.request.*;
import com.pig.easy.bpm.api.dto.response.*;

import java.util.List;
/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author pig
 * @since 2021-04-09
 */
@Mapper
public interface ProcessDetailMapper extends BaseMapper<ProcessDetailDO> {

    List<ProcessDetailDTO> getListByCondition(ProcessDetailQueryDTO param);

}
