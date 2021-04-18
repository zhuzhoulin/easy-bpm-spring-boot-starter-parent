package com.pig.easy.bpm.web.controller.process;


import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.api.dto.request.VariableDictQueryDTO;
import com.pig.easy.bpm.api.dto.request.VariableDictSaveOrUpdateDTO;
import com.pig.easy.bpm.api.dto.response.VariableDictDTO;
import com.pig.easy.bpm.api.dto.response.VariableDictExportDTO;
import com.pig.easy.bpm.api.service.VariableDictService;
import com.pig.easy.bpm.web.vo.request.VariableDictQueryVO;
import com.pig.easy.bpm.web.vo.request.VariableDictSaveOrUpdateVO;
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
 * 变量表 前端控制器
 * </p>
 *
 * @author pig
 * @since 2021-04-11
 */
@RestController
@RequestMapping("/variableDict")
public class VariableDictController {

    @Autowired
    VariableDictService service;

    @ApiOperation(value = "查询变量表列表", notes = "查询变量表列表", produces = "application/json")
    @PostMapping("/getListPage")
    public JsonResult getListPage(@Valid @RequestBody VariableDictQueryVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        VariableDictQueryDTO queryDTO = BeanUtils.switchToDTO(param, VariableDictQueryDTO.class);

        Result<PageInfo<VariableDictDTO>> result = service.getListPageByCondition(queryDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "查询变量表列表", notes = "查询变量表列表", produces = "application/json")
    @PostMapping("/getList")
    public JsonResult getList(@Valid @RequestBody VariableDictQueryVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        VariableDictQueryDTO queryDTO = BeanUtils.switchToDTO(param, VariableDictQueryDTO.class);

        Result<List<VariableDictDTO>> result = service.getListByCondition(queryDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "新增变量表", notes = "新增变量表", produces = "application/json")
    @PostMapping("/insert")
    public JsonResult insertVariableDict(@Valid @RequestBody VariableDictSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        VariableDictSaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, VariableDictSaveOrUpdateDTO.class);

        Result<Integer> result = service.insertVariableDict(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "修改变量表", notes = "修改变量表", produces = "application/json")
    @PostMapping("/update")
    public JsonResult updateVariableDict(@Valid @RequestBody VariableDictSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        VariableDictSaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, VariableDictSaveOrUpdateDTO.class);

        Result<Integer> result = service.updateVariableDict(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
          return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "删除变量表", notes = "删除变量表", produces = "application/json")
    @PostMapping("/deleteById")
    public JsonResult deleteById(@Valid @RequestBody VariableDictSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        VariableDictSaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, VariableDictSaveOrUpdateDTO.class);
        saveDTO.setValidState(0);

        Result<Integer> result = service.deleteVariableDict(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "下载变量表", notes = "下载变量表", produces = "application/json")
    @PostMapping("download")
    public void download(HttpServletResponse response,@Valid @RequestBody VariableDictQueryVO param) throws IOException {

        EasyBpmAsset.isAssetEmpty(param);
        VariableDictQueryDTO queryDTO = BeanUtils.switchToDTO(param, VariableDictQueryDTO.class);

        Result<List<VariableDictDTO>> result = service.getListByCondition(queryDTO);
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = String.valueOf(System.currentTimeMillis()) + "变量表.xlsx";
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        EasyExcel.write(response.getOutputStream(), VariableDictExportDTO.class).registerConverter(new LocalDateTimeConverter()).sheet().doWrite(result.getData());
    }

    @ApiOperation(value = "根据编号获取变量表", notes = "根据编号获取变量表", produces = "application/json")
    @PostMapping("/getById")
    public JsonResult getById(@Valid @RequestBody VariableDictSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        EasyBpmAsset.isAssetEmpty(param.getVariableId());

        Result<VariableDictDTO> result = service.getVariableDictById(param.getVariableId());
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }
}
