package com.pig.easy.bpm.api.service;

import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.api.dto.request.*;
import com.pig.easy.bpm.api.dto.response.ReturnNodeDTO;
import com.pig.easy.bpm.api.dto.response.UserTaskDTO;
import com.pig.easy.bpm.api.entity.UserTaskDO;
import com.pig.easy.bpm.common.generator.BaseService;
import com.pig.easy.bpm.common.utils.Result;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pig
 * @since 2020-05-20
 */
public interface UserTaskService  extends BaseService<UserTaskDO> {

    Result<PageInfo> getToDoListByCondition(UserTaskInfoQueryDTO userTaskReqDTO);

    Result<PageInfo> getDraftListByCondition(UserTaskInfoQueryDTO userTaskReqDTO);

    Result<PageInfo> getApplyListByCondition(UserTaskInfoQueryDTO userTaskReqDTO);

    Result<PageInfo> getHaveDoListByCondition(UserTaskInfoQueryDTO userTaskReqDTO);

    Result<PageInfo<UserTaskDTO>> getListByCondition(UserTaskReqDTO userTaskReqDTO);

    Result<UserTaskDTO> getUserTaskByTaskId(Long taskId);

    Result<List<UserTaskDTO>> getUserTaskByApplyId(Long applyId);

    Result<List<UserTaskDTO>> getUserTaskByProcInstId(String procInstId);

    Result<UserTaskDTO> insertUserTask(UserTaskSaveDTO userTaskSaveDTO);

    Result<Boolean> updateUserTask(UserTaskUpdateDTO userTaskUpdateDTO);

    Result<Boolean> completeTask(CompleteTaskDTO completeTaskDTO);

    Result<Boolean> batchCompleteTask(CompleteTaskDTO completeTaskDTO);

    Result<List<ReturnNodeDTO>> getRandomJumpNode(Long taskId);

    Result<List<ReturnNodeDTO>> getReturnNode(Long taskId);

    Result<Map<String,Object>> getCountListByUserType(Long userId, String tenantId, String userType);
}
