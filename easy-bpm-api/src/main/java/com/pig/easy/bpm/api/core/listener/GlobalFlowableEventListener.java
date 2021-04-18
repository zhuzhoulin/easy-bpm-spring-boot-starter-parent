package com.pig.easy.bpm.api.core.listener;

import com.pig.easy.bpm.api.core.event.BpmEvent;
import com.pig.easy.bpm.api.core.event.BpmEventDispatcher;
import com.pig.easy.bpm.api.core.event.BpmEventType;
import com.pig.easy.bpm.api.core.event.BpmSourceDTO;
import com.pig.easy.bpm.api.dto.request.ApplyUpdateDTO;
import com.pig.easy.bpm.api.service.ApplyService;
import com.pig.easy.bpm.common.constant.BpmConstant;
import com.pig.easy.bpm.common.constant.TaskConstant;
import com.pig.easy.bpm.common.utils.spring.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEventType;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEventListener;
import org.flowable.common.engine.impl.event.FlowableEngineEventImpl;
import org.flowable.common.engine.impl.event.FlowableEntityEventImpl;
import org.flowable.engine.HistoryService;
import org.flowable.engine.ProcessEngine;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.flowable.task.service.impl.persistence.entity.TaskEntityImpl;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/5/20 11:04
 * @see    FlowableEngineEventType
 */
@AutoConfigureAfter({ProcessEngine.class, DataSource.class})
@Component
@Slf4j
public class GlobalFlowableEventListener implements FlowableEventListener {

    @Autowired
    @Lazy
    private ApplyService applyService;
    @Autowired
    @Lazy
    private ProcessEngine processEngine;
    @Autowired
    BpmEventDispatcher bestBpmEventDispatcher;

    @Override
    public void onEvent(FlowableEvent event) {

        FlowableEngineEventImpl flowableEntityEvent = (FlowableEngineEventImpl) event;
        String processInstanceId = flowableEntityEvent.getProcessInstanceId();
        String approveAction = null;
        TaskEntityImpl taskEntity = new TaskEntityImpl();
        Map<String, Object> variables = Collections.synchronizedMap(new HashMap<>());

        switch (event.getType().name()) {

            case "PROCESS_STARTED":
                log.info("流程实例{}已开始", processInstanceId);
                sendBpmEvent(BpmEventType.PROCESS_STARTED,flowableEntityEvent);
                break;
            case "PROCESS_COMPLETED":
            case "PROCESS_CANCELLED":
                updateApplyStatusByProInsId(processInstanceId, BpmConstant.APPLY_STATUS_DISABLE);
                try {
                    // 如果有加事务，则可以在这丽获取
                    variables = processEngine.getRuntimeService().getVariables(flowableEntityEvent.getExecutionId());
                    approveAction = variables.getOrDefault(TaskConstant.APPROVE_ACTION_DESC, TaskConstant.APPROVE_ACTION_PASS).toString();
                } catch (Exception e) {
                    // 如果没有加事务，则 通过历史记录获取
                    HistoryService historyService = processEngine.getHistoryService();
                    HistoricVariableInstance historicVariableInstance = historyService.createHistoricVariableInstanceQuery()
                            .processInstanceId(processInstanceId).variableName(TaskConstant.APPROVE_ACTION_DESC).singleResult();
                    approveAction = historicVariableInstance.getValue().toString();
                }
                if (TaskConstant.APPROVE_ACTION_PASS.equals(approveAction)) {
                    updateApplyStatusByProInsId(processInstanceId, BpmConstant.APPLY_STATUS_PASS);
                } else if (TaskConstant.APPROVE_ACTION_FAIL.equals(approveAction)) {
                    updateApplyStatusByProInsId(processInstanceId, BpmConstant.APPLY_STATUS_REJECT);
                } else if (TaskConstant.APPROVE_ACTION_REJECT.equals(approveAction)) {
                    updateApplyStatusByProInsId(processInstanceId, BpmConstant.APPLY_STATUS_REJECT);
                } else {
                    updateApplyStatusByProInsId(processInstanceId, BpmConstant.APPLY_STATUS_DISABLE);
                }
                sendBpmEvent(BpmEventType.valueOf(event.getType().name()),flowableEntityEvent);
                log.info("流程实例{}已取消", processInstanceId);
                break;
            case "TASK_CREATED":
                if (event instanceof FlowableEntityEventImpl) {
                    if (!(((FlowableEntityEventImpl) event).getEntity() instanceof TaskEntity)) {
                        return;
                    }
                    taskEntity = (TaskEntityImpl) ((FlowableEntityEventImpl) event).getEntity();
                    sendBpmEvent(BpmEventType.TASK_CREATED,taskEntity);
                    log.info("TASK_CREATED ----【" + taskEntity.getId() + "-" + taskEntity.getTaskDefinitionKey() + "】已创建");
                }
                break;
            case "TASK_COMPLETED":

                if (event instanceof FlowableEntityEventImpl) {
                    if (!(((FlowableEntityEventImpl) event).getEntity() instanceof TaskEntity)) {
                        return;
                    }
                    taskEntity = (TaskEntityImpl) ((FlowableEntityEventImpl) event).getEntity();
                    sendBpmEvent(BpmEventType.TASK_COMPLETED,taskEntity);
                    log.info("TASK_CREATED ----【" + taskEntity.getId() + "-" + taskEntity.getTaskDefinitionKey() + "】已完成");
                }
                break;
            default:
                sendBpmEvent(BpmEventType.valueOf(event.getType().name()),flowableEntityEvent);
                break;
        }
    }

    private <T> void sendBpmEvent(BpmEventType bestBpmEventType, T object){
        BpmSourceDTO bpmSourceDTO = new BpmSourceDTO();
        bpmSourceDTO.setEventType(bestBpmEventType);
        bpmSourceDTO.setData(object);
        bpmSourceDTO.setClassClz(object.getClass());
        bestBpmEventDispatcher.dispatchEvent(new BpmEvent(bpmSourceDTO));
    }

    private void sendEvent(FlowableEvent flowableEvent, String nodeId){

        try{
            FlowableEngineEventImpl flowableEntityEvent = (FlowableEngineEventImpl) flowableEvent;
            String executionId = flowableEntityEvent.getExecutionId();
            Map<String, Object> variables = SpringUtils.getBean(ProcessEngine.class).getRuntimeService().getVariables(executionId);

        } catch (Exception e){
            e.printStackTrace();
            log.error("sendEvent error {}",e);
        }
    }

    private void updateApplyStatusByProInsId(String processInstanceId, Integer status) {

        ApplyUpdateDTO applyUpdateDTO = new ApplyUpdateDTO();
        applyUpdateDTO.setProcInstId(processInstanceId);
        applyUpdateDTO.setApplyStatus(status);
        applyUpdateDTO.setUpdateTime(LocalDateTime.now());
        applyUpdateDTO.setCompleteTime(LocalDateTime.now());
        applyService.updateApplyByProcInstId(applyUpdateDTO);
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }

    @Override
    public boolean isFireOnTransactionLifecycleEvent() {
        return false;
    }

    @Override
    public String getOnTransaction() {
        return null;
    }
}
