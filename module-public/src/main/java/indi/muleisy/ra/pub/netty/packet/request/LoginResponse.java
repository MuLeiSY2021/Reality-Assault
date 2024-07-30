package indi.muleisy.ra.pub.netty.packet.request;

import indi.muleisy.ra.pub.netty.packet.RawPacketType;
import indi.muleisy.ra.pub.netty.packet.ResponsePacket;
import indi.muleisy.ra.pub.rpc.ResultCode;

public class LoginResponse extends ResponsePacket {

    public LoginResponse() {
        super(RawPacketType.LOGIN_RESPONSE);
    }

    @Override
    public LoginResponse success() {
        super.code = ResultCode.SUCCESS;
        return this;
    }

    @Override
    public LoginResponse failure() {
        super.code = ResultCode.USER_LOGIN_ERROR;
        return this;
    }
}
