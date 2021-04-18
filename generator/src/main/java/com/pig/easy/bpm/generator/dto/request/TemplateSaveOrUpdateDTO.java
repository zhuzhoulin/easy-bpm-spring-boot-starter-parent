package com.pig.easy.bpm.generator.dto.request;

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
    * 模板表
    * </p>
 *
 * @author pig
 * @since 2021-03-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TemplateSaveOrUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 模板编号
     */
    private Long  templateId;

    /**
     * 模板名称
     */
    private String  templateName;

    /**
     * 模板内容
     */
    private String  templateContent;

    /**
     * 模板类型 1 后端 0 前端
     */
    private Integer  templateType;

    /**
     * 自定义输入文件名称
     */
    private String  outputFileName;

    /**
     * 模板路径
     */
    private String  templatePath;

    /**
     * 模板组编号
     */
    private Long  templateGroupId;

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
