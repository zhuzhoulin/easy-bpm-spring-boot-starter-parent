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
    * 角色表
    * </p>
 *
 * @author pig
 * @since 2021-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RoleExportDTO implements Serializable {

    @ExcelIgnore
    private static final long serialVersionUID = 1L;

    /**
     * 角色编号
     */
    @ExcelProperty( value = "角色编号")
    private Long  roleId;

    /**
     * 角色编码
     */
    @ExcelProperty( value = "角色编码")
    private String  roleCode;

    /**
     * 角色名称
     */
    @ExcelProperty( value = "角色名称")
    private String  roleName;

    /**
     * 角色级别
     */
    @ExcelProperty( value = "角色级别")
    private Integer  roleLevel;

    /**
     * 角色简称
     */
    @ExcelProperty( value = "角色简称")
    private String  roleAbbr;

    /**
     * 角色别名
     */
    @ExcelProperty( value = "角色别名")
    private String  roleAliasName;

    /**
     * 租户编号
     */
    @ExcelProperty( value = "租户编号")
    private String  tenantId;

    /**
     * 归属公司编号
     */
    @ExcelProperty( value = "归属公司编号")
    private Long  companyId;

    /**
     * 归属部门编号
     */
    @ExcelProperty( value = "归属部门编号")
    private Long  deptId;

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
     * 更新时间
     */
    @ExcelProperty( value = "创建时间")
    private LocalDateTime  createTime;

    /**
     * 更新时间
     */
    @ExcelProperty( value = "更新时间")
    private LocalDateTime  updateTime;
}
