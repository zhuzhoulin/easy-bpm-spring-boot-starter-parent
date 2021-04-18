package com.pig.easy.bpm.web.vo.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
 * @since 2020-09-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)

@ApiModel(value = "对象", description = "")
public class TenantQueryVO implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "租户编号")
    /**
     * 租户编号
     */
    private String tenantId;


    @ApiModelProperty(value = "租户名称")
    /**
     * 租户名称
     */
    private String tenantName;


    private LocalDateTime effectiveStartTime;


    private LocalDateTime effectiveEndTime;


    @ApiModelProperty(value = "备注")
    /**
     * 备注
     */
    private String remarks;


    @ApiModelProperty(value = "有效状态；0表示无效，1表示有效")
    /**
     * 有效状态；0表示无效，1表示有效
     */
    private Integer validState;


    @ApiModelProperty(value = "操作人工号")
    /**
     * 操作人工号
     */
    private Long operatorId;


    @ApiModelProperty(value = "操作人姓名")
    /**
     * 操作人姓名
     */
    private String operatorName;


    @ApiModelProperty(value = "创建时间")
    /**
     * 创建时间
     */
    private LocalDateTime createTime;


    @ApiModelProperty(value = "更新时间")
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 当前页码
     */
    private Integer pageIndex;

    /**
     * 每页展示数量
     */
    private Integer pageSize;


}
