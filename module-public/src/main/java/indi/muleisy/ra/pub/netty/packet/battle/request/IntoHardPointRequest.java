package indi.muleisy.ra.pub.netty.packet.battle.request;

import indi.muleisy.ra.pub.netty.packet.RequestPacket;
import indi.muleisy.ra.pub.netty.packet.battle.PacketType;
import lombok.Getter;

@Getter
public class IntoHardPointRequest extends RequestPacket {
    private final byte id;

    public IntoHardPointRequest(byte id) {
        super(PacketType.INTO_HARDPOINT_REQUEST);
        this.id = id;
    }
}
