package com.pig.easy.bpm.auth.service;

import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.auth.dto.request.CompanyQueryDTO;
import com.pig.easy.bpm.auth.dto.request.CompanySaveOrUpdateDTO;
import com.pig.easy.bpm.auth.dto.response.CompanyDTO;
import com.pig.easy.bpm.auth.dto.response.ItemDTO;
import com.pig.easy.bpm.auth.dto.response.TreeDTO;
import com.pig.easy.bpm.auth.entity.CompanyDO;
import com.pig.easy.bpm.common.generator.BaseService;
import com.pig.easy.bpm.common.utils.Result;

import java.util.List;
/**
 * <p>
 * 公司表 服务类
 * </p>
 *
 * @author pig
 * @since 2021-03-20
 */
public interface CompanyService extends BaseService<CompanyDO> {

    Result<PageInfo<CompanyDTO>> getListPageByCondition(CompanyQueryDTO param);

    Result<Integer> insertCompany(CompanySaveOrUpdateDTO param);

    Result<Integer> updateCompany(CompanySaveOrUpdateDTO param);

    Result<Integer> deleteCompany(CompanySaveOrUpdateDTO param);

    Result<CompanyDTO> getCompanyById(Long companyId);

    Result<List<CompanyDTO>> getListByCondition(CompanyQueryDTO param);

    Result<List<TreeDTO>> getCompanyTree(Long parentId, String tenantId,String showTreeType);

    Result<List<ItemDTO>> getCompanyIdAndNameDictList(CompanyQueryDTO companyQueryDTO);

}
