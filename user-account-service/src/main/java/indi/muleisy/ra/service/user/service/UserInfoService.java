package indi.muleisy.ra.service.user.service;

import indi.muleisy.ra.service.user.config.MallRPConfig;
import indi.muleisy.ra.service.user.model.User;
import indi.muleisy.ra.service.user.repository.UserInfoRepository;
import indi.muleisy.ra.service.user.rpc.MallInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private VerificationService verificationService;

    @Autowired
    private OAuth2Service oauth2Service;

    private final MallInterface mallInterface;

    public UserInfoService() {
        this.mallInterface = MallRPConfig.connect();
    }

    // 处理昵称的增改逻辑
    public boolean updateNickname(String id, String newNickname) {
        User user = getUserById(id);
        if (user != null) {
            if (user.getNickname() != null) {
                return false;
            } else {
                boolean success = mallInterface.consumeRenameCard(id);
                if (!success) {
                    return false;
                }
            }
            user.setNickname(newNickname);
            userInfoRepository.save(user);
            return true;
        }
        return false;
    }

    // 处理头像的增改逻辑
    public boolean updateAvatar(String id, String newAvatarUrl) {
        // 调用第三方图片审核服务
        boolean isApproved = checkImageApproval(newAvatarUrl);
        if (isApproved) {
            User user = getUserById(id);
            if (user != null) {
                user.setAvatar(newAvatarUrl);
                userInfoRepository.save(user);
                return true;
            }
        }
        return false;
    }

    // 更新性别
    public boolean updateGender(String id, boolean gender) {
        User user = getUserById(id);
        if (user != null) {
            user.setGender(gender);
            userInfoRepository.save(user);
            return true;
        }
        return false;
    }

    // 更新生日（每年一次）
    public boolean updateBirthdate(String id, Date birthdate) {
        String redisKey = "user:birthdate:lastUpdate:" + id;
        Long lastUpdateTime = redisTemplate.getExpire(redisKey, TimeUnit.MILLISECONDS);
        if (lastUpdateTime == null || System.currentTimeMillis() - lastUpdateTime > TimeUnit.DAYS.toMillis(365)) {
            User user = getUserById(id);
            if (user != null) {
                user.setBirthdate(birthdate);
                userInfoRepository.save(user);
                redisTemplate.opsForValue().set(redisKey, String.valueOf(System.currentTimeMillis()));
                redisTemplate.expire(redisKey, 365, TimeUnit.DAYS);
                return true;
            }
        }
        return false;
    }

    // 更新邮箱
    public boolean updateEmail(String id, String newEmail, String verificationCode) {
        if (verificationService.verifyEmailCode(newEmail, verificationCode)) {
            User user = getUserById(id);
            if (user != null) {
                user.setEmail(newEmail);
                userInfoRepository.save(user);
                return true;
            }
        }
        return false;
    }

    // 更新电话
    public boolean updatePhone(String id, String newPhone, String verificationCode) {
        if (verificationService.verifyPhoneCode(newPhone, verificationCode)) {
            User user = getUserById(id);
            if (user != null) {
                user.setPhone(newPhone);
                userInfoRepository.save(user);
                return true;
            }
        }
        return false;
    }

    // 删除邮箱、电话或第三方令牌
    public boolean deleteContactInfo(String id, String type) {
        User user = getUserById(id);
        if (user != null && canDeleteContactInfo(user)) {
            switch (type) {
                case "email":
                    user.setEmail(null);
                    break;
                case "phone":
                    user.setPhone(null);
                    break;
                case "qq":
                    user.setQQToken(null);
                    break;
                case "wechat":
                    user.setWeChatToken(null);
                    break;
                case "github":
                    user.setGithubToken(null);
                    break;
                default:
                    return false;
            }
            userInfoRepository.save(user);
            return true;
        }
        return false;
    }

    // 更新第三方令牌
    public boolean updateThirdPartyToken(String id, String provider, String token) {
        if (oauth2Service.verifyThirdPartyToken(provider, token)) {
            User user = getUserById(id);
            if (user != null) {
                switch (provider) {
                    case "qq":
                        user.setQQToken(token);
                        break;
                    case "wechat":
                        user.setWeChatToken(token);
                        break;
                    case "github":
                        user.setGithubToken(token);
                        break;
                    default:
                        return false;
                }
                userInfoRepository.save(user);
                return true;
            }
        }
        return false;
    }

    // 辅助方法

    private User getUserById(String id) {
        return userInfoRepository.findUserById(id);
    }

    private boolean checkImageApproval(String imageUrl) {
        // 调用第三方图片审核服务逻辑
        return true;
    }

    private boolean canDeleteContactInfo(User user) {
        return (user.getEmail() != null || user.getPhone() != null ||
                user.getQQToken() != null || user.getWeChatToken() != null || user.getGithubToken() != null);
    }
}
