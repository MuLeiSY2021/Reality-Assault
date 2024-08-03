package indi.muleisy.ra.service.ebusiness.order.service;

import indi.muleisy.ra.pub.jwt.JwtUtil;
import indi.muleisy.ra.service.ebusiness.order.model.User;
import indi.muleisy.ra.service.ebusiness.order.repository.UserVerifyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.Base64;

@Service

public class JwtService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private UserVerifyRepository userVerifyRepository;

    public boolean validateJwt(String encryptedJwt) {
        String userId = (String) redisTemplate.opsForValue().get(encryptedJwt);
        if (userId == null) {
            return false; // JWT 不存在或已过期
        }
        PublicKey publicKey = getUserPublicKey(userId);
        String decryptedJwt = JwtUtil.INSTANCE.decryptJwt(encryptedJwt, publicKey);
        return JwtUtil.INSTANCE.isJwtValid(decryptedJwt);
    }

    private PublicKey getUserPublicKey(String userId) {
        User user = userVerifyRepository.findUserById(userId);
        byte[] keyBytes = Base64.getDecoder().decode(user.getPublicKey());
        return JwtUtil.INSTANCE.getPublicKey(keyBytes);
    }
}
