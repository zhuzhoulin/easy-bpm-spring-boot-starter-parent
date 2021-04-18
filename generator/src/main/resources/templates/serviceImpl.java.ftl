package ${package.ServiceImpl};

import ${package.Entity}.${entity}DO;
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.generator.BaseServiceImpl;
import com.pig.easy.bpm.common.utils.BeanUtils;
import com.pig.easy.bpm.common.utils.CommonUtils;
import com.pig.easy.bpm.common.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import ${cfg.dtoRequest}.*;
import ${cfg.dtoResponse}.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}DO>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}DO> implements ${table.serviceName} {

    @Autowired
    ${table.mapperName} mapper;

    @Override
    public Result<PageInfo<${entity}DTO>> getListPageByCondition(${entity}QueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        int pageIndex = CommonUtils.evalInt(param.getPageIndex(), DEFAULT_PAGE_INDEX);
        int pageSize = CommonUtils.evalInt(param.getPageSize(), DEFAULT_PAGE_SIZE);

        PageHelper.startPage(pageIndex, pageSize);
        param.setValidState(VALID_STATE);
        List<${entity}DTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        PageInfo<${entity}DTO> pageInfo = new PageInfo<>(list);
        return Result.responseSuccess(pageInfo);
    }

    @Override
    public Result<List<${entity}DTO>> getListByCondition(${entity}QueryDTO param) {

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        param.setValidState(VALID_STATE);
        List<${entity}DTO> list = mapper.getListByCondition(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        return Result.responseSuccess(list);
    }


    @Override
    public Result<Integer> insert${entity}(${entity}SaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }
        ${entity}DO temp = BeanUtils.switchToDO(param, ${entity}DO.class);
        Integer num = mapper.insert(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer>update${entity}(${entity}SaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        ${entity}DO temp = BeanUtils.switchToDO(param, ${entity}DO.class);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    public Result<Integer>delete${entity}(${entity}SaveOrUpdateDTO param){

        if (param == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        ${entity}DO temp = BeanUtils.switchToDO(param, ${entity}DO.class);
        temp.setValidState(INVALID_STATE);
        Integer num = mapper.updateById(temp);
        return Result.responseSuccess(num);
    }

    @Override
    <#list table.fields as field>
        <#if field.keyFlag>
    public Result<${entity}DTO> get${entity}ById(${field.propertyType} ${field.propertyName}){

        if (${field.propertyName} == null) {
            return Result.responseError(EntityError.ILLEGAL_ARGUMENT_ERROR);
        }

        ${entity}DO query = new ${entity}DO();
        query.set${field.capitalName}(${field.propertyName});
        query = mapper.selectOne(new QueryWrapper<>(query));

        if (query == null) {
            return Result.responseError(EntityError.DATA_NOT_FOUND_ERROR);
        }
        ${entity}DTO result = BeanUtils.switchToDTO(query, ${entity}DTO.class);
        return Result.responseSuccess(result);
    }
        </#if>
    </#list>
}
</#if>
