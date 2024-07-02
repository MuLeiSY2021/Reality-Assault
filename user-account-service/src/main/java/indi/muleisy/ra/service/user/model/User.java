package indi.muleisy.ra.service.user.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data
public class User {
    @Id
    private String id;
    private String phone;
    private String email;
    private String oauth2Provider;
    private String oauth2Token;
    private String publicKey;

    // 其他用户信息字段
    private String nickname;
    private String avatar;
    private String gender;
    private String birthday;
    private int age;
}
