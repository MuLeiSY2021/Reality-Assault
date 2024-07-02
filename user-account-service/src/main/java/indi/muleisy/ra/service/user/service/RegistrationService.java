package  indi.muleisy.ra.service.user.service;

import indi.muleisy.ra.service.user.model.User;
import indi.muleisy.ra.service.user.repository.CacheRepository;
import indi.muleisy.ra.service.user.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

@Service
public class RegistrationService {

    @Autowired
    private VerificationService verificationService;

    @Autowired
    private CacheRepository cacheRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    public String registerByPhone(String phone, String code, String password) {
        if (!verificationService.verifyPhoneCode(phone, code)) {
            throw new IllegalArgumentException("Invalid verification code.");
        }

        String userId = UUID.randomUUID().toString();
        User user = new User();
        user.setId(userId);
        user.setPhone(phone);

        saveUser(user, password);
        return generateJwt(user);
    }

    public String registerByEmail(String email, String code, String password) {
        if (!verificationService.verifyEmailCode(email, code)) {
            throw new IllegalArgumentException("Invalid verification code.");
        }

        String userId = UUID.randomUUID().toString();
        User user = new User();
        user.setId(userId);
        user.setEmail(email);

        saveUser(user, password);
        return generateJwt(user);
    }

    public String registerByOAuth2(String provider, String token, String password) {
        // 根据 provider 和 token 获取用户信息
        String userId = UUID.randomUUID().toString();
        User user = new User();
        user.setId(userId);
        // 填充第三方认证信息
        user.setOauth2Provider(provider);
        user.setOauth2Token(token);

        saveUser(user, password);
        return generateJwt(user);
    }

    private void saveUser(User user, String password) {
        // 将用户信息存入 MongoDB
        mongoTemplate.save(user);

        // 对密码进行加盐
        String salt = generateSalt();
        String saltedPassword = hashPassword(password, salt);

        // 将盐和加盐后的密码存入 MySQL
        jdbcTemplate.update("INSERT INTO user_credentials (user_id, salt, password) VALUES (?, ?, ?)",
                user.getId(), salt, saltedPassword);

        // 生成 RSA 密钥对
        KeyPair keyPair = generateRsaKeyPair();
        String publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());

        // 将公钥存入 MongoDB
        user.setPublicKey(publicKey);
        mongoTemplate.save(user);
    }

    private String generateSalt() {
        // 生成随机盐
        return UUID.randomUUID().toString();
    }

    private String hashPassword(String password, String salt) {
        // 使用盐对密码进行哈希
        // 这里可以使用例如 BCrypt、SHA-256 等哈希算法
        return password + salt; // 简单示例，实际应用中请使用安全的哈希算法
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
        return jwtUtil.generateToken(user.getId(), "USER_ROLE");
    }
}
