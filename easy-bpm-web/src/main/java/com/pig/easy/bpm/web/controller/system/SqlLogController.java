package com.pig.easy.bpm.web.controller.system;


import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.auth.core.controller.BaseController;
import com.pig.easy.bpm.auth.dto.request.SqlLogQueryDTO;
import com.pig.easy.bpm.auth.dto.request.SqlLogSaveOrUpdateDTO;
import com.pig.easy.bpm.auth.dto.response.SqlLogDTO;
import com.pig.easy.bpm.auth.dto.response.SqlLogExportDTO;
import com.pig.easy.bpm.auth.service.SqlLogService;
import com.pig.easy.bpm.web.vo.request.SqlLogQueryVO;
import com.pig.easy.bpm.web.vo.request.SqlLogSaveOrUpdateVO;
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
 * SQL日志表 前端控制器
 * </p>
 *
 * @author pig
 * @since 2021-03-22
 */
@RestController
@RequestMapping("/sqlLog")
public class SqlLogController extends BaseController {

    @Autowired
    SqlLogService service;

    @ApiOperation(value = "查询SQL日志表列表", notes = "查询SQL日志表列表", produces = "application/json")
    @PostMapping("/getListPage")
    public JsonResult getListPage(@Valid @RequestBody SqlLogQueryVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        SqlLogQueryDTO queryDTO = BeanUtils.switchToDTO(param, SqlLogQueryDTO.class);

        Result<PageInfo<SqlLogDTO>> result = service.getListPageByCondition(queryDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "查询SQL日志表列表", notes = "查询SQL日志表列表", produces = "application/json")
    @PostMapping("/getList")
    public JsonResult getList(@Valid @RequestBody SqlLogQueryVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        SqlLogQueryDTO queryDTO = BeanUtils.switchToDTO(param, SqlLogQueryDTO.class);

        Result<List<SqlLogDTO>> result = service.getListByCondition(queryDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "新增SQL日志表", notes = "新增SQL日志表", produces = "application/json")
    @PostMapping("/insert")
    public JsonResult insertSqlLog(@Valid @RequestBody SqlLogSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        SqlLogSaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, SqlLogSaveOrUpdateDTO.class);

        Result<Integer> result = service.insertSqlLog(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "修改SQL日志表", notes = "修改SQL日志表", produces = "application/json")
    @PostMapping("/update")
    public JsonResult updateSqlLog(@Valid @RequestBody SqlLogSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        SqlLogSaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, SqlLogSaveOrUpdateDTO.class);

        Result<Integer> result = service.updateSqlLog(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
          return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "删除SQL日志表", notes = "删除SQL日志表", produces = "application/json")
    @PostMapping("/deleteById")
    public JsonResult deleteById(@Valid @RequestBody SqlLogSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        SqlLogSaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, SqlLogSaveOrUpdateDTO.class);
        saveDTO.setValidState(0);

        Result<Integer> result = service.deleteSqlLog(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "下载SQL日志表", notes = "下载SQL日志表", produces = "application/json")
    @PostMapping("download")
    public void download(HttpServletResponse response,@Valid @RequestBody SqlLogQueryVO param) throws IOException {

        EasyBpmAsset.isAssetEmpty(param);
        SqlLogQueryDTO queryDTO = BeanUtils.switchToDTO(param, SqlLogQueryDTO.class);

        Result<List<SqlLogDTO>> result = service.getListByCondition(queryDTO);
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = String.valueOf(System.currentTimeMillis()) + "SQL日志表.xlsx";
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        EasyExcel.write(response.getOutputStream(), SqlLogExportDTO.class).registerConverter(new LocalDateTimeConverter()).sheet().doWrite(result.getData());
    }

    @ApiOperation(value = "根据编号获取SQL日志表", notes = "根据编号获取SQL日志表", produces = "application/json")
    @PostMapping("/getById")
    public JsonResult getById(@Valid @RequestBody SqlLogSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        EasyBpmAsset.isAssetEmpty(param.getLogId());

        Result<SqlLogDTO> result = service.getSqlLogById(param.getLogId());
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }
}
