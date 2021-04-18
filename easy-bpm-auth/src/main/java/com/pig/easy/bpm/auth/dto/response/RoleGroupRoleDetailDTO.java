package com.pig.easy.bpm.auth.dto.response;

import com.pig.easy.bpm.common.generator.dto.response.BaseResponseDTO;
import lombok.Data;
import lombok.ToString;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/6/24 14:38
 */
@Data
@ToString
public class RoleGroupRoleDetailDTO extends BaseResponseDTO {

    private static final long serialVersionUID = 5556845095344444760L;

    private Long roleGroupId;

    private String roleGroupCode;

    private String roleGroupName;

    private String roleGroupBusinessLine;

    private Integer roleGroupLevel;

    private Integer roleGroupType;

    private String tenantId;

    private Long roleId;

    private String roleName;

    private String roleCode;

    private String roleAbbr;

    private Integer  roleLevel;

    private String roleAliasName;

    private Long companyId;

    private String companyCode;

    private String companyName;

    private Long deptId;

    private String deptName;

    private String deptCode;

    private String deptBusinessLine;

}
