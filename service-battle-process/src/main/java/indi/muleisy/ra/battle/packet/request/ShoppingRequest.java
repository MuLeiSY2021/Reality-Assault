package indi.muleisy.ra.battle.packet.request;

import indi.muleisy.ra.battle.packet.PacketType;
import indi.muleisy.ra.pub.netty.packet.PacketTypeI;
import indi.muleisy.ra.pub.netty.packet.RequestPacket;
import lombok.Getter;

@Getter
public class ShoppingRequest extends RequestPacket {

    private Integer id;

    public ShoppingRequest() {
        super(PacketType.SHOPPIN_REQUEST);
    }
}
