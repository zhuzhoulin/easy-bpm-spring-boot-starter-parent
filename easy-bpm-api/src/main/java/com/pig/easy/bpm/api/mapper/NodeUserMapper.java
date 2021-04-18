package com.pig.easy.bpm.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pig.easy.bpm.api.entity.NodeUserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 节点人员表 Mapper 接口
 * </p>
 *
 * @author pig
 * @since 2020-07-04
 */
@Mapper
public interface NodeUserMapper extends BaseMapper<NodeUserDO> {

    int batchInsert(@Param("list") List<NodeUserDO> saveList);
}
