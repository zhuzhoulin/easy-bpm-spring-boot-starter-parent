package com.pig.easy.bpm.auth.service;

import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.auth.dto.request.DeptQueryDTO;
import com.pig.easy.bpm.auth.dto.request.DeptSaveOrUpdateDTO;
import com.pig.easy.bpm.auth.dto.response.DeptDTO;
import com.pig.easy.bpm.auth.dto.response.ItemDTO;
import com.pig.easy.bpm.auth.dto.response.TreeDTO;
import com.pig.easy.bpm.auth.entity.DeptDO;
import com.pig.easy.bpm.common.generator.BaseService;
import com.pig.easy.bpm.common.utils.Result;

import java.util.List;
/**
 * <p>
 * 部门表 服务类
 * </p>
 *
 * @author pig
 * @since 2021-03-20
 */
public interface DeptService extends BaseService<DeptDO> {

    Result<PageInfo<DeptDTO>> getListPageByCondition(DeptQueryDTO param);

    Result<Integer> insertDept(DeptSaveOrUpdateDTO param);

    Result<Integer> updateDept(DeptSaveOrUpdateDTO param);

    Result<Integer> deleteDept(DeptSaveOrUpdateDTO param);

    Result<DeptDTO> getDeptById(Long deptId);

    Result<List<DeptDTO>> getListByCondition(DeptQueryDTO param);

    Result<List<ItemDTO>> getDeptItemList(DeptQueryDTO deptQueryDTO);

    Result<List<ItemDTO>> getDeptIdItemList(DeptQueryDTO deptQueryDTO);

    Result<List<TreeDTO>> getDeptTree(Long parentDeptId, String tenantId);

    Result<List<TreeDTO>> getDeptTree(Long parentDeptId, Long companyId, String tenantId);

    Result<List<TreeDTO>> getOrganTree(String tenantId, Long compantId);
}
