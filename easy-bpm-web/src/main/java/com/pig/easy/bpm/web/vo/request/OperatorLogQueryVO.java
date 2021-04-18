package com.pig.easy.bpm.web.vo.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author pig
 * @since 2021-03-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OperatorLogQueryVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日志编号
     */
    private Long  logId;


    /**
     * 操作用户工号
     */
    private Long  userId;


    /**
     * 操作用户
     */
    private String  nickname;


    /**
     * 追踪编号
     */
    private String  traceId;


    /**
     * 业务类型
     */
    private String  businessType;


    /**
     * 模块名称
     */
    private String  moduleName;


    /**
     * 请求类名称
     */
    private String  className;


    /**
     * 请求方法名称
     */
    private String  methodName;


    /**
     * 请求方式(POST/GET)
     */
    private String  requestMethod;


    /**
     * 操作地址
     */
    private String  ipAddress;


    /**
     * 详细地址
     */
    private String  detailAddress;


    /**
     * 浏览器
     */
    private String  browser;


    /**
     * 操作系统
     */
    private String  os;


    /**
     * 操作类别0其它 1后台用户 2手机端用户
     */
    private Integer  operatorType;


    /**
     * 请求参数
     */
    private String  param;


    /**
     * 状态码
     */
    private Integer  code;


    /**
     * 返回描述
     */
    private String  message;


    /**
     * 返回值
     */
    private String  data;


    /**
     * 备注
     */
    private String  remarks;


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



    /**
     *  当前页码
     */
    private Integer pageIndex;

    /**
     * 每页展示数量
     */
    private Integer pageSize;

}
