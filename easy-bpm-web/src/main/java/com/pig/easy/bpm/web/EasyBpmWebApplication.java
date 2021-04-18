package com.pig.easy.bpm.web;

import com.pig.easy.bpm.api.annotations.EnableEasyBpmApi;
import com.pig.easy.bpm.auth.annotations.EnableEasyBpmAuth;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.concurrent.CountDownLatch;

@MapperScan(basePackages ="com.pig.easy.bpm.**.mapper")
@EnableSwagger2
@EnableEasyBpmAuth
@EnableEasyBpmApi
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class},scanBasePackages = {"com.pig.easy.bpm.auth.*","com.pig.easy.bpm.web.*","com.pig.easy.bpm.common.*","com.pig.easy.bpm.api.*"})
public class EasyBpmWebApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(EasyBpmWebApplication.class, args);
        CountDownLatch count = new CountDownLatch(1);
        count.await();
    }

}
