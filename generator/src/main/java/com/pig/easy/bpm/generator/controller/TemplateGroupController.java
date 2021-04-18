package com.pig.easy.bpm.generator.controller;


import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.common.converter.LocalDateTimeConverter;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.utils.BeanUtils;
import com.pig.easy.bpm.common.utils.EasyBpmAsset;
import com.pig.easy.bpm.common.utils.JsonResult;
import com.pig.easy.bpm.common.utils.Result;
import com.pig.easy.bpm.generator.dto.request.TemplateGroupQueryDTO;
import com.pig.easy.bpm.generator.dto.request.TemplateGroupSaveOrUpdateDTO;
import com.pig.easy.bpm.generator.dto.response.TemplateGroupDTO;
import com.pig.easy.bpm.generator.dto.response.TemplateGroupExportDTO;
import com.pig.easy.bpm.generator.service.TemplateGroupService;
import com.pig.easy.bpm.generator.vo.request.TemplateGroupQueryVO;
import com.pig.easy.bpm.generator.vo.request.TemplateGroupSaveOrUpdateVO;
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
 * 模板分组表 前端控制器
 * </p>
 *
 * @author pig
 * @since 2021-03-08
 */
@RestController
@RequestMapping("/templateGroup")
public class TemplateGroupController {

    @Autowired
    TemplateGroupService service;

    @ApiOperation(value = "查询模板分组表列表", notes = "查询模板分组表列表", produces = "application/json")
    @PostMapping("/getListPage")
    public JsonResult getListPage(@Valid @RequestBody TemplateGroupQueryVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        TemplateGroupQueryDTO queryDTO = BeanUtils.switchToDTO(param, TemplateGroupQueryDTO.class);

        Result<PageInfo<TemplateGroupDTO>> result = service.getListPageByCondition(queryDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "查询模板分组表列表", notes = "查询模板分组表列表", produces = "application/json")
    @PostMapping("/getList")
    public JsonResult getList(@Valid @RequestBody TemplateGroupQueryVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        TemplateGroupQueryDTO queryDTO = BeanUtils.switchToDTO(param, TemplateGroupQueryDTO.class);

        Result<List<TemplateGroupDTO>> result = service.getListByCondition(queryDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "新增模板分组表", notes = "新增模板分组表", produces = "application/json")
    @PostMapping("/insert")
    public JsonResult insertTemplateGroup(@Valid @RequestBody TemplateGroupSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        TemplateGroupSaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, TemplateGroupSaveOrUpdateDTO.class);

        Result<Integer> result = service.insertTemplateGroup(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "修改模板分组表", notes = "修改模板分组表", produces = "application/json")
    @PostMapping("/update")
    public JsonResult updateTemplateGroup(@Valid @RequestBody TemplateGroupSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        TemplateGroupSaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, TemplateGroupSaveOrUpdateDTO.class);

        Result<Integer> result = service.updateTemplateGroup(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
          return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "删除模板分组表", notes = "删除模板分组表", produces = "application/json")
    @PostMapping("/deleteById")
    public JsonResult deleteById(@Valid @RequestBody TemplateGroupSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        TemplateGroupSaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, TemplateGroupSaveOrUpdateDTO.class);
        saveDTO.setValidState(0);

        Result<Integer> result = service.deleteTemplateGroup(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "下载模板分组表", notes = "下载模板分组表", produces = "application/json")
    @PostMapping("download")
    public void download(HttpServletResponse response,@Valid @RequestBody TemplateGroupQueryVO param) throws IOException {

        EasyBpmAsset.isAssetEmpty(param);
        TemplateGroupQueryDTO queryDTO = BeanUtils.switchToDTO(param, TemplateGroupQueryDTO.class);

        Result<List<TemplateGroupDTO>> result = service.getListByCondition(queryDTO);
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = String.valueOf(System.currentTimeMillis()) + "模板分组表.xlsx";
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        EasyExcel.write(response.getOutputStream(), TemplateGroupExportDTO.class).registerConverter(new LocalDateTimeConverter()).sheet().doWrite(result.getData());
    }

    @ApiOperation(value = "根据编号获取模板分组表", notes = "根据编号获取模板分组表", produces = "application/json")
    @PostMapping("/getById")
    public JsonResult getById(@Valid @RequestBody TemplateGroupSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        EasyBpmAsset.isAssetEmpty(param.getTemplateGroupId());

        Result<TemplateGroupDTO> result = service.getTemplateGroupById(param.getTemplateGroupId());
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }
}
