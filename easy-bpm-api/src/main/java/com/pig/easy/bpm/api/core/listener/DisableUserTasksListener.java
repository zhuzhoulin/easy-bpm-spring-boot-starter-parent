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
import org.flowable.engine.TaskService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author : pig
 * @date : 2020/07/13 11:45
 */
@Component
@Slf4j
public class DisableUserTasksListener implements ExecutionListener {

    private static final long serialVersionUID = 7847355922619440599L;

    @Resource
    private UserTaskService userTaskService;

    @Autowired
    private TaskService taskService;

    @Override
    public void notify(DelegateExecution delegateExecution) {

        log.info("###############DisableUserTasksListener######[{}][{}]######start#####", delegateExecution.getCurrentActivityId(), delegateExecution.getCurrentFlowElement().getName());

        Long applyId = CommonUtils.evalLong(delegateExecution.getVariable(BpmConstant.APPLY_ID));

        if (applyId > 0) {
            List<Task> list = taskService.createTaskQuery().processInstanceId(delegateExecution.getProcessInstanceId()).executionId(delegateExecution.getId()).taskDefinitionKey(delegateExecution.getCurrentActivityId()).list();

            HashMap<String, Task> flowUserTaskMap = new HashMap<>();
            for (Task task : list) {
                flowUserTaskMap.put(task.getId(), task);
            }

            Result<List<UserTaskDTO>> result3 = userTaskService.getUserTaskByApplyId(applyId);
            if (result3.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                return;
            }
            result3.getData().stream().filter(temp ->
                    temp.getTaskStatus() < TaskConstant.TASK_COMPLETED
                            && flowUserTaskMap.get(temp.getActTaskId()) != null).forEach(
                    userTask -> {
                        UserTaskUpdateDTO disableUserTask = BeanUtils.switchToDTO(userTask, UserTaskUpdateDTO.class);
                        disableUserTask.setUpdateTime(LocalDateTime.now());
                        disableUserTask.setTaskStatus(TaskConstant.TASK_DISABLE);
                        disableUserTask.setApproveTime(LocalDateTime.now());
                        userTaskService.updateUserTask(disableUserTask);
                    }
            );
        }

        log.info("###############DisableUserTasksListener######[{}][{}]######end#####", delegateExecution.getCurrentActivityId(), delegateExecution.getCurrentFlowElement().getName());
    }
}
