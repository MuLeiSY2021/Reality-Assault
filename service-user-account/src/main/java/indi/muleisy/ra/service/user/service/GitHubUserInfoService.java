package indi.muleisy.ra.service.user.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GitHubUserInfoService {

    private final RestTemplate restTemplate;

    public GitHubUserInfoService() {
        this.restTemplate = new RestTemplate();
    }

    public String getUserInfo(String accessToken) {
        //TODO Auto-generated method stub
        String url = "https://api.github.com/user";
        String response = restTemplate.getForObject(url + "?access_token=" + accessToken, String.class);
        // 这里根据 GitHub 返回的 JSON 结构解析用户信息
        // 假设返回的是 JSON 字符串，可以直接返回或进一步解析
        return response;
    }
}
