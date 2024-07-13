package indi.muleisy.ra.rpc;

import lombok.Getter;

@Getter
public enum ResultCode {

    SUCCESS(1,"成功"),

    PARAM_IS_INVALID(1001,"参数无效"),
    PARAM_IS_BLANK(1002,"参数为空"),
    USER_NOT_LOGGED_IN(2001,"用户未登录"),
    USER_LOGIN_ERROR(2002,"用户不存在或密码错误"),
    USER_NOT_EXISITED(2003,"用户不存在"),

    USER_VERIFY_ERROR(2004,"验证码错误");

    private final Integer code;
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}