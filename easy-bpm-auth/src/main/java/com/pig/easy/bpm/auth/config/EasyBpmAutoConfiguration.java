package com.pig.easy.bpm.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * todo:
 *
 * @author : zhoulin.zhu
 * @date : 2021/2/3 18:08
 */
@Configuration
@EnableTransactionManagement
@EnableAsync
public class EasyBpmAutoConfiguration {
}
