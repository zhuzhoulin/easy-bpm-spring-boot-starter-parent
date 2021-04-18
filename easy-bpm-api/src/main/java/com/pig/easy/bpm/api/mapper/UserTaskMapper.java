package com.pig.easy.bpm.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pig.easy.bpm.api.dto.request.UserTaskInfoQueryDTO;
import com.pig.easy.bpm.api.dto.request.UserTaskReqDTO;
import com.pig.easy.bpm.api.dto.response.UserTaskDTO;
import com.pig.easy.bpm.api.dto.response.UserTaskInfoDTO;
import com.pig.easy.bpm.api.entity.UserTaskDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author pig
 * @since 2020-05-20
 */
@Mapper
public interface UserTaskMapper extends BaseMapper<UserTaskDO> {

    List<UserTaskInfoDTO> getToDoListByCondition(UserTaskInfoQueryDTO userTaskReqDTO);

    List<UserTaskInfoDTO> getDraftListByCondition(UserTaskInfoQueryDTO userTaskReqDTO);

    List<UserTaskInfoDTO> getApplyListByCondition(UserTaskInfoQueryDTO userTaskReqDTO);

    List<UserTaskInfoDTO> getHaveDoListByCondition(UserTaskInfoQueryDTO userTaskReqDTO);

    List<UserTaskDTO> getListByCondition(UserTaskReqDTO userTaskReqDTO);
}
