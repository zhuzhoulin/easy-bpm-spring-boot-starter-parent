package com.pig.easy.bpm.auth.dto.request;

import com.pig.easy.bpm.common.generator.dto.request.BaseRequestDTO;
import lombok.*;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/6/24 14:48
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleGroupRoleDetailQueryDTO extends BaseRequestDTO {

    private static final long serialVersionUID = 2581347520950990725L;

    private Long roleGroupId;

    private String roleGroupCode;

    private String businessLine;

    private Integer roleGroupLevel;

    private Integer roleGroupType;

    private String tenantId;

    private Long roleId;

    private String roleCode;

    private Long companyId;

    private Long deptId;

    private Integer pageSize;

    private Integer pageIndex;
}
