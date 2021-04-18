package ${package.Mapper};

import ${package.Entity}.${entity}DO;
import ${superMapperClassPackage};
import org.apache.ibatis.annotations.Mapper;
import ${cfg.dtoRequest}.*;
import ${cfg.dtoResponse}.*;

import java.util.List;
/**
 * <p>
 * ${table.comment!} Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}DO>
<#else>
@Mapper
public interface ${table.mapperName} extends ${superMapperClass}<${entity}DO> {

    List<${entity}DTO> getListByCondition(${entity}QueryDTO param);

}
</#if>
