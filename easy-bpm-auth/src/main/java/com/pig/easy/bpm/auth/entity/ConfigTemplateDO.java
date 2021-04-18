package com.pig.easy.bpm.auth.entity;

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

/**
 * <p>
 * 
 * </p>
 *
 * @author pig
 * @since 2021-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bpm_config_template")
public class ConfigTemplateDO extends Model<ConfigTemplateDO> {

    private static final long serialVersionUID = 1L;

    /**
     * 模板编号
     */
    @TableId(value = "`template_id`", type = IdType.AUTO)
    private Long templateId;

    /**
     * 模板编号
     */
    @TableField("`template_code`")
    private String templateCode;

    /**
     * 模板名称
     */
    @TableField("`template_name`")
    private String templateName;

    /**
     * 模板key
     */
    @TableField("`template_key`")
    private String templateKey;

    /**
     * 模板值
     */
    @TableField("`template_value`")
    private String templateValue;

    /**
     * 模板类型
     */
    @TableField("`template_type`")
    private String templateType;

    /**
     * 模板字段状态 1 未发布 2 已发布
     */
    @TableField("`template_status`")
    private Integer templateStatus;

    /**
     * 备注
     */
    @TableField("`remarks`")
    private String remarks;

    /**
     * 状态 1 有效 0 失效
     */
    @TableField("`valid_state`")
    private Integer validState;

    /**
     * 操作人工号
     */
    @TableField("`operator_id`")
    private Long operatorId;

    /**
     * 操作人姓名
     */
    @TableField("`operator_name`")
    private String operatorName;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.templateId;
    }

}
