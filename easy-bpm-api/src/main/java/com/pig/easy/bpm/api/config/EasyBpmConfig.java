package com.pig.easy.bpm.api.config;

import com.pig.easy.bpm.api.core.event.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * todo: besr bpm 默认配置文件
 *
 * @author : pig
 * @date : 2020/5/20 16:11
 * @see BpmEventDispatcherImpl
 * @see BpmEventListenerImpl
 * @see SendEmailListener
 */
@Configuration
public class EasyBpmConfig {

    @Autowired
    SendEmailListener sendEmailListener;
    /**
     * 功能描述: init bpmEventDispatcher
     *
     *
     * @return : com.pig.easy.bpm.event.BpmEventDispatcher
     * @author : pig
     * @date : 2020/5/20 18:11
     */
    @Bean
    public BpmEventDispatcher bpmEventDispatcher() {
        BpmEventDispatcherImpl bestBpmEventDispatcher = new BpmEventDispatcherImpl();

        bestBpmEventDispatcher.addEventListener(new BpmEventListenerImpl());
        bestBpmEventDispatcher.addEventListener(sendEmailListener,BpmEventType.TASK_CREATED);
        return bestBpmEventDispatcher;
    }

}
