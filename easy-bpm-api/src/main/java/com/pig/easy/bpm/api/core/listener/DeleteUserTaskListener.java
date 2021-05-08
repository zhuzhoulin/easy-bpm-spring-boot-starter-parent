package com.pig.easy.bpm.api.core.listener;

import com.pig.easy.bpm.api.dto.request.UserTaskUpdateDTO;
import com.pig.easy.bpm.api.dto.response.UserTaskDTO;
import com.pig.easy.bpm.api.service.UserTaskService;
import com.pig.easy.bpm.common.constant.BpmConstant;
import com.pig.easy.bpm.common.constant.TaskConstant;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.utils.BeanUtils;
import com.pig.easy.bpm.common.utils.CommonUtils;
import com.pig.easy.bpm.common.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * todo:
 *
 * @author : zhoulin.zhu
 * @date : 2021/5/8 15:18
 */
@Component
@Slf4j
public class DeleteUserTaskListener implements TaskListener {

    private static final long serialVersionUID = -1L;

    @Resource
    private UserTaskService userTaskService;

    @Override
    public void notify(DelegateTask delegateTask) {

        Long applyId = CommonUtils.evalLong(delegateTask.getVariable(BpmConstant.APPLY_ID));

        if (applyId > 0) {

            Result<UserTaskDTO> result3 = userTaskService.getUserTaskByTaskId(Long.valueOf(delegateTask.getId()));
            if (result3.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                return;
            }

            UserTaskDTO userTask = result3.getData();
            if (userTask.getTaskStatus() < TaskConstant.TASK_COMPLETED) {
                UserTaskUpdateDTO disableUserTask = BeanUtils.switchToDTO(userTask, UserTaskUpdateDTO.class);
                disableUserTask.setUpdateTime(LocalDateTime.now());
                disableUserTask.setTaskStatus(TaskConstant.TASK_DISABLE);
                disableUserTask.setApproveTime(LocalDateTime.now());
                userTaskService.updateUserTask(disableUserTask);
            }
        }
    }
}
