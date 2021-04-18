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
    * 通知内容表
    * </p>
 *
 * @author pig
 * @since 2021-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MessageContentExportDTO implements Serializable {

    @ExcelIgnore
    private static final long serialVersionUID = 1L;

    /**
     * 通知编号
     */
    @ExcelProperty( value = "通知编号")
    private Long  contentId;

    /**
     * 内容编码
     */
    @ExcelProperty( value = "内容编码")
    private String  contentCode;

    /**
     * 租户编号
     */
    @ExcelProperty( value = "租户编号")
    private String  tenantId;

    /**
     * 通知标题
     */
    @ExcelProperty( value = "通知标题")
    private String  messageTitle;

    /**
     * 通知内容
     */
    @ExcelProperty( value = "通知内容")
    private String  messageContent;

    /**
     * 通知类型编号 ’HTML‘:网页,'TEXT'：文本
     */
    @ExcelProperty( value = "通知类型")
    private String  messageTypeCode;

    /**
     * 通知系统
     */
    @ExcelProperty( value = "通知系统")
    private String  messageSystemCode;

    /**
     * 通知平台 
     */
    @ExcelProperty( value = "通知平台 ")
    private String  messagePlatform;

    /**
     * 流程编号
     */
    @ExcelProperty( value = "流程编号")
    private Integer  processId;

    /**
     * 是否为默认版本 0 否，1 是
     */
    @ExcelProperty( value = "是否为默认版本")
    private Integer  defaultFalg;

    /**
     * 触发事件编码
     */
    @ExcelProperty( value = "触发事件编码")
    private String  eventCodes;

    /**
     * 触发事件名称
     */
    @ExcelProperty( value = "触发事件名称")
    private String  eventNames;

    /**
     * 排序
     */
    @ExcelProperty( value = "排序")
    private Integer  order;

    /**
     * 备注
     */
    @ExcelProperty( value = "备注")
    private String  remarks;

    /**
     * 状态 1 有效 0 失效
     */
    @ExcelProperty( value = "状态")
    private Integer  validState;

    /**
     * 操作人工号
     */
    @ExcelProperty( value = "操作人工号")
    private Integer  operatorId;

    /**
     * 操作人工姓名
     */
    @ExcelProperty( value = "操作人工姓名")
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
