package com.pig.easy.bpm.web.vo.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 通知白名单
 * </p>
 *
 * @author pig
 * @since 2021-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MessageWhiteListQueryVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String  whiteId;


    /**
     * 租户编号
     */
    private String  tenantId;


    /**
     * 作用于流程编号， 0为所有流程
     */
    private Integer  processId;


    /**
     * 白名单类型 ALL：所有，APPROVE：审批，OVERTIME：超时
     */
    private String  whiteTypeCode;


    /**
     * 白名单
     */
    private String  whiteList;


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
