package com.pig.easy.bpm.api.service.impl;

import com.pig.easy.bpm.api.dto.response.*;
import com.pig.easy.bpm.api.service.*;
import com.pig.easy.bpm.auth.service.ConfigService;
import com.pig.easy.bpm.common.constant.TaskConstant;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.utils.EasyBpmAsset;
import com.pig.easy.bpm.common.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.HistoryService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/7/4 14:18
 */
@Service
@Slf4j
public class ProcessDiagramServiceImpl implements ProcessDiagramService {

    @Autowired
    ApplyService applyService;
    @Autowired
    ProcessDetailService processDetailService;
    @Autowired
    NodeService nodeService;
    @Autowired
    HistoryService historyService;
    @Autowired
    UserTaskService userTaskService;
    @Autowired
    ConfigService configService;
    @Autowired
    NodeUserService nodeUserService;

    private static final String APPROVED_NODE_FILL = "approvedNodeFill";
    private static final String APPROVED_NODE_STROKE = "approvedNodeStroke";
    private static final String APPROVED_NODE_STROKEN_WIDTH = "approvedNodeStrokenWidth";

    @Override
    public Result<ProcessDiagramDTO> getProcessDiagramByApplyId(Long applyId) {

        EasyBpmAsset.isAssetEmpty(applyId);

        Result<ApplyDTO> result = applyService.getApplyByApplyId(applyId);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result.getEntityError());
        }
        ApplyDTO applyDTO = result.getData();

        Result<ProcessDetailDTO> result1 = processDetailService.getProcessDetailByDefinitionId(applyDTO.getDefinitionId());
        if (result1.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result1.getEntityError());
        }
        ProcessDetailDTO processDetailDTO = result1.getData();

        Result<List<NodeUserDTO>> result2 = nodeUserService.getListByApplyId(applyId);
        if (result2.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result2.getEntityError());
        }
        List<NodeUserDTO> nodeUserDTOS = result2.getData();
        // 避免只需要修改节点名称 导致要发版
        Map<String, String> nodeIdAndNodeUserMap = nodeUserDTOS.stream().collect(Collectors.toMap(NodeUserDTO::getNodeId, NodeUserDTO::getAssigneeUserNameList, (newVal, oldVal) -> newVal));
        Map<String, String> nodeIdAndNodeNameMap = nodeUserDTOS.stream().collect(Collectors.toMap(NodeUserDTO::getNodeId, NodeUserDTO::getNodeName, (newVal, oldVal) -> newVal));

        List<HistoricActivityInstance> historyProcess = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(applyDTO.getProcInstId())
                .finished()
                .orderByHistoricActivityInstanceEndTime().asc()
                .list();
        Result<List<UserTaskDTO>> result3 = userTaskService.getUserTaskByApplyId(applyId);
        if (result3.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result3.getEntityError());
        }

        Map<String, String> nodeUserMap = new ConcurrentHashMap<>();
        ProcessDiagramDTO processDiagramDTO = new ProcessDiagramDTO();
        Map<String, DiagramNodeDTO> resultMap = new HashMap<>();
        DiagramNodeDTO diagramNodeDTO = new DiagramNodeDTO();

        Map<String, String> currentNodeIdMap = new HashMap<>();

        List<UserTaskDTO> userTaskDTOS = result3.getData();
        for (UserTaskDTO userTaskDTO : userTaskDTOS) {
            if (userTaskDTO.getTaskStatus() <= TaskConstant.TASK_COMPLETED) {
                if (userTaskDTO.getTaskStatus() < TaskConstant.TASK_COMPLETED) {
                    currentNodeIdMap.put(userTaskDTO.getTaskNodeCode(), userTaskDTO.getTaskNodeCode());
                }
                if (nodeUserMap.get(userTaskDTO.getTaskNodeCode()) == null) {
                    nodeUserMap.put(userTaskDTO.getTaskNodeCode(),
                            (StringUtils.isEmpty(userTaskDTO.getTaskAssigneeRealName()) ? userTaskDTO.getTaskOwnerRealName() : userTaskDTO.getTaskAssigneeRealName()));
                } else {
                    nodeUserMap.put(userTaskDTO.getTaskNodeCode(), nodeUserMap.get(userTaskDTO.getTaskNodeCode()) + "," +
                            (StringUtils.isEmpty(userTaskDTO.getTaskAssigneeRealName()) ? userTaskDTO.getTaskOwnerRealName() : userTaskDTO.getTaskAssigneeRealName()));
                }
            }
        }

        // 已审批节点
        for (Map.Entry<String, String> entry : nodeUserMap.entrySet()) {
            if (nodeIdAndNodeUserMap.get(entry.getKey()) != null) {
                diagramNodeDTO = new DiagramNodeDTO();
                diagramNodeDTO.setNodeName(nodeIdAndNodeNameMap.get(entry.getKey()) + "(" + entry.getValue() + ")");
                nodeIdAndNodeUserMap.put(entry.getKey(), "(" + entry.getValue() + ")");
                diagramNodeDTO.setNodeId(entry.getKey());
                diagramNodeDTO.setApproved(true);
                resultMap.put(entry.getKey(), diagramNodeDTO);
            }
        }

        for (HistoricActivityInstance historicActivityInstance : historyProcess) {
            String activityType = historicActivityInstance.getActivityType();
            if (activityType.equals("sequenceFlow")
                    || activityType.equals("startEvent")
                    || activityType.equals("endEvent")
                    || activityType.toLowerCase().contains("gateway")
                    || activityType.toLowerCase().contains("subProcess")) {
                nodeIdAndNodeNameMap.put(historicActivityInstance.getActivityId(), historicActivityInstance.getActivityName());
                diagramNodeDTO = new DiagramNodeDTO();
                diagramNodeDTO.setNodeId(historicActivityInstance.getActivityId());
                diagramNodeDTO.setNodeName(historicActivityInstance.getActivityName());
                diagramNodeDTO.setApproved(true);
                resultMap.put(historicActivityInstance.getActivityId(), diagramNodeDTO);
            }
        }

        for (Map.Entry<String, String> entry : currentNodeIdMap.entrySet()) {
            if (resultMap.get(entry.getKey()) != null) {
                diagramNodeDTO = resultMap.get(entry.getKey());
                diagramNodeDTO.setApproved(false);
                diagramNodeDTO.setCurrent(true);
                resultMap.put(entry.getKey(), diagramNodeDTO);
            }
        }

        processDiagramDTO.setProcessXml(processDetailDTO.getProcessXml());
        processDiagramDTO.setNodeMap(resultMap);
        return Result.responseSuccess(processDiagramDTO);
    }

    @Override
    public Result<ProcessDiagramDTO> getProcessDiagramByProInstId(String procInstId) {

        EasyBpmAsset.isAssetEmpty(procInstId);

        Result<ApplyDTO> result = applyService.getApplyByProcInstId(procInstId);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result.getEntityError());
        }
        ApplyDTO applyDTO = result.getData();

        return getProcessDiagramByApplyId(applyDTO.getApplyId());
    }
}
