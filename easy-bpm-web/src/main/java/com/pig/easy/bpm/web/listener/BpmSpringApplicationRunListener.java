package com.pig.easy.bpm.web.listener;

import com.pig.easy.bpm.common.spring.listener.BpmApplicationListener;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link org.springframework.boot.SpringApplicationRunListener} before {@link org.springframework.boot.context.event.EventPublishingRunListener} execution.
 *
 * @author : zhoulin.zhu
 * @date : 2021/1/21 11:20
 */
public class BpmSpringApplicationRunListener implements org.springframework.boot.SpringApplicationRunListener, Ordered {

    private final SpringApplication application;

    private final String[] args;

    public BpmSpringApplicationRunListener(SpringApplication application, String[] args) {
        this.application = application;
        this.args = args;
    }
    private   List<BpmApplicationListener> bpmApplicationListeners = new ArrayList<>();

    {
        bpmApplicationListeners.add(new StartApplicationListener());
    }

    /**
     * Before {@link org.springframework.boot.context.event.EventPublishingRunListener}.
     *
     * @return HIGHEST_PRECEDENCE
     */
    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }

    @Override
    public void starting() {
        System.out.println("bootstrapContext ===============================starting========================================= " );
        for (BpmApplicationListener bpmApplicationListener : bpmApplicationListeners) {
            bpmApplicationListener.starting();
        }
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        for (BpmApplicationListener bpmApplicationListener : bpmApplicationListeners) {
            bpmApplicationListener.environmentPrepared(environment);
        }
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        for (BpmApplicationListener bpmApplicationListener : bpmApplicationListeners) {
            bpmApplicationListener.contextPrepared(context);
        }
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        for (BpmApplicationListener bpmApplicationListener : bpmApplicationListeners) {
            bpmApplicationListener.contextLoaded(context);
        }
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        for (BpmApplicationListener bpmApplicationListener : bpmApplicationListeners) {
            bpmApplicationListener.started(context);
        }
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        for (BpmApplicationListener bpmApplicationListener : bpmApplicationListeners) {
            bpmApplicationListener.running(context);
        }
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        for (BpmApplicationListener bpmApplicationListener : bpmApplicationListeners) {
            bpmApplicationListener.failed(context,exception);
        }
        System.out.println("bootstrapContext ===============================failed========================================= " + context);
    }
}
