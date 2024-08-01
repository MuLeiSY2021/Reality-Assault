package indi.muleisy.ra.pub.netty.packet.battle.response;

import indi.muleisy.ra.pub.netty.packet.ResponsePacket;
import indi.muleisy.ra.pub.netty.packet.battle.PacketType;

public class SellingResponse extends ResponsePacket {

    public SellingResponse() {
        super(PacketType.SELLING_RESPONSE);
    }

    @Override
    public ResponsePacket failure() {
        return null;
    }
}
