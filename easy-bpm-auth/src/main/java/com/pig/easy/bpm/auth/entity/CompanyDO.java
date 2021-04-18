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
 * 公司表
 * </p>
 *
 * @author pig
 * @since 2021-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bpm_company")
public class CompanyDO extends Model<CompanyDO> {

    private static final long serialVersionUID = 1L;

    /**
     * 公司编码
     */
    @TableId(value = "`company_id`", type = IdType.AUTO)
    private Long companyId;

    /**
     * 部门编码
     */
    @TableField("`company_code`")
    private String companyCode;

    /**
     * 上级公司编号
     */
    @TableField("`company_parent_id`")
    private Long companyParentId;

    /**
     * 上级公司编码
     */
    @TableField("`company_parent_code`")
    private String companyParentCode;

    /**
     * 公司名称
     */
    @TableField("`company_name`")
    private String companyName;

    /**
     * 公司简称
     */
    @TableField("`company_abbr`")
    private String companyAbbr;

    /**
     * 公司承继
     */
    @TableField("`company_level`")
    private Integer companyLevel;

    /**
     * 排序
     */
    @TableField("`company_order`")
    private Integer companyOrder;

    /**
     * 公司展示图标
     */
    @TableField("`company_icon`")
    private String companyIcon;

    /**
     * 公司展示url
     */
    @TableField("`company_url`")
    private String companyUrl;

    /**
     * 租户编号
     */
    @TableField("`tenant_id`")
    private String tenantId;

    /**
     * 1 正常开业  2 拟筹 2 已关停
     */
    @TableField("`company_status`")
    private Integer companyStatus;

    /**
     * 有效状态；0表示无效，1表示有效
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
        return this.companyId;
    }

}
