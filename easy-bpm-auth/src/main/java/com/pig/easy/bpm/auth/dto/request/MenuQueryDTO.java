package com.pig.easy.bpm.auth.dto.request;

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
import com.pig.easy.bpm.common.generator.dto.request.BaseRequestDTO;
import java.io.Serializable;
import java.util.Date;
import java.math.*;
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

public class MenuQueryDTO extends BaseRequestDTO implements Serializable {


    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long  menuId;


    /**
     * 资源编码
     */
    private String  menuCode;


    /**
     * 菜单名称
     */
    private String  menuName;


    /**
     * 菜单编码
     */
    private String  menuIcon;


    /**
     * 菜单URL
     */
    private String  menuUrl;


    /**
     * 菜单类型 menu: 菜单
     */
    private String  menuType;


    /**
     * 元数据
     */
    private String  meta;


    /**
     * 是否总是显示 1 是 0 否
     */
    private Integer  alwaysShow;


    /**
     * 上级编号 0为 1级
     */
    private Long  parentId;


    /**
     * 租户编号
     */
    private String  tenantId;


    /**
     * 组件地址
     */
    private String  component;


    /**
     * 是否隐藏 0 不隐藏 1 隐藏
     */
    private Integer  hidden;


    /**
     * 排序
     */
    private Integer  sort;


    /**
     * 重定向值
     */
    private String  redirect;


    /**
     * 备注
     */
    private String  remarks;


    /**
     * 状态 0 失效 1 有效
     */
    private Integer  validState;


    /**
     * 操作者工号
     */
    private Long  operatorId;


    /**
     * 操作人名称
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



    /**
     *  当前页码
     */
    private Integer pageIndex;

    /**
     * 每页展示数量
     */
    private Integer pageSize;

}
