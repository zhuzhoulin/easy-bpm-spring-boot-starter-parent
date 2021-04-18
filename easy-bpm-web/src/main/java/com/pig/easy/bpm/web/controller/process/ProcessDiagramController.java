package com.pig.easy.bpm.web.controller.process;

import com.pig.easy.bpm.api.dto.response.ProcessDiagramDTO;
import com.pig.easy.bpm.api.service.ProcessDiagramService;
import com.pig.easy.bpm.auth.core.controller.BaseController;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.utils.JsonResult;
import com.pig.easy.bpm.common.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/7/5 18:29
 */
@Api(tags = "流程图详细信息", value = "流程图详细信息")
@RestController
@RequestMapping("/processDiagram")
public class ProcessDiagramController extends BaseController {

    @Resource
    ProcessDiagramService processDiagramService;

    @ApiOperation(value = "获取图流程详细信息", notes = "获取图流程详细信息", produces = "application/json")
    @PostMapping("/getProcessDiagramByApplyId/{applyId}")
    public JsonResult getProcessDiagramByApplyId(
            @ApiParam(required = true, name = "申请编号", value = "applyId", example = "1") @PathVariable("applyId") Long applyId
    ) {
        Result<ProcessDiagramDTO> result = processDiagramService.getProcessDiagramByApplyId(applyId);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }
}
