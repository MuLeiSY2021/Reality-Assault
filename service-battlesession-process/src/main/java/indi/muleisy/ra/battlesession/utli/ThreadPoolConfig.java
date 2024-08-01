package indi.muleisy.ra.battlesession.utli;

import java.util.concurrent.TimeUnit;

public class ThreadPoolConfig {
    // 核心线程数
    public static final int CORE_POOL_SIZE = 5;
    // 最大线程数
    public static final int MAX_POOL_SIZE = 10;
    // 线程存活时间
    public static final long KEEP_ALIVE_TIME = 60L;
    // 时间单位
    public static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
    // 任务队列容量
    public static final int QUEUE_CAPACITY = 100;
}
