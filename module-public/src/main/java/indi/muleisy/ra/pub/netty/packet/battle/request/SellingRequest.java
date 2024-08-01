package indi.muleisy.ra.pub.netty.packet.battle.request;

import indi.muleisy.ra.pub.netty.packet.RequestPacket;
import indi.muleisy.ra.pub.netty.packet.battle.PacketType;

public class SellingRequest extends RequestPacket {

    public SellingRequest() {
        super(PacketType.SELLING_REQUEST);
    }
}
