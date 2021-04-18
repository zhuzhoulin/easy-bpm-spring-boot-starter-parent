package com.pig.easy.bpm.api.dto.response;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.Version;
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
    * 
    * </p>
 *
 * @author pig
 * @since 2021-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ProcessRuleExportDTO implements Serializable {

    @ExcelIgnore
    private static final long serialVersionUID = 1L;

    /**
     * 规则 guid
     */
    @ExcelProperty( value = "规则 guid")
    private String  ruleId;

    /**
     * 规则名称
     */
    @ExcelProperty( value = "规则名称")
    private String  ruleName;

    /**
     * 规则编号
     */
    @ExcelProperty( value = "规则编号")
    private String  ruleCode;

    /**
     * 规则类型 1 人员规则
     */
    @ExcelProperty( value = "规则类型 1 人员规则")
    private Integer  ruleType;

    @ExcelProperty( value = "租户编号")
    private String  tenantId;

    /**
     * 流程编号
     */
    @ExcelProperty( value = "流程编号")
    private Integer  processId;

    /**
     * 角色组编号
     */
    @ExcelProperty( value = "角色组编号")
    private Integer  roleGroupId;

    /**
     * 角色编号
     */
    @ExcelProperty( value = "角色编号")
    private Integer  roleId;

    /**
     * 用户编号
     */
    @ExcelProperty( value = "用户编号")
    private Integer  userId;

    /**
     * 规则表达式
     */
    @ExcelProperty( value = "规则表达式")
    private String  ruleExpression;

    /**
     * 规则动作
     */
    @ExcelProperty( value = "规则动作")
    private String  ruleAction;

    /**
     * 规则涉及人员（新增/减少）多个人以‘,’区分
     */
    @ExcelProperty( value = "人员工号")
    private String  ruleUsers;

    @ExcelProperty( value = "备注")
    private String  remarks;

    @ExcelProperty( value = "状态")
    private Integer  validState;

    @ExcelProperty( value = "操作工号")
    private Integer  operatorId;

    @ExcelProperty( value = "操作姓名")
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
