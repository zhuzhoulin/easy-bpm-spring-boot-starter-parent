package com.pig.easy.bpm.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * todo:  校验是否需要登录，这一部分 可以放在 网关做处理
 *
 * @author : pig
 * @since  : 2020/5/15 10:42
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Login {

    boolean value() default true;
}
