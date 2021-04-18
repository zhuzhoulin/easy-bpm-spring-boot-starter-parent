package com.pig.easy.bpm.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pig.easy.bpm.api.dto.request.ApplyQueryDTO;
import com.pig.easy.bpm.api.dto.request.LineChartQueryDTO;
import com.pig.easy.bpm.api.dto.response.ApplyDTO;
import com.pig.easy.bpm.api.dto.response.LineCharDTO;
import com.pig.easy.bpm.api.entity.ApplyDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 申请表 Mapper 接口
 * </p>
 *
 * @author pig
 * @since 2020-05-20
 */
@Mapper
public interface ApplyMapper extends BaseMapper<ApplyDO> {

    List<ApplyDTO> getListByCondition(ApplyQueryDTO applyQueryDTO);

    List<LineCharDTO> getApplyLineChart(LineChartQueryDTO lineChartQueryDTO);
}
