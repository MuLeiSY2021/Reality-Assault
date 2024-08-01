package indi.muleisy.ra.service.user.model;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "users")
@Data
public class User {
    @Indexed
    private Long id;
    private String phone;
    private String email;
    private String QQToken;
    private String publicKey;
    private String weChatToken;
    private String githubToken;

    // 其他用户信息字段
    private Date birthdate;
    private String nickname;
    private String avatar;
    private boolean gender;
    private int age;
}
