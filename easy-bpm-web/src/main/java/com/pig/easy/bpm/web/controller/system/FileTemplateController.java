package com.pig.easy.bpm.web.controller.system;


import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.auth.core.controller.BaseController;
import com.pig.easy.bpm.auth.dto.request.FileTemplateQueryDTO;
import com.pig.easy.bpm.auth.dto.request.FileTemplateSaveOrUpdateDTO;
import com.pig.easy.bpm.auth.dto.response.FileTemplateDTO;
import com.pig.easy.bpm.auth.dto.response.FileTemplateExportDTO;
import com.pig.easy.bpm.auth.service.FileTemplateService;
import com.pig.easy.bpm.common.converter.LocalDateTimeConverter;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.utils.BeanUtils;
import com.pig.easy.bpm.common.utils.EasyBpmAsset;
import com.pig.easy.bpm.common.utils.JsonResult;
import com.pig.easy.bpm.common.utils.Result;
import com.pig.easy.bpm.web.vo.request.FileTemplateQueryVO;
import com.pig.easy.bpm.web.vo.request.FileTemplateSaveOrUpdateVO;
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
 * 模板文件表 前端控制器
 * </p>
 *
 * @author pig
 * @since 2021-03-20
 */
@RestController
@RequestMapping("/fileTemplate")
public class FileTemplateController extends BaseController {

    @Autowired
    FileTemplateService service;

    @ApiOperation(value = "查询模板文件表列表", notes = "查询模板文件表列表", produces = "application/json")
    @PostMapping("/getListPage")
    public JsonResult getListPage(@Valid @RequestBody FileTemplateQueryVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        FileTemplateQueryDTO queryDTO = BeanUtils.switchToDTO(param, FileTemplateQueryDTO.class);

        Result<PageInfo<FileTemplateDTO>> result = service.getListPageByCondition(queryDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "查询模板文件表列表", notes = "查询模板文件表列表", produces = "application/json")
    @PostMapping("/getList")
    public JsonResult getList(@Valid @RequestBody FileTemplateQueryVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        FileTemplateQueryDTO queryDTO = BeanUtils.switchToDTO(param, FileTemplateQueryDTO.class);

        Result<List<FileTemplateDTO>> result = service.getListByCondition(queryDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "新增模板文件表", notes = "新增模板文件表", produces = "application/json")
    @PostMapping("/insert")
    public JsonResult insertFileTemplate(@Valid @RequestBody FileTemplateSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        FileTemplateSaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, FileTemplateSaveOrUpdateDTO.class);

        Result<Integer> result = service.insertFileTemplate(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "修改模板文件表", notes = "修改模板文件表", produces = "application/json")
    @PostMapping("/update")
    public JsonResult updateFileTemplate(@Valid @RequestBody FileTemplateSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        FileTemplateSaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, FileTemplateSaveOrUpdateDTO.class);

        Result<Integer> result = service.updateFileTemplate(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
          return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "删除模板文件表", notes = "删除模板文件表", produces = "application/json")
    @PostMapping("/deleteById")
    public JsonResult deleteById(@Valid @RequestBody FileTemplateSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        FileTemplateSaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, FileTemplateSaveOrUpdateDTO.class);
        saveDTO.setValidState(0);

        Result<Integer> result = service.deleteFileTemplate(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "下载模板文件表", notes = "下载模板文件表", produces = "application/json")
    @PostMapping("download")
    public void download(HttpServletResponse response,@Valid @RequestBody FileTemplateQueryVO param) throws IOException {

        EasyBpmAsset.isAssetEmpty(param);
        FileTemplateQueryDTO queryDTO = BeanUtils.switchToDTO(param, FileTemplateQueryDTO.class);

        Result<List<FileTemplateDTO>> result = service.getListByCondition(queryDTO);
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = String.valueOf(System.currentTimeMillis()) + "模板文件表.xlsx";
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        EasyExcel.write(response.getOutputStream(), FileTemplateExportDTO.class).registerConverter(new LocalDateTimeConverter()).sheet().doWrite(result.getData());
    }

    @ApiOperation(value = "根据编号获取模板文件表", notes = "根据编号获取模板文件表", produces = "application/json")
    @PostMapping("/getById")
    public JsonResult getById(@Valid @RequestBody FileTemplateSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        EasyBpmAsset.isAssetEmpty(param.getTempalteId());

        Result<FileTemplateDTO> result = service.getFileTemplateById(param.getTempalteId());
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }
}
