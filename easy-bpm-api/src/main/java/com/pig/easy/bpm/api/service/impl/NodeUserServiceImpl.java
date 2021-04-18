package com.pig.easy.bpm.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pig.easy.bpm.api.dto.request.NodeUserQueryDTO;
import com.pig.easy.bpm.api.dto.request.NodeUserSaveOrUpdateDTO;
import com.pig.easy.bpm.api.dto.response.NodeUserDTO;
import com.pig.easy.bpm.api.entity.NodeUserDO;
import com.pig.easy.bpm.api.mapper.NodeUserMapper;
import com.pig.easy.bpm.api.service.NodeUserService;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.generator.BaseServiceImpl;
import com.pig.easy.bpm.common.utils.BeanUtils;
import com.pig.easy.bpm.common.utils.EasyBpmAsset;
import com.pig.easy.bpm.common.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 * 节点人员表 服务实现类
 * </p>
 *
 * @author pig
 * @since 2020-07-04
 */
@Service
@Slf4j
public class NodeUserServiceImpl extends BaseServiceImpl<NodeUserMapper, NodeUserDO> implements NodeUserService {

    @Autowired
    NodeUserMapper nodeUserMapper;

    @Override
    public Result<List<NodeUserDTO>> getListByCondition(NodeUserQueryDTO nodeUserQueryDTO) {

        if (nodeUserQueryDTO == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        NodeUserDO nodeUserDO = BeanUtils.objectToBean(nodeUserQueryDTO, NodeUserDO.class);

        List<NodeUserDO> list = nodeUserMapper.selectList(new QueryWrapper<>(nodeUserDO));
        if (list == null) {
            list = new ArrayList<>();
        }
        NodeUserDTO nodeUserDTO = null;
        List<NodeUserDTO> result = new ArrayList<>();
        for (NodeUserDO tempNodeUserDO : list) {
            nodeUserDTO = BeanUtils.switchToDTO(tempNodeUserDO, NodeUserDTO.class);
            result.add(nodeUserDTO);
        }
        return Result.responseSuccess(result);
    }

    @Override
    public Result<List<NodeUserDTO>> getListByApplyId(Long applyId) {

        EasyBpmAsset.isAssetEmpty(applyId);

        List<NodeUserDO> nodeUserDOS = nodeUserMapper.selectList(new QueryWrapper<>(NodeUserDO.builder().validState(VALID_STATE).applyId(applyId).build()));
        NodeUserDTO nodeUserDTO = null;
        List<NodeUserDTO> result = new ArrayList<>();
        for (NodeUserDO tempNodeUserDO : nodeUserDOS) {
            nodeUserDTO = BeanUtils.switchToDTO(tempNodeUserDO, NodeUserDTO.class);
            result.add(nodeUserDTO);
        }
        return Result.responseSuccess(result);

    }

    @Transactional
    @Override
    public Result<Integer> batchSave(List<NodeUserSaveOrUpdateDTO> nodeUserList) {

        if (nodeUserList == null || nodeUserList.size() == 0) {
            return Result.responseSuccess(0);
        }

        EasyBpmAsset.isAssetEmpty(nodeUserList.get(0).getApplyId());

        //先清除在插入
        nodeUserMapper.update(NodeUserDO.builder().validState(INVALID_STATE).updateTime(LocalDateTime.now()).build(),
                new QueryWrapper<>(NodeUserDO.builder().applyId(nodeUserList.get(0).getApplyId()).validState(VALID_STATE).build()));

        List<NodeUserDO> saveList = new ArrayList<>();

        NodeUserDO nodeUserDO = null;
        for (NodeUserSaveOrUpdateDTO nodeUserSaveOrUpdateDTO : nodeUserList) {
            nodeUserDO = BeanUtils.switchToDO(nodeUserSaveOrUpdateDTO, NodeUserDO.class);
            saveList.add(nodeUserDO);
        }

        AtomicInteger atomicInteger = new AtomicInteger(0);
        if (saveList.size() > 0) {
            atomicInteger.addAndGet(nodeUserMapper.batchInsert(saveList));
        }

        return Result.responseSuccess(atomicInteger.get());
    }

    @Override
    public Result<Integer> updateNodeUserDefinitionIdByProcInscId(String procInscId, String definitionId) {

        EasyBpmAsset.isAssetEmpty(procInscId);
        EasyBpmAsset.isAssetEmpty(definitionId);

        int num = nodeUserMapper.update(NodeUserDO.builder().definitionId(definitionId).updateTime(LocalDateTime.now()).build(),
                new QueryWrapper<>(NodeUserDO.builder().procInstId(procInscId).build()));

        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer> insert(NodeUserSaveOrUpdateDTO nodeUserSaveOrUpdateDTO) {

        EasyBpmAsset.isAssetEmpty(nodeUserSaveOrUpdateDTO);
        NodeUserDO nodeUserDO = BeanUtils.switchToDO(nodeUserSaveOrUpdateDTO, NodeUserDO.class);
        List<NodeUserDO> saveList = new ArrayList<>();
        saveList.add(nodeUserDO);
        int insert = nodeUserMapper.batchInsert(saveList);
        return Result.responseSuccess(insert);
    }
}
