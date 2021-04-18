package com.pig.easy.bpm.api.utils;/**
 * Created by Administrator on 2019/7/23.
 */

import com.pig.easy.bpm.common.utils.SystemClock;
import org.flowable.common.engine.impl.cfg.IdGenerator;

import java.util.Random;

/**
 * todo: flowable Id Generator
 */
public class SnowIdGenerator implements IdGenerator {

    /** 开始时间 */
    private final long startTime = 1616590235718L;
    /** 机器ID所占大小 */
    private final long workerIdBits = 5L;
    /** 数据标识id所占的位数*/
    private final long dataCenterIdBits = 5L;
    /** 支持的最大机器id(十进制)，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
    -1L 左移 5位 (worker id 所占位数) 即 5位二进制所能获得的最大十进制数 - 31 */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    /** 支持的最大数据标识id - 31*/
    private final long maxDataCenterId = -1L ^ (-1L << dataCenterIdBits);
    /** 序列在id中占的位数*/
    private final long sequenceBits = 12L;
    /** 机器ID 左移位数 - 12 (即末 sequence 所占用的位数)*/
    private final long workerIdMoveBits = sequenceBits;
    /** 数据标识id 左移位数 - 17(12+5)*/
    private final long dataCenterIdMoveBits = sequenceBits + workerIdBits;
    /** 时间截向 左移位数 - 22(5+5+12)*/
    private final long timestampMoveBits = sequenceBits + workerIdBits + dataCenterIdBits;
    /** 生成序列的掩码(12位所对应的最大整数值)，这里为4095 (0b111111111111=0xfff=4095)*/
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);


    /**
     * 工作机器ID(0~31)
     */
    private long workerId;
    /**
     * 数据中心ID(0~31)
     */
    private long dataCenterId;
    /**
     * 毫秒内序列(0~4095)
     */
    private long sequence = 0L;
    /**
     * 上次生成ID的时间截
     */
    private long lastTimestamp = -1L;

    /**
     * 功能描述: 随机生成 workerId 和 dataCenterId ，解决多台机器并发问题
     *
     * @author : pig
     * @date :
     */
    public SnowIdGenerator() {
        long workerId = getRandom();
        long dataCenterId = getRandom();
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("Worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (dataCenterId > maxDataCenterId || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format("DataCenter Id can't be greater than %d or less than 0", maxDataCenterId));
        }
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    /**
     * 生成1-31之间的随机数
     *
     * @return
     */
    private long getRandom() {
        int max = (int) (maxDataCenterId);
        int min = 1;
        Random random = new Random();
        long result = random.nextInt(max - min) + min;
        return result;
    }

    /**
     * 构造函数
     *
     * @param workerId     工作ID (0~31)
     * @param dataCenterId 数据中心ID (0~31)
     */
    public SnowIdGenerator(long workerId, long dataCenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("Worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (dataCenterId > maxDataCenterId || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format("DataCenter Id can't be greater than %d or less than 0", maxDataCenterId));
        }
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    @Override
    public synchronized String getNextId() {
        long timestamp = SystemClock.now();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = blockTillNextMillis(lastTimestamp);
            }
        }
        else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;

        long nextId = ((timestamp - startTime) << timestampMoveBits)
                | (dataCenterId << dataCenterIdMoveBits)
                | (workerId << workerIdMoveBits)
                | sequence;
        return Long.toString(nextId);
    }

    private long blockTillNextMillis(long lastTimestamp) {
        long timestamp = currentTime();
        while (timestamp <= lastTimestamp) {
            timestamp = currentTime();
        }
        return timestamp;
    }

    // 获得以毫秒为单位的当前时间
    private long currentTime() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
        System.out.println(" System.currentTimeMillis() = " + System.currentTimeMillis());
    }
}
