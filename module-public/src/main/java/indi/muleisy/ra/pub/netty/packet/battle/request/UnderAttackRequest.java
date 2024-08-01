package indi.muleisy.ra.pub.netty.packet.battle.request;

import indi.muleisy.ra.pub.netty.packet.RequestPacket;
import indi.muleisy.ra.pub.netty.packet.battle.PacketType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnderAttackRequest extends RequestPacket {
    private byte damage;

    public UnderAttackRequest() {
        super(PacketType.UNDER_ATTACK_REQUEST);
    }
}
