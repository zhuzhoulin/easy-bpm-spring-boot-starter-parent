package com.pig.easy.bpm.api.dto.response;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/7/29 20:40
 */
@Data
@ToString
public class OrganUserTree implements Serializable {

    private static final long serialVersionUID = 8682204807183647898L;

    private Long deptUserId;

    private Long parentId;

    private String code;

    private String name;

    private Long companyId;

    private String companyCode;

    private String companyName;

    private Long deptId;

    private String deptCode;

    private String deptName;

    private Long userId;

    private String userName;

    private String realName;

    private String positionCode;

    private String positionName;

    /**
     *  类型 1 公司 2 部门 3 人员
     *
     */
    private Integer type;

    private List<OrganUserTree> children;
}
