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

import java.io.Serializable;
import java.util.Date;
import java.math.*;
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
public class MessageWhiteListDTO implements Serializable {

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



}
