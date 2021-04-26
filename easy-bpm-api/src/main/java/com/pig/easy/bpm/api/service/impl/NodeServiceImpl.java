package com.pig.easy.bpm.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.api.dto.request.NodeQueryDTO;
import com.pig.easy.bpm.api.dto.request.NodeSaveOrUpdateDTO;
import com.pig.easy.bpm.api.dto.response.FlowUserTaskDTO;
import com.pig.easy.bpm.api.dto.response.NodeDTO;
import com.pig.easy.bpm.api.entity.NodeDO;
import com.pig.easy.bpm.api.mapper.NodeMapper;
import com.pig.easy.bpm.api.service.NodeService;
import com.pig.easy.bpm.api.service.NodeUserService;
import com.pig.easy.bpm.api.utils.FlowElementUtils;
import com.pig.easy.bpm.auth.dto.request.RoleGroupRoleDetailQueryDTO;
import com.pig.easy.bpm.auth.dto.request.UserRoleDetailQueryDTO;
import com.pig.easy.bpm.auth.dto.response.RoleGroupRoleDetailDTO;
import com.pig.easy.bpm.auth.dto.response.UserDTO;
import com.pig.easy.bpm.auth.dto.response.UserRoleDetailDTO;
import com.pig.easy.bpm.auth.service.RoleGroupService;
import com.pig.easy.bpm.auth.service.RoleService;
import com.pig.easy.bpm.auth.service.UserRoleService;
import com.pig.easy.bpm.auth.service.UserService;
import com.pig.easy.bpm.common.constant.BpmConstant;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.generator.BaseServiceImpl;
import com.pig.easy.bpm.common.utils.BeanUtils;
import com.pig.easy.bpm.common.utils.CommonUtils;
import com.pig.easy.bpm.common.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.*;
import org.flowable.engine.ProcessEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * <p>
 * 流程节点表 服务实现类
 * </p>
 *
 * @author pig
 * @since 2021-04-09
 */
@Service
@Slf4j
public class NodeServiceImpl extends BaseServiceImpl<NodeMapper, NodeDO> implements NodeService {

    @Autowired
    NodeMapper mapper;
    @Autowired
    @Lazy
    ProcessEngine processEngine;
    @Autowired
    UserService userService;
    @Autowired
    RoleGroupService roleGroupService;
    @Autowired
    RoleService roleService;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    NodeUserService nodeUserService;

    @Override
    public Result<PageInfo<NodeDTO>> getListPageByCondition(NodeQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        int pageIndex = CommonUtils.evalInt(param.getPageIndex(), DEFAULT_PAGE_INDEX);
        int pageSize = CommonUtils.evalInt(param.getPageSize(), DEFAULT_PAGE_SIZE);

        PageHelper.startPage(pageIndex, pageSize);
        param.setValidState(VALID_STATE);
        List<NodeDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        PageInfo<NodeDTO> pageInfo = new PageInfo<>(list);
        return Result.responseSuccess(pageInfo);
    }

    @Override
    public Result<List<NodeDTO>> getListByCondition(NodeQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        param.setValidState(VALID_STATE);
        List<NodeDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        return Result.responseSuccess(list);
    }

