package indi.muleisy.ra.pub.netty.packet.request;

import indi.muleisy.ra.pub.netty.packet.PacketTypeI;
import indi.muleisy.ra.pub.netty.packet.RequestPacket;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest extends RequestPacket {

    private String rsaToken;

    public LoginRequest(PacketTypeI type) {
        super(type);
    }
}
