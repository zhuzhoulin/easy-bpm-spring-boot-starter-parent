package com.pig.easy.bpm.api.service;

import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.api.dto.request.NodeQueryDTO;
import com.pig.easy.bpm.api.dto.request.NodeSaveOrUpdateDTO;
import com.pig.easy.bpm.api.dto.response.FlowUserTaskDTO;
import com.pig.easy.bpm.api.dto.response.NodeDTO;
import com.pig.easy.bpm.api.entity.NodeDO;
import com.pig.easy.bpm.common.generator.BaseService;
import com.pig.easy.bpm.common.utils.Result;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 流程节点表 服务类
 * </p>
 *
 * @author pig
 * @since 2021-04-09
 */
public interface NodeService extends BaseService<NodeDO> {

    Result<PageInfo<NodeDTO>> getListPageByCondition(NodeQueryDTO param);

    Result<Integer> insertNode(NodeSaveOrUpdateDTO param);

    Result<Integer> updateNode(NodeSaveOrUpdateDTO param);

    Result<Integer> deleteNode(NodeSaveOrUpdateDTO param);

    Result<NodeDTO> getNodeById(Long id);

    Result<List<NodeDTO>> getListByCondition(NodeQueryDTO param);

    Result<Integer> batchInsertOrUpdateNodeInfo(List<NodeSaveOrUpdateDTO> addNodeInfoDTOS, String definitionId);

    Result<NodeDTO> getNodeInfoByNodeIdAndDefinitionId(String nodeId, String procDefId);

    Result<List<FlowUserTaskDTO>> getNextNodeList(String procDefId, String nodeId, Map<String, Object> dataMap, boolean multipleRecursion);

    Result<FlowUserTaskDTO> calcNodeUsers(String procDefId, String nodeId, Map<String, Object> dataMap);
}
