package com.pig.easy.bpm.auth.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author pig
 * @since 2021-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("bpm_menu")
public class MenuDO extends Model<MenuDO> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "`menu_id`", type = IdType.AUTO)
    private Long menuId;

    /**
     * 资源编码
     */
    @TableField("`menu_code`")
    private String menuCode;

    /**
     * 菜单名称
     */
    @TableField("`menu_name`")
    private String menuName;

    /**
     * 菜单编码
     */
    @TableField("`menu_icon`")
    private String menuIcon;

    /**
     * 菜单URL
     */
    @TableField("`menu_url`")
    private String menuUrl;

    /**
     * 菜单类型 menu: 菜单
     */
    @TableField("`menu_type`")
    private String menuType;

    /**
     * 元数据
     */
    @TableField("`meta`")
    private String meta;

    /**
     * 是否总是显示 1 是 0 否
     */
    @TableField("`always_show`")
    private Integer alwaysShow;

    /**
     * 上级编号 0为 1级
     */
    @TableField("`parent_id`")
    private Long parentId;

    /**
     * 租户编号
     */
    @TableField("`tenant_id`")
    private String tenantId;

    /**
     * 组件地址
     */
    @TableField("`component`")
    private String component;

    /**
     * 是否隐藏 0 不隐藏 1 隐藏
     */
    @TableField("`hidden`")
    private Integer hidden;

    /**
     * 排序
     */
    @TableField("`sort`")
    private Integer sort;

    /**
     * 重定向值
     */
    @TableField("`redirect`")
    private String redirect;

    /**
     * 备注
     */
    @TableField("`remarks`")
    private String remarks;

    /**
     * 状态 0 失效 1 有效
     */
    @TableField("`valid_state`")
    private Integer validState;

    /**
     * 操作者工号
     */
    @TableField("`operator_id`")
    private Long operatorId;

    /**
     * 操作人名称
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
        return this.menuId;
    }

}
