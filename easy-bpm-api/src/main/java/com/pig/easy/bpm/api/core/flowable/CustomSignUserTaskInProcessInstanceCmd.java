package com.pig.easy.bpm.api.core.flowable;

import com.pig.easy.bpm.api.dto.request.ApplyUpdateDTO;
import com.pig.easy.bpm.api.dto.request.ProcessDetailSaveOrUpdateDTO;
import com.pig.easy.bpm.api.dto.response.ProcessInfoDTO;
import com.pig.easy.bpm.api.service.ApplyService;
import com.pig.easy.bpm.api.service.NodeUserService;
import com.pig.easy.bpm.api.service.ProcessDetailService;
import com.pig.easy.bpm.api.service.ProcessService;
import com.pig.easy.bpm.common.constant.BpmConstant;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.execption.BpmException;
import com.pig.easy.bpm.common.utils.Result;
import com.pig.easy.bpm.common.utils.spring.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.BpmnAutoLayout;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.*;
import org.flowable.bpmn.model.Process;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.impl.cmd.AbstractDynamicInjectionCmd;
import org.flowable.engine.impl.context.Context;
import org.flowable.engine.impl.dynamic.BaseDynamicSubProcessInjectUtil;
import org.flowable.engine.impl.dynamic.DynamicUserTaskBuilder;
import org.flowable.engine.impl.persistence.entity.*;
import org.flowable.engine.impl.util.CommandContextUtil;
import org.flowable.engine.impl.util.ProcessDefinitionUtil;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/4/8 14:46
 */
@Slf4j
public class CustomSignUserTaskInProcessInstanceCmd extends AbstractDynamicInjectionCmd implements Command<Void> {

    private String processInstanceId;
    private List<DynamicUserTaskBuilder> dynamicUserTaskBuilderList;
    private FlowElement currentFlowElemet;
    private DynamicUserTaskBuilder dynamicUserTaskBuilder;
    private boolean jump;

    public CustomSignUserTaskInProcessInstanceCmd(String processInstanceId, List<DynamicUserTaskBuilder> dynamicUserTaskBuilderList, FlowElement currentFlowElemet, boolean jump) {
        this.processInstanceId = processInstanceId;
        this.dynamicUserTaskBuilderList = dynamicUserTaskBuilderList;
        this.currentFlowElemet = currentFlowElemet;
        this.dynamicUserTaskBuilder = dynamicUserTaskBuilderList.size() > 0 ? dynamicUserTaskBuilderList.get(0) : new DynamicUserTaskBuilder();
        this.jump = jump;
    }

    @Override
    public Void execute(CommandContext commandContext) {
        createDerivedProcessDefinitionForProcessInstance(commandContext, processInstanceId);
        return null;
    }

