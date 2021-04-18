package com.pig.easy.bpm.common.annotation;

import com.pig.easy.bpm.common.config.LogAspectAdvisorConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
@Import(LogAspectAdvisorConfig.class)
public @interface EnableLogAspect {

    boolean value() default true;

    /**
     * 模块
     */
    public String title() default "";

    /**
     * 是否保存请求的参数
     */
    public boolean isSaveRequestData() default true;
}
