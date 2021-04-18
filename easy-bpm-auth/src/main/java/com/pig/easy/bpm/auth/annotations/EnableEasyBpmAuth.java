package com.pig.easy.bpm.auth.annotations;

import com.pig.easy.bpm.auth.config.EasyBpmAutoConfiguration;
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
@MapperScan
@Target(ElementType.TYPE)
@Import(EasyBpmAutoConfiguration.class)
public @interface EnableEasyBpmAuth {

    @AliasFor(annotation = MapperScan.class, attribute = "basePackages")
    String[] scanBasePackages() default {};

    @AliasFor(annotation = MapperScan.class, attribute = "basePackageClasses")
    Class<?>[] scanBasePackageClasses() default {};
}
