package indi.muleisy.ra.battle.packet.response;

import indi.muleisy.ra.battle.packet.PacketType;
import indi.muleisy.ra.pub.netty.packet.ResponsePacket;

public class SellingResponse extends ResponsePacket {

    public SellingResponse() {
        super(PacketType.SELLING_RESPONSE);
    }

    @Override
    public ResponsePacket failure() {
        return null;
    }
}
