package indi.muleisy.ra.pub.netty.packet.battle.response;

import indi.muleisy.ra.pub.netty.packet.ResponsePacket;
import indi.muleisy.ra.pub.netty.packet.battle.PacketType;

public class UnderAttackResponse extends ResponsePacket {


    public UnderAttackResponse() {
        super(PacketType.UNDER_ATTACK_RESPONSE);
    }

    @Override
    public ResponsePacket failure() {
        return null;
    }
}
