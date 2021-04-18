package com.pig.easy.bpm.web.controller.system;


import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.auth.core.controller.BaseController;
import com.pig.easy.bpm.auth.dto.request.RoleQueryDTO;
import com.pig.easy.bpm.auth.dto.request.RoleSaveOrUpdateDTO;
import com.pig.easy.bpm.auth.dto.response.RoleDTO;
import com.pig.easy.bpm.auth.dto.response.RoleExportDTO;
import com.pig.easy.bpm.auth.service.RoleService;
import com.pig.easy.bpm.common.converter.LocalDateTimeConverter;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.utils.BeanUtils;
import com.pig.easy.bpm.common.utils.EasyBpmAsset;
import com.pig.easy.bpm.common.utils.JsonResult;
import com.pig.easy.bpm.common.utils.Result;
import com.pig.easy.bpm.web.vo.request.RoleQueryVO;
import com.pig.easy.bpm.web.vo.request.RoleSaveOrUpdateVO;
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
 * 角色表 前端控制器
 * </p>
 *
 * @author pig
 * @since 2021-03-20
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {

    @Autowired
    RoleService service;

    @ApiOperation(value = "查询角色表列表", notes = "查询角色表列表", produces = "application/json")
    @PostMapping("/getListPage")
    public JsonResult getListPage(@Valid @RequestBody RoleQueryVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        RoleQueryDTO queryDTO = BeanUtils.switchToDTO(param, RoleQueryDTO.class);

        Result<PageInfo<RoleDTO>> result = service.getListPageByCondition(queryDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "查询角色表列表", notes = "查询角色表列表", produces = "application/json")
    @PostMapping("/getList")
    public JsonResult getList(@Valid @RequestBody RoleQueryVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        RoleQueryDTO queryDTO = BeanUtils.switchToDTO(param, RoleQueryDTO.class);

        Result<List<RoleDTO>> result = service.getListByCondition(queryDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "新增角色表", notes = "新增角色表", produces = "application/json")
    @PostMapping("/insert")
    public JsonResult insertRole(@Valid @RequestBody RoleSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        RoleSaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, RoleSaveOrUpdateDTO.class);

        Result<Integer> result = service.insertRole(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "修改角色表", notes = "修改角色表", produces = "application/json")
    @PostMapping("/update")
    public JsonResult updateRole(@Valid @RequestBody RoleSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        RoleSaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, RoleSaveOrUpdateDTO.class);

        Result<Integer> result = service.updateRole(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
          return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "删除角色表", notes = "删除角色表", produces = "application/json")
    @PostMapping("/deleteById")
    public JsonResult deleteById(@Valid @RequestBody RoleSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        RoleSaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, RoleSaveOrUpdateDTO.class);
        saveDTO.setValidState(0);

        Result<Integer> result = service.deleteRole(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "下载角色表", notes = "下载角色表", produces = "application/json")
    @PostMapping("download")
    public void download(HttpServletResponse response,@Valid @RequestBody RoleQueryVO param) throws IOException {

        EasyBpmAsset.isAssetEmpty(param);
        RoleQueryDTO queryDTO = BeanUtils.switchToDTO(param, RoleQueryDTO.class);

        Result<List<RoleDTO>> result = service.getListByCondition(queryDTO);
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = String.valueOf(System.currentTimeMillis()) + "角色表.xlsx";
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        EasyExcel.write(response.getOutputStream(), RoleExportDTO.class).registerConverter(new LocalDateTimeConverter()).sheet().doWrite(result.getData());
    }

    @ApiOperation(value = "根据编号获取角色表", notes = "根据编号获取角色表", produces = "application/json")
    @PostMapping("/getById")
    public JsonResult getById(@Valid @RequestBody RoleSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        EasyBpmAsset.isAssetEmpty(param.getRoleId());

        Result<RoleDTO> result = service.getRoleById(param.getRoleId());
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }
}
