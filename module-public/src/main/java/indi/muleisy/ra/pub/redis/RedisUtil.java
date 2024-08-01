package indi.muleisy.ra.pub.redis;

import lombok.Getter;
import redis.clients.jedis.Jedis;

public class RedisUtil {
    private static final String REDIS_HOST = "localhost";
    private static final int REDIS_PORT = 6379;

    @Getter
    private final static Jedis jedis;

    static {
        jedis = new Jedis(REDIS_HOST, REDIS_PORT);
    }

    public static void close() {
        if (jedis != null) {
            jedis.close();
        }
    }
}
