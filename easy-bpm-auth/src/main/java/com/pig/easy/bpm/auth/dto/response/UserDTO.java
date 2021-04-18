package com.pig.easy.bpm.auth.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String  id;

    /**
     * 用户编号
     */
    private Long  userId;

    /**
     * 用户账户
     */
    private String  userName;

    /**
     * 用户名称
     */
    private String  realName;

    /**
     * 邮箱
     */
    private String  email;

    /**
     * 联系方式
     */
    private String  phone;

    /**
     * 岗位编码
     */
    private String  positionCode;

    /**
     * 岗位名称
     */
    private String  positionName;

    /**
     * 性别，0表示未知，1表示男，2表示女
     */
    private Integer  gender;

    /**
     * 头像图片链接
     */
    private String  avatar;

    /**
     * 生日
     */
    private LocalDateTime  birthDate;

    /**
     * 公司编号
     */
    private Long  companyId;

    /**
     * 部门编号
     */
    private Long  deptId;

    /**
     * 当前用户活动租户编号
     */
    private String  tenantId;

    /**
     * 密码
     */
    private String  password;

    /**
     * 入职时间
     */
    private LocalDateTime  entryTime;

    /**
     * 离职时间
     */
    private LocalDateTime  leaveTime;

    /**
     * 雇佣状态 1 在职 2 离职
     */
    private Integer  hireStatus;

    /**
     * 来源： system 系统，people：人工 ldap：ldap
     */
    private String  source;

    /**
     * 是否锁定 1 锁定 0 未锁定
     */
    private Integer  lockStatus;

    /**
     * 有效状态；0表示无效，1表示有效
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

    private List<ListDTO> companyList;

    private List<ListDTO> deptList;

    private List<ListDTO> roleList;

    private String accessToken;

    private String system;

    private String platform;

    private String companyCode;

    private String companyName;

    private String deptName;

    private String deptCode;



}
