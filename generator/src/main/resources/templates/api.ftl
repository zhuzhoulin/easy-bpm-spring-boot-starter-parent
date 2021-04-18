import request from '@/utils/request'

export function get${entity}ListPage(query) {
  return request({
    url: '<#if cfg.contentPath??>${cfg.contentPath}</#if><#if package.ModuleName??>/${package.ModuleName}</#if>/${table.entityPath}/getListPage',
    method: 'post',
    data: query
  })
}

export function get${entity}List(query) {
  return request({
    url: '<#if cfg.contentPath??>${cfg.contentPath}</#if><#if package.ModuleName??>/${package.ModuleName}</#if>/${table.entityPath}/getList',
    method: 'post',
    data: query
  })
}

export function save${entity}(form) {
  return request({
    url: '<#if cfg.contentPath??>${cfg.contentPath}</#if><#if package.ModuleName??>/${package.ModuleName}</#if>/${table.entityPath}/insert',
    method: 'post',
    data: form
  })
}

export function update${entity}(form) {
  return request({
    url: '<#if cfg.contentPath?? >${cfg.contentPath}</#if><#if package.ModuleName??>/${package.ModuleName}</#if>/${table.entityPath}/update',
    method: 'post',
    data: form
  })
}

export function deleteById(id) {
  return request({
    url: '<#if cfg.contentPath??>${cfg.contentPath}</#if><#if package.ModuleName??>/${package.ModuleName}</#if>/${table.entityPath}/deleteById',
    method: 'post',
        <#list table.fields as field>
            <#if field.keyFlag>
    data: { '${field.propertyName}': id }
            </#if>
        </#list>
  })
}

export function getById(id) {
  return request({
    url: '<#if cfg.contentPath??>${cfg.contentPath}</#if><#if package.ModuleName??>/${package.ModuleName}</#if>/${table.entityPath}/getById',
    method: 'post',
    <#list table.fields as field>
        <#if field.keyFlag>
    data: { '${field.propertyName}': id }
        </#if>
    </#list>
  })
}
