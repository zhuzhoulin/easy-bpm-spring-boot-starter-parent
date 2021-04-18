package com.pig.easy.bpm.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * todo:
 *
 * @author : zhoulin.zhu
 * @date : 2021/2/3 14:24
 */
@Configuration
@EnableTransactionManagement
@EnableAsync
public class EasyBpmApiAutoConfiguration {

}
