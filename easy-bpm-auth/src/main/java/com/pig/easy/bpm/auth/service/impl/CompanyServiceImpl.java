package com.pig.easy.bpm.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.auth.dto.request.CompanyQueryDTO;
import com.pig.easy.bpm.auth.dto.request.CompanySaveOrUpdateDTO;
import com.pig.easy.bpm.auth.dto.response.CompanyDTO;
import com.pig.easy.bpm.auth.dto.response.ItemDTO;
import com.pig.easy.bpm.auth.dto.response.TreeDTO;
import com.pig.easy.bpm.auth.entity.CompanyDO;
import com.pig.easy.bpm.auth.mapper.CompanyMapper;
import com.pig.easy.bpm.auth.service.CompanyService;
import com.pig.easy.bpm.auth.utils.TreeUtils;
import com.pig.easy.bpm.common.constant.BpmConstant;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.generator.BaseServiceImpl;
import com.pig.easy.bpm.common.utils.*;
import com.pig.easy.bpm.common.utils.id.SnowKeyGenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.krb5.internal.PAData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * <p>
 * 公司表 服务实现类
 * </p>
 *
 * @author pig
 * @since 2021-03-20
 */
@Service
public class CompanyServiceImpl extends BaseServiceImpl<CompanyMapper, CompanyDO> implements CompanyService {

    @Autowired
    CompanyMapper mapper;

    @Override
    public Result<PageInfo<CompanyDTO>> getListPageByCondition(CompanyQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        int pageIndex = CommonUtils.evalInt(param.getPageIndex(), DEFAULT_PAGE_INDEX);
        int pageSize = CommonUtils.evalInt(param.getPageSize(), DEFAULT_PAGE_SIZE);

        PageHelper.startPage(pageIndex, pageSize);
        param.setValidState(VALID_STATE);
        List<CompanyDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        PageInfo<CompanyDTO> pageInfo = new PageInfo<>(list);
        return Result.responseSuccess(pageInfo);
    }

    @Override
    public Result<List<CompanyDTO>> getListByCondition(CompanyQueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        param.setValidState(VALID_STATE);
        List<CompanyDTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        return Result.responseSuccess(list);
    }

    @Override
    public Result<List<TreeDTO>> getCompanyTree(Long parentId, String tenantId,String showTreeType ){

        if(CommonUtils.evalLong(parentId) < 0){
            parentId = 0L;
        }
       EasyBpmAsset.isEmpty(tenantId);
        CompanyQueryDTO companyQueryDTO = new CompanyQueryDTO();
        companyQueryDTO.setTenantId(tenantId);

        Result<List<CompanyDTO>> result = getListByCondition(companyQueryDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return Result.responseError(result.getEntityError());
        }
        List<CompanyDTO> companyDTOList = result.getData();
        List<TreeDTO> list = new ArrayList<>();
        TreeDTO treeDTO = null;

        for (CompanyDTO companyDTO : companyDTOList) {
            if (companyDTO != null) {
                treeDTO = new TreeDTO();
                treeDTO.setId(SnowKeyGenUtils.getInstance().getNextId());
                treeDTO.setTreeId(companyDTO.getCompanyId());
                treeDTO.setTreeCode(companyDTO.getCompanyCode());
                treeDTO.setTreeName(companyDTO.getCompanyName());
                treeDTO.setParentId(companyDTO.getCompanyParentId());
                treeDTO.setTreeTypeCode(BpmConstant.TREE_TYPE_CODE_COMPANY);
                treeDTO.setTreeType(BpmConstant.TREE_TYPE_COMPANY);
                if(StringUtils.isNotEmpty(showTreeType)
                        && !BpmConstant.TREE_TYPE_CODE_COMPANY.equals(showTreeType)){
                    treeDTO.setDisabled(true);
                }
                list.add(treeDTO);
            }
        }
        list = TreeUtils.makeTree(list,parentId);

        /* 移除非 */
        Iterator<TreeDTO> iterator = list.iterator();
        while (iterator.hasNext()){
            if(!iterator.next().getParentId().equals(parentId)){
                iterator.remove();
            }
        }
        return Result.responseSuccess(list);
    }

    @Override
    public Result<List<ItemDTO>> getCompanyIdAndNameDictList(CompanyQueryDTO companyQueryDTO) {

        EasyBpmAsset.isAssetEmptyByDefault(companyQueryDTO);
        Result<List<CompanyDTO>> result = getListByCondition(companyQueryDTO);

        List<ItemDTO> list = new ArrayList<>(result.getData().size());
        ItemDTO itemDTO = new ItemDTO();
        for (CompanyDTO companyDTO : result.getData()) {
            if (companyDTO != null) {
                itemDTO = new ItemDTO(companyDTO.getCompanyName(), companyDTO.getCompanyId(),false);
                list.add(itemDTO);
            }
        }
        itemDTO = null;
        return Result.responseSuccess(list);
    }


    @Override
    public Result<Integer> insertCompany(CompanySaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        CompanyDO temp = BeanUtils.switchToDO(param, CompanyDO.class);
        Integer num = mapper.insert(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer>updateCompany(CompanySaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        CompanyDO temp = BeanUtils.switchToDO(param, CompanyDO.class);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer>deleteCompany(CompanySaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        CompanyDO temp = BeanUtils.switchToDO(param, CompanyDO.class);
        temp.setValidState(INVALID_STATE);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<CompanyDTO> getCompanyById(Long companyId){

        if (companyId == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        CompanyDO query = new CompanyDO();
        query.setCompanyId(companyId);
        query = mapper.selectOne(new QueryWrapper<>(query));

        if (query == null) {
            return Result.responseError(EntityError.DATA_NOT_FOUND_ERROR);
        }
        CompanyDTO result = BeanUtils.switchToDTO(query, CompanyDTO.class);
        return Result.responseSuccess(result);
    }
}
