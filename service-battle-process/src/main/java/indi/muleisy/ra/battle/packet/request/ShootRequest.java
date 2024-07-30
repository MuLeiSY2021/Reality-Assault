package indi.muleisy.ra.battle.packet.request;

import indi.muleisy.ra.battle.packet.PacketType;
import indi.muleisy.ra.pub.netty.packet.RequestPacket;
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
