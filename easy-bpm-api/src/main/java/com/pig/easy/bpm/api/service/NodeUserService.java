package com.pig.easy.bpm.api.service;


import com.pig.easy.bpm.api.dto.request.NodeUserQueryDTO;
import com.pig.easy.bpm.api.dto.request.NodeUserSaveOrUpdateDTO;
import com.pig.easy.bpm.api.dto.response.NodeUserDTO;
import com.pig.easy.bpm.api.entity.NodeUserDO;
import com.pig.easy.bpm.common.generator.BaseService;
import com.pig.easy.bpm.common.utils.Result;

import java.util.List;

/**
 * <p>
 * 节点人员表 服务类
 * </p>
 *
 * @author pig
 * @since 2020-07-04
 */
public interface NodeUserService  extends BaseService<NodeUserDO> {

    Result<List<NodeUserDTO>> getListByCondition(NodeUserQueryDTO nodeUserQueryDTO);

    Result<List<NodeUserDTO>> getListByApplyId(Long applyId);

    Result<Integer> batchSave(List<NodeUserSaveOrUpdateDTO> nodeUserList);

    Result<Integer> updateNodeUserDefinitionIdByProcInscId(String procInscId, String definitionId);

    Result<Integer> insert(NodeUserSaveOrUpdateDTO nodeUserSaveOrUpdateDTO);
}
