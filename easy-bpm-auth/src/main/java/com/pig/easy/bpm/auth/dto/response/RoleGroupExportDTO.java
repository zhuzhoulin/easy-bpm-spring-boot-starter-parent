package com.pig.easy.bpm.auth.dto.response;

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
import com.alibaba.excel.annotation.*;
import java.io.Serializable;
import java.util.Date;
import java.math.*;
/**
 * <p>
    * 角色组
    * </p>
 *
 * @author pig
 * @since 2021-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RoleGroupExportDTO implements Serializable {

    @ExcelIgnore
    private static final long serialVersionUID = 1L;

    @ExcelProperty( value = "")
    private Long  roleGroupId;

    /**
     * 角色组编码
     */
    @ExcelProperty( value = "角色组编码")
    private String  roleGroupCode;

    /**
     * 角色组名称
     */
    @ExcelProperty( value = "角色组名称")
    private String  roleGroupName;

    /**
     * 角色组简称
     */
    @ExcelProperty( value = "角色组简称")
    private String  roleGroupAbbr;

    /**
     * 所属条线，如 level 2 会根据 条线 找 level 1
     */
    @ExcelProperty( value = "所属条线")
    private String  businessLine;

    /**
     * 角色组等级
     */
    @ExcelProperty( value = "角色组等级")
    private Integer  roleGroupLevel;

    /**
     * 角色组类别
     */
    @ExcelProperty( value = "角色组类别")
    private Integer  roleGroupType;

    /**
     * 租户编号
     */
    @ExcelProperty( value = "租户编号")
    private String  tenantId;

    /**
     * 备注
     */
    @ExcelProperty( value = "备注")
    private String  remarks;

    /**
     * 状态 1 有效 0 失效
     */
    @ExcelProperty( value = "状态 ")
    private Integer  validState;

    /**
     * 操作人工号
     */
    @ExcelProperty( value = "操作人工号")
    private Long  operatorId;

    /**
     * 操作人姓名
     */
    @ExcelProperty( value = "操作人姓名")
    private String  operatorName;

    /**
     * 创建时间
     */
    @ExcelProperty( value = "创建时间")
    private LocalDateTime  createTime;

    /**
     * 更新时间
     */
    @ExcelProperty( value = "更新时间")
    private LocalDateTime  updateTime;
}
