package indi.muleisy.ra.service.user.service;

import indi.muleisy.ra.pub.jwt.JwtUtil;
import indi.muleisy.ra.service.user.model.User;
import indi.muleisy.ra.service.user.model.UserCredentials;
import indi.muleisy.ra.service.user.repository.UserCredentialsRepository;
import indi.muleisy.ra.service.user.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class LoginService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    @Autowired
    private OAuth2Service oAuth2Service;

    public String loginByIdentifier(String identifier, String password) {
        User user = getUserByIdentifier(identifier);
        if (user != null && verifyPassword(password, user)) {
            String jwt = generateJwt(user);
            storeJwtInCache(jwt, user.getId());
            return jwt;
        }
        return null; // 登录失败
    }

    public String loginByOAuth2(String provider, String token) {
        User user = getUserByOAuth2Provider(provider, token);
        if (user != null) {
            String jwt = generateJwt(user);
            storeJwtInCache(jwt, user.getId());
            return jwt;
        }
        return null; // 登录失败
    }

    private User getUserByIdentifier(String identifier) {
        // 根据邮箱、手机或用户名获取用户
        return userInfoRepository.findByEmailOrPhoneOrOpenId(identifier);
    }

    private User getUserByOAuth2Provider(String provider, String token) {
        String openId = oAuth2Service.getUserByProviderAndToken(provider, token);

        return userInfoRepository.findByEmailOrPhoneOrOpenId(openId);
    }

    private boolean verifyPassword(String rawPassword, User user) {
        UserCredentials userCredentials = userCredentialsRepository.getUserCredentialsById(user.getId());

        if (userCredentials != null) {
            String salt = userCredentials.getSalt();
            String hashedPassword = hashPassword(rawPassword, salt);
            String storedPassword = userCredentials.getPassword();
            return hashedPassword.equals(storedPassword);
        }
        return false;
    }

    private String hashPassword(String password, String salt) {
        return BCrypt.hashpw(password, salt);
    }

    private String generateJwt(User user) {
        return JwtUtil.INSTANCE.generateToken(user.getId().toString(), "USER_ROLE");
    }

    private void storeJwtInCache(String jwt, Long userId) {
        redisTemplate.opsForValue().set(jwt, userId);
        // 设置JWT在Redis中的过期时间，假设为1小时
        redisTemplate.expire(jwt, 1, TimeUnit.HOURS);
    }
}
