package com.pig.easy.bpm.web.controller.system;


import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.auth.core.controller.BaseController;
import com.pig.easy.bpm.auth.dto.request.CompanyQueryDTO;
import com.pig.easy.bpm.auth.dto.request.CompanySaveOrUpdateDTO;
import com.pig.easy.bpm.auth.dto.response.CompanyDTO;
import com.pig.easy.bpm.auth.dto.response.CompanyExportDTO;
import com.pig.easy.bpm.auth.dto.response.ItemDTO;
import com.pig.easy.bpm.auth.dto.response.TreeDTO;
import com.pig.easy.bpm.auth.service.CompanyService;
import com.pig.easy.bpm.common.converter.LocalDateTimeConverter;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.utils.BeanUtils;
import com.pig.easy.bpm.common.utils.EasyBpmAsset;
import com.pig.easy.bpm.common.utils.JsonResult;
import com.pig.easy.bpm.common.utils.Result;
import com.pig.easy.bpm.web.vo.request.CompanyQueryVO;
import com.pig.easy.bpm.web.vo.request.CompanySaveOrUpdateVO;
import com.pig.easy.bpm.web.vo.request.TreeQueryVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;


/**
 * <p>
 * 公司表 前端控制器
 * </p>
 *
 * @author pig
 * @since 2021-03-20
 */
@RestController
@RequestMapping("/company")
public class CompanyController extends BaseController {

    @Autowired
    CompanyService service;

    @ApiOperation(value = "查询公司表列表", notes = "查询公司表列表", produces = "application/json")
    @PostMapping("/getListPage")
    public JsonResult getListPage(@Valid @RequestBody CompanyQueryVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        CompanyQueryDTO queryDTO = BeanUtils.switchToDTO(param, CompanyQueryDTO.class);

        Result<PageInfo<CompanyDTO>> result = service.getListPageByCondition(queryDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "查询公司表列表", notes = "查询公司表列表", produces = "application/json")
    @PostMapping("/getList")
    public JsonResult getList(@Valid @RequestBody CompanyQueryVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        CompanyQueryDTO queryDTO = BeanUtils.switchToDTO(param, CompanyQueryDTO.class);

        Result<List<CompanyDTO>> result = service.getListByCondition(queryDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "新增公司表", notes = "新增公司表", produces = "application/json")
    @PostMapping("/insert")
    public JsonResult insertCompany(@Valid @RequestBody CompanySaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        CompanySaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, CompanySaveOrUpdateDTO.class);

        Result<Integer> result = service.insertCompany(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "修改公司表", notes = "修改公司表", produces = "application/json")
    @PostMapping("/update")
    public JsonResult updateCompany(@Valid @RequestBody CompanySaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        CompanySaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, CompanySaveOrUpdateDTO.class);

        Result<Integer> result = service.updateCompany(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
          return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "删除公司表", notes = "删除公司表", produces = "application/json")
    @PostMapping("/deleteById")
    public JsonResult deleteById(@Valid @RequestBody CompanySaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        CompanySaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, CompanySaveOrUpdateDTO.class);
        saveDTO.setValidState(0);

        Result<Integer> result = service.deleteCompany(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "下载公司表", notes = "下载公司表", produces = "application/json")
    @PostMapping("download")
    public void download(HttpServletResponse response,@Valid @RequestBody CompanyQueryVO param) throws IOException {

        EasyBpmAsset.isAssetEmpty(param);
        CompanyQueryDTO queryDTO = BeanUtils.switchToDTO(param, CompanyQueryDTO.class);

        Result<List<CompanyDTO>> result = service.getListByCondition(queryDTO);
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = String.valueOf(System.currentTimeMillis()) + "公司表.xlsx";
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        EasyExcel.write(response.getOutputStream(), CompanyExportDTO.class).registerConverter(new LocalDateTimeConverter()).sheet().doWrite(result.getData());
    }

    @ApiOperation(value = "根据编号获取公司表", notes = "根据编号获取公司表", produces = "application/json")
    @PostMapping("/getById")
    public JsonResult getById(@Valid @RequestBody CompanySaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        EasyBpmAsset.isAssetEmpty(param.getCompanyId());

        Result<CompanyDTO> result = service.getCompanyById(param.getCompanyId());
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "获取公司树", notes = "获取公司树", produces = "application/json")
    @PostMapping("/getCompanyTree")
    public JsonResult getCompanyTree(@Valid @RequestBody TreeQueryVO treeQueryVO) {

        Result<List<TreeDTO>> result = service.getCompanyTree(treeQueryVO.getParentId(), treeQueryVO.getTenantId(),null);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "获取公司ID name 字典", notes = "获取公司字典", produces = "application/json")
    @PostMapping("/getCompanyIdAndNameDictList")
    public JsonResult getCompanyIdAndNameDictList(@Valid @RequestBody CompanyQueryVO companyVO) {

        if(companyVO == null){
            companyVO = new CompanyQueryVO();
        }
        CompanyQueryDTO companyQueryDTO = BeanUtils.switchToDTO(companyVO,CompanyQueryDTO.class);
        Result<List<ItemDTO>> result = service.getCompanyIdAndNameDictList(companyQueryDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }
}
