package com.pig.easy.bpm.web.controller.process;


import com.pig.easy.bpm.web.vo.request.ProcessRuleQueryVO;
import com.pig.easy.bpm.web.vo.request.ProcessRuleSaveOrUpdateVO;
import org.springframework.web.bind.annotation.RequestMapping;
import com.pig.easy.bpm.api.dto.request.*;
import com.pig.easy.bpm.api.dto.response.*;
import org.springframework.web.bind.annotation.*;
import com.pig.easy.bpm.api.service.ProcessRuleService;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import javax.validation.Valid;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.utils.BeanUtils;
import com.pig.easy.bpm.common.utils.EasyBpmAsset;
import com.pig.easy.bpm.common.utils.JsonResult;
import com.pig.easy.bpm.common.utils.Result;
import com.pig.easy.bpm.common.converter.LocalDateTimeConverter;
import java.io.IOException;
import com.alibaba.excel.EasyExcel;

import io.swagger.annotations.ApiOperation;

import java.net.URLEncoder;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pig
 * @since 2021-04-09
 */
@RestController
@RequestMapping("/processRule")
public class ProcessRuleController {

    @Autowired
    ProcessRuleService service;

    @ApiOperation(value = "查询列表", notes = "查询列表", produces = "application/json")
    @PostMapping("/getListPage")
    public JsonResult getListPage(@Valid @RequestBody ProcessRuleQueryVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        ProcessRuleQueryDTO queryDTO = BeanUtils.switchToDTO(param, ProcessRuleQueryDTO.class);

        Result<PageInfo<ProcessRuleDTO>> result = service.getListPageByCondition(queryDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "查询列表", notes = "查询列表", produces = "application/json")
    @PostMapping("/getList")
    public JsonResult getList(@Valid @RequestBody ProcessRuleQueryVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        ProcessRuleQueryDTO queryDTO = BeanUtils.switchToDTO(param, ProcessRuleQueryDTO.class);

        Result<List<ProcessRuleDTO>> result = service.getListByCondition(queryDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "新增", notes = "新增", produces = "application/json")
    @PostMapping("/insert")
    public JsonResult insertProcessRule(@Valid @RequestBody ProcessRuleSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        ProcessRuleSaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, ProcessRuleSaveOrUpdateDTO.class);

        Result<Integer> result = service.insertProcessRule(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "修改", notes = "修改", produces = "application/json")
    @PostMapping("/update")
    public JsonResult updateProcessRule(@Valid @RequestBody ProcessRuleSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        ProcessRuleSaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, ProcessRuleSaveOrUpdateDTO.class);

        Result<Integer> result = service.updateProcessRule(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
          return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "删除", notes = "删除", produces = "application/json")
    @PostMapping("/deleteById")
    public JsonResult deleteById(@Valid @RequestBody ProcessRuleSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        ProcessRuleSaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, ProcessRuleSaveOrUpdateDTO.class);
        saveDTO.setValidState(0);

        Result<Integer> result = service.deleteProcessRule(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "下载", notes = "下载", produces = "application/json")
    @PostMapping("download")
    public void download(HttpServletResponse response,@Valid @RequestBody ProcessRuleQueryVO param) throws IOException {

        EasyBpmAsset.isAssetEmpty(param);
        ProcessRuleQueryDTO queryDTO = BeanUtils.switchToDTO(param, ProcessRuleQueryDTO.class);

        Result<List<ProcessRuleDTO>> result = service.getListByCondition(queryDTO);
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = String.valueOf(System.currentTimeMillis()) + ".xlsx";
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        EasyExcel.write(response.getOutputStream(), ProcessRuleExportDTO.class).registerConverter(new LocalDateTimeConverter()).sheet().doWrite(result.getData());
    }

    @ApiOperation(value = "根据编号获取", notes = "根据编号获取", produces = "application/json")
    @PostMapping("/getById")
    public JsonResult getById(@Valid @RequestBody ProcessRuleSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        EasyBpmAsset.isAssetEmpty(param.getRuleId());

        Result<ProcessRuleDTO> result = service.getProcessRuleById(param.getRuleId());
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }
}
