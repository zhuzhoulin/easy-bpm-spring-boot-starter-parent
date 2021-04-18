package com.pig.easy.bpm.api.config;

import com.pig.easy.bpm.auth.config.VisiableThreadPoolTaskExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * todo:
 *
 * @author : pig
 */
@Configuration
@EnableAsync
@Slf4j
public class TaskExecutePoolConfig implements AsyncConfigurer {

    @Bean
    @Override
    public Executor getAsyncExecutor() {
        log.info("--------------------------开启线程池-----start---------");
        ThreadPoolTaskExecutor executor = new VisiableThreadPoolTaskExecutor();
        /* 当前线程数 */
        executor.setCorePoolSize(10);
        /* 最大线程数 */
        executor.setMaxPoolSize(20);
        /* 线程池所使用的缓冲队列 */
        executor.setQueueCapacity(10000);
        /* 等待任务在关机时完成--表明等待所有线程执行完 */
        executor.setWaitForTasksToCompleteOnShutdown(true);
        /* 等待时间 （默认为0，此时立即停止），并没等待xx秒后强制停止 */
        executor.setAwaitTerminationSeconds(60 * 5);
        /* 线程名称前缀 */
        executor.setThreadNamePrefix("easybpm");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        /* 初始化 */
        executor.initialize();
        log.info("--------------------------开启线程池-----end---------");
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {

        return new AsyncUncaughtExceptionHandler() {
            @Override
            public void handleUncaughtException(Throwable arg0, Method arg1, Object... arg2) {
                log.error("==========================" + arg0.getMessage() + "=======================", arg0);
                log.error("execption method:" + arg1.getName());
            }
        };
    }
}
