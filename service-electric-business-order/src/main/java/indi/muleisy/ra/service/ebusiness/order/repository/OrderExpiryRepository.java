package indi.muleisy.ra.service.ebusiness.order.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OrderExpiryRepository {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void registerOrderExpiry(String orderId) {
        // 将订单注册到Redis，设置过期时间为30分钟
        redisTemplate.opsForValue().set(orderId, "PENDING", 30 * 60);
    }
}
