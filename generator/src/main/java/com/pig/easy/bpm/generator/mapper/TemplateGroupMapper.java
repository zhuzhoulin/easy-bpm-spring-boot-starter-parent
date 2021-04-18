package com.pig.easy.bpm.generator.mapper;

import com.pig.easy.bpm.generator.entity.TemplateGroupDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.pig.easy.bpm.generator.dto.request.*;
import com.pig.easy.bpm.generator.dto.response.*;

import java.util.List;
/**
 * <p>
 * 模板分组表 Mapper 接口
 * </p>
 *
 * @author pig
 * @since 2021-03-08
 */
@Mapper
public interface TemplateGroupMapper extends BaseMapper<TemplateGroupDO> {

    List<TemplateGroupDTO> getListByCondition(TemplateGroupQueryDTO param);

}
