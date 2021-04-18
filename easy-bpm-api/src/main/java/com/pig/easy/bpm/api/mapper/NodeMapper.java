package com.pig.easy.bpm.api.mapper;

import com.pig.easy.bpm.api.entity.NodeDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.pig.easy.bpm.api.dto.request.*;
import com.pig.easy.bpm.api.dto.response.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * <p>
 * 流程节点表 Mapper 接口
 * </p>
 *
 * @author pig
 * @since 2021-04-09
 */
@Mapper
public interface NodeMapper extends BaseMapper<NodeDO> {

    List<NodeDTO> getListByCondition(NodeQueryDTO param);

    int batchInsert(@Param("list") List<NodeDO> addList);

    int batchUpdate(@Param("list") List<NodeDO> addList);

}
