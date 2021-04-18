package com.pig.easy.bpm.api.core.flowable;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.Process;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.engine.impl.persistence.deploy.DeploymentManager;
import org.flowable.engine.impl.util.CommandContextUtil;
import org.flowable.engine.impl.util.ProcessDefinitionUtil;

import java.io.Serializable;

/**
 * 功能描述: 获取流程定义( 避免误修改到缓存)
 */
public class GetProcessCmd implements Command<Process>, Serializable {

    private static final long serialVersionUID = 1437115519351132006L;

    String processDefinitionId;

    public GetProcessCmd(String processDefinitionId) {

        this.processDefinitionId = processDefinitionId;
    }

    @Override
    public Process execute(CommandContext commandContext) {

//        ProcessDetailService processDetailService = SpringContextHandler.getBean(ProcessDetailService.class);
//        Result<ProcessDetailDTO> result = processDetailService.getProcessDetailByDefinitionId(processDefinitionId);
//
//        if(result.getEntityError().getCode() != EntityError.SUCCESS.getCode()){
//            BpmnModel bpmnModel = ProcessDefinitionUtil.getBpmnModel(processDefinitionId);
//            return bpmnModel.getProcesses().get(0);
//        }
//        ProcessDetailDTO processDetailDTO = result.getData();
//        BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(new BytesStreamSource(processDetailDTO.getProcessXml().getBytes()), false, false);
//        return bpmnModel.getProcesses().get(0);
        DeploymentManager deploymentManager = CommandContextUtil.getProcessEngineConfiguration().getDeploymentManager();
        deploymentManager.getProcessDefinitionCache().remove(processDefinitionId);
        BpmnModel bpmnModel = ProcessDefinitionUtil.getBpmnModel(processDefinitionId);
        return bpmnModel.getProcesses().get(0);
    }

}
