package com.pig.easy.bpm.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.auth.dto.request.DeptQueryDTO;
import com.pig.easy.bpm.auth.dto.request.DeptSaveOrUpdateDTO;
import com.pig.easy.bpm.auth.dto.response.DeptDTO;
import com.pig.easy.bpm.auth.dto.response.ItemDTO;
import com.pig.easy.bpm.auth.dto.response.TreeDTO;
import com.pig.easy.bpm.auth.entity.DeptDO;
import com.pig.easy.bpm.auth.mapper.DeptMapper;
import com.pig.easy.bpm.auth.service.CompanyService;
import com.pig.easy.bpm.auth.service.DeptService;
import com.pig.easy.bpm.auth.utils.TreeUtils;
import com.pig.easy.bpm.common.constant.BpmConstant;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.generator.BaseServiceImpl;
import com.pig.easy.bpm.common.utils.BeanUtils;
import com.pig.easy.bpm.common.utils.CommonUtils;
import com.pig.easy.bpm.common.utils.EasyBpmAsset;
import com.pig.easy.bpm.common.utils.Result;
import com.pig.easy.bpm.common.utils.id.SnowKeyGenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author pig
 * @since 2021-03-20
 */
@Service
public class DeptServiceImpl extends BaseServiceImpl<DeptMapper, DeptDO> implements DeptService {

    @Autowired
    DeptMapper mapper;
    @Autowired
    CompanyService companyService;

    @Override
    public Result<PageInfo<DeptDTO>> getListPageByCondition(DeptQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        int pageIndex = CommonUtils.evalInt(param.getPageIndex(), DEFAULT_PAGE_INDEX);
        int pageSize = CommonUtils.evalInt(param.getPageSize(), DEFAULT_PAGE_SIZE);

        PageHelper.startPage(pageIndex, pageSize);
        param.setValidState(VALID_STATE);
        List<DeptDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        PageInfo<DeptDTO> pageInfo = new PageInfo<>(list);
        return Result.responseSuccess(pageInfo);
    }

    @Override
    public Result<List<DeptDTO>> getListByCondition(DeptQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        param.setValidState(VALID_STATE);
        List<DeptDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        return Result.responseSuccess(list);
    }

