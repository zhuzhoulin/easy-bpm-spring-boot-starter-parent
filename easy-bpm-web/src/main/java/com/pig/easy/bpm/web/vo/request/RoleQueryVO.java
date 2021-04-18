package com.pig.easy.bpm.web.vo.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

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
public class RoleQueryVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色编号
     */
    private Long  roleId;


    /**
     * 角色编码
     */
    private String  roleCode;


    /**
     * 角色名称
     */
    private String  roleName;


    /**
     * 角色级别
     */
    private Integer  roleLevel;


    /**
     * 角色简称
     */
    private String  roleAbbr;


    /**
     * 角色别名
     */
    private String  roleAliasName;


    /**
     * 租户编号
     */
    private String  tenantId;


    /**
     * 归属公司编号
     */
    private Long  companyId;


    /**
     * 归属部门编号
     */
    private Long  deptId;


    /**
     * 备注
     */
    private String  remarks;


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
     * 更新时间
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
