package com.pig.easy.bpm.auth.dto.request;

import com.pig.easy.bpm.common.generator.dto.request.BaseRequestDTO;
import lombok.Data;
import lombok.ToString;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/6/14 13:11
 */
@Data
@ToString
public class UserRoleReqDTO extends BaseRequestDTO {

    private static final long serialVersionUID = 8435718576925496355L;

    private Long id;

    private Long userId;

    private Long roleId;

    private String aliasName;

    private String remarks;

    /**
     * 租户编号
     */
    private String tenantId;

    /**
     * 状态 1 有效 0 失效
     */
    private Integer validState;

    private Long operatorId;

    private String operatorName;

    private Integer pageSize;

    private Integer pageIndex;

}
