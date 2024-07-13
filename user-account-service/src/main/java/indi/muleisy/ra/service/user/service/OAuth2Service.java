package indi.muleisy.ra.service.user.service;

import indi.muleisy.ra.service.user.model.User;
import indi.muleisy.ra.utils.Config;
import indi.muleisy.ra.utils.ProviderConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class OAuth2Service {

    private final RestTemplate restTemplate;

    @Autowired
    private QQUserInfoService qqUserInfoService;

    public OAuth2Service() {
        this.restTemplate = new RestTemplate();
    }

    public String getUserInfo(String provider, String token) {
        switch (provider.toLowerCase()) {
            case "github":
                return fetchGitHubUserInfo(token);
            case "qq":
                return fetchQQUserInfo(token);
            case "wechat":
                return fetchWeChatUserInfo(token);
            default:
                throw new IllegalArgumentException("Unsupported provider: " + provider);
        }
    }

    private String fetchGitHubUserInfo(String token) {
        String url = "https://api.github.com/user";
        return fetchUserInfo(url, token);
    }

    private String fetchQQUserInfo(String token) {
        String url = "https://graph.qq.com/user/get_user_info";
        return fetchUserInfo(url, token);
    }

    private String fetchWeChatUserInfo(String token) {
        String url = "https://api.weixin.qq.com/sns/userinfo";
        return fetchUserInfo(url, token);
    }

    private String fetchUserInfo(String url, String token) {
        try {
            return restTemplate.getForObject(url + "?access_token=" + token, String.class);
        } catch (HttpClientErrorException e) {
            throw new IllegalArgumentException("Invalid token or unable to fetch user info from " + url, e);
        }
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
}
