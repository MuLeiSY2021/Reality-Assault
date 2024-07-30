package indi.muleisy.ra.battle.packet.response;

import indi.muleisy.ra.battle.packet.PacketType;
import indi.muleisy.ra.pub.netty.packet.PacketTypeI;
import indi.muleisy.ra.pub.netty.packet.ResponsePacket;

public class ShoppingResponse extends ResponsePacket {

    public ShoppingResponse() {
        super(PacketType.SHOPPIN_RESPONSE);
    }

    @Override
    public ResponsePacket failure() {
        return null;
    }
}
