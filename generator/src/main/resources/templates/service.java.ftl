package ${package.Service};

import ${package.Entity}.${entity}DO;
import ${superServiceClassPackage};
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.common.generator.BaseService;
import com.pig.easy.bpm.common.utils.Result;
import ${cfg.dtoRequest}.*;
import ${cfg.dtoResponse}.*;
import java.util.List;
/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}DO>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}DO> {

    Result<PageInfo<${entity}DTO>> getListPageByCondition(${entity}QueryDTO param);

    Result<Integer> insert${entity}(${entity}SaveOrUpdateDTO param);

    Result<Integer> update${entity}(${entity}SaveOrUpdateDTO param);

    Result<Integer> delete${entity}(${entity}SaveOrUpdateDTO param);

    <#list table.fields as field>
        <#if field.keyFlag>
    Result<${entity}DTO> get${entity}ById(${field.propertyType} ${field.propertyName});
        </#if>
    </#list>

    Result<List<${entity}DTO>> getListByCondition(${entity}QueryDTO param);
}
</#if>
