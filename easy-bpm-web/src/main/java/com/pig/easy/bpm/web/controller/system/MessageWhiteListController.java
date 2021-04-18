package com.pig.easy.bpm.web.controller.system;


import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.auth.core.controller.BaseController;
import com.pig.easy.bpm.auth.dto.request.MessageWhiteListQueryDTO;
import com.pig.easy.bpm.auth.dto.request.MessageWhiteListSaveOrUpdateDTO;
import com.pig.easy.bpm.auth.dto.response.MessageWhiteListDTO;
import com.pig.easy.bpm.auth.dto.response.MessageWhiteListExportDTO;
import com.pig.easy.bpm.auth.service.MessageWhiteListService;
import com.pig.easy.bpm.common.converter.LocalDateTimeConverter;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.utils.BeanUtils;
import com.pig.easy.bpm.common.utils.EasyBpmAsset;
import com.pig.easy.bpm.common.utils.JsonResult;
import com.pig.easy.bpm.common.utils.Result;
import com.pig.easy.bpm.web.vo.request.MessageWhiteListQueryVO;
import com.pig.easy.bpm.web.vo.request.MessageWhiteListSaveOrUpdateVO;
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
 * 通知白名单 前端控制器
 * </p>
 *
 * @author pig
 * @since 2021-03-20
 */
@RestController
@RequestMapping("/messageWhiteList")
public class MessageWhiteListController extends BaseController {

    @Autowired
    MessageWhiteListService service;

    @ApiOperation(value = "查询通知白名单列表", notes = "查询通知白名单列表", produces = "application/json")
    @PostMapping("/getListPage")
    public JsonResult getListPage(@Valid @RequestBody MessageWhiteListQueryVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        MessageWhiteListQueryDTO queryDTO = BeanUtils.switchToDTO(param, MessageWhiteListQueryDTO.class);

        Result<PageInfo<MessageWhiteListDTO>> result = service.getListPageByCondition(queryDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "查询通知白名单列表", notes = "查询通知白名单列表", produces = "application/json")
    @PostMapping("/getList")
    public JsonResult getList(@Valid @RequestBody MessageWhiteListQueryVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        MessageWhiteListQueryDTO queryDTO = BeanUtils.switchToDTO(param, MessageWhiteListQueryDTO.class);

        Result<List<MessageWhiteListDTO>> result = service.getListByCondition(queryDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "新增通知白名单", notes = "新增通知白名单", produces = "application/json")
    @PostMapping("/insert")
    public JsonResult insertMessageWhiteList(@Valid @RequestBody MessageWhiteListSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        MessageWhiteListSaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, MessageWhiteListSaveOrUpdateDTO.class);

        Result<Integer> result = service.insertMessageWhiteList(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "修改通知白名单", notes = "修改通知白名单", produces = "application/json")
    @PostMapping("/update")
    public JsonResult updateMessageWhiteList(@Valid @RequestBody MessageWhiteListSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        MessageWhiteListSaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, MessageWhiteListSaveOrUpdateDTO.class);

        Result<Integer> result = service.updateMessageWhiteList(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
          return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "删除通知白名单", notes = "删除通知白名单", produces = "application/json")
    @PostMapping("/deleteById")
    public JsonResult deleteById(@Valid @RequestBody MessageWhiteListSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        MessageWhiteListSaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, MessageWhiteListSaveOrUpdateDTO.class);
        saveDTO.setValidState(0);

        Result<Integer> result = service.deleteMessageWhiteList(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "下载通知白名单", notes = "下载通知白名单", produces = "application/json")
    @PostMapping("download")
    public void download(HttpServletResponse response,@Valid @RequestBody MessageWhiteListQueryVO param) throws IOException {

        EasyBpmAsset.isAssetEmpty(param);
        MessageWhiteListQueryDTO queryDTO = BeanUtils.switchToDTO(param, MessageWhiteListQueryDTO.class);

        Result<List<MessageWhiteListDTO>> result = service.getListByCondition(queryDTO);
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = String.valueOf(System.currentTimeMillis()) + "通知白名单.xlsx";
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        EasyExcel.write(response.getOutputStream(), MessageWhiteListExportDTO.class).registerConverter(new LocalDateTimeConverter()).sheet().doWrite(result.getData());
    }

    @ApiOperation(value = "根据编号获取通知白名单", notes = "根据编号获取通知白名单", produces = "application/json")
    @PostMapping("/getById")
    public JsonResult getById(@Valid @RequestBody MessageWhiteListSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        EasyBpmAsset.isAssetEmpty(param.getWhiteId());

        Result<MessageWhiteListDTO> result = service.getMessageWhiteListById(param.getWhiteId());
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }
}
