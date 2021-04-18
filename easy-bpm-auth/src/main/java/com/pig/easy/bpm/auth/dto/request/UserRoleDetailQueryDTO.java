package com.pig.easy.bpm.auth.dto.request;

import com.pig.easy.bpm.common.generator.dto.request.BaseRequestDTO;
import lombok.*;

import java.util.List;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/6/24 17:19
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleDetailQueryDTO extends BaseRequestDTO {

    private static final long serialVersionUID = -8228989912953552751L;

    private List<Long> userIdList;

    private List<Long> roleIdList;

    private Long userId;

    private Long roleId;

    private String roleCode;

    private String tenantId;

    private Integer pageSize;

    private Integer pageIndex;
}