    @Override
    public Result<List<ItemDTO>> getDeptItemList(DeptQueryDTO deptQueryDTO) {

        deptQueryDTO.setPageIndex(DEFAULT_PAGE_INDEX);
        deptQueryDTO.setPageSize(MAX_PAGE_SIZE);
        Result<List<DeptDTO>> result = getListByCondition(deptQueryDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result.getEntityError());
        }
        List<ItemDTO> itemDTOS = new ArrayList<>();
        ItemDTO itemDTO = null;
        for (DeptDTO dept : result.getData()) {
            itemDTO = new ItemDTO(dept.getDeptName(), dept.getDeptCode(),false);
            itemDTOS.add(itemDTO);
        }
        return Result.responseSuccess(itemDTOS);
    }

    @Override
    public Result<List<ItemDTO>> getDeptIdItemList(DeptQueryDTO deptQueryDTO) {

        deptQueryDTO.setPageIndex(DEFAULT_PAGE_INDEX);
        deptQueryDTO.setPageSize(MAX_PAGE_SIZE);
        Result<List<DeptDTO>> result = getListByCondition(deptQueryDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result.getEntityError());
        }
        List<ItemDTO> itemDTOS = new ArrayList<>();
        ItemDTO itemDTO = null;
        for (DeptDTO dept : result.getData()) {
            itemDTO = new ItemDTO(dept.getDeptName(), dept.getDeptId(),false);
            itemDTOS.add(itemDTO);
        }
        return Result.responseSuccess(itemDTOS);
    }

    @Override
    public Result<List<TreeDTO>> getDeptTree(Long parentDeptId, String tenantId) {
        Result<List<TreeDTO>> result = getDeptTree(parentDeptId, null, tenantId);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result.getEntityError());
        }
        return Result.responseSuccess(result.getData());
    }

    @Override
    public Result<List<TreeDTO>> getDeptTree(Long parentDeptId, Long companyId, String tenantId) {
        if (CommonUtils.evalLong(parentDeptId) < 0) {
            parentDeptId = 0L;
        }
        if (CommonUtils.evalLong(companyId) < 0) {
            companyId = 0L;
        }
        EasyBpmAsset.isAssetEmpty(tenantId);
        DeptQueryDTO deptQueryDTO = new DeptQueryDTO();
        deptQueryDTO.setTenantId(tenantId);

        Result<List<DeptDTO>> result = getListByCondition(deptQueryDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result.getEntityError());
        }

        List<TreeDTO> list = new ArrayList<>();
        TreeDTO treeDTO = null;

        for (DeptDTO deptDTO : result.getData()) {
            if (deptDTO != null) {
                treeDTO = new TreeDTO();
                treeDTO.setId(SnowKeyGenUtils.getInstance().getNextId());
                treeDTO.setTreeId(deptDTO.getDeptId());
                treeDTO.setTreeCode(deptDTO.getDeptCode());
                treeDTO.setTreeName(deptDTO.getDeptName());
                treeDTO.setTempTreeId(deptDTO.getCompanyId());
                treeDTO.setParentId(deptDTO.getDeptParentId());
                treeDTO.setTreeTypeCode(BpmConstant.TREE_TYPE_CODE_DEPT);
                treeDTO.setTreeType(BpmConstant.TREE_TYPE_DEPT);
                list.add(treeDTO);
            }
        }
        list = TreeUtils.makeTree(list, parentDeptId);

        /* 移除非 */
        Iterator<TreeDTO> iterator = list.iterator();
        while (iterator.hasNext()) {
            TreeDTO next = iterator.next();
            if (!next.getParentId().equals(parentDeptId)
                    || !companyId.equals(next.getTempTreeId())) {
                iterator.remove();
            }
        }

        return Result.responseSuccess(list);
    }

    @Override
    public Result<List<TreeDTO>> getOrganTree(String tenantId, Long compantId) {

        Result<List<TreeDTO>> result = companyService.getCompanyTree(compantId, tenantId,BpmConstant.TREE_TYPE_CODE_COMPANY);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result.getEntityError());
        }
        List<TreeDTO> companyTreeList = result.getData();

        List<TreeDTO> list = new ArrayList<>();
        for (TreeDTO temp : companyTreeList) {
            list.add(makeOrganTree(temp, tenantId, 0L, 1));
        }

        return Result.responseSuccess(list);
    }

    private TreeDTO makeOrganTree(TreeDTO tree, String tenantId, Long parentId, Integer treeType) {

        List<TreeDTO> tempList = new ArrayList<>();
        if (tree.getChildren() == null) {
            tree.setChildren(new ArrayList<>());
        }
        List<TreeDTO> children = tree.getChildren();
        Result<List<TreeDTO>> result = getDeptTree(parentId, tree.getTreeId(), tenantId);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            log.error("makeOrganTree error,message :" + result.getEntityError());
            return tree;
        }
        tempList.addAll(result.getData());

        for (TreeDTO temp : children) {
            if (temp.getTreeType().equals(treeType)) {
                tempList.add(makeOrganTree(temp, tenantId, temp.getTreeId(), treeType));
            }
        }
        tree.setChildren(tempList);
        return tree;
    }

    @Override
    public Result<Integer> insertDept(DeptSaveOrUpdateDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        DeptDO temp = BeanUtils.switchToDO(param, DeptDO.class);
        Integer num = mapper.insert(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer> updateDept(DeptSaveOrUpdateDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        DeptDO temp = BeanUtils.switchToDO(param, DeptDO.class);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer> deleteDept(DeptSaveOrUpdateDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        DeptDO temp = BeanUtils.switchToDO(param, DeptDO.class);
        temp.setValidState(INVALID_STATE);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<DeptDTO> getDeptById(Long deptId) {

        if (deptId == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        DeptDO query = new DeptDO();
        query.setDeptId(deptId);
        query = mapper.selectOne(new QueryWrapper<>(query));

        if (query == null) {
            return Result.responseError(EntityError.DATA_NOT_FOUND_ERROR);
        }
        DeptDTO result = BeanUtils.switchToDTO(query, DeptDTO.class);
        return Result.responseSuccess(result);
    }
}
