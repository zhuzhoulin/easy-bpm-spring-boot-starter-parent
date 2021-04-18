package com.pig.easy.bpm.web.listener;

import com.pig.easy.bpm.common.spring.listener.BpmApplicationListener;
import com.pig.easy.bpm.web.utils.EnvUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * todo:
 *
 * @author : zhoulin.zhu
 * @date : 2021/1/21 10:48
 */
public class StartApplicationListener implements BpmApplicationListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(StartApplicationListener.class);


    @Override
    public void starting() {
        LOGGER.info("***************************StartApplicationListener*********************************************");

    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        EnvUtil.setEnvironment(environment);
        EnvUtil.getDefaultApplicatioResource();
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {

    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {

    }

    @Override
    public void started(ConfigurableApplicationContext context) {

    }

    @Override
    public void running(ConfigurableApplicationContext context) {

    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        LOGGER.error("Startup errors : {}", exception);
        context.close();

        LOGGER.error("Bpm failed to start, please see log for more details.");
    }
}