    @Override
    protected void updateBpmnProcess(CommandContext commandContext, Process process,
                                     BpmnModel bpmnModel, ProcessDefinitionEntity originalProcessDefinitionEntity, DeploymentEntity newDeploymentEntity) {

        List<StartEvent> startEvents = process.findFlowElementsOfType(StartEvent.class);
        StartEvent initialStartEvent = null;
        for (StartEvent startEvent : startEvents) {
            if (startEvent.getEventDefinitions().size() == 0) {
                initialStartEvent = startEvent;
                break;

            } else if (initialStartEvent == null) {
                initialStartEvent = startEvent;
            }
        }
        if (currentFlowElemet != null) {

            Process tempProcess = process;
            List<UserTask> userTasks = new ArrayList<>(dynamicUserTaskBuilderList.size());

            for (DynamicUserTaskBuilder dynamicUserTaskBuilder : dynamicUserTaskBuilderList) {
                UserTask userTask = new UserTask();
                BeanUtils.copyProperties(currentFlowElemet, userTask);
                if (dynamicUserTaskBuilder.getId() != null) {
                    userTask.setId(dynamicUserTaskBuilder.getId());
                } else {
                    userTask.setId(dynamicUserTaskBuilder.nextTaskId(process.getFlowElementMap()));
                }
                dynamicUserTaskBuilder.setDynamicTaskId(userTask.getId());

                userTask.setName(next(dynamicUserTaskBuilder.getName(),process.getFlowElementMap()));
                userTask.setAssignee(dynamicUserTaskBuilder.getAssignee());

                /* 设置新增节点自定义属性 */
                ReentrantLock lock = new ReentrantLock();
                try {
                    lock.lock();
                    Map<String, List<ExtensionAttribute>> attributes = new HashMap<>();
                    List<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
                    ExtensionAttribute extensionAttribute = new ExtensionAttribute();

                    /* 指定节点找人方式 */
                    extensionAttribute.setName("findUserType");
                    extensionAttribute.setValue(String.valueOf(BpmConstant.FIND_USER_TYPE_BY_USER));
                    extensionAttributeList = new ArrayList<>();
                    extensionAttributeList.add(extensionAttribute);
                    attributes.put("findUserType", extensionAttributeList);

                    extensionAttribute = new ExtensionAttribute();
                    extensionAttribute.setName("userIdList");
                    extensionAttribute.setValue(dynamicUserTaskBuilder.getAssignee());
                    extensionAttributeList = new ArrayList<>();
                    extensionAttributeList.add(extensionAttribute);
                    attributes.put("userIdList", extensionAttributeList);

                    extensionAttribute = new ExtensionAttribute();
                    extensionAttribute.setName("taskType");
                    extensionAttribute.setValue(BpmConstant.TASK_TYPE_APPROVE);
                    extensionAttributeList = new ArrayList<>();
                    extensionAttributeList.add(extensionAttribute);
                    attributes.put("taskType", extensionAttributeList);

                    Map<String, List<ExtensionElement>> extensionElements = new HashMap<>();
                    ExtensionElement extensionElement = new ExtensionElement();
                    extensionElement.setNamespace("http://flowable.org/bpmn");
                    extensionElement.setNamespacePrefix("flowable");
                    extensionElement.setName("customProperties");
                    extensionElement.setAttributes(attributes);

                    List<ExtensionElement> extensionElementList = new ArrayList<>();
                    extensionElementList.add(extensionElement);
                    extensionElements.put("customProperties", extensionElementList);
                    userTask.setExtensionElements(extensionElements);

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }

                userTasks.add(userTask);
                process.addFlowElement(userTask);
            }

            UserTask currentFlowElemet = (UserTask) this.currentFlowElemet;
            List<SequenceFlow> outgoingFlows = new ArrayList<>();
            SequenceFlow sequenceFlow = null;
            /* 画串行连线 */
            for (int i = 0; i < userTasks.size(); i++) {
                UserTask userTask = userTasks.get(i);

                if (i == userTasks.size() - 1) {
                    // 是最后一个任务节点
                    for (SequenceFlow sequenceFlow1 : currentFlowElemet.getOutgoingFlows()) {
                        sequenceFlow = new SequenceFlow(userTask.getId(), sequenceFlow1.getTargetRef());
                        sequenceFlow.setSkipExpression(sequenceFlow1.getSkipExpression());
                        sequenceFlow.setConditionExpression(sequenceFlow1.getConditionExpression());
                        sequenceFlow.setExtensionElements(sequenceFlow1.getExtensionElements());
                        sequenceFlow.setExecutionListeners(sequenceFlow1.getExecutionListeners());
                        sequenceFlow.setName(sequenceFlow1.getName());
                        sequenceFlow.setId(dynamicUserTaskBuilder.nextFlowId(process.getFlowElementMap()));
                        outgoingFlows.add(sequenceFlow);
                        process.removeFlowElement(sequenceFlow1.getId());
                        recordNewSequence(commandContext, sequenceFlow, currentFlowElemet.getExtensionId(), originalProcessDefinitionEntity.getId());
                        process.addFlowElement(sequenceFlow);
                    }
                    userTask.setOutgoingFlows(outgoingFlows);
                } else {
                    sequenceFlow = new SequenceFlow(userTask.getId(), userTasks.get(i + 1).getId());
                    sequenceFlow.setId(dynamicUserTaskBuilder.nextFlowId(process.getFlowElementMap()));
                    recordNewSequence(commandContext, sequenceFlow, currentFlowElemet.getExtensionId(), originalProcessDefinitionEntity.getId());
                    userTask.setOutgoingFlows(Arrays.asList(sequenceFlow));
                    process.addFlowElement(sequenceFlow);
                }
            }
            sequenceFlow = new SequenceFlow(currentFlowElemet.getId(), dynamicUserTaskBuilderList.get(0).getId());
            sequenceFlow.setId(dynamicUserTaskBuilder.nextFlowId(process.getFlowElementMap()));
            recordNewSequence(commandContext, sequenceFlow, currentFlowElemet.getExtensionId(), originalProcessDefinitionEntity.getId());
            currentFlowElemet.setOutgoingFlows(Arrays.asList(sequenceFlow));
            process.addFlowElement(sequenceFlow);

            BpmnModel tempBpmnModel = new BpmnModel();
            tempBpmnModel.addProcess(process);
            /* 生成BPMN自动布局 */
            new BpmnAutoLayout(tempBpmnModel).execute();
            bpmnModel = tempBpmnModel;

            process = tempProcess;
        } else {

            ParallelGateway parallelGateway = new ParallelGateway();
            parallelGateway.setId(dynamicUserTaskBuilder.nextForkGatewayId(process.getFlowElementMap()));
            process.addFlowElement(parallelGateway);

            UserTask userTask = new UserTask();
            if (dynamicUserTaskBuilder.getId() != null) {
                userTask.setId(dynamicUserTaskBuilder.getId());
            } else {
                userTask.setId(dynamicUserTaskBuilder.nextTaskId(process.getFlowElementMap()));
            }
            dynamicUserTaskBuilder.setDynamicTaskId(userTask.getId());

            userTask.setName(dynamicUserTaskBuilder.getName());
            userTask.setAssignee(dynamicUserTaskBuilder.getAssignee());
            process.addFlowElement(userTask);

            EndEvent endEvent = new EndEvent();
            endEvent.setId(dynamicUserTaskBuilder.nextEndEventId(process.getFlowElementMap()));
            process.addFlowElement(endEvent);

            SequenceFlow flowToUserTask = new SequenceFlow(parallelGateway.getId(), userTask.getId());
            flowToUserTask.setId(dynamicUserTaskBuilder.nextFlowId(process.getFlowElementMap()));
            process.addFlowElement(flowToUserTask);

            SequenceFlow flowFromUserTask = new SequenceFlow(userTask.getId(), endEvent.getId());
            flowFromUserTask.setId(dynamicUserTaskBuilder.nextFlowId(process.getFlowElementMap()));
            process.addFlowElement(flowFromUserTask);

            SequenceFlow initialFlow = initialStartEvent.getOutgoingFlows().get(0);
            initialFlow.setSourceRef(parallelGateway.getId());

            SequenceFlow flowFromStart = new SequenceFlow(initialStartEvent.getId(), parallelGateway.getId());
            flowFromStart.setId(dynamicUserTaskBuilder.nextFlowId(process.getFlowElementMap()));
            process.addFlowElement(flowFromStart);

            GraphicInfo elementGraphicInfo = bpmnModel.getGraphicInfo(initialStartEvent.getId());
            if (elementGraphicInfo != null) {
                double yDiff = 0;
                double xDiff = 80;
                if (elementGraphicInfo.getY() < 173) {
                    yDiff = 173 - elementGraphicInfo.getY();
                    elementGraphicInfo.setY(173);
                }

                Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
                for (String locationId : locationMap.keySet()) {
                    if (initialStartEvent.getId().equals(locationId)) {
                        continue;
                    }

                    GraphicInfo locationGraphicInfo = locationMap.get(locationId);
                    locationGraphicInfo.setX(locationGraphicInfo.getX() + xDiff);
                    locationGraphicInfo.setY(locationGraphicInfo.getY() + yDiff);
                }

                Map<String, List<GraphicInfo>> flowLocationMap = bpmnModel.getFlowLocationMap();
                for (String flowId : flowLocationMap.keySet()) {
                    if (flowFromStart.getId().equals(flowId)) {
                        continue;
                    }

                    List<GraphicInfo> flowGraphicInfoList = flowLocationMap.get(flowId);
                    for (GraphicInfo flowGraphicInfo : flowGraphicInfoList) {
                        flowGraphicInfo.setX(flowGraphicInfo.getX() + xDiff);
                        flowGraphicInfo.setY(flowGraphicInfo.getY() + yDiff);
                    }
                }

                GraphicInfo forkGraphicInfo = new GraphicInfo(elementGraphicInfo.getX() + 75, elementGraphicInfo.getY() - 5, 40, 40);
                bpmnModel.addGraphicInfo(parallelGateway.getId(), forkGraphicInfo);

                bpmnModel.addFlowGraphicInfoList(flowFromStart.getId(), createWayPoints(elementGraphicInfo.getX() + 30, elementGraphicInfo.getY() + 15,
                        elementGraphicInfo.getX() + 75, elementGraphicInfo.getY() + 15));

                GraphicInfo newTaskGraphicInfo = new GraphicInfo(elementGraphicInfo.getX() + 185, elementGraphicInfo.getY() - 163, 80, 100);
                bpmnModel.addGraphicInfo(userTask.getId(), newTaskGraphicInfo);

                bpmnModel.addFlowGraphicInfoList(flowToUserTask.getId(), createWayPoints(elementGraphicInfo.getX() + 95, elementGraphicInfo.getY() - 5,
                        elementGraphicInfo.getX() + 95, elementGraphicInfo.getY() - 123, elementGraphicInfo.getX() + 185, elementGraphicInfo.getY() - 123));

                GraphicInfo endGraphicInfo = new GraphicInfo(elementGraphicInfo.getX() + 335, elementGraphicInfo.getY() - 137, 28, 28);
                bpmnModel.addGraphicInfo(endEvent.getId(), endGraphicInfo);

                bpmnModel.addFlowGraphicInfoList(flowFromUserTask.getId(), createWayPoints(elementGraphicInfo.getX() + 285, elementGraphicInfo.getY() - 123,
                        elementGraphicInfo.getX() + 335, elementGraphicInfo.getY() - 123));
            }
        }

        BaseDynamicSubProcessInjectUtil.processFlowElements(commandContext, process, bpmnModel, originalProcessDefinitionEntity, newDeploymentEntity);
    }

