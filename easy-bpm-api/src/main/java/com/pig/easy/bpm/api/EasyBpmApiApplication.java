package com.pig.easy.bpm.api;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.pig.easy.bpm.api.annotations.EnableEasyBpmApi;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.concurrent.CountDownLatch;

@EnableEasyBpmApi(scanBasePackages ="com.pig.easy.bpm")
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class,DruidDataSourceAutoConfigure.class})
public class EasyBpmApiApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(EasyBpmApiApplication.class).web(WebApplicationType.NONE).run(args);
        CountDownLatch count = new CountDownLatch(1);
        try {
            count.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
