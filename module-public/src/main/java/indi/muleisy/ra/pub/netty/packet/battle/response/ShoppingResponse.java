package indi.muleisy.ra.pub.netty.packet.battle.response;

import indi.muleisy.ra.pub.netty.packet.ResponsePacket;
import indi.muleisy.ra.pub.netty.packet.battle.PacketType;

public class ShoppingResponse extends ResponsePacket {

    public ShoppingResponse() {
        super(PacketType.SHOPPIN_RESPONSE);
    }

    @Override
    public ResponsePacket failure() {
        return null;
    }
}
