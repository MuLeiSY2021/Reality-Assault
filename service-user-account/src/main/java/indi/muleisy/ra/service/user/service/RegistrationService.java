package indi.muleisy.ra.service.user.service;

import indi.muleisy.ra.service.user.model.User;
import indi.muleisy.ra.service.user.model.UserCredentials;
import indi.muleisy.ra.service.user.repository.UserInfoRepository;
import indi.muleisy.ra.service.user.repository.UserCredentialsRepository;
import indi.muleisy.ra.pub.config.Config;
import indi.muleisy.ra.pub.config.ProviderConstant;
import indi.muleisy.ra.pub.utils.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

@Service
public class RegistrationService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private QQUserInfoService qqUserInfoService;

    @Autowired
    private WeChatUserInfoService weChatUserInfoService;

    @Autowired
    private GitHubUserInfoService gitHubUserInfoService;

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    public Object registerByPhone(String phone, String password) {
        User user = new User();
        user.setPhone(phone);

        saveUser(user, password);
        return generateJwt(user);
    }

    public Object registerByEmail(String email, String password) {
        User user = new User();
        user.setEmail(email);

        saveUser(user, password);
        return generateJwt(user);
    }

    public String registerByOAuth2(String provider, String token, String password) {
        User user = new User();
        switch (ProviderConstant.nameOf(provider)) {
            case QQ_PROVIDER_NAME:
                String openid = qqUserInfoService.getOpenId(token);
                qqUserInfoService.putUserInfo(user, token, Config.INSTANCE.getAppid(), openid);
                user.setQQToken(openid);
                break;
            case WECHAT_PROVIDER_NAME:
                //TODO:
                weChatUserInfoService.getUserInfo(token);
                break;
            case GITHUB_PROVIDER_NAME:
                //TODO:
                gitHubUserInfoService.getUserInfo(token);
                break;
            default:
                throw new IllegalArgumentException("Unsupported provider: " + provider);
        }

        saveUser(user, password);
        return generateJwt(user);
    }

    private void saveUser(User user, String password) {
        UserCredentials userCredentials = new UserCredentials();
        String salt = generateSalt();
        String saltedPassword = hashPassword(password, salt);
        userCredentials.setPassword(saltedPassword);
        userCredentials.setSalt(salt);

        Long credentialsId = userCredentialsRepository.saveUserCredentials(userCredentials);
        user.setId(credentialsId);

        KeyPair keyPair = generateRsaKeyPair();
        String publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());

        user.setPublicKey(publicKey);
        saveUserToMongo(user);
    }

    private void saveUserToMongo(User user) {
        userInfoRepository.saveUser(user);
    }

    private String generateSalt() {
        return UUID.randomUUID().toString();
    }

    private String hashPassword(String password, String salt) {
        return BCrypt.hashpw(password, salt);
    }

    private KeyPair generateRsaKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating RSA key pair", e);
        }
    }

    private String generateJwt(User user) {
        return JwtUtil.INSTANCE.generateToken(user.getId().toString(), "USER_ROLE");
    }


}
