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
 * 字典详细表
 * </p>
 *
 * @author pig
 * @since 2021-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bpm_dict_item")
public class DictItemDO extends Model<DictItemDO> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "`item_id`", type = IdType.AUTO)
    private Long itemId;

    /**
     * 字典编号
     */
    @TableField("`dict_id`")
    private Long dictId;

    /**
     * 字典项值
     */
    @TableField("`item_value`")
    private String itemValue;

    /**
     * 字典项文本
     */
    @TableField("`item_text`")
    private String itemText;

    /**
     * 父级编号
     */
    @TableField("`parent_id`")
    private Long parentId;

    /**
     * 租户编号
     */
    @TableField("`tenant_id`")
    private String tenantId;

    /**
     * 排序
     */
    @TableField("`sort`")
    private Integer sort;

    /**
     * 备注
     */
    @TableField("`remark`")
    private String remark;

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
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    @Override
    protected Serializable pkVal() {
        return this.itemId;
    }

}
