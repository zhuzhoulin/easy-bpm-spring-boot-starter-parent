<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package.Mapper}.${table.mapperName}">

<#if enableCache>
    <!-- 开启二级缓存 -->
    <cache type="org.mybatis.caches.ehcache.LoggingEhcache"/>

</#if>
<#if baseResultMap>
    <!-- 通用查询映射结果 -->
    <resultMap id="BaseResultMap" type="${package.Entity}.${entity}DO">
<#list table.fields as field>
<#if field.keyFlag><#--生成主键排在第一位-->
        <id column="${field.name}" property="${field.propertyName}" />
</#if>
</#list>
<#list table.commonFields as field><#--生成公共字段 -->
    <result column="${field.name}" property="${field.propertyName}" />
</#list>
<#list table.fields as field>
<#if !field.keyFlag><#--生成普通字段 -->
        <result column="${field.name}" property="${field.propertyName}" />
</#if>
</#list>
    </resultMap>

</#if>
<#if baseColumnList>
    <!-- 通用查询结果列 -->
    <sql id="Base_Column_List">
<#list table.commonFields as field>
        ${field.name},
</#list>
        ${table.fieldNames}
    </sql>

</#if>
    <select id="getListByCondition" parameterType="${cfg.dtoRequest}.${entity}QueryDTO"
            resultType="${cfg.dtoResponse}.${entity}DTO">
        select
            <include refid="Base_Column_List"/>
        from
            ${table.name} t
        where
            1 = 1
        <#list table.fields as field>
            <#if field.customMap['customFieldInfo']['propertyType'] == "String" >
	   <if test="${field.propertyName} != null and ${field.propertyName} != ''">
            <#elseif field.customMap['customFieldInfo']['propertyType'] == "Long">
	   <if test="${field.propertyName} != null and ${field.propertyName} > 0">
            <#elseif field.customMap['customFieldInfo']['propertyType'] == "Integer" && field.propertyName != 'validState'>
        <if test="${field.propertyName} != null and ${field.propertyName} > 0">
            <#elseif field.customMap['customFieldInfo']['propertyType'] == "Integer" && field.propertyName == 'validState'>
		<if test="${field.propertyName} != null and ${field.propertyName} >= 0">
            <#else >
        <if test="${field.propertyName} != null ">
            </#if>
            <#if field.customMap['customFieldInfo']['queryMethod'] == "EQ" >
	         and t.${field.name} = <#noparse>#{</#noparse>${field.propertyName}<#noparse>}</#noparse>
            <#elseif field.customMap['customFieldInfo']['queryMethod'] == "NE">
	         and t.${field.name} != <#noparse>#{</#noparse>${field.propertyName}<#noparse>}</#noparse>
            <#elseif field.customMap['customFieldInfo']['queryMethod'] == "GT">
	         and t.${field.name} &gt; <#noparse>#{</#noparse>${field.propertyName}<#noparse>}</#noparse>
            <#elseif field.customMap['customFieldInfo']['queryMethod'] == "GTE">
	         and t.${field.name} &gt;= <#noparse>#{</#noparse>${field.propertyName}<#noparse>}</#noparse>
            <#elseif field.customMap['customFieldInfo']['queryMethod'] == "LT">
	         and t.${field.name} &lt; <#noparse>#{</#noparse>${field.propertyName}<#noparse>}</#noparse>
            <#elseif field.customMap['customFieldInfo']['queryMethod'] == "LTe">
	         and t.${field.name} &lt;= <#noparse>#{</#noparse>${field.propertyName}<#noparse>}</#noparse>
            <#elseif field.customMap['customFieldInfo']['queryMethod'] == "LIKE">
	         and t.${field.name} like concat('%',<#noparse>#{</#noparse>${field.propertyName}<#noparse>}</#noparse>,'%')
            <#elseif field.customMap['customFieldInfo']['queryMethod'] == "BETWEEN">
	         and t.${field.name} = <#noparse>#{</#noparse>${field.propertyName}<#noparse>}</#noparse>
            <#else>
             and t.${field.name} = <#noparse>#{</#noparse>${field.propertyName}<#noparse>}</#noparse>
            </#if>
        </if>

          <#if field.customMap['customFieldInfo']['queryMethod'] == "BETWEEN" >
        <if test="${field.propertyName}Start != null">
            and t.${field.name} &gt;= <#noparse>#{</#noparse>${field.propertyName}Start<#noparse>}</#noparse>
        </if>

        <if test="${field.propertyName}End != null">
            and t.${field.name} &lt;= <#noparse>#{</#noparse>${field.propertyName}End<#noparse>}</#noparse>
        </if>
           </#if>
        </#list>
    </select>
</mapper>
