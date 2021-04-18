package ${cfg.dtoResponse};

<#list table.importPackages as pkg>
import ${pkg};
</#list>
<#if entityLombokModel>
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
</#if>
import com.alibaba.excel.annotation.*;
import java.io.Serializable;
import java.util.Date;
import java.math.*;
/**
 * <p>
    * ${table.comment!}
    * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if entityLombokModel>
@Data
    <#if superEntityClass??>
@EqualsAndHashCode(callSuper = true)
    <#else>
@EqualsAndHashCode(callSuper = false)
    </#if>
@Accessors(chain = true)
</#if>
public class ${entity}ExportDTO implements Serializable {

<#if entitySerialVersionUID>
    @ExcelIgnore
    private static final long serialVersionUID = 1L;
</#if>
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>

    <#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['downloadFlag'] == 1 >
    <#if field.comment!?length gt 0>
    /**
     * ${field.comment}
     */
    </#if>
    @ExcelProperty( value = "<#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formLabel']?? >${field.customMap['customFieldInfo']['formLabel']}<#else>${field.comment}</#if>")
    private <#if field.customMap['customFieldInfo'] ?? && field.customMap['customFieldInfo']['propertyType']?? >${field.customMap['customFieldInfo']['propertyType']} <#else>${field.propertyType}</#if> ${field.propertyName};
    </#if>
</#list>
<#------------  END 字段循环遍历  ---------->
}
