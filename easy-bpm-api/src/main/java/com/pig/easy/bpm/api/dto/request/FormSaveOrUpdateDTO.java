package com.pig.easy.bpm.api.dto.request;

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
 * @since 2021-04-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FormSaveOrUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 表单编号
     */
    private Long  formId;

    /**
     * 表单KEY
     */
    private String  formKey;

    /**
     * 表单名称
     */
    private String  formName;

    /**
     * 租户编号
     */
    private String  tenantId;

    /**
     * 表单类型 1 PC 表单 2 移动表单
     */
    private Integer  formType;

    /**
     * 排序
     */
    private Integer  sort;

    /**
     * 表单json数据
     */
    private String  formData;

    /**
     * 备注
     */
    private String  remarks;

    /**
     * 状态 1 有效 0 失效
     */
    private Integer  validState;

    /**
     * 创建时间
     */
    private LocalDateTime  createTime;

    /**
     * 更新时间
     */
    private LocalDateTime  updateTime;

    /**
     * 操作人工号
     */
    private Long  operatorId;

    /**
     * 操作人姓名
     */
    private String  operatorName;



}
