package com.pig.easy.bpm.web.controller.system;


import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.auth.core.controller.BaseController;
import com.pig.easy.bpm.auth.dto.request.ConfigQueryDTO;
import com.pig.easy.bpm.auth.dto.request.ConfigSaveOrUpdateDTO;
import com.pig.easy.bpm.auth.dto.response.ConfigDTO;
import com.pig.easy.bpm.auth.dto.response.ConfigExportDTO;
import com.pig.easy.bpm.auth.service.ConfigService;
import com.pig.easy.bpm.common.converter.LocalDateTimeConverter;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.utils.BeanUtils;
import com.pig.easy.bpm.common.utils.EasyBpmAsset;
import com.pig.easy.bpm.common.utils.JsonResult;
import com.pig.easy.bpm.common.utils.Result;
import com.pig.easy.bpm.web.vo.request.ConfigQueryVO;
import com.pig.easy.bpm.web.vo.request.ConfigSaveOrUpdateVO;
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
 * @since 2021-03-20
 */
@RestController
@RequestMapping("/config")
public class ConfigController extends BaseController {

    @Autowired
    ConfigService service;

    @ApiOperation(value = "查询列表", notes = "查询列表", produces = "application/json")
    @PostMapping("/getListPage")
    public JsonResult getListPage(@Valid @RequestBody ConfigQueryVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        ConfigQueryDTO queryDTO = BeanUtils.switchToDTO(param, ConfigQueryDTO.class);

        Result<PageInfo<ConfigDTO>> result = service.getListPageByCondition(queryDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "查询列表", notes = "查询列表", produces = "application/json")
    @PostMapping("/getList")
    public JsonResult getList(@Valid @RequestBody ConfigQueryVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        ConfigQueryDTO queryDTO = BeanUtils.switchToDTO(param, ConfigQueryDTO.class);

        Result<List<ConfigDTO>> result = service.getListByCondition(queryDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "新增", notes = "新增", produces = "application/json")
    @PostMapping("/insert")
    public JsonResult insertConfig(@Valid @RequestBody ConfigSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        ConfigSaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, ConfigSaveOrUpdateDTO.class);

        Result<Integer> result = service.insertConfig(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "修改", notes = "修改", produces = "application/json")
    @PostMapping("/update")
    public JsonResult updateConfig(@Valid @RequestBody ConfigSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        ConfigSaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, ConfigSaveOrUpdateDTO.class);

        Result<Integer> result = service.updateConfig(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
          return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "删除", notes = "删除", produces = "application/json")
    @PostMapping("/deleteById")
    public JsonResult deleteById(@Valid @RequestBody ConfigSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        ConfigSaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, ConfigSaveOrUpdateDTO.class);
        saveDTO.setValidState(0);

        Result<Integer> result = service.deleteConfig(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "下载", notes = "下载", produces = "application/json")
    @PostMapping("download")
    public void download(HttpServletResponse response,@Valid @RequestBody ConfigQueryVO param) throws IOException {

        EasyBpmAsset.isAssetEmpty(param);
        ConfigQueryDTO queryDTO = BeanUtils.switchToDTO(param, ConfigQueryDTO.class);

        Result<List<ConfigDTO>> result = service.getListByCondition(queryDTO);
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = String.valueOf(System.currentTimeMillis()) + ".xlsx";
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        EasyExcel.write(response.getOutputStream(), ConfigExportDTO.class).registerConverter(new LocalDateTimeConverter()).sheet().doWrite(result.getData());
    }

    @ApiOperation(value = "根据编号获取", notes = "根据编号获取", produces = "application/json")
    @PostMapping("/getById")
    public JsonResult getById(@Valid @RequestBody ConfigSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        EasyBpmAsset.isAssetEmpty(param.getConfigId());

        Result<ConfigDTO> result = service.getConfigById(param.getConfigId());
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }
}
