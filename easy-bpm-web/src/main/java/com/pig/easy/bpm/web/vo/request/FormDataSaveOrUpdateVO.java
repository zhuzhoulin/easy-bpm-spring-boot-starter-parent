package com.pig.easy.bpm.web.vo.request;

import java.math.BigDecimal;
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
    * 
    * </p>
 *
 * @author pig
 * @since 2021-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FormDataSaveOrUpdateVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据ID
     */
    private Long  dataId;

    /**
     * 数据KEY
     */
    private String  dataKey;

    /**
     * 数据中文名称
     */
    private String  dataName;

    /**
     * 字符串数据值
     */
    private String  stringValue;

    /**
     * boolean 值
     */
    private Boolean  booleanValue;

    /**
     * 数值值
     */
    private BigDecimal  numberValue;

    /**
     * 日期格式
     */
    private String  numberFormat;

    /**
     * 日期格式
     */
    private LocalDateTime  dateValue;

    /**
     * 日期格式
     */
    private String  datePattern;

    /**
     * select 选中值
     */
    private String  selectValue;

    /**
     * SELECT 下拉选项
     */
    private String  selectItem;

    /**
     * 大字段
     */
    private String  textValue;

    /**
     * 数据类型
     */
    private String  dataType;

    /**
     * 表单编号
     */
    private Long  formId;

    /**
     * 表单KEY
     */
    private String  formKey;

    /**
     * 租户
     */
    private String  tenantId;

    /**
     * 流程编号
     */
    private Long  processId;

    /**
     * 申请编号
     */
    private Long  applyId;

    /**
     * 流程实例编号
     */
    private String  procInstId;

    /**
     * 任务编号
     */
    private Long  taskId;

    /**
     * 有效状态；0表示无效，1表示有效
     */
    private Integer  validState;

    /**
     * create_time
     */
    private LocalDateTime  createTime;

    /**
     * 创建时间
     */
    private LocalDateTime  updateTime;

    /**
     * 操作人工号
     */
    private Long  operatorId;

    /**
     * operator_name
     */
    private String  operatorName;




}
