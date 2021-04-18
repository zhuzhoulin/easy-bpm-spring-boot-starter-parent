package com.pig.easy.bpm.auth.dto.response;

import com.pig.easy.bpm.common.generator.dto.response.BaseResponseDTO;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/6/14 13:07
 */
@Data
@ToString
public class UserRoleDTO extends BaseResponseDTO {

    private static final long serialVersionUID = 4286560983779665021L;

    private Long id;

    /**
     * 用户工号
     */
    private Long userId;

    /**
     * 角色编号
     */
    private Long roleId;

    /**
     * 用户角色 别名
     */
    private String aliasName;

    /**
     * 租户编号
     */
    private String tenantId;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 状态 1 有效 0 失效
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
