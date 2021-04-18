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
 * @date : 2020/7/30 11:27
 */
@Data
@ToString
public class TreeQueryVO implements Serializable {

    private static final long serialVersionUID = -6873091728121268629L;

    private Long parentId;

    @NotNull
    @NotEmpty
    private String tenantId;
}
