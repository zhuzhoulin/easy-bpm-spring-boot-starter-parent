package com.pig.easy.bpm.web.controller.process;


import com.pig.easy.bpm.web.vo.request.NodeQueryVO;
import com.pig.easy.bpm.web.vo.request.NodeSaveOrUpdateVO;
import org.springframework.web.bind.annotation.RequestMapping;
import com.pig.easy.bpm.api.dto.request.*;
import com.pig.easy.bpm.api.dto.response.*;
import org.springframework.web.bind.annotation.*;
import com.pig.easy.bpm.api.service.NodeService;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import javax.validation.Valid;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.utils.BeanUtils;
import com.pig.easy.bpm.common.utils.EasyBpmAsset;
import com.pig.easy.bpm.common.utils.JsonResult;
import com.pig.easy.bpm.common.utils.Result;
import com.pig.easy.bpm.common.converter.LocalDateTimeConverter;
import java.io.IOException;
import com.alibaba.excel.EasyExcel;

import io.swagger.annotations.ApiOperation;

import java.net.URLEncoder;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 流程节点表 前端控制器
 * </p>
 *
 * @author pig
 * @since 2021-04-09
 */
@RestController
@RequestMapping("/node")
public class NodeController {

    @Autowired
    NodeService service;

    @ApiOperation(value = "查询流程节点表列表", notes = "查询流程节点表列表", produces = "application/json")
    @PostMapping("/getListPage")
    public JsonResult getListPage(@Valid @RequestBody NodeQueryVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        NodeQueryDTO queryDTO = BeanUtils.switchToDTO(param, NodeQueryDTO.class);

        Result<PageInfo<NodeDTO>> result = service.getListPageByCondition(queryDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "查询流程节点表列表", notes = "查询流程节点表列表", produces = "application/json")
    @PostMapping("/getList")
    public JsonResult getList(@Valid @RequestBody NodeQueryVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        NodeQueryDTO queryDTO = BeanUtils.switchToDTO(param, NodeQueryDTO.class);

        Result<List<NodeDTO>> result = service.getListByCondition(queryDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "新增流程节点表", notes = "新增流程节点表", produces = "application/json")
    @PostMapping("/insert")
    public JsonResult insertNode(@Valid @RequestBody NodeSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        NodeSaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, NodeSaveOrUpdateDTO.class);

        Result<Integer> result = service.insertNode(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "修改流程节点表", notes = "修改流程节点表", produces = "application/json")
    @PostMapping("/update")
    public JsonResult updateNode(@Valid @RequestBody NodeSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        NodeSaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, NodeSaveOrUpdateDTO.class);

        Result<Integer> result = service.updateNode(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
          return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "删除流程节点表", notes = "删除流程节点表", produces = "application/json")
    @PostMapping("/deleteById")
    public JsonResult deleteById(@Valid @RequestBody NodeSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        NodeSaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, NodeSaveOrUpdateDTO.class);
        saveDTO.setValidState(0);

        Result<Integer> result = service.deleteNode(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "下载流程节点表", notes = "下载流程节点表", produces = "application/json")
    @PostMapping("download")
    public void download(HttpServletResponse response,@Valid @RequestBody NodeQueryVO param) throws IOException {

        EasyBpmAsset.isAssetEmpty(param);
        NodeQueryDTO queryDTO = BeanUtils.switchToDTO(param, NodeQueryDTO.class);

        Result<List<NodeDTO>> result = service.getListByCondition(queryDTO);
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = String.valueOf(System.currentTimeMillis()) + "流程节点表.xlsx";
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        EasyExcel.write(response.getOutputStream(), NodeExportDTO.class).registerConverter(new LocalDateTimeConverter()).sheet().doWrite(result.getData());
    }

    @ApiOperation(value = "根据编号获取流程节点表", notes = "根据编号获取流程节点表", produces = "application/json")
    @PostMapping("/getById")
    public JsonResult getById(@Valid @RequestBody NodeSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        EasyBpmAsset.isAssetEmpty(param.getId());

        Result<NodeDTO> result = service.getNodeById(param.getId());
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }
}
