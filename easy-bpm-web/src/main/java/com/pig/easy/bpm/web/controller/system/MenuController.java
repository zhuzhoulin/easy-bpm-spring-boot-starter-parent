package com.pig.easy.bpm.web.controller.system;


import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.auth.dto.request.MenuQueryDTO;
import com.pig.easy.bpm.auth.dto.request.MenuSaveOrUpdateDTO;
import com.pig.easy.bpm.auth.dto.response.MenuDTO;
import com.pig.easy.bpm.auth.dto.response.MenuExportDTO;
import com.pig.easy.bpm.auth.dto.response.MenuTreeDTO;
import com.pig.easy.bpm.auth.service.MenuService;
import com.pig.easy.bpm.common.converter.LocalDateTimeConverter;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.utils.BeanUtils;
import com.pig.easy.bpm.common.utils.EasyBpmAsset;
import com.pig.easy.bpm.common.utils.JsonResult;
import com.pig.easy.bpm.common.utils.Result;
import com.pig.easy.bpm.web.vo.request.MenuQueryVO;
import com.pig.easy.bpm.web.vo.request.MenuSaveOrUpdateVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;


/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author pig
 * @since 2021-04-06
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    MenuService service;

    @ApiOperation(value = "查询菜单表列表", notes = "查询菜单表列表", produces = "application/json")
    @PostMapping("/getListPage")
    public JsonResult getListPage(@Valid @RequestBody MenuQueryVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        MenuQueryDTO queryDTO = BeanUtils.switchToDTO(param, MenuQueryDTO.class);

        Result<PageInfo<MenuDTO>> result = service.getListPageByCondition(queryDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "查询菜单表列表", notes = "查询菜单表列表", produces = "application/json")
    @PostMapping("/getList")
    public JsonResult getList(@Valid @RequestBody MenuQueryVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        MenuQueryDTO queryDTO = BeanUtils.switchToDTO(param, MenuQueryDTO.class);

        Result<List<MenuDTO>> result = service.getListByCondition(queryDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "新增菜单表", notes = "新增菜单表", produces = "application/json")
    @PostMapping("/insert")
    public JsonResult insertMenu(@Valid @RequestBody MenuSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        MenuSaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, MenuSaveOrUpdateDTO.class);

        Result<Integer> result = service.insertMenu(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "修改菜单表", notes = "修改菜单表", produces = "application/json")
    @PostMapping("/update")
    public JsonResult updateMenu(@Valid @RequestBody MenuSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        MenuSaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, MenuSaveOrUpdateDTO.class);

        Result<Integer> result = service.updateMenu(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
          return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "删除菜单表", notes = "删除菜单表", produces = "application/json")
    @PostMapping("/deleteById")
    public JsonResult deleteById(@Valid @RequestBody MenuSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        MenuSaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, MenuSaveOrUpdateDTO.class);
        saveDTO.setValidState(0);

        Result<Integer> result = service.deleteMenu(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "下载菜单表", notes = "下载菜单表", produces = "application/json")
    @PostMapping("download")
    public void download(HttpServletResponse response,@Valid @RequestBody MenuQueryVO param) throws IOException {

        EasyBpmAsset.isAssetEmpty(param);
        MenuQueryDTO queryDTO = BeanUtils.switchToDTO(param, MenuQueryDTO.class);

        Result<List<MenuDTO>> result = service.getListByCondition(queryDTO);
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = String.valueOf(System.currentTimeMillis()) + "菜单表.xlsx";
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        EasyExcel.write(response.getOutputStream(), MenuExportDTO.class).registerConverter(new LocalDateTimeConverter()).sheet().doWrite(result.getData());
    }

    @ApiOperation(value = "查询树形菜单", notes = "查询树形菜单", produces = "application/json")
    @PostMapping("/getMenuTree/{tenantId}")
    public JsonResult getList(@ApiParam(required = true, name = "租户编号", value = "tenantId", example = "pig") @PathVariable("tenantId") String tenantId) {

       EasyBpmAsset.isAssetEmpty(tenantId);
        Result<List<MenuTreeDTO>> result = service.getMenuTree(tenantId, null);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }


    @ApiOperation(value = "根据编号获取菜单表", notes = "根据编号获取菜单表", produces = "application/json")
    @PostMapping("/getById")
    public JsonResult getById(@Valid @RequestBody MenuSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        EasyBpmAsset.isAssetEmpty(param.getMenuId());

        Result<MenuDTO> result = service.getMenuById(param.getMenuId());
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }
}
