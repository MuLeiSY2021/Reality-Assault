package indi.muleisy.ra.battle.packet.request;

import indi.muleisy.ra.battle.packet.PacketType;
import indi.muleisy.ra.pub.netty.packet.RequestPacket;
import lombok.Getter;

@Getter
public class OutHardPointRequest extends RequestPacket {
    private final byte id;

    public OutHardPointRequest(byte id) {
        super(PacketType.OUT_HARDPOINT_REQUEST);
        this.id = id;
    }
}
