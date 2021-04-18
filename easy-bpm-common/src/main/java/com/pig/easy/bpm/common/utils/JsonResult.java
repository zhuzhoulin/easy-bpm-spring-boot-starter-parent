package com.pig.easy.bpm.common.utils;

import com.pig.easy.bpm.common.entityError.EntityError;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * todo: web 封装接口统一返回类
 *
 * @author : pig
 * @date : 2020/5/14 15:15
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class JsonResult implements Serializable {

    private static final long serialVersionUID = -8480431517116436735L;

    /**
     * 状态码
     */
    private int code;

    /**
     * 描述
     */
    private String message;

    /**
     * 返回时间
     */
    private Date responseDate;

    /**
     * 返回值
     */
    private Object data;

    private boolean error;

    public JsonResult(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.data = new Date();
    }

    public static JsonResult success(Object data) {
        return new JsonResult(EntityError.SUCCESS.getCode(), EntityError.SUCCESS.getMessage(), new Date(), data, false);
    }

    public static JsonResult success() {
        return new JsonResult(EntityError.SUCCESS.getCode(), EntityError.SUCCESS.getMessage(), new Date(), null, false);
    }

    public static JsonResult error(EntityError entityError) {
        return new JsonResult(entityError.getCode(), entityError.getMessage(), new Date(), null, true);
    }

    public static JsonResult fail() {
        return new JsonResult(EntityError.SYSTEM_ERROR.getCode(), EntityError.SYSTEM_ERROR.getMessage(), new Date(), null, true);
    }

}
