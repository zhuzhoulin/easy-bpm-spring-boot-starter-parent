package com.pig.easy.bpm.api.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("bpm_process_detail")
public class ProcessDetailDO extends Model<ProcessDetailDO> {

    private static final long serialVersionUID = 1L;

    /**
     * 流程详细编号
     */
    @TableId(value = "`process_detail_id`", type = IdType.AUTO)
    private Long processDetailId;

    /**
     * 租户编号
     */
    @TableField("`tenant_id`")
    private String tenantId;

    @TableField("`process_id`")
    private Long processId;

    /**
     * 流程XML格式数据
     */
    @TableField("`process_xml`")
    private String processXml;

    /**
     * 默认版本号
     */
    @TableField("`definition_id`")
    private String definitionId;

    /**
     * 申请标题规则
     */
    @TableField("`apply_title_rule`")
    private String applyTitleRule;

    /**
     * 流程到期时间
     */
    @TableField("`apply_due_date`")
    private LocalDateTime applyDueDate;

    /**
     * 是否自动完成第一个节点任务 1 是 0 否
     */
    @TableField("`auto_complete_first_node`")
    private Integer autoCompleteFirstNode;

    /**
     * 1 未发布 2 已发布 
     */
    @TableField("`publish_status`")
    private Integer publishStatus;

    /**
     * 1 默认版本 2 非默认版本
     */
    @TableField("`main_version`")
    private Integer mainVersion;

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

    @TableField("`operator_id`")
    private Long operatorId;

    /**
     * 操作人姓名
     */
    @TableField("`operator_name`")
    private String operatorName;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.processDetailId;
    }

}
