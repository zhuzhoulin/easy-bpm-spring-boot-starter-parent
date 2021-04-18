package ${package.Controller};


import org.springframework.web.bind.annotation.RequestMapping;
import ${cfg.voRequest}.*;
import ${cfg.dtoRequest}.*;
import ${cfg.dtoResponse}.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.lang3.StringUtils;
import ${package.Service}.${table.serviceName};
import java.util.List;
import javax.validation.Valid;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.utils.BeanUtils;
import com.pig.easy.bpm.common.utils.EasyBpmAsset;
import com.pig.easy.bpm.common.utils.JsonResult;
import com.pig.easy.bpm.common.utils.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;



<#--<#if restControllerStyle>-->
<#--import org.springframework.web.bind.annotation.RestController;-->
<#--<#else>-->
<#--import org.springframework.stereotype.Controller;-->
<#--</#if>-->
import org.springframework.web.bind.annotation.RestController;

<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#--<#if restControllerStyle>-->
<#--@RestController-->
<#--<#else>-->
<#--@Controller-->
<#--</#if>-->
@RestController
<#if swagger2>
@Api(tags = "${table.comment}管理", value = "${table.comment}管理")
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

    @Autowired
    ${table.serviceName} service;

    @ApiOperation(value = "查询${table.comment}列表", notes = "查询${table.comment}列表", produces = "application/json")
    @PostMapping("/getListPage")
    public JsonResult getListPage(@Valid @RequestBody ${entity}QueryVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        ${entity}QueryDTO queryDTO = BeanUtils.switchToDTO(param, ${entity}QueryDTO.class);

        Result<PageInfo<${entity}DTO>> result = service.getListPageByCondition(queryDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "查询${table.comment}列表", notes = "查询${table.comment}列表", produces = "application/json")
    @PostMapping("/getList")
    public JsonResult getList(@Valid @RequestBody ${entity}QueryVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        ${entity}QueryDTO queryDTO = BeanUtils.switchToDTO(param, ${entity}QueryDTO.class);

        Result<List<${entity}DTO>> result = service.getListByCondition(queryDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "新增${table.comment}", notes = "新增${table.comment}", produces = "application/json")
    @PostMapping("/insert")
    public JsonResult insert${entity}(@Valid @RequestBody ${entity}SaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        ${entity}SaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, ${entity}SaveOrUpdateDTO.class);

        Result<Integer> result = service.insert${entity}(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    @ApiOperation(value = "修改${table.comment}", notes = "修改${table.comment}", produces = "application/json")
    @PostMapping("/update")
    public JsonResult update${entity}(@Valid @RequestBody ${entity}SaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        ${entity}SaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, ${entity}SaveOrUpdateDTO.class);

        Result<Integer> result = service.update${entity}(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
          return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }

    <#list table.fields as field>
        <#if field.keyFlag>
    @ApiOperation(value = "删除${table.comment}", notes = "删除${table.comment}", produces = "application/json")
    @PostMapping("/deleteById")
    public JsonResult deleteById(@Valid @RequestBody ${entity}SaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        ${entity}SaveOrUpdateDTO saveDTO = BeanUtils.switchToDTO(param, ${entity}SaveOrUpdateDTO.class);
        saveDTO.setValidState(0);

        Result<Integer> result = service.delete${entity}(saveDTO);
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }
        </#if>
    </#list>

    @ApiOperation(value = "下载${table.comment}", notes = "下载${table.comment}", produces = "application/json")
    @GetMapping("download")
    public void download(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = String.valueOf(System.currentTimeMillis()) + ".xlsx";
        Result<List<${entity}DTO>> result = templateGroupService.getListByCondition(new TemplateGroupQueryDTO());
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        EasyExcel.write(response.getOutputStream(), ${entity}ExportDTO.class).registerConverter(new LocalDateTimeConverter()).sheet().doWrite(result.getData());
    }

    <#list table.fields as field>
        <#if field.keyFlag>
    @ApiOperation(value = "根据编号获取${table.comment}", notes = "根据编号获取${table.comment}", produces = "application/json")
    @PostMapping("/getById")
    public JsonResult getById(@Valid @RequestBody ${entity}SaveOrUpdateVO param) {

        EasyBpmAsset.isAssetEmpty(param);
        EasyBpmAsset.isAssetEmpty(param.get${field.capitalName}());

        Result<${entity}DTO> result = service.get${entity}ById(param.get${field.capitalName}());
        if (result.getEntityError().getCode() != EntityError.SUCCESS.getCode()) {
            return JsonResult.error(result.getEntityError());
        }
        return JsonResult.success(result.getData());
    }
        </#if>
    </#list>
}
</#if>
