package com.pig.easy.bpm.web.vo.request;


import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 租户表
 * </p>
 *
 * @author pig
 * @since 2020-09-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)

@ApiModel(value = "Tenant对象", description = "租户表")
public class TenantSaveOrUpdateVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 租户编号
     */
    private String tenantId;

    /**
     * 租户名称
     */
    private String tenantName;

    private LocalDateTime effectiveStartTime;

    private LocalDateTime effectiveEndTime;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 有效状态；0表示无效，1表示有效
     */
    private Integer validState;

    /**
     * 操作人工号
     */
    private Long operatorId;

    /**
     * 操作人姓名
     */
    private String operatorName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
