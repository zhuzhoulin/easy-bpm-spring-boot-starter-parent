package com.pig.easy.bpm.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pig.easy.bpm.api.dto.request.FormDataQueryDTO;
import com.pig.easy.bpm.api.dto.response.FormDataDTO;
import com.pig.easy.bpm.api.entity.FormDataDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
public interface FormDataMapper extends BaseMapper<FormDataDO> {

    List<FormDataDTO> getListByCondition(FormDataQueryDTO param);

    int batchSave(@Param("list") List<FormDataDO> updateFormDataList);

    int batchUpdate(@Param("list") List<FormDataDO> updateFormDataList);

}
