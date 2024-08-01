package indi.muleisy.ra.pub.netty.packet.battle.request;

import indi.muleisy.ra.pub.netty.packet.RequestPacket;
import indi.muleisy.ra.pub.netty.packet.battle.PacketType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShootRequest extends RequestPacket {
    public boolean mainhand;

    public ShootRequest() {
        super(PacketType.SHOOT_REQUEST);
    }
}