    @Transactional
    @Override
    public Result<Integer> batchInsertOrUpdateNodeInfo(List<NodeSaveOrUpdateDTO> addNodeInfoDTOS, String definitionId) {
        if (addNodeInfoDTOS == null
                || addNodeInfoDTOS.size() == 0
                || StringUtils.isEmpty(definitionId)) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        List<NodeDO> nodeDOList = mapper.selectList(new QueryWrapper<>(NodeDO.builder().validState(VALID_STATE).definitionId(definitionId).build()));
        if (nodeDOList == null) {
            nodeDOList = new ArrayList<>();
        }
        Map<String, NodeDO> nodeMap = nodeDOList.stream().collect(Collectors.toMap(NodeDO::getNodeId, a -> a, (oldVal, newVal) -> newVal));

        List<NodeDO> addList = new ArrayList<>();
        List<NodeDO> updateList = new ArrayList<>();
        NodeDO nodeDO = null;
        for (NodeSaveOrUpdateDTO addNode : addNodeInfoDTOS) {
            if (StringUtils.isEmpty(addNode.getNodeId())) {
                continue;
            }
            nodeDO = BeanUtils.switchToDO(addNode, NodeDO.class);
            nodeDO.setDefinitionId(definitionId);

            if (nodeMap.get(addNode.getNodeId()) == null) {
                nodeDO.setValidState(VALID_STATE);
                addList.add(nodeDO);
                mapper.insert(nodeDO);
            } else {
                nodeDO.setId(nodeMap.get(addNode.getNodeId()).getId());
                updateList.add(nodeDO);
            }
        }

        AtomicInteger automicInteger = new AtomicInteger();
        if (addList.size() > 0) {
            // 批量插入是 有数据库默认值时
            automicInteger.addAndGet(addList.size());
        }
        if (updateList.size() > 0) {
            automicInteger.addAndGet(mapper.batchUpdate(updateList));
        }
        return Result.responseSuccess(automicInteger.get());
    }

    @Override
    public Result<NodeDTO> getNodeInfoByNodeIdAndDefinitionId(String nodeId, String definitionId) {
        if (StringUtils.isEmpty(definitionId)
                || StringUtils.isEmpty(nodeId)) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        NodeDO nodeDO = mapper.selectOne(new QueryWrapper<>(NodeDO.builder().validState(VALID_STATE).nodeId(nodeId).definitionId(definitionId).build()));
        if (nodeDO == null) {
            return Result.responseError(EntityError.DATA_NOT_FOUND_ERROR);
        }
        NodeDTO nodeInfoDTO = BeanUtils.switchToDO(nodeDO, NodeDTO.class);
        return Result.responseSuccess(nodeInfoDTO);
    }

    @Override
    public Result<List<FlowUserTaskDTO>> getNextNodeList(String procDefId, String nodeId, Map<String, Object> dataMap, boolean multipleRecursion) {

        List<FlowUserTaskDTO> userTasks = new ArrayList<>();
        BpmnModel bpmnModel = processEngine.getRepositoryService().getBpmnModel(procDefId);
        org.flowable.bpmn.model.Process process = bpmnModel.getProcesses().get(0);
        Collection<FlowElement> flowElements = process.getFlowElements();
        FlowElement currentFlowElement = null;

        if (StringUtils.isEmpty(nodeId)) {
            currentFlowElement = FlowElementUtils.getStartFlowElement(flowElements);
        } else {
            currentFlowElement = FlowElementUtils.getFlowElementById(nodeId, flowElements);
        }

        getNextNode(procDefId, flowElements, currentFlowElement, dataMap, userTasks, multipleRecursion);

        return Result.responseSuccess(userTasks);
    }

    @Override
    public Result<FlowUserTaskDTO> calcNodeUsers(String procDefId, String nodeId, Map<String, Object> dataMap) {

        FlowUserTaskDTO flowUserTaskDTO = new FlowUserTaskDTO();
        flowUserTaskDTO.setNodeId(nodeId);
        calcNodeUser(flowUserTaskDTO, procDefId, dataMap);
        return Result.responseSuccess(flowUserTaskDTO);
    }

