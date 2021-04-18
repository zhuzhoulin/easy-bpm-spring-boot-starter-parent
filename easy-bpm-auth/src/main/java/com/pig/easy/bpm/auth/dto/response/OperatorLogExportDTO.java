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
    * 
    * </p>
 *
 * @author pig
 * @since 2021-03-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OperatorLogExportDTO implements Serializable {

    @ExcelIgnore
    private static final long serialVersionUID = 1L;

    /**
     * 日志编号
     */
    @ExcelProperty( value = "日志编号")
    private Long  logId;

    /**
     * 操作用户工号
     */
    @ExcelProperty( value = "操作用户工号")
    private Long  userId;

    /**
     * 操作用户
     */
    @ExcelProperty( value = "操作用户")
    private String  nickname;

    /**
     * 追踪编号
     */
    @ExcelProperty( value = "追踪编号")
    private String  traceId;

    /**
     * 业务类型
     */
    @ExcelProperty( value = "业务类型")
    private String  businessType;

    /**
     * 模块名称
     */
    @ExcelProperty( value = "模块名称")
    private String  moduleName;

    /**
     * 请求类名称
     */
    @ExcelProperty( value = "请求类名称")
    private String  className;

    /**
     * 请求方法名称
     */
    @ExcelProperty( value = "请求方法名称")
    private String  methodName;

    /**
     * 请求方式(POST/GET)
     */
    @ExcelProperty( value = "请求方式")
    private String  requestMethod;

    /**
     * 操作地址
     */
    @ExcelProperty( value = "操作地址")
    private String  ipAddress;

    /**
     * 详细地址
     */
    @ExcelProperty( value = "详细地址")
    private String  detailAddress;

    /**
     * 浏览器
     */
    @ExcelProperty( value = "浏览器")
    private String  browser;

    /**
     * 操作系统
     */
    @ExcelProperty( value = "操作系统")
    private String  os;

    /**
     * 操作类别0其它 1后台用户 2手机端用户
     */
    @ExcelProperty( value = "操作类别")
    private Integer  operatorType;

    /**
     * 请求参数
     */
    @ExcelProperty( value = "请求参数")
    private String  param;

    /**
     * 状态码
     */
    @ExcelProperty( value = "状态码")
    private Integer  code;

    /**
     * 返回描述
     */
    @ExcelProperty( value = "返回描述")
    private String  message;

    /**
     * 返回值
     */
    @ExcelProperty( value = "返回值")
    private String  data;

    /**
     * 备注
     */
    @ExcelProperty( value = "备注")
    private String  remarks;

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
