package com.pig.easy.bpm.api.core.flowable;

import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.common.engine.impl.util.io.BytesStreamSource;
import org.flowable.engine.impl.persistence.deploy.DeploymentManager;
import org.flowable.engine.impl.persistence.entity.DeploymentEntity;
import org.flowable.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.flowable.engine.impl.persistence.entity.ResourceEntity;
import org.flowable.engine.impl.util.CommandContextUtil;
import org.flowable.engine.runtime.ProcessInstance;

import java.io.Serializable;

/**
 * todo: 通过流程实例拿流程模型，解决动态加节点导致流程图不显示
 *
 * @author : pig
 * @date : 2020/4/8 12:31
 */
public class GetProcessInstanceBpmnModelCmd implements Command<BpmnModel>,Serializable {

    private static final long serialVersionUID = -2234685368826280017L;

    private String processInstanceId;

    public GetProcessInstanceBpmnModelCmd(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    @Override
    public BpmnModel execute(CommandContext commandContext) {

        ProcessInstance processInstance = CommandContextUtil.getExecutionEntityManager(commandContext).findById(processInstanceId);
        DeploymentManager deploymentManager = CommandContextUtil.getProcessEngineConfiguration().getDeploymentManager();
        ProcessDefinitionEntity originalProcessDefinitionEntity = CommandContextUtil.getProcessDefinitionEntityManager(commandContext).findById(processInstance.getProcessDefinitionId());
        DeploymentEntity deploymentEntity = deploymentManager.getDeploymentEntityManager().findById(processInstance.getDeploymentId());
        BpmnModel bpmnModel = createBpmnModel(commandContext, originalProcessDefinitionEntity, deploymentEntity);
        return bpmnModel;
    }

    protected BpmnModel createBpmnModel(CommandContext commandContext, ProcessDefinitionEntity originalProcessDefinitionEntity, DeploymentEntity newDeploymentEntity) {
        ResourceEntity originalBpmnResource = CommandContextUtil.getResourceEntityManager(commandContext)
                .findResourceByDeploymentIdAndResourceName(originalProcessDefinitionEntity.getDeploymentId(), originalProcessDefinitionEntity.getResourceName());
        BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(new BytesStreamSource(originalBpmnResource.getBytes()), false, false);
        //updateBpmnProcess(commandContext, process, bpmnModel, originalProcessDefinitionEntity, newDeploymentEntity);
        return bpmnModel;
    }
}