    private void getNextNode(String definitionId, Collection<FlowElement> flowElements, FlowElement flowElement, Map<String, Object> map, List<FlowUserTaskDTO> nextUserTask, boolean multipleRecursion) {

        // 还需支持默认连线 如果有设置默认条线，则先判断其他条线是否满足，如果都不满足，则加上默认条线
        String defaultFlow = "";

        //如果是结束节点
        if (flowElement instanceof EndEvent) {
            //如果是子任务的结束节点
            if (FlowElementUtils.getSubProcess(flowElements, flowElement) != null) {
                flowElement = FlowElementUtils.getSubProcess(flowElements, flowElement);
            }
        }
        List<SequenceFlow> outGoingFlows = null;
        if (flowElement instanceof Task) {
            defaultFlow = ((Task) flowElement).getDefaultFlow();
            outGoingFlows = ((Task) flowElement).getOutgoingFlows();
        } else if (flowElement instanceof Gateway) {
            defaultFlow = ((Gateway) flowElement).getDefaultFlow();
            outGoingFlows = ((Gateway) flowElement).getOutgoingFlows();
        } else if (flowElement instanceof StartEvent) {
            outGoingFlows = ((StartEvent) flowElement).getOutgoingFlows();
        } else if (flowElement instanceof SubProcess) {
            defaultFlow = ((SubProcess) flowElement).getDefaultFlow();
            outGoingFlows = ((SubProcess) flowElement).getOutgoingFlows();
        }

        boolean goDefaultFlow = true;
        if (outGoingFlows != null && outGoingFlows.size() > 0) {
            //遍历所有的出线--找到可以正确执行的那一条
            for (SequenceFlow sequenceFlow : outGoingFlows) {
                //1.有表达式，且为true
                //2.无表达式
                String expression = sequenceFlow.getConditionExpression();

                /* 非默认连线 */
                if (!sequenceFlow.getId().equals(defaultFlow)) {
                    if (StringUtils.isEmpty(expression) || FlowElementUtils.getJuleValue(expression, map)) {

                        goDefaultFlow = false;

                        //出线的下一节点
                        String nextFlowElementID = sequenceFlow.getTargetRef();
                        //查询下一节点的信息
                        FlowElement nextFlowElement = FlowElementUtils.getFlowElementById(nextFlowElementID, flowElements);

                        //用户任务
                        if (nextFlowElement instanceof UserTask) {
                            UserTask userTask = (UserTask) nextFlowElement;
                            FlowUserTaskDTO flowUserTaskDTO = new FlowUserTaskDTO();
                            flowUserTaskDTO.setNodeId(userTask.getId());
                            flowUserTaskDTO.setNodeName(userTask.getName());
                            flowUserTaskDTO.setParentNodeId(flowElement.getId());
                            flowUserTaskDTO.setParentNodeName(flowElement.getName());
                            calcNodeUser(flowUserTaskDTO, definitionId, map);

                            if (flowUserTaskDTO.isError()) {
                                // 跳出
                                break;
                            }

                            // 是否需要连续找下一个节点 直到结束 true ：连续 false : 直找到第一个即可
                            if (multipleRecursion || flowUserTaskDTO.isSkip()) {
                                nextUserTask.add(flowUserTaskDTO);
                                getNextNode(definitionId, flowElements, nextFlowElement, map, nextUserTask, multipleRecursion);
                            }

                        }
                        //排他网关
                        else if (nextFlowElement instanceof ExclusiveGateway) {
                            getNextNode(definitionId, flowElements, nextFlowElement, map, nextUserTask, multipleRecursion);
                        }
                        //并行网关
                        else if (nextFlowElement instanceof ParallelGateway) {
                            getNextNode(definitionId, flowElements, nextFlowElement, map, nextUserTask, multipleRecursion);
                        }
                        //接收任务
                        else if (nextFlowElement instanceof ReceiveTask) {
                            getNextNode(definitionId, flowElements, nextFlowElement, map, nextUserTask, multipleRecursion);
                        }
                        //子任务的起点
                        else if (nextFlowElement instanceof StartEvent) {
                            getNextNode(definitionId, flowElements, nextFlowElement, map, nextUserTask, multipleRecursion);
                        }
                        //结束节点
                        else if (nextFlowElement instanceof EndEvent) {
                            getNextNode(definitionId, flowElements, nextFlowElement, map, nextUserTask, multipleRecursion);
                        } else {
                            getNextNode(definitionId, flowElements, nextFlowElement, map, nextUserTask, multipleRecursion);
                        }
                    }
                }
            }

            /* 如果其他条线没有符合的条件，则走默认条线 */
            if (goDefaultFlow && defaultFlow != null) {
                for (SequenceFlow sequenceFlow : outGoingFlows) {
                    if (sequenceFlow.getId().equals(defaultFlow)) {
                        //出线的下一节点
                        String nextFlowElementID = sequenceFlow.getTargetRef();
                        //查询下一节点的信息
                        FlowElement nextFlowElement = FlowElementUtils.getFlowElementById(nextFlowElementID, flowElements);
                        //用户任务
                        if (nextFlowElement instanceof UserTask) {
                            UserTask userTask = (UserTask) nextFlowElement;
                            FlowUserTaskDTO flowUserTaskDTO = new FlowUserTaskDTO();
                            flowUserTaskDTO.setNodeId(userTask.getId());
                            flowUserTaskDTO.setNodeName(userTask.getName());
                            flowUserTaskDTO.setParentNodeId(flowElement.getId());
                            flowUserTaskDTO.setParentNodeName(flowElement.getName());
                            calcNodeUser(flowUserTaskDTO, definitionId, map);
                            if (flowUserTaskDTO.isError()) {
                                break;
                            }
                            // 是否需要连续找下一个节点 直到结束 true ：连续 false : 直找到第一个即可
                            if (multipleRecursion || flowUserTaskDTO.isSkip()) {
                                nextUserTask.add(flowUserTaskDTO);
                                getNextNode(definitionId, flowElements, nextFlowElement, map, nextUserTask, multipleRecursion);
                            }
                        }
                        //排他网关
                        else if (nextFlowElement instanceof ExclusiveGateway) {
                            getNextNode(definitionId, flowElements, nextFlowElement, map, nextUserTask, multipleRecursion);
                        }
                        //并行网关
                        else if (nextFlowElement instanceof ParallelGateway) {
                            getNextNode(definitionId, flowElements, nextFlowElement, map, nextUserTask, multipleRecursion);
                        }
                        //接收任务
                        else if (nextFlowElement instanceof ReceiveTask) {
                            getNextNode(definitionId, flowElements, nextFlowElement, map, nextUserTask, multipleRecursion);
                        }
                        //子任务的起点
                        else if (nextFlowElement instanceof StartEvent) {
                            getNextNode(definitionId, flowElements, nextFlowElement, map, nextUserTask, multipleRecursion);
                        }
                        //结束节点
                        else if (nextFlowElement instanceof EndEvent) {
                            getNextNode(definitionId, flowElements, nextFlowElement, map, nextUserTask, multipleRecursion);
                        } else {
                            getNextNode(definitionId, flowElements, nextFlowElement, map, nextUserTask, multipleRecursion);
                        }
                    }
                }
            }
        }
    }

