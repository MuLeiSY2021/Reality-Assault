package indi.muleisy.ra.pub.rpc;

import indi.muleisy.ra.pub.netty.packet.ResponsePacket;
import lombok.Getter;

@Getter
public enum ResultCode {

    SUCCESS(1,"成功"),

    PARAM_IS_INVALID(1001,"参数无效"),
    PARAM_IS_BLANK(1002,"参数为空"),
    USER_NOT_LOGGED_IN(2001,"用户未登录"),
    USER_LOGIN_ERROR(2002,"用户不存在或密码错误"),
    USER_NOT_EXISITED(2003,"用户不存在"),

    USER_VERIFY_ERROR(2004,"验证码错误"),
    USER_NOT_EXISTED(2005, "用户不存在"),
    NOT_IN_THIS_BATTLE(3001,"不在该战局"),
    NOT_ENOUGH_CLIP(4001, "子弹不足"),
    NOT_ENOUGH_AMMO(4002, "弹夹数不足"),

    ;

    private final Integer code;
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}