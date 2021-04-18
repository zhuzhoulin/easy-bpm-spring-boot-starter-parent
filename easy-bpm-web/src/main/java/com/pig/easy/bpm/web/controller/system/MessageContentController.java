package com.pig.easy.bpm.web.controller.system;


import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.auth.core.controller.BaseController;
import com.pig.easy.bpm.auth.dto.request.MessageContentQueryDTO;
import com.pig.easy.bpm.auth.dto.request.MessageContentSaveOrUpdateDTO;
import com.pig.easy.bpm.auth.dto.response.MessageContentDTO;
import com.pig.easy.bpm.auth.dto.response.MessageContentExportDTO;
import com.pig.easy.bpm.auth.service.MessageContentService;
import com.pig.easy.bpm.common.converter.LocalDateTimeConverter;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.utils.BeanUtils;
import com.pig.easy.bpm.common.utils.EasyBpmAsset;
import com.pig.easy.bpm.common.utils.JsonResult;
import com.pig.easy.bpm.common.utils.Result;
import com.pig.easy.bpm.web.vo.request.MessageContentQueryVO;
import com.pig.easy.bpm.web.vo.request.MessageContentSaveOrUpdateVO;
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
 * 通知内容表 前端控制器
 * </p>
 *
 * @author pig
 * @since 2021-03-20
 */
@RestController
@RequestMapping("/messageContent")
public class MessageContentController extends BaseController {

    @Autowired
    MessageContentService service;

    @ApiOperation(value = "查询通知内容表列表", notes = "查询通知内容表列表", produces = "application/json")
    @PostMapping("/getListPage")
    public JsonResult getListPage(@Valid @RequestBody MessageContentQueryVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        MessageContentQueryDTO queryDTO = BeanUtils.switchToDTO(param, MessageContentQueryDTO.class);

        Result<PageInfo<MessageContentDTO>> result = service.getListPageByCondition(queryDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "查询通知内容表列表", notes = "查询通知内容表列表", produces = "application/json")
    @PostMapping("/getList")
    public JsonResult getList(@Valid @RequestBody MessageContentQueryVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        MessageContentQueryDTO queryDTO = BeanUtils.switchToDTO(param, MessageContentQueryDTO.class);

        Result<List<MessageContentDTO>> result = service.getListByCondition(queryDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "新增通知内容表", notes = "新增通知内容表", produces = "application/json")
    @PostMapping("/insert")
    public JsonResult insertMessageContent(@Valid @RequestBody MessageContentSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        MessageContentSaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, MessageContentSaveOrUpdateDTO.class);

        Result<Integer> result = service.insertMessageContent(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "修改通知内容表", notes = "修改通知内容表", produces = "application/json")
    @PostMapping("/update")
    public JsonResult updateMessageContent(@Valid @RequestBody MessageContentSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        MessageContentSaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, MessageContentSaveOrUpdateDTO.class);

        Result<Integer> result = service.updateMessageContent(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
          return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "删除通知内容表", notes = "删除通知内容表", produces = "application/json")
    @PostMapping("/deleteById")
    public JsonResult deleteById(@Valid @RequestBody MessageContentSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        MessageContentSaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, MessageContentSaveOrUpdateDTO.class);
        saveDTO.setValidState(0);

        Result<Integer> result = service.deleteMessageContent(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "下载通知内容表", notes = "下载通知内容表", produces = "application/json")
    @PostMapping("download")
    public void download(HttpServletResponse response,@Valid @RequestBody MessageContentQueryVO param) throws IOException {

        EasyBpmAsset.isAssetEmpty(param);
        MessageContentQueryDTO queryDTO = BeanUtils.switchToDTO(param, MessageContentQueryDTO.class);

        Result<List<MessageContentDTO>> result = service.getListByCondition(queryDTO);
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = String.valueOf(System.currentTimeMillis()) + "通知内容表.xlsx";
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        EasyExcel.write(response.getOutputStream(), MessageContentExportDTO.class).registerConverter(new LocalDateTimeConverter()).sheet().doWrite(result.getData());
    }

    @ApiOperation(value = "根据编号获取通知内容表", notes = "根据编号获取通知内容表", produces = "application/json")
    @PostMapping("/getById")
    public JsonResult getById(@Valid @RequestBody MessageContentSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        EasyBpmAsset.isAssetEmpty(param.getContentId());

        Result<MessageContentDTO> result = service.getMessageContentById(param.getContentId());
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }
}
