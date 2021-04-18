package com.pig.easy.bpm.generator.controller;

import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.generator.dto.response.DictDTO;
import com.pig.easy.bpm.common.utils.*;
import com.pig.easy.bpm.generator.dto.request.ColumnSaveOrUpdateDTO;
import com.pig.easy.bpm.generator.dto.request.TableStrategyConfigQueryDTO;
import com.pig.easy.bpm.generator.dto.request.TableStrategyConfigSaveOrUpdateDTO;
import com.pig.easy.bpm.generator.dto.response.ColumnDTO;
import com.pig.easy.bpm.generator.dto.response.TableStrategyConfigDTO;
import com.pig.easy.bpm.generator.service.CodeTableService;
import com.pig.easy.bpm.generator.service.GeneratorService;
import com.pig.easy.bpm.generator.service.TableStrategyConfigService;
import com.pig.easy.bpm.generator.service.TemplateGroupService;
import com.pig.easy.bpm.generator.utils.DbUtils;
import com.pig.easy.bpm.generator.vo.request.*;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * todo:
 *
 * @author : zhoulin.zhu
 * @date : 2021/2/24 10:31
 */
@RestController
@RequestMapping("/codeTable")
public class CodeTableController {

    @Autowired
    CodeTableService service;
    @Autowired
    GeneratorService generatorService;
    @Autowired
    TableStrategyConfigService tableStrategyConfigService;
    @Autowired
    TemplateGroupService templateGroupService;

    @ApiOperation(value = "测试连接", notes = "测试连接", produces = "application/json")
    @PostMapping("/testConn")
    public JsonResult testConn(@Valid @RequestBody DbConfigSaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);

        boolean b = DbUtils.testConn(param.getDbDriverName(), param.getDbUrl(), param.getDbUsername(), param.getDbPassword());

