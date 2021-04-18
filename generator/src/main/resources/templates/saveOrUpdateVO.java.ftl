package ${cfg.voRequest};

<#list table.importPackages as pkg>
import ${pkg};
</#list>
<#if swagger2>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
</#if>
<#if entityLombokModel>
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
</#if>

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
<#if superEntityClass??>
public class ${entity}SaveOrUpdateVO extends ${superEntityClass} implements Serializable {
<#else>
public class ${entity}SaveOrUpdateVO implements Serializable {
</#if>

<#if entitySerialVersionUID>
    private static final long serialVersionUID = 1L;
</#if>
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>

    <#if field.comment!?length gt 0>
    /**
     * ${field.comment}
     */
    </#if>
    private <#if field.customMap['customFieldInfo'] ?? && field.customMap['customFieldInfo']['propertyType']?? >${field.customMap['customFieldInfo']['propertyType']} <#else>${field.propertyType}</#if> ${field.propertyName};
</#list>
<#------------  END 字段循环遍历  ---------->

<#if !entityLombokModel>
    <#list table.fields as field>
        <#if field.propertyType == "boolean">
            <#assign getprefix="is"/>
        <#else>
            <#assign getprefix="get"/>
        </#if>
    public <#if field.customMap['customFieldInfo'] ?? && field.customMap['customFieldInfo']['propertyType']?? >${field.customMap['customFieldInfo']['propertyType']} <#else>${field.propertyType}</#if> ${getprefix}${field.capitalName}() {
        return ${field.propertyName};
    }

        <#if entityBuilderModel>
    public ${entity} set${field.capitalName}(<#if field.customMap['customFieldInfo'] ?? && field.customMap['customFieldInfo']['propertyType']?? >${field.customMap['customFieldInfo']['propertyType']} <#else>${field.propertyType}</#if> ${field.propertyName}) {
        <#else>
    public void set${field.capitalName}(<#if field.customMap['customFieldInfo'] ?? && field.customMap['customFieldInfo']['propertyType']?? >${field.customMap['customFieldInfo']['propertyType']} <#else>${field.propertyType}</#if> ${field.propertyName}) {
        </#if>
        this.${field.propertyName} = ${field.propertyName};
        <#if entityBuilderModel>
        return this;
        </#if>
    }
    </#list>
</#if>

<#if entityColumnConstant>
    <#list table.fields as field>
    public static final String ${field.name?upper_case} = "${field.name}";

    </#list>
</#if>


<#if !entityLombokModel>
    @Override
    public String toString() {
        return "${entity}{" +
    <#list table.fields as field>
        <#if field_index==0>
            "${field.propertyName}=" + ${field.propertyName} +
        <#else>
            ", ${field.propertyName}=" + ${field.propertyName} +
        </#if>
    </#list>
        "}";
    }
</#if>
}