    private void calcNodeUser(FlowUserTaskDTO flowUserTaskDTO, String definitionId, Map<String, Object> dataMap) {

        if (StringUtils.isEmpty(definitionId)
                || flowUserTaskDTO == null
                || StringUtils.isEmpty(flowUserTaskDTO.getNodeId())) {
            return;
        }
        Result<NodeDTO> result = getNodeInfoByNodeIdAndDefinitionId(flowUserTaskDTO.getNodeId(), definitionId);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            log.error("calcNodeUser error ，param 【{}，{}】 : message {} ", flowUserTaskDTO.getNodeId(), definitionId, result.getEntityError().getMessage());
        }
        List<Long> owners = new ArrayList<>();
        List<String> ownerNames = new ArrayList<>();
        List<String> ownerUserIds = new ArrayList<>();
        List<Long> roleIdList = new ArrayList<>();
        NodeDTO nodeInfoDTO = result.getData();

        Result<List<UserRoleDetailDTO>> result2 = null;
        String[] split = null;
        Result<UserDTO> result3 = Result.responseError(EntityError.SYSTEM_ERROR);
        // 如果是发起人节点
        if (nodeInfoDTO.getTaskType().equals(BpmConstant.TASK_TYPE_START)) {
            if (dataMap.get(BpmConstant.APPLY_USER_INFO) != null) {
                UserDTO userInfoDTO = (UserDTO) dataMap.get(BpmConstant.APPLY_USER_INFO);
                owners.add(userInfoDTO.getUserId());
                ownerNames.add(userInfoDTO.getRealName());
                ownerUserIds.add(userInfoDTO.getUserId().toString());
            }
        } else {
            switch (nodeInfoDTO.getFindUserType()) {

                case BpmConstant.FIND_USER_TYPE_BY_ROLE_GROUP:

                    if (StringUtils.isEmpty(nodeInfoDTO.getRoleGroupCode())) {
                        break;
                    }
                    Result<List<RoleGroupRoleDetailDTO>> result1 = roleService.getRoleGroupRoleDetailList(RoleGroupRoleDetailQueryDTO.builder().roleGroupCode(nodeInfoDTO.getRoleGroupCode()).build());
                    List<RoleGroupRoleDetailDTO> data = result1.getData();
                    if (result1.getData().size() == 0) {
                        break;
                    }
                    /* 根据 条线及层级获取 角色组 关联角色 比如 分公司部门负责人  找 总公司部门负责人 */
                    if (BpmConstant.ROLE_GROUP_TYPE_SPECIAL == data.get(0).getRoleGroupType()) {
                        for (RoleGroupRoleDetailDTO roleGroupRoleDetailDTO : data) {
                            if (roleGroupRoleDetailDTO.getRoleLevel().equals(data.get(0).getRoleGroupLevel())
                                    && roleGroupRoleDetailDTO.getDeptBusinessLine().equals(data.get(0).getRoleGroupBusinessLine())) {
                                roleIdList.add(roleGroupRoleDetailDTO.getRoleId());
                            }
                        }
                    }
                    if (BpmConstant.ROLE_GROUP_TYPE_COMMON == data.get(0).getRoleGroupType()) {
                        for (RoleGroupRoleDetailDTO roleGroupRoleDetailDTO : data) {
                            roleIdList.add(roleGroupRoleDetailDTO.getRoleId());
                        }
                    }
                    result2 = userRoleService.getUserRoleDetailByCondition(UserRoleDetailQueryDTO.builder().roleIdList(roleIdList).build());
                    if (result2.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                        log.error("getUserRoleDetailByCondition error ，param 【{}】 : message {} ", roleIdList, result2.getEntityError());
                    }
                    for (UserRoleDetailDTO userRoleDetailDTO : result2.getData()) {
                        owners.add(userRoleDetailDTO.getUserId());
                        ownerNames.add(userRoleDetailDTO.getRealName());
                        ownerUserIds.add(userRoleDetailDTO.getUserId().toString());
                    }

                    break;
                case BpmConstant.FIND_USER_TYPE_BY_ROLE:

                    if (StringUtils.isEmpty(nodeInfoDTO.getRoleCode())) {
                        return;
                    }
                    result2 = userRoleService.getUserRoleDetailByCondition(UserRoleDetailQueryDTO.builder().roleCode(nodeInfoDTO.getRoleCode()).build());
                    if (result2.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
                        log.error("getUserRoleDetailByCondition error ，param 【{}】 : message {} ", roleIdList, result2.getEntityError());
                    }
                    for (UserRoleDetailDTO userRoleDetailDTO : result2.getData()) {
                        owners.add(userRoleDetailDTO.getUserId());
                        ownerNames.add(userRoleDetailDTO.getRealName());
                        ownerUserIds.add(userRoleDetailDTO.getUserId().toString());
                    }

                    break;
                case BpmConstant.FIND_USER_TYPE_BY_USER:

                    if (StringUtils.isEmpty(nodeInfoDTO.getUserIdList())) {
                        return;
                    }
                    split = nodeInfoDTO.getUserIdList().split(",");
                    for (String userId : split) {
                        if (StringUtils.isEmpty(userId)) {
                            continue;
                        }
                        result3 = userService.getUserInfoById(Long.valueOf(userId));
                        if (result3.getEntityError().getCode() == EntityError.SUCCESS.getCode()) {
                            ownerNames.add(result3.getData().getRealName());
                        }
                        owners.add(Long.valueOf(userId));
                        ownerUserIds.add(userId);
                    }
                    break;
                case BpmConstant.FIND_USER_TYPE_BY_DESIGNATED_PERSONNEL:
                    if (dataMap.get(nodeInfoDTO.getAssigneeField()) != null) {
                        String userIdListStr = dataMap.get(nodeInfoDTO.getAssigneeField()).toString();
                        split = userIdListStr.split(",");
                        for (String userId : split) {
                            if (StringUtils.isEmpty(userId)) {
                                continue;
                            }
                            result3 = userService.getUserInfoById(Long.valueOf(userId));
                            if (result3.getEntityError().getCode() == EntityError.SUCCESS.getCode()) {
                                ownerNames.add(result3.getData().getRealName());
                            }
                            owners.add(Long.valueOf(userId));
                            ownerUserIds.add(userId);
                        }
                    }

                    break;
                case BpmConstant.FIND_USER_TYPE_BY_APPLYER:

                    if (dataMap.get(BpmConstant.APPLY_USER_INFO) != null) {
                        UserDTO userInfoDTO = (UserDTO) dataMap.get(BpmConstant.APPLY_USER_INFO);
                        owners.add(userInfoDTO.getUserId());
                        ownerNames.add(userInfoDTO.getRealName());
                        ownerUserIds.add(userInfoDTO.getUserId().toString());
                    }
                    break;
                case BpmConstant.FIND_USER_TYPE_BY_NODE_USER:

                    if (dataMap.get(nodeInfoDTO.getRelationNodeId()) != null) {
                        List<Long> tempOwners = (List<Long>) dataMap.get(nodeInfoDTO.getRelationNodeId());
                        owners.addAll(tempOwners);
                        for (Long temp : tempOwners) {
                            result3 = userService.getUserInfoById(temp);
                            if (result3.getEntityError().getCode() == EntityError.SUCCESS.getCode()) {
                                ownerNames.add(result3.getData().getRealName());
                            }
                            ownerUserIds.add(temp.toString());
                        }
                    }
                    break;
                default:
                    break;
            }
        }

