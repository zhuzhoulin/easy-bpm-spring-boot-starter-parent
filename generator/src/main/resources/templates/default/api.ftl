import request from '@/utils/request';

export function get${entity}Page(query) {
    return request({
        url: '/api/${moduleName}/${entity}/listPage',
        method: 'post',
        data: query
    });
}

export function save${entity}(form) {
    return request({
        url: '/api/${moduleName}/${entity}/save',
        method: 'post',
        data: form
    });
}

export function delete${entity}(id) {
    return request({
        url: '/api/${moduleName}/${entity}/delete',
        method: 'post',
        data: { 'id': id }
    });
}

export function get${entity}ById(id) {
    return request({
        url: '/api/${moduleName}/${entity}/getById',
        method: 'post',
        data: { 'id': id }
    });
}