package indi.muleisy.ra.battle.packet.request;

import indi.muleisy.ra.battle.packet.PacketType;
import indi.muleisy.ra.pub.netty.packet.PacketTypeI;
import indi.muleisy.ra.pub.netty.packet.RequestPacket;

public class SellingRequest extends RequestPacket {

    public SellingRequest() {
        super(PacketType.SELLING_REQUEST);
    }
}
