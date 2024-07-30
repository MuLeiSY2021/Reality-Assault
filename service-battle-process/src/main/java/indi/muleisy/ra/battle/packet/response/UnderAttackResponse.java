package indi.muleisy.ra.battle.packet.response;

import indi.muleisy.ra.battle.packet.PacketType;
import indi.muleisy.ra.pub.netty.packet.PacketTypeI;
import indi.muleisy.ra.pub.netty.packet.ResponsePacket;

public class UnderAttackResponse extends ResponsePacket {


    public UnderAttackResponse() {
        super(PacketType.UNDER_ATTACK_RESPONSE);
    }

    @Override
    public ResponsePacket failure() {
        return null;
    }
}
