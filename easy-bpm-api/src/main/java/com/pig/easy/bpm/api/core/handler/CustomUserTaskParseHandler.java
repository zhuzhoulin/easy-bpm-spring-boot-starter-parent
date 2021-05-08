package com.pig.easy.bpm.api.core.handler;


import com.pig.easy.bpm.api.utils.FlowElementUtils;
import com.pig.easy.bpm.common.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.*;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.delegate.ExecutionListener;
import org.flowable.engine.impl.bpmn.parser.BpmnParse;
import org.flowable.task.service.delegate.TaskListener;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/5/20 10:47
 */
@AutoConfigureAfter({ProcessEngine.class, DataSource.class})
@Component
@Slf4j
public class CustomUserTaskParseHandler extends UserTaskParseHandler {

    private static final String DEFAULT_ASSIGNEE_LIST_EXP = "${assigneeList}";
    private static final String ASSIGNEE_USER_EXP = "assignee";
    private static final String DEFAULT_ASSIGNEE_USER_EXP = "assigneeExp";

    /**
     * 即 只要有一个人完成任务，则当前任务也算完成
     */
    private static final String COMPLETION_CONDITION = "${nrOfCompletedInstances/nrOfInstances >= 0}";

    private static final String DEFAULT_SKIP_EXPRESSION = "${approveAction == \"approveReject\"}";

    @Override
    protected void executeParse(BpmnParse bpmnParse, UserTask userTask) {
        super.executeParse(bpmnParse, userTask);

        long startTime = System.currentTimeMillis();

        log.info("#################CustomUserTaskParseHandler########start################");
        Map<String, String> executionListernerMap = userTask.getExecutionListeners().stream().collect(Collectors.toMap(FlowableListener::getImplementation, FlowableListener::getEvent, (oldValue, newValue) -> newValue));

        FlowableListener disableUserTasksListener = new FlowableListener();
        disableUserTasksListener.setEvent(ExecutionListener.EVENTNAME_END);
        disableUserTasksListener.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_DELEGATEEXPRESSION);
        disableUserTasksListener.setImplementation("#{disableUserTasksListener}");

        if (executionListernerMap.get(disableUserTasksListener.getImplementation()) == null) {
            userTask.getExecutionListeners().add(disableUserTasksListener);
        }

        FlowableListener assigneListener = new FlowableListener();
        //改成由上一个节点获取下一个节点的逻辑，不在由 事件监听处理
        assigneListener.setEvent(ExecutionListener.EVENTNAME_START);
        assigneListener.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_DELEGATEEXPRESSION);
        assigneListener.setImplementation("#{findNextUserTaskUsersListener}");
        if (executionListernerMap.get(assigneListener.getImplementation()) == null) {
            userTask.getExecutionListeners().add(assigneListener);
        }

        Map<String, String> userTaskListernerMap = userTask.getTaskListeners().stream().collect(Collectors.toMap(FlowableListener::getImplementation, FlowableListener::getEvent, (oldValue, newValue) -> newValue));

        FlowableListener createListener = new FlowableListener();
        createListener.setEvent(TaskListener.EVENTNAME_CREATE);
        createListener.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_DELEGATEEXPRESSION);
        createListener.setImplementation("#{createUserTaskListener}");

        if (userTaskListernerMap.get(createListener.getImplementation()) == null) {
            userTask.getTaskListeners().add(createListener);
        }

        FlowableListener deleteListener = new FlowableListener();
        deleteListener.setEvent(TaskListener.EVENTNAME_DELETE);
        deleteListener.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_DELEGATEEXPRESSION);
        deleteListener.setImplementation("#{deleteUserTaskListener}");

        if (userTaskListernerMap.get(deleteListener.getImplementation()) == null) {
            userTask.getTaskListeners().add(deleteListener);
        }

        ExtensionElement extensionElement = FlowElementUtils.getExtensionElementFromFlowElementByName(userTask, null);

        MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics = new MultiInstanceLoopCharacteristics();
        multiInstanceLoopCharacteristics.setInputDataItem(DEFAULT_ASSIGNEE_LIST_EXP);
        multiInstanceLoopCharacteristics.setElementVariable(DEFAULT_ASSIGNEE_USER_EXP);

        if (userTask.getLoopCharacteristics() != null) {
            /* 设置完成任务条件 */
            String expression = FlowElementUtils.getAttributesFromExtensionElementByName(extensionElement, "expression");
            if (!StringUtils.isEmpty(expression)) {
                multiInstanceLoopCharacteristics.setCompletionCondition(expression);
            } else if (!StringUtils.isEmpty(userTask.getLoopCharacteristics().getCompletionCondition())) {
                multiInstanceLoopCharacteristics.setCompletionCondition(userTask.getLoopCharacteristics().getCompletionCondition());
            } else {
                multiInstanceLoopCharacteristics.setCompletionCondition(COMPLETION_CONDITION);
            }

        } else {
            multiInstanceLoopCharacteristics.setSequential(false);
        }
        String proportion = FlowElementUtils.getAttributesFromExtensionElementByName(extensionElement, "proportion");
        if (StringUtils.isEmpty(multiInstanceLoopCharacteristics.getCompletionCondition())
                && !StringUtils.isEmpty(proportion)) {
            multiInstanceLoopCharacteristics.setCompletionCondition("${nrOfCompletedInstances/nrOfInstances >= " + CommonUtils.evalInt(proportion)/100 + "}");
        }

        userTask.setLoopCharacteristics(multiInstanceLoopCharacteristics);

         /*  跳过表达式
                1. 如果node表中有配置 ，则以node为准
                2. 如果表中没有配置跳过表达式，流程图中 配置了，则以流程图为准
                3. 如果都没有，则默认设置 跳过表达式
            */
        String skipExpression = FlowElementUtils.getAttributesFromExtensionElementByName(extensionElement, "skipExpression");
        if (!StringUtils.isEmpty(skipExpression)) {
            userTask.setSkipExpression(skipExpression);
        } else if (StringUtils.isEmpty(userTask.getSkipExpression())) {
            userTask.setSkipExpression(DEFAULT_SKIP_EXPRESSION);
        }

        log.info(System.currentTimeMillis() - startTime + "ms");

        log.info("#################CustomUserTaskParseHandler######end###############");
    }
}
