package com.pig.easy.bpm.api.annotations;

import com.pig.easy.bpm.api.config.EasyBpmApiAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * todo:
 *
 * @author : zhoulin.zhu
 * @date : 2021/2/3 16:06
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
@MapperScan
@Import(EasyBpmApiAutoConfiguration.class)
public @interface EnableEasyBpmApi {

    @AliasFor(annotation = MapperScan.class, attribute = "basePackages")
    String[] scanBasePackages() default {};

    @AliasFor(annotation = MapperScan.class, attribute = "basePackageClasses")
    Class<?>[] scanBasePackageClasses() default {};
}
