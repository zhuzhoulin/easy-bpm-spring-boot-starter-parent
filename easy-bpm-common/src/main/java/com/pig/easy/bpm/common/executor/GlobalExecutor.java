package com.pig.easy.bpm.common.executor;

import org.apache.commons.lang3.ClassUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * todo:
 *
 * @author : zhoulin.zhu
 * @date : 2021/3/22 12:04
 */
public class GlobalExecutor {

    private static final ScheduledExecutorService COMMON_EXECUTOR = ExecutorFactory.Managed
            .newScheduledExecutorService(ClassUtils.getCanonicalName(GlobalExecutor.class), 4,
                    new NameThreadFactory("com.pig.easy.bpm.log"));

    private static final ExecutorService LOGGER_EXECUTOR = ExecutorFactory.Managed
            .newFixedExecutorService(ClassUtils.getCanonicalName(GlobalExecutor.class), 8,
                    new NameThreadFactory("com.pig.easy.bpm.log"));

    public static void runWithoutThread(Runnable runnable) {
        runnable.run();
    }


    public static void executeByCommon(Runnable runnable) {
        if (COMMON_EXECUTOR.isShutdown()) {
            return;
        }
        COMMON_EXECUTOR.execute(runnable);
    }

    public static void scheduleByCommon(Runnable runnable, long delayMs) {
        if (COMMON_EXECUTOR.isShutdown()) {
            return;
        }
        COMMON_EXECUTOR.schedule(runnable, delayMs, TimeUnit.MILLISECONDS);
    }

    public static void submitLoggerDataTask(Runnable runnable) {
        LOGGER_EXECUTOR.submit(runnable);
    }
    public static void executeByLogger(Runnable runnable) {
        LOGGER_EXECUTOR.execute(runnable);
    }
}
