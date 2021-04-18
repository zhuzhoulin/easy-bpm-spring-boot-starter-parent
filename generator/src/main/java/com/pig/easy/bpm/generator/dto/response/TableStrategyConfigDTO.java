package com.pig.easy.bpm.generator.dto.response;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.math.*;
/**
 * <p>
    * 配置策略表
    * </p>
 *
 * @author pig
 * @since 2021-03-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TableStrategyConfigDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 配置编号
     */
    private Long  configId;

    /**
     * 数据源ID
     */
    private Long  dbId;

    /**
     * 表名称
     */
    private String  tableName;

    /**
     * 作者
     */
    private String  author;

    /**
     * 项目路径
     */
    private String  contextPath;

    /**
     * 模块名称
     */
    private String  moduleName;

    /**
     * 公共父级包名
     */
    private String  parentPackage;

    /**
     * 表名前缀
     */
    private String  tablePrefix;

    /**
     * 字段前缀
     */
    private String  fieldPrefix;

    /**
     * 后端路径
     */
    private String  projectPath;

    /**
     * 前端路径
     */
    private String  vuePath;

    /**
     * 是否覆盖 1 是 0 否
     */
    private Integer  override;

    /**
     * 父级菜单编号
     */
    private Long  parentMenuId;

    /**
     * controller 命名规则
     */
    private String  controllerName;

    /**
     * impl命名规则
     */
    private String  serviceImplName;

    /**
     * service 命名规则
     */
    private String  serviceName;

    /**
     * xml 命名规则
     */
    private String  xmlName;

    /**
     * mapper 命名规则
     */
    private String  mapperName;

    /**
     * 实体类 命名规则
     */
    private String  entityName;

    /**
     * 自定义实体父类
     */
    private String  superEntityClass;

    /**
     * 自定义 mapper 父类
     */
    private String  superMapperClass;

    /**
     * service 父类
     */
    private String  superServiceClass;

    /**
     * service 实现类父类
     */
    private String  superServiceImplClass;

    /**
     * controller 父类
     */
    private String  superControllerClass;

    /**
     * 租户编号
     */
    private String  tenantId;

    /**
     * 备注
     */
    private String  remarks;

    /**
     * 状态 1 有效 0 失效
     */
    private Integer  validState;

    /**
     * 操作人工号
     */
    private Integer  operatorId;

    /**
     * 操作人姓名
     */
    private String  operatorName;

    /**
     * 创建时间
     */
    private LocalDateTime  createTime;

    /**
     * 更新时间
     */
    private LocalDateTime  updateTime;



}
