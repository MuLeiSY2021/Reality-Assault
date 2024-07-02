package indi.muleisy.ra.service.user.service;

import indi.muleisy.ra.service.user.repository.CacheRepository;
import indi.muleisy.ra.service.user.repository.MessagingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class VerificationService {

    @Autowired
    private CacheRepository cacheRepository;

    @Autowired
    private MessagingRepository messagingRepository;

    private static final String PHONE_PREFIX = "phone:";
    private static final String EMAIL_PREFIX = "email:";
    private static final int CODE_EXPIRATION_MINUTES = 5;

    public String sendPhoneVerification(String phone) {
        String code = generateVerificationCode();
        cacheRepository.save(PHONE_PREFIX + phone, code, CODE_EXPIRATION_MINUTES, TimeUnit.MINUTES);
        messagingRepository.sendSms(phone, code);
        return "Verification code sent to phone: " + phone;
    }

    public String sendEmailVerification(String email) {
        String code = generateVerificationCode();
        cacheRepository.save(EMAIL_PREFIX + email, code, CODE_EXPIRATION_MINUTES, TimeUnit.MINUTES);
        messagingRepository.sendEmail(email, code);
        return "Verification code sent to email: " + email;
    }

    public boolean verifyPhoneCode(String phone, String code) {
        String cachedCode = (String) cacheRepository.get(PHONE_PREFIX + phone);
        if (cachedCode != null && cachedCode.equals(code)) {
            cacheRepository.delete(PHONE_PREFIX + phone);
            return true;
        }
        return false;
    }

    public boolean verifyEmailCode(String email, String code) {
        String cachedCode = (String) cacheRepository.get(EMAIL_PREFIX + email);
        if (cachedCode != null && cachedCode.equals(code)) {
            cacheRepository.delete(EMAIL_PREFIX + email);
            return true;
        }
        return false;
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}
