package com.pig.easy.bpm.common.utils;

import com.pig.easy.bpm.common.entityError.EntityError;
import lombok.*;

import java.io.Serializable;

/**
 * todo: provider 封装接口统一返回类
 *
 * @author : pig
 * @date : 2020/5/14 15:34
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 310224791665511701L;

    private EntityError entityError;

    private T data = null;

    public static <T> Result<T> response(EntityError entityError, T data) {
        return new Result(entityError, data);
    }

    public static <T> Result<T> responseSuccess(T data) {
        return new Result(EntityError.SUCCESS, data);
    }

    public static <T> Result<T> responseError(EntityError entityError) {
        return new Result(entityError, null);
    }
}
