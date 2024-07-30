package indi.muleisy.ra.battle.packet.request;

import indi.muleisy.ra.battle.packet.PacketType;
import indi.muleisy.ra.pub.netty.packet.PacketTypeI;
import indi.muleisy.ra.pub.netty.packet.RequestPacket;
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
