package com.pig.easy.bpm.auth.dto.response;

import com.pig.easy.bpm.common.generator.dto.response.BaseResponseDTO;
import lombok.Data;
import lombok.ToString;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/6/14 14:35
 */
@Data
@ToString
public class UserRoleDetailDTO extends BaseResponseDTO {

    private static final long serialVersionUID = 1909099587910912089L;

    private Long userId;

    private String realName;

    private Long roleId;

    private String tenantId;

    private String aliasName;

    private Integer roleLevel;

    private String roleName;

    private String roleCode;

    private String roleAbbr;

    private Long deptId;

    private String deptCode;

    private String deptName;

    private Long companyId;

    private String companyName;

    private String companyCode;
}
