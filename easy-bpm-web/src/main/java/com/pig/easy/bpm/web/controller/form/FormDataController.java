package com.pig.easy.bpm.web.controller.form;


import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.api.dto.request.FormDataQueryDTO;
import com.pig.easy.bpm.api.dto.request.FormDataSaveOrUpdateDTO;
import com.pig.easy.bpm.api.dto.response.FormDataDTO;
import com.pig.easy.bpm.api.dto.response.FormDataExportDTO;
import com.pig.easy.bpm.api.service.FormDataService;
import com.pig.easy.bpm.web.vo.request.FormDataQueryVO;
import com.pig.easy.bpm.web.vo.request.FormDataSaveOrUpdateVO;
import com.pig.easy.bpm.common.converter.LocalDateTimeConverter;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.utils.BeanUtils;
import com.pig.easy.bpm.common.utils.EasyBpmAsset;
import com.pig.easy.bpm.common.utils.JsonResult;
import com.pig.easy.bpm.common.utils.Result;
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
 *  前端控制器
 * </p>
 *
 * @author pig
 * @since 2021-04-09
 */
@RestController
@RequestMapping("/formData")
public class FormDataController {

    @Autowired
    FormDataService service;

    @ApiOperation(value = "查询列表", notes = "查询列表", produces = "application/json")
    @PostMapping("/getListPage")
    public JsonResult getListPage(@Valid @RequestBody FormDataQueryVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        FormDataQueryDTO queryDTO = BeanUtils.switchToDTO(param, FormDataQueryDTO.class);

        Result<PageInfo<FormDataDTO>> result = service.getListPageByCondition(queryDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "查询列表", notes = "查询列表", produces = "application/json")
    @PostMapping("/getList")
    public JsonResult getList(@Valid @RequestBody FormDataQueryVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        FormDataQueryDTO queryDTO = BeanUtils.switchToDTO(param, FormDataQueryDTO.class);

        Result<List<FormDataDTO>> result = service.getListByCondition(queryDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "新增", notes = "新增", produces = "application/json")
    @PostMapping("/insert")
    public JsonResult insertFormData(@Valid @RequestBody FormDataSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        FormDataSaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, FormDataSaveOrUpdateDTO.class);

        Result<Integer> result = service.insertFormData(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "修改", notes = "修改", produces = "application/json")
    @PostMapping("/update")
    public JsonResult updateFormData(@Valid @RequestBody FormDataSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        FormDataSaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, FormDataSaveOrUpdateDTO.class);

        Result<Integer> result = service.updateFormData(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
          return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "删除", notes = "删除", produces = "application/json")
    @PostMapping("/deleteById")
    public JsonResult deleteById(@Valid @RequestBody FormDataSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        FormDataSaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, FormDataSaveOrUpdateDTO.class);
        saveDTO.setValidState(0);

        Result<Integer> result = service.deleteFormData(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "下载", notes = "下载", produces = "application/json")
    @PostMapping("download")
    public void download(HttpServletResponse response,@Valid @RequestBody FormDataQueryVO param) throws IOException {

        EasyBpmAsset.isAssetEmpty(param);
        FormDataQueryDTO queryDTO = BeanUtils.switchToDTO(param, FormDataQueryDTO.class);

        Result<List<FormDataDTO>> result = service.getListByCondition(queryDTO);
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = String.valueOf(System.currentTimeMillis()) + ".xlsx";
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        EasyExcel.write(response.getOutputStream(), FormDataExportDTO.class).registerConverter(new LocalDateTimeConverter()).sheet().doWrite(result.getData());
    }

    @ApiOperation(value = "根据编号获取", notes = "根据编号获取", produces = "application/json")
    @PostMapping("/getById")
    public JsonResult getById(@Valid @RequestBody FormDataSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        EasyBpmAsset.isAssetEmpty(param.getDataId());

        Result<FormDataDTO> result = service.getFormDataById(param.getDataId());
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }
}
