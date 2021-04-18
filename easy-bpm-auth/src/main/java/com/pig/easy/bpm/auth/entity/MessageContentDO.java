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
 * 通知内容表
 * </p>
 *
 * @author pig
 * @since 2021-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bpm_message_content")
public class MessageContentDO extends Model<MessageContentDO> {

    private static final long serialVersionUID = 1L;

    /**
     * 通知编号
     */
    @TableId(value = "`content_id`", type = IdType.AUTO)
    private Long contentId;

    /**
     * 内容编码
     */
    @TableField("`content_code`")
    private String contentCode;

    /**
     * 租户编号
     */
    @TableField("`tenant_id`")
    private String tenantId;

    /**
     * 通知标题
     */
    @TableField("`message_title`")
    private String messageTitle;

    /**
     * 通知内容
     */
    @TableField("`message_content`")
    private String messageContent;

    /**
     * 通知类型编号 ’HTML‘:网页,'TEXT'：文本
     */
    @TableField("`message_type_code`")
    private String messageTypeCode;

    /**
     * 通知系统
     */
    @TableField("`message_system_code`")
    private String messageSystemCode;

    /**
     * 通知平台 
     */
    @TableField("`message_platform`")
    private String messagePlatform;

    /**
     * 流程编号
     */
    @TableField("`process_id`")
    private Integer processId;

    /**
     * 是否为默认版本 0 否，1 是
     */
    @TableField("`default_falg`")
    private Integer defaultFalg;

    /**
     * 触发事件编码
     */
    @TableField("`event_codes`")
    private String eventCodes;

    /**
     * 触发事件名称
     */
    @TableField("`event_names`")
    private String eventNames;

    /**
     * 排序
     */
    @TableField("`order`")
    private Integer order;

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
    private Integer operatorId;

    /**
     * 操作人工姓名
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
        return this.contentId;
    }

}
