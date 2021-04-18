package com.pig.easy.bpm.common.spring.listener;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * todo:
 *
 * @author : zhoulin.zhu
 * @date : 2021/1/21 10:46
 */
public interface BpmApplicationListener {

    /**
     */
    void starting();

    /**
     *
     * @param environment environment
     */
    void environmentPrepared(ConfigurableEnvironment environment);

    /**
     *
     * @param context context
     */
    void contextPrepared(ConfigurableApplicationContext context);

    /**
     *
     * @param context context
     */
    void contextLoaded(ConfigurableApplicationContext context);

    /**
     *
     * @param context context
     */
    void started(ConfigurableApplicationContext context);

    /**
     *
     * @param context context
     */
    void running(ConfigurableApplicationContext context);

    /**
     *
     * @param context   context
     * @param exception exception
     */
    void failed(ConfigurableApplicationContext context, Throwable exception);
}
