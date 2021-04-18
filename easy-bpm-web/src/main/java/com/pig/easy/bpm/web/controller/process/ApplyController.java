package com.pig.easy.bpm.web.controller.process;


import com.pig.easy.bpm.api.dto.request.ApplyReqDTO;
import com.pig.easy.bpm.api.dto.request.LineChartQueryDTO;
import com.pig.easy.bpm.api.dto.response.ApproveTaskDTO;
import com.pig.easy.bpm.api.dto.response.ChartDTO;
import com.pig.easy.bpm.api.service.ApplyService;
import com.pig.easy.bpm.auth.core.controller.BaseController;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.utils.BeanUtils;
import com.pig.easy.bpm.common.utils.JsonResult;
import com.pig.easy.bpm.common.utils.Result;
import com.pig.easy.bpm.web.vo.request.ApplyAddVO;
import com.pig.easy.bpm.web.vo.request.LineChartQueryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 * 申请表 前端控制器
 * </p>
 *
 * @author pig
 * @since 2020-05-20
 */
@RestController
@RequestMapping("/apply")
@Api(tags = "申请单管理", value = "申请单管理")
public class ApplyController extends BaseController {

    @Resource
    private ApplyService applyService;

    @ApiOperation(value = "发起流程", notes = "发起流程", produces = "application/json")
    @PostMapping("/startProcess")
    public JsonResult startPocess(@Valid @RequestBody ApplyAddVO applyAddVO) {

        if (applyAddVO == null
                || StringUtils.isEmpty(applyAddVO.getProcessKey())) {
            return JsonResult.error(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        ApplyReqDTO applyAddReqDTO = switchToDTO(applyAddVO, ApplyReqDTO.class);
        applyAddReqDTO.setData(applyAddVO.getData());
        /* 设置制单人，兼容替人发单的情况 */
        applyAddReqDTO.setCreateUserId(currentUserInfo().getUserId());
        applyAddReqDTO.setCreaterRealName(currentUserInfo().getRealName());

        Result<Boolean> result = applyService.startProcess(applyAddReqDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "保存为草稿流程", notes = "保存为草稿流程", produces = "application/json")
    @PostMapping("/saveDraftApply")
    public JsonResult saveDraftApply(@Valid @RequestBody ApplyAddVO applyAddVO) {

        if (applyAddVO == null
                || StringUtils.isEmpty(applyAddVO.getProcessKey())) {
            return JsonResult.error(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        ApplyReqDTO applyAddReqDTO = switchToDTO(applyAddVO, ApplyReqDTO.class);
        applyAddReqDTO.setData(applyAddVO.getData());
        /* 设置制单人，兼容替人发单的情况 */
        applyAddReqDTO.setCreateUserId(currentUserInfo().getUserId());
        applyAddReqDTO.setCreaterRealName(currentUserInfo().getRealName());

        Result<Long> result = applyService.saveDraftApply(applyAddReqDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "获取审批详情", notes = "获取审批详情", produces = "application/json")
    @PostMapping("/getApproveDetail/{taskId}")
    public JsonResult getApproveDetail(@ApiParam(required = true, name = "任务编号", value = "taskId", example = "1") @PathVariable("taskId") Long taskId) {

        Result<ApproveTaskDTO> result = applyService.getApproveDetail(taskId);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "获取申请详情", notes = "获取申请详情", produces = "application/json")
    @PostMapping("/getApplyDetail/{applyId}")
    public JsonResult getApplyDetail(@ApiParam(required = true, name = "申请编号", value = "applyId", example = "1") @PathVariable("applyId") Long applyId) {

        Result<ApproveTaskDTO> result = applyService.getApplyDetail(applyId);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "获取折线图数据", notes = "获取折线图数据", produces = "application/json")
    @PostMapping("/getApplyLineChart")
    public JsonResult getApplyLineChart(@RequestBody LineChartQueryVO lineChartQueryVO) {

        if(lineChartQueryVO == null){
            return JsonResult.error(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        LineChartQueryDTO lineCharDTO = BeanUtils.switchToDTO(lineChartQueryVO, LineChartQueryDTO.class);
        Result<ChartDTO> result = applyService.getApplyLineChart(lineCharDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }
}

