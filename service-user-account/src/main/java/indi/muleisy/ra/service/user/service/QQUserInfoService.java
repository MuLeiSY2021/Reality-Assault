package indi.muleisy.ra.service.user.service;

import indi.muleisy.ra.service.user.model.QQErrorResponse;
import indi.muleisy.ra.service.user.model.User;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QQUserInfoService {

    private final RestTemplate restTemplate;

    public QQUserInfoService() {
        this.restTemplate = new RestTemplate();
    }

    public void putUserInfo(User user, String accessToken, String oauthConsumerKey, String openid) {
        String url = "https://graph.qq.com/user/get_user_info";
        String response = restTemplate.getForObject(url + "?access_token=" + accessToken + "&oauth_consumer_key=" + oauthConsumerKey + "&openid=" + openid, String.class);

        JSONObject json;
        if (response != null) {
            json = new JSONObject(response);
            if (json.getInt("ret") != 0) {
                throw new RuntimeException("Failed to get QQ user info: " + json.getString("msg"));
            }
            user.setNickname(json.getString("nickname"));
            user.setAvatar(json.getString("figureurl_qq_1"));
        }
    }

    public String getOpenId(String accessToken) {
        String url = "https://graph.qq.com/oauth2.0/me";
        String response = restTemplate.getForObject(url + "?access_token=" + accessToken, String.class);

        // QQ 返回的格式为 callback( {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} );
        String jsonStr = response.substring(response.indexOf("(") + 1, response.indexOf(")"));
        JSONObject json = new JSONObject(jsonStr);

        return json.getString("openid");
    }


    public boolean verifyToken(String accessToken) {
        String url = "https://graph.qq.com/oauth2.0/me";
        try {
            ResponseEntity<QQErrorResponse> responseEntity = restTemplate.getForEntity(url + "?access_token=" + accessToken, QQErrorResponse.class);
        } catch (Exception e) {
            String response = restTemplate.getForObject(url + "?access_token=" + accessToken, String.class);

            // QQ 返回的格式为 callback( {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} );
            String jsonStr = response.substring(response.indexOf("(") + 1, response.indexOf(")"));
            return jsonStr != null;
        }
        return false; // 验证失败
    }
}