    @Override
    protected void updateExecutions(CommandContext commandContext, ProcessDefinitionEntity processDefinitionEntity,
                                    ExecutionEntity processInstance, List<ExecutionEntity> childExecutions) {

        ExecutionEntityManager executionEntityManager = CommandContextUtil.getExecutionEntityManager(commandContext);
        List<ExecutionEntity> oldExecution = executionEntityManager.findChildExecutionsByProcessInstanceId(processInstance.getProcessInstanceId());
        ExecutionEntity execution = executionEntityManager.createChildExecution(processInstance);
        BpmnModel bpmnModel = ProcessDefinitionUtil.getBpmnModel(processDefinitionEntity.getId());

        invokeBpm(processDefinitionEntity, bpmnModel);

        /* 如果为true,则取消当前节点剩余任务 并 跳转至 第一个节点 */
        if (jump) {
            org.flowable.task.service.TaskService taskService = CommandContextUtil.getTaskService(commandContext);
            List<TaskEntity> taskEntities = taskService.findTasksByProcessInstanceId(processInstanceId);
            int countTaskNum = 0;
            String executionId = null;

            for (TaskEntity taskEntity : taskEntities) {
                taskEntity.getIdentityLinks().stream().forEach(identityLinkEntity -> {
                    if (identityLinkEntity.isGroup()) {
                        taskEntity.deleteGroupIdentityLink(identityLinkEntity.getGroupId(), "candidate");
                    } else {
                        taskEntity.deleteUserIdentityLink(identityLinkEntity.getUserId(), "participant");
                    }
                });
                if (taskEntity.getTaskDefinitionKey().equals(currentFlowElemet.getId())) {
                    executionId = taskEntity.getExecutionId();
                    countTaskNum = countTaskNum + 1;
                    taskService.deleteTask(taskEntity, false);
                }
            }

            UserTask currentFlowElemet = (UserTask) this.currentFlowElemet;
            if (currentFlowElemet.getLoopCharacteristics() != null) {

            }
            ExecutionEntity threeExecutionEntity = executionEntityManager.findById(executionId);

            ExecutionEntity secondExecutionEntity = threeExecutionEntity.getParent();

            executionEntityManager.deleteChildExecutions(secondExecutionEntity, "jump", false);
            secondExecutionEntity.setActive(true);
            secondExecutionEntity.setMultiInstanceRoot(false);
            executionEntityManager.update(secondExecutionEntity);
            //if((UserTas)currentFlowElemet.)
            UserTask userTask = (UserTask) bpmnModel.getProcessById(processDefinitionEntity.getKey()).getFlowElement(dynamicUserTaskBuilder.getId());
            execution.setCurrentFlowElement(userTask);
            Context.getAgenda().planContinueProcessOperation(execution);
        }

    }

