package com.pig.easy.bpm.auth.dto.response;

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
 * @since 2021-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserExportDTO implements Serializable {

    @ExcelIgnore
    private static final long serialVersionUID = 1L;


    /**
     * 用户编号
     */
    @ExcelProperty( value = "用户编号")
    private Long  userId;

    /**
     * 用户账户
     */
    @ExcelProperty( value = "用户账户")
    private String  userName;

    /**
     * 用户名称
     */
    @ExcelProperty( value = "用户名称")
    private String  realName;

    /**
     * 邮箱
     */
    @ExcelProperty( value = "邮箱")
    private String  email;

    /**
     * 联系方式
     */
    @ExcelProperty( value = "联系方式")
    private String  phone;

    /**
     * 岗位编码
     */
    @ExcelProperty( value = "岗位编码")
    private String  positionCode;

    /**
     * 岗位名称
     */
    @ExcelProperty( value = "岗位名称")
    private String  positionName;

    /**
     * 性别，0表示未知，1表示男，2表示女
     */
    @ExcelProperty( value = "性别")
    private Integer  gender;

    /**
     * 头像图片链接
     */
    @ExcelProperty( value = "头像图片链接")
    private String  avatar;

    /**
     * 生日
     */
    @ExcelProperty( value = "生日")
    private LocalDateTime  birthDate;

    /**
     * 公司编号
     */
    @ExcelProperty( value = "公司编号")
    private Long  companyId;

    /**
     * 部门编号
     */
    @ExcelProperty( value = "部门编号")
    private Long  deptId;

    /**
     * 当前用户活动租户编号
     */
    @ExcelProperty( value = "当前用户活动租户编号")
    private String  tenantId;


    /**
     * 入职时间
     */
    @ExcelProperty( value = "入职时间")
    private LocalDateTime  entryTime;

    /**
     * 离职时间
     */
    @ExcelProperty( value = "离职时间")
    private LocalDateTime  leaveTime;

    /**
     * 雇佣状态 1 在职 2 离职
     */
    @ExcelProperty( value = "雇佣状态")
    private Integer  hireStatus;

    /**
     * 来源： system 系统，people：人工 ldap：ldap
     */
    @ExcelProperty( value = "来源")
    private String  source;

    /**
     * 是否锁定 1 锁定 0 未锁定
     */
    @ExcelProperty( value = "是否锁定")
    private Integer  lockStatus;

    /**
     * 有效状态；0表示无效，1表示有效
     */
    @ExcelProperty( value = "有效状态")
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
