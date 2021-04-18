package com.pig.easy.bpm.generator.controller;


import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.common.converter.LocalDateTimeConverter;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.utils.BeanUtils;
import com.pig.easy.bpm.common.utils.EasyBpmAsset;
import com.pig.easy.bpm.common.utils.JsonResult;
import com.pig.easy.bpm.common.utils.Result;
import com.pig.easy.bpm.generator.dto.request.ColumnQueryDTO;
import com.pig.easy.bpm.generator.dto.request.ColumnSaveOrUpdateDTO;
import com.pig.easy.bpm.generator.dto.response.ColumnDTO;
import com.pig.easy.bpm.generator.dto.response.ColumnExportDTO;
import com.pig.easy.bpm.generator.service.ColumnService;
import com.pig.easy.bpm.generator.vo.request.ColumnQueryVO;
import com.pig.easy.bpm.generator.vo.request.ColumnSaveOrUpdateVO;
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
 * 字段表 前端控制器
 * </p>
 *
 * @author pig
 * @since 2021-03-11
 */
@RestController
@RequestMapping("/column")
public class ColumnController {

    @Autowired
    ColumnService service;

    @ApiOperation(value = "查询字段表列表", notes = "查询字段表列表", produces = "application/json")
    @PostMapping("/getListPage")
    public JsonResult getListPage(@Valid @RequestBody ColumnQueryVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        ColumnQueryDTO queryDTO = BeanUtils.switchToDTO(param, ColumnQueryDTO.class);

        Result<PageInfo<ColumnDTO>> result = service.getListPageByCondition(queryDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "查询字段表列表", notes = "查询字段表列表", produces = "application/json")
    @PostMapping("/getList")
    public JsonResult getList(@Valid @RequestBody ColumnQueryVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        ColumnQueryDTO queryDTO = BeanUtils.switchToDTO(param, ColumnQueryDTO.class);

        Result<List<ColumnDTO>> result = service.getListByCondition(queryDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "新增字段表", notes = "新增字段表", produces = "application/json")
    @PostMapping("/insert")
    public JsonResult insertColumn(@Valid @RequestBody ColumnSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        ColumnSaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, ColumnSaveOrUpdateDTO.class);

        Result<Integer> result = service.insertColumn(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "修改字段表", notes = "修改字段表", produces = "application/json")
    @PostMapping("/update")
    public JsonResult updateColumn(@Valid @RequestBody ColumnSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        ColumnSaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, ColumnSaveOrUpdateDTO.class);

        Result<Integer> result = service.updateColumn(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
          return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "删除字段表", notes = "删除字段表", produces = "application/json")
    @PostMapping("/deleteById")
    public JsonResult deleteById(@Valid @RequestBody ColumnSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        ColumnSaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, ColumnSaveOrUpdateDTO.class);
        saveDTO.setValidState(0);

        Result<Integer> result = service.deleteColumn(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "下载字段表", notes = "下载字段表", produces = "application/json")
    @PostMapping("download")
    public void download(HttpServletResponse response,@Valid @RequestBody ColumnQueryVO param) throws IOException {

        EasyBpmAsset.isAssetEmpty(param);
        ColumnQueryDTO queryDTO = BeanUtils.switchToDTO(param, ColumnQueryDTO.class);

        Result<List<ColumnDTO>> result = service.getListByCondition(queryDTO);
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = String.valueOf(System.currentTimeMillis()) + "字段表.xlsx";
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        EasyExcel.write(response.getOutputStream(), ColumnExportDTO.class).registerConverter(new LocalDateTimeConverter()).sheet().doWrite(result.getData());
    }

    @ApiOperation(value = "根据编号获取字段表", notes = "根据编号获取字段表", produces = "application/json")
    @PostMapping("/getById")
    public JsonResult getById(@Valid @RequestBody ColumnSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        EasyBpmAsset.isAssetEmpty(param.getColumnId());

        Result<ColumnDTO> result = service.getColumnById(param.getColumnId());
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }
}
