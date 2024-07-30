package indi.muleisy.ra.service.user.service;

import indi.muleisy.ra.pub.config.ProviderConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OAuth2Service {

    private final RestTemplate restTemplate;

    @Autowired
    private QQUserInfoService qqUserInfoService;

    public OAuth2Service() {
        this.restTemplate = new RestTemplate();
    }

    public String getUserByProviderAndToken(String provider, String token) {
        switch (ProviderConstant.nameOf(provider)) {
            case QQ_PROVIDER_NAME:
                return qqUserInfoService.getOpenId(token);
            case WECHAT_PROVIDER_NAME:
                //TODO:
            case GITHUB_PROVIDER_NAME:
                //TODO:
            default:
                throw new IllegalArgumentException("Unsupported provider: " + provider);
        }
    }

    public boolean verifyThirdPartyToken(String provider, String token) {
        switch (ProviderConstant.nameOf(provider)) {
            case QQ_PROVIDER_NAME:
                return qqUserInfoService.verifyToken(token);
            case WECHAT_PROVIDER_NAME:
                //TODO:
            case GITHUB_PROVIDER_NAME:
                //TODO:
            default:
                throw new IllegalArgumentException("Unsupported provider: " + provider);
        }
    }
}
