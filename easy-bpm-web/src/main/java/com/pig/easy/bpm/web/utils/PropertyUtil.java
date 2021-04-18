package com.pig.easy.bpm.web.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * todo:
 *
 * @author : zhoulin.zhu
 * @date : 2021/2/2 11:59
 */
public class PropertyUtil implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyUtil.class);

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {

        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("############################################# ==> PropertyUtil ####################start#########################");
        }
        loadSetting();
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("############################################# ==> PropertyUtil #####################end########################");
        }
    }

    private void loadSetting() {
        try {
//            System.setProperty("bpm.ip", IpUtils.getHostIp());
//            System.setProperty("server.port", EnvUtil.getPort() + "");

        } catch (Exception e) {
            LOGGER.error("read application.property failed", e);
            throw e;
        }
    }
}
