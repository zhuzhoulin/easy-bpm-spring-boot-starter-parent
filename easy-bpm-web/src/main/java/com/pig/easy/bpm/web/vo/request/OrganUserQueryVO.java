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
 * @date : 2020/7/31 11:54
 */
@Data
@ToString
public class OrganUserQueryVO implements Serializable {

    private static final long serialVersionUID = -2896788712176114827L;

    @NotEmpty
    @NotNull
    private String tenantId;

    private Long parentId;
}