    private void invokeBpm(ProcessDefinitionEntity processDefinitionEntity, BpmnModel bpmnModel) {
        try {
            /* 更新流程表中 流程定义编号 */
            ProcessEngine processEngine = SpringUtils.getBean(ProcessEngine.class);

            String definitionId = processDefinitionEntity.getId();
            ApplyService applyService = SpringUtils.getBean(ApplyService.class);
            ApplyUpdateDTO applyUpdateDTO = new ApplyUpdateDTO();
            applyUpdateDTO.setUpdateTime(LocalDateTime.now());
            applyUpdateDTO.setDefinitionId(definitionId);
            applyUpdateDTO.setProcInstId(processInstanceId);
            applyService.updateApplyByProcInstId(applyUpdateDTO);

            ProcessDetailService processDetailService = SpringUtils.getBean(ProcessDetailService.class);
            byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(bpmnModel);
            String processXml = new String(bpmnBytes);

            /* 更新流程表中 流程定义编号 */
            processDetailService.saveNodeList(processXml, definitionId, processDefinitionEntity.getKey(), null, null);

            ProcessService processService = SpringUtils.getBean(ProcessService.class);
            Result<ProcessInfoDTO> result = processService.getProcessWithDetailByProcessKey(processDefinitionEntity.getKey());
            ProcessDetailSaveOrUpdateDTO tempProcessDetail = new ProcessDetailSaveOrUpdateDTO();
            tempProcessDetail.setTenantId(result.getData().getTenantId());
            tempProcessDetail.setDefinitionId(definitionId);
            tempProcessDetail.setProcessXml(processXml);
            tempProcessDetail.setApplyDueDate(result.getData().getApplyDueDate());
            tempProcessDetail.setApplyTitleRule(result.getData().getApplyTitleRule());
            tempProcessDetail.setProcessId(result.getData().getProcessId());
            tempProcessDetail.setRemarks(result.getData().getRemarks());
            tempProcessDetail.setAutoCompleteFirstNode(result.getData().getAutoCompleteFirstNode());
            tempProcessDetail.setPublishStatus(BpmConstant.PROCESS_DETAIL_PUBLISH);
            tempProcessDetail.setMainVersion(BpmConstant.PROCESS_DERTAIL_NOT_MAIN_VERSION);

            processDetailService.insertProcessDetail(tempProcessDetail);

            /* 更新节点人员表中 流程定义编号 */
            NodeUserService nodeUserService = SpringUtils.getBean(NodeUserService.class);
            nodeUserService.updateNodeUserDefinitionIdByProcInscId(processInstanceId, processDefinitionEntity.getKey());

        } catch (Exception e) {
            e.printStackTrace();
            throw BpmException.builder().entityError(new EntityError(EntityError.SYSTEM_ERROR.getCode(), e.getMessage())).build();
        }
    }

