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
@TableName("bpm_config")
public class ConfigDO extends Model<ConfigDO> {

    private static final long serialVersionUID = 1L;

    /**
     * 配置编码
     */
    @TableId(value = "`config_id`", type = IdType.AUTO)
    private Long configId;

    /**
     * 模板编号
     */
    @TableField("`config_code`")
    private String configCode;

    /**
     * 模板名称
     */
    @TableField("`config_name`")
    private String configName;

    /**
     * 模板编号
     */
    @TableField("`template_id`")
    private Long templateId;

    /**
     * 模板key
     */
    @TableField("`config_key`")
    private String configKey;

    /**
     * 模板值
     */
    @TableField("`config_value`")
    private String configValue;

    /**
     * 模板类型
     */
    @TableField("`config_type`")
    private String configType;

    /**
     * 租户编号
     */
    @TableField("`tenant_id`")
    private String tenantId;

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
        return this.configId;
    }

}
