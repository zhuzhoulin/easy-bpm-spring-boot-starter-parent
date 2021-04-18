package com.pig.easy.bpm.auth.mapper;

import com.pig.easy.bpm.auth.entity.FileTemplateDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.pig.easy.bpm.auth.dto.request.*;
import com.pig.easy.bpm.auth.dto.response.*;

import java.util.List;
/**
 * <p>
 * 模板文件表 Mapper 接口
 * </p>
 *
 * @author pig
 * @since 2021-03-20
 */
@Mapper
public interface FileTemplateMapper extends BaseMapper<FileTemplateDO> {

    List<FileTemplateDTO> getListByCondition(FileTemplateQueryDTO param);

}