        flowUserTaskDTO.setFindUserType(nodeInfoDTO.getFindUserType());
        flowUserTaskDTO.setPriority(nodeInfoDTO.getPriority());
        flowUserTaskDTO.setFormKey(nodeInfoDTO.getFormKey());
        flowUserTaskDTO.setOwnerNames(ownerNames);
        flowUserTaskDTO.setOwners(owners);
        flowUserTaskDTO.setOwnerUserIds(ownerUserIds);
        flowUserTaskDTO.setHandlerStrategy(nodeInfoDTO.getHandlerStrategy());

        if (owners.size() == 0) {
            switch (nodeInfoDTO.getHandlerStrategy()) {
                case BpmConstant.HANDLER_STRATEGY_SKIP:
                    flowUserTaskDTO.setSkip(true);
                    break;
                case BpmConstant.HANDLER_STRATEGY_ADMIN:
                    flowUserTaskDTO.setDefaultSetAdmin(true);
                    break;
                case BpmConstant.HANDLER_STRATEGY_ERROR:
                    flowUserTaskDTO.setError(true);
                    break;
                default:
                    break;
            }
        }
        dataMap.put(nodeInfoDTO.getNodeId(), owners);
    }



    @Override
    public Result<Integer> insertNode(NodeSaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        NodeDO temp = BeanUtils.switchToDO(param, NodeDO.class);
        Integer num = mapper.insert(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer>updateNode(NodeSaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        NodeDO temp = BeanUtils.switchToDO(param, NodeDO.class);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer>deleteNode(NodeSaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        NodeDO temp = BeanUtils.switchToDO(param, NodeDO.class);
        temp.setValidState(INVALID_STATE);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<NodeDTO> getNodeById(Long id){

        if (id == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        NodeDO query = new NodeDO();
        query.setId(id);
        query = mapper.selectOne(new QueryWrapper<>(query));

        if (query == null) {
            return Result.responseError(EntityError.DATA_NOT_FOUND_ERROR);
        }
        NodeDTO result = BeanUtils.switchToDTO(query, NodeDTO.class);
        return Result.responseSuccess(result);
    }
}
