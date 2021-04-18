package com.pig.easy.bpm.web.vo.request;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/7/31 12:00
 */
@Data
@ToString
public class OrganDeptQueryVO implements Serializable {

    private static final long serialVersionUID = -8384725710500273802L;

    @NotEmpty
    @NotNull
    private String tenantId;

    private Long parentId;

    private Long companyId;
}
