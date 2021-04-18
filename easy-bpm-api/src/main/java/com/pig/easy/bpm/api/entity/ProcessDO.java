package com.pig.easy.bpm.api.entity;

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
 * 流程表
 * </p>
 *
 * @author pig
 * @since 2021-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bpm_process")
public class ProcessDO extends Model<ProcessDO> {

    private static final long serialVersionUID = 1L;

    /**
     * 流程编码
     */
    @TableId(value = "`process_id`", type = IdType.AUTO)
    private Long processId;

    /**
     * 流程key
     */
    @TableField("`process_key`")
    private String processKey;

    /**
     * 流程名称
     */
    @TableField("`process_name`")
    private String processName;

    /**
     * 流程归属菜单编号
     */
    @TableField("`process_menu_id`")
    private Long processMenuId;

    /**
     * 流程简称
     */
    @TableField("`process_abbr`")
    private String processAbbr;

    /**
     * 流程分类 1 正常
     */
    @TableField("`process_type`")
    private Integer processType;

    /**
     * 所属公司
     */
    @TableField("`company_id`")
    private Long companyId;

    /**
     * 公司编码
     */
    @TableField("`company_code`")
    private String companyCode;

    /**
     * 排序
     */
    @TableField("`sort`")
    private Integer sort;

    /**
     * 租户编号
     */
    @TableField("`tenant_id`")
    private String tenantId;

    /**
     * 默认流程详细编号
     */
    @TableField("`process_detail_id`")
    private Long processDetailId;

    /**
     * 流程状态: 1 未发布 2 已发布
     */
    @TableField("`process_status`")
    private Integer processStatus;

    /**
     * 流程备注
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
        return this.processId;
    }

}
