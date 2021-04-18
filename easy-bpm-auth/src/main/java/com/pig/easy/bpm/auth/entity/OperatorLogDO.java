package com.pig.easy.bpm.auth.entity;

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
@TableName("bpm_operator_log")
public class OperatorLogDO extends Model<OperatorLogDO> {

    private static final long serialVersionUID = 1L;

    /**
     * 日志编号
     */
    @TableId(value = "`log_id`", type = IdType.AUTO)
    private Long logId;

    /**
     * 操作用户工号
     */
    @TableField("`user_id`")
    private Long userId;

    /**
     * 操作用户
     */
    @TableField("`nickname`")
    private String nickname;

    /**
     * 追踪编号
     */
    @TableField("`trace_id`")
    private String traceId;

    /**
     * 业务类型
     */
    @TableField("`business_type`")
    private String businessType;

    /**
     * 模块名称
     */
    @TableField("`module_name`")
    private String moduleName;

    /**
     * 请求类名称
     */
    @TableField("`class_name`")
    private String className;

    /**
     * 请求方法名称
     */
    @TableField("`method_name`")
    private String methodName;

    /**
     * 请求方式(POST/GET)
     */
    @TableField("`request_method`")
    private String requestMethod;

    /**
     * 操作地址
     */
    @TableField("`ip_address`")
    private String ipAddress;

    /**
     * 详细地址
     */
    @TableField("`detail_address`")
    private String detailAddress;

    /**
     * 浏览器
     */
    @TableField("`browser`")
    private String browser;

    /**
     * 操作系统
     */
    @TableField("`os`")
    private String os;

    /**
     * 操作类别0其它 1后台用户 2手机端用户
     */
    @TableField("`operator_type`")
    private Integer operatorType;

    /**
     * 请求参数
     */
    @TableField("`param`")
    private String param;

    /**
     * 状态码
     */
    @TableField("`code`")
    private Integer code;

    /**
     * 返回描述
     */
    @TableField("`message`")
    private String message;

    /**
     * 返回值
     */
    @TableField("`data`")
    private String data;

    /**
     * 备注
     */
    @TableField("`remarks`")
    private String remarks;

    /**
     * 有效状态；0表示无效，1表示有效
     */
    @TableField("`valid_state`")
    private Integer validState;

    /**
     * 操作人工号
     */
    @TableField("`operator_id`")
    private Long operatorId;

    /**
     * 操作人姓名
     */
    @TableField("`operator_name`")
    private String operatorName;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.logId;
    }

}
