package indi.muleisy.ra.pub.netty.packet.battle.request;

import indi.muleisy.ra.pub.netty.packet.RequestPacket;
import indi.muleisy.ra.pub.netty.packet.battle.PacketType;
import lombok.Getter;

@Getter
public class ShoppingRequest extends RequestPacket {

    private Integer id;

    public ShoppingRequest() {
        super(PacketType.SHOPPIN_REQUEST);
    }
}
