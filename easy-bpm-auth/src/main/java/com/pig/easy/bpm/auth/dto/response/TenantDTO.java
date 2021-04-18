package com.pig.easy.bpm.auth.dto.response;

import com.pig.easy.bpm.common.generator.dto.response.BaseResponseDTO;
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
public class TenantDTO extends BaseResponseDTO implements Serializable {

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
     * 操作人工号
     */
    private Long operatorId;


    /**
     * 操作人姓名
     */
    private String operatorName;
}