    private void recordNewSequence(CommandContext commandContext, SequenceFlow sequenceFlow, String extensionId, String procDefId) {
        ExecutionEntityManager executionEntityManager = CommandContextUtil.getExecutionEntityManager(commandContext);
        List<ExecutionEntity> oldExecution = executionEntityManager.findChildExecutionsByProcessInstanceId(processInstanceId);

        HistoricActivityInstanceEntityManager historicActivityInstanceEntityManager = CommandContextUtil.getHistoricActivityInstanceEntityManager(commandContext);
        HistoricActivityInstanceEntity historicActivityInstanceEntity = historicActivityInstanceEntityManager.create();
        historicActivityInstanceEntity.setActivityName(sequenceFlow.getName());
        historicActivityInstanceEntity.setActivityId(sequenceFlow.getId());
        historicActivityInstanceEntity.setExecutionId(oldExecution.get(0).getId());
        historicActivityInstanceEntity.setTenantId(oldExecution.get(0).getTenantId());
        historicActivityInstanceEntity.setProcessInstanceId(processInstanceId);
        historicActivityInstanceEntity.setActivityType("sequenceFlow");
        historicActivityInstanceEntity.setProcessDefinitionId(procDefId);
        historicActivityInstanceEntity.setStartTime(new Date());
        historicActivityInstanceEntity.setEndTime(new Date());

        historicActivityInstanceEntityManager.insert(historicActivityInstanceEntity);
    }

    protected int counter = 1;

    protected String next(String prefix, Map<String, FlowElement> flowElementMap) {

        Map<String, FlowElement> tempMap = new ConcurrentHashMap<>();

        for(Map.Entry<String, FlowElement> entry : flowElementMap.entrySet()){
            if(!StringUtils.isEmpty(entry.getValue().getName())){
                tempMap.put(entry.getValue().getName(),entry.getValue());
            }
        }

        String nextId = null;
        boolean nextIdNotFound = true;
        while (nextIdNotFound) {
            if (!tempMap.containsKey(prefix + counter)) {
                nextId = prefix + counter;
                nextIdNotFound = false;
            }
            counter++;
        }
        return nextId;
    }
}
