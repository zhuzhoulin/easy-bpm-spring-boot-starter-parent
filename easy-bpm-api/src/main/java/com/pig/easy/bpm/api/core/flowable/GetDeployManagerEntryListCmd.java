package com.pig.easy.bpm.api.core.flowable;/**
 * Created by Administrator on 2020/4/8.
 */

import org.flowable.bpmn.model.UserTask;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.engine.impl.DeploymentQueryImpl;
import org.flowable.engine.impl.persistence.deploy.DeploymentManager;
import org.flowable.engine.impl.persistence.deploy.ProcessDefinitionCacheEntry;
import org.flowable.engine.impl.persistence.entity.DeploymentEntity;
import org.flowable.engine.impl.util.CommandContextUtil;
import org.flowable.engine.impl.util.ProcessDefinitionUtil;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * todo:
 *
 * @author : pig
 * @date : 2020/4/8 9:34
 */
public class GetDeployManagerEntryListCmd implements  Command<List<ProcessDefinitionCacheEntry>>, Serializable  {

    private static final long serialVersionUID = -7622304596382539249L;

    private String processDefinitionId;

    public GetDeployManagerEntryListCmd(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    @Override
    public List<ProcessDefinitionCacheEntry> execute(CommandContext commandContext) {
        DeploymentManager deploymentManager = CommandContextUtil.getProcessEngineConfiguration().getDeploymentManager();
        ProcessDefinitionCacheEntry processDefinitionCacheEntry = deploymentManager.getProcessDefinitionCache().get(processDefinitionId);
        DeploymentEntity deploymentEntity = deploymentManager.getDeploymentEntityManager().findById(processDefinitionCacheEntry.getProcessDefinition().getDeploymentId());
        UserTask userTask = (UserTask) ProcessDefinitionUtil.getBpmnModel(processDefinitionId).getProcessById(processDefinitionCacheEntry.getProcessDefinition().getKey()).getFlowElement("UserTaskAdd");
        List<String> deploymentResourceNames = deploymentManager.getDeploymentEntityManager().getDeploymentResourceNames(processDefinitionId);
        Map<String,Object> param = new HashMap<>();
        param.put("key",deploymentEntity.getKey());
        param.put("name",deploymentEntity.getName());
        param.put("derivedFrom",deploymentEntity.getDerivedFrom());
        DeploymentQueryImpl deploymentQuery = new DeploymentQueryImpl();
        deploymentQuery.deploymentKey(deploymentEntity.getKey());
        deploymentManager.getDeploymentEntityManager().findDeploymentsByQueryCriteria(deploymentQuery);
        return null;
    }
}
