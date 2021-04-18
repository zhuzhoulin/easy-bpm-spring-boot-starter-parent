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

import java.io.Serializable;
import java.util.Date;
import java.math.*;
/**
 * <p>
    * 部门表
    * </p>
 *
 * @author pig
 * @since 2021-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DeptDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 部门编号
     */
    private Long  deptId;

    /**
     * 部门编码
     */
    private String  deptCode;

    /**
     * 部门名称
     */
    private String  deptName;

    /**
     * 所属公司编码
     */
    private Long  companyId;

    /**
     * 所属公司编码
     */
    private String  companyCode;

    /**
     * 归属业务条线
     */
    private String  businessLine;

    /**
     * 所属租户
     */
    private String  tenantId;

    /**
     * 上级部门编号
     */
    private Long  deptParentId;

    /**
     * 上级部门编码
     */
    private String  deptParentCode;

    /**
     * 部门层级
     */
    private Integer  deptLevel;

    /**
     * 部门类型
     */
    private String  deptType;

    /**
     * 部门类型编码
     */
    private String  deptTypeCode;

    /**
     * 备注
     */
    private String  remark;

    /**
     * 排序
     */
    private Integer  deptOrder;

    /**
     * 状态 1 有效 0 失效
     */
    private Integer  validState;

    /**
     * 操作人工号
     */
    private Long  operatorId;

    /**
     * 操作人姓名
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



}
