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
    * 字段表
    * </p>
 *
 * @author pig
 * @since 2021-03-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ColumnDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 列序号
     */
    private Long  columnId;

    /**
     * 数据源ID
     */
    private Long  dbId;

    /**
     * 表名
     */
    private String  tableName;

    /**
     * 字段名
     */
    private String  columnName;

    /**
     * 编码
     */
    private String  collation;

    /**
     * 额外参数
     */
    private String  extra;

    /**
     * 权限
     */
    private String  privileges;

    /**
     * 默认值
     */
    private String  defaultValue;

    /**
     * 字段描述
     */
    private String  columnComment;

    /**
     * 标签名称
     */
    private String  formLabel;

    /**
     * Java字段类型
     */
    private String  propertyType;

    /**
     * 字段属性名
     */
    private String  propertyName;

    /**
     * 字段类型
     */
    private String  columnType;

    /**
     * 主键 1 是 0 否
     */
    private Integer  keyFlag;

    /**
     * 自增列 1 是 0 否
     */
    private Integer  keyIdentityFlag;

    /**
     * 必填 1 是 0 否
     */
    private Integer  notNull;

    /**
     * 查询条件 1 是 0 否
     */
    private Integer  queryFlag;

    /**
     * 查询方式
     */
    private String  queryMethod;

    /**
     * 插入是否显示 1 是 0 否
     */
    private Integer  insertFlag;

    /**
     * 更新是否显示 1 是 0 否
     */
    private Integer  updateFlag;

    /**
     * 查询是否显示 1 是 0 否
     */
    private Integer  listFlag;

    /**
     * 下载是否显示 1 是 0 否
     */
    private Integer  downloadFlag;

    /**
     * 校验类型
     */
    private String  validateType;

    /**
     * 校验 最大长度
     */
    private String  validateMaxLength;

    /**
     * 校验 最小长度
     */
    private String  validateMinLength;

    /**
     * 校验 最大值
     */
    private String  validateMaxValue;

    /**
     * 校验 最小值
     */
    private String  validateMinValue;

    /**
     * 表单类型
     */
    private String  formType;

    /**
     * 关联字典编码
     */
    private String  dictCode;

    /**
     * 关联字典名称
     */
    private String  dictName;

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
