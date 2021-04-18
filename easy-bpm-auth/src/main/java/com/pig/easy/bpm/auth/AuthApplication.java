package com.pig.easy.bpm.auth;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.concurrent.CountDownLatch;

//@EnableEasyBpmAuth(scanBasePackages ={"com.pig.easy.bpm.auth.mapper"})
@SpringBootApplication
public class AuthApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(AuthApplication.class).web(WebApplicationType.NONE).run(args);
		CountDownLatch count = new CountDownLatch(1);
		try {
			count.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
