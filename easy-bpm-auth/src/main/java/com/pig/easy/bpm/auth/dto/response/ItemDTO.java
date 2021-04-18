package com.pig.easy.bpm.auth.dto.response;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/6/6 17:39
 */
@Data
@ToString
public class ItemDTO implements Serializable {

    private static final long serialVersionUID = -3976783430269656977L;

    public ItemDTO(String label, Object value,Boolean disabled) {
        this.label = label;
        this.value = value;
        this.disabled = disabled;
    }

    public ItemDTO() {
    }

    private String label;

    private Object value;

    private Boolean disabled;

}