        if(b){
            return JsonResult.success(b);
        }
        return JsonResult.error(new EntityError(400, "连接数据库失败，请检查参数！"));
    }


    @ApiOperation(value = "查询策略列表", notes = "查询策略列表", produces = "application/json")
    @PostMapping("/getStrategyConfigByDbId")
    public JsonResult getStrategyConfigByDbId(@Valid @RequestBody CodeTableVO codeTableVO) {

        EasyBpmAsset.isAssetEmpty(codeTableVO);
        EasyBpmAsset.isAssetEmpty(codeTableVO.getDbId());
        TableStrategyConfigQueryDTO param = new TableStrategyConfigQueryDTO();
        param.setDbId(codeTableVO.getDbId());

        Result<List<TableStrategyConfigDTO>> result = tableStrategyConfigService.getListByCondition(param);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "查询数据源列表", notes = "查询数据源列表", produces = "application/json")
    @PostMapping("/getList")
    public JsonResult getTableInfoList(@Valid @RequestBody CodeTableVO codeTableVO) {

        EasyBpmAsset.isAssetEmpty(codeTableVO);

        String[] includeTables = null;
        if (StringUtils.isNotEmpty(codeTableVO.getIncludeTables())){
            includeTables = codeTableVO.getIncludeTables().split(",");
        }

        Result<List<TableInfo>> result = service.getTableInfoList(codeTableVO.getDbId(), includeTables, codeTableVO.getQueryTableFiled());
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "查询数据表字段列表", notes = "查询数据表字段列表", produces = "application/json")
    @PostMapping("/getTableColumnList")
    public JsonResult getTableColumnList(@Valid @RequestBody CodeTableVO codeTableVO) {

        EasyBpmAsset.isAssetEmpty(codeTableVO);

        Result<List<ColumnDTO>> result = service.getTableColumnList(codeTableVO.getDbId(), codeTableVO.getIncludeTables());
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "保存或修改数据源表字段列表", notes = "保存或修改数据源表字段列表", produces = "application/json")
    @PostMapping("/saveOrUpdateListAndGetList")
    public JsonResult saveOrUpdateListAndGetList(@Valid @RequestBody TableFieldSaveVO tableFieldSaveVO) {

        EasyBpmAsset.isAssetEmpty(tableFieldSaveVO);
        EasyBpmAsset.isAssetEmpty(tableFieldSaveVO.getColumnList());
        List<ColumnSaveOrUpdateVO> columnSaveOrUpdateVOList = tableFieldSaveVO.getColumnList();
        List<ColumnSaveOrUpdateDTO> list = new ArrayList<>();
        columnSaveOrUpdateVOList.stream().forEach(columnSaveOrUpdateVO -> {
            list.add(BeanUtils.switchToDTO(columnSaveOrUpdateVO,ColumnSaveOrUpdateDTO.class));
        });

        Result<List<ColumnDTO>> result = service.saveOrUpdateListAndGetList(list,false);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }


    @ApiOperation(value = "下载代码", notes = "下载代码", produces = "application/json")
    @RequestMapping("downloadZip")
    public void downloadZip(HttpServletResponse response,@Valid @RequestBody GeneratorVO generatorVO) {

        EasyBpmAsset.isAssetEmpty(generatorVO);
        EasyBpmAsset.isAssetEmpty(generatorVO.getDbId());
        EasyBpmAsset.isAssetEmpty(generatorVO.getIncludeTables());
        String[] includeTables = null;
        if (StringUtils.isNotEmpty(generatorVO.getIncludeTables())){
            includeTables = generatorVO.getIncludeTables().split(",");
        }
        List<String> selectTemplateList =  generatorVO.getSelectTemplateList() == null ? new ArrayList<>(): generatorVO.getSelectTemplateList();
        selectTemplateList = selectTemplateList.stream().map(a->a.replaceAll("'","")).collect(Collectors.toList());

        Result<Map<String, Object>> result = generatorService.initAndExecute(generatorVO.getDbId(), generatorVO.getTempleateId(),selectTemplateList,true
                , generatorVO.getPreView(), includeTables);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return;
        }
        byte[] zip = (byte[])result.getData().get("zip");

        try {
            String downloadName = URLEncoder.encode("gengeraor.zip", "UTF-8");
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Disposition", "attachment; filename="+downloadName);
            response.addHeader("Content-Length", "" + (zip).length);
            response.setContentType("application/octet-stream; charset=UTF-8");
            IOUtils.write(zip, response.getOutputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "保存生成代码到本地", notes = "保存生成代码到本地", produces = "application/json")
    @RequestMapping("gengratorCode")
    public JsonResult gengratorCode(@Valid @RequestBody GeneratorVO generatorVO) {

        EasyBpmAsset.isAssetEmpty(generatorVO);
        EasyBpmAsset.isAssetEmpty(generatorVO.getDbId());
        EasyBpmAsset.isAssetEmpty(generatorVO.getIncludeTables());
        String[] includeTables = null;
        if (StringUtils.isNotEmpty(generatorVO.getIncludeTables())){
            includeTables = generatorVO.getIncludeTables().split(",");
        }
        TableStrategyConfigSaveOrUpdateDTO tableStrategyConfig = null;
        if (generatorVO.getTableStrategyConfig() != null){
            tableStrategyConfig = BeanUtils.switchToDTO(generatorVO.getTableStrategyConfig(),TableStrategyConfigSaveOrUpdateDTO.class);
        }

        List<String> selectTemplateList =  generatorVO.getSelectTemplateList() == null ? new ArrayList<>(): generatorVO.getSelectTemplateList();
        selectTemplateList = selectTemplateList.stream().map(a->a.replaceAll("'","")).collect(Collectors.toList());

        Result<Map<String, Object>> result = generatorService.generatorCode(generatorVO.getDbId(), generatorVO.getTempleateId(),
                selectTemplateList,tableStrategyConfig,false, false, includeTables);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }


    @ApiOperation(value = "生成代码", notes = "生成代码", produces = "application/json")
    @PostMapping("/initAndExecute")
    public JsonResult initAndExecute(@Valid @RequestBody GeneratorVO generatorVO) {

        EasyBpmAsset.isAssetEmpty(generatorVO);
        EasyBpmAsset.isAssetEmpty(generatorVO.getDbId());
        EasyBpmAsset.isAssetEmpty(generatorVO.getIncludeTables());
        String[] includeTables = null;
        if (StringUtils.isNotEmpty(generatorVO.getIncludeTables())){
            includeTables = generatorVO.getIncludeTables().split(",");
        }
        List<String> selectTemplateList =  generatorVO.getSelectTemplateList() == null ? new ArrayList<>(): generatorVO.getSelectTemplateList();
        selectTemplateList = selectTemplateList.stream().map(a->a.replaceAll("'","")).collect(Collectors.toList());
        Result<Map<String, Object>> result = generatorService.initAndExecute(generatorVO.getDbId(), generatorVO.getTempleateId(),selectTemplateList,false, generatorVO.getPreView(), includeTables);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }

        Map<String, Object> data = result.getData();
        List<DictDTO> dictList = new ArrayList<>();
        DictDTO dictDTO = null;
        for (Map.Entry<String,Object> entry: result.getData().entrySet()) {
            dictDTO = new DictDTO();
            dictDTO.setValue(entry.getValue());
            dictDTO.setLabel(entry.getKey());
            dictList.add(dictDTO);
        }
        return JsonResult.success(dictList);
    }



}
