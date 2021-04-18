package com.pig.easy.bpm.web.vo.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 通知内容表
 * </p>
 *
 * @author pig
 * @since 2021-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MessageContentQueryVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 通知编号
     */
    private Long  contentId;


    /**
     * 内容编码
     */
    private String  contentCode;


    /**
     * 租户编号
     */
    private String  tenantId;


    /**
     * 通知标题
     */
    private String  messageTitle;


    /**
     * 通知内容
     */
    private String  messageContent;


    /**
     * 通知类型编号 ’HTML‘:网页,'TEXT'：文本
     */
    private String  messageTypeCode;


    /**
     * 通知系统
     */
    private String  messageSystemCode;


    /**
     * 通知平台 
     */
    private String  messagePlatform;


    /**
     * 流程编号
     */
    private Integer  processId;


    /**
     * 是否为默认版本 0 否，1 是
     */
    private Integer  defaultFalg;


    /**
     * 触发事件编码
     */
    private String  eventCodes;


    /**
     * 触发事件名称
     */
    private String  eventNames;


    /**
     * 排序
     */
    private Integer  order;


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
    private Integer  operatorId;


    /**
     * 操作人工姓名
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
