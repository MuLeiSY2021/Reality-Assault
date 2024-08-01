package indi.muleisy.ra.pub.netty.packet.battle.request;

import indi.muleisy.ra.pub.netty.packet.RequestPacket;
import indi.muleisy.ra.pub.netty.packet.battle.PacketType;
import lombok.Getter;

@Getter
public class OutHardPointRequest extends RequestPacket {
    private final byte id;

    public OutHardPointRequest(byte id) {
        super(PacketType.OUT_HARDPOINT_REQUEST);
        this.id = id;
    }
}
