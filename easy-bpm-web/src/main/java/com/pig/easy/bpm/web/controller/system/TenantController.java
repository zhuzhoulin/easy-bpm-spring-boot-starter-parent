package com.pig.easy.bpm.web.controller.system;


import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.auth.core.controller.BaseController;
import com.pig.easy.bpm.auth.dto.request.TenantQueryDTO;
import com.pig.easy.bpm.auth.dto.request.TenantSaveOrUpdateDTO;
import com.pig.easy.bpm.auth.dto.response.TenantDTO;
import com.pig.easy.bpm.auth.service.TenantService;
import com.pig.easy.bpm.common.constant.BpmConstant;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.utils.EasyBpmAsset;
import com.pig.easy.bpm.common.utils.JsonResult;
import com.pig.easy.bpm.common.utils.Result;
import com.pig.easy.bpm.web.vo.request.TenantQueryVO;
import com.pig.easy.bpm.web.vo.request.TenantSaveOrUpdateVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;


/**
 * <p>
 * 租户表 前端控制器
 * </p>
 *
 * @author pig
 * @since 2020-09-02
 */
@RestController
@Api(tags = "租户表管理", value = "租户表管理")
@RequestMapping("/tenant")
public class TenantController extends BaseController {

    @Resource
    TenantService service;

    @ApiOperation(value = "查询租户表列表", notes = "查询租户表列表", produces = "application/json")
    @PostMapping("/getList")
    public JsonResult getList(@Valid @RequestBody TenantQueryVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        EasyBpmAsset.isAssetEmpty(param.getTenantId());
        TenantQueryDTO queryDTO = switchToDTO(param, TenantQueryDTO.class);

        Result<PageInfo<TenantDTO>> result = service.getListByCondition(queryDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "新增租户表", notes = "新增租户表", produces = "application/json")
    @PostMapping("/insert")
    public JsonResult insertTenant(@Valid @RequestBody TenantSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        EasyBpmAsset.isAssetEmpty(param.getTenantId());
        TenantSaveOrUpdateDTO saveDTO = switchToDTO(param, TenantSaveOrUpdateDTO.class);
        saveDTO.setOperatorId(currentUserInfo().getUserId());
        saveDTO.setOperatorName(currentUserInfo().getRealName());

        Result<Integer> result = service.insertTenant(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "修改租户表", notes = "修改租户表", produces = "application/json")
    @PostMapping("/update")
    public JsonResult updateTenant(@Valid @RequestBody TenantSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        EasyBpmAsset.isAssetEmpty(param.getTenantId());
        TenantSaveOrUpdateDTO saveDTO = switchToDTO(param, TenantSaveOrUpdateDTO.class);
        saveDTO.setOperatorId(currentUserInfo().getUserId());
        saveDTO.setOperatorName(currentUserInfo().getRealName());

        Result<Integer> result = service.updateTenant(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "删除租户表", notes = "删除租户表", produces = "application/json")
    @PostMapping("/delete")
    public JsonResult delete(@Valid @RequestBody TenantSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        EasyBpmAsset.isAssetEmpty(param.getTenantId());
        TenantSaveOrUpdateDTO saveDTO = switchToDTO(param, TenantSaveOrUpdateDTO.class);
        saveDTO.setOperatorId(currentUserInfo().getUserId());
        saveDTO.setOperatorName(currentUserInfo().getRealName());
        saveDTO.setValidState(BpmConstant.INVALID_STATE);

        Result<Integer> result = service.deleteTenant(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

}

