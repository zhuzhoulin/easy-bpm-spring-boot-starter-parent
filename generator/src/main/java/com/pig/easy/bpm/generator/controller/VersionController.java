package com.pig.easy.bpm.generator.controller;


import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.common.converter.LocalDateTimeConverter;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.utils.BeanUtils;
import com.pig.easy.bpm.common.utils.EasyBpmAsset;
import com.pig.easy.bpm.common.utils.JsonResult;
import com.pig.easy.bpm.common.utils.Result;
import com.pig.easy.bpm.generator.dto.request.VersionQueryDTO;
import com.pig.easy.bpm.generator.dto.request.VersionSaveOrUpdateDTO;
import com.pig.easy.bpm.generator.dto.response.VersionDTO;
import com.pig.easy.bpm.generator.dto.response.VersionExportDTO;
import com.pig.easy.bpm.generator.service.VersionService;
import com.pig.easy.bpm.generator.vo.request.VersionQueryVO;
import com.pig.easy.bpm.generator.vo.request.VersionSaveOrUpdateVO;
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
 * 版本表 前端控制器
 * </p>
 *
 * @author pig
 * @since 2021-03-10
 */
@RestController
@RequestMapping("/version")
public class VersionController {

    @Autowired
    VersionService service;

    @ApiOperation(value = "查询版本表列表", notes = "查询版本表列表", produces = "application/json")
    @PostMapping("/getListPage")
    public JsonResult getListPage(@Valid @RequestBody VersionQueryVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        VersionQueryDTO queryDTO = BeanUtils.switchToDTO(param, VersionQueryDTO.class);

        Result<PageInfo<VersionDTO>> result = service.getListPageByCondition(queryDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "查询版本表列表", notes = "查询版本表列表", produces = "application/json")
    @PostMapping("/getList")
    public JsonResult getList(@Valid @RequestBody VersionQueryVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        VersionQueryDTO queryDTO = BeanUtils.switchToDTO(param, VersionQueryDTO.class);

        Result<List<VersionDTO>> result = service.getListByCondition(queryDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "新增版本表", notes = "新增版本表", produces = "application/json")
    @PostMapping("/insert")
    public JsonResult insertVersion(@Valid @RequestBody VersionSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        VersionSaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, VersionSaveOrUpdateDTO.class);

        Result<Integer> result = service.insertVersion(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "修改版本表", notes = "修改版本表", produces = "application/json")
    @PostMapping("/update")
    public JsonResult updateVersion(@Valid @RequestBody VersionSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        VersionSaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, VersionSaveOrUpdateDTO.class);

        Result<Integer> result = service.updateVersion(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
          return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "删除版本表", notes = "删除版本表", produces = "application/json")
    @PostMapping("/deleteById")
    public JsonResult deleteById(@Valid @RequestBody VersionSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        VersionSaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, VersionSaveOrUpdateDTO.class);
        saveDTO.setValidState(0);

        Result<Integer> result = service.deleteVersion(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "下载版本表", notes = "下载版本表", produces = "application/json")
    @PostMapping("download")
    public void download(HttpServletResponse response,@Valid @RequestBody VersionQueryVO param) throws IOException {

        EasyBpmAsset.isAssetEmpty(param);
        VersionQueryDTO queryDTO = BeanUtils.switchToDTO(param, VersionQueryDTO.class);

        Result<List<VersionDTO>> result = service.getListByCondition(queryDTO);
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = String.valueOf(System.currentTimeMillis()) + "版本表.xlsx";
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        EasyExcel.write(response.getOutputStream(), VersionExportDTO.class).registerConverter(new LocalDateTimeConverter()).sheet().doWrite(result.getData());
    }

    @ApiOperation(value = "根据编号获取版本表", notes = "根据编号获取版本表", produces = "application/json")
    @PostMapping("/getById")
    public JsonResult getById(@Valid @RequestBody VersionSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        EasyBpmAsset.isAssetEmpty(param.getId());

        Result<VersionDTO> result = service.getVersionById(param.getId());
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }
}
