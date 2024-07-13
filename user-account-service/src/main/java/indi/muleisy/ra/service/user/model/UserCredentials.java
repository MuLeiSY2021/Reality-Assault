package indi.muleisy.ra.service.user.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data

public class UserCredentials {
    @Id
    private Long id; // 自增主键

    private String password;

    private String salt;
}
