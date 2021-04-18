package com.pig.easy.bpm.api.core.flowable;

import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.engine.impl.persistence.deploy.DeploymentManager;
import org.flowable.engine.impl.persistence.deploy.ProcessDefinitionCacheEntry;
import org.flowable.engine.impl.util.CommandContextUtil;

import java.io.Serializable;

/**
 *
 * @author: zhuzl
 * @date : 2019/7/3 13:48
 */
public class GetProcessDefinitionCacheEntryCmd implements Command<ProcessDefinitionCacheEntry>, Serializable {

	private static final long serialVersionUID = -3194072260173186567L;
	private String processDefinitionId;
	
	public GetProcessDefinitionCacheEntryCmd(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	@Override
	public ProcessDefinitionCacheEntry execute(CommandContext commandContext) {
		DeploymentManager deploymentManager = CommandContextUtil.getProcessEngineConfiguration().getDeploymentManager();

		return deploymentManager.getProcessDefinitionCache().get(processDefinitionId);
	}


}
