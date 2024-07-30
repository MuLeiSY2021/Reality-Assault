package indi.muleisy.ra.battle.packet.request;

import indi.muleisy.ra.battle.packet.PacketType;
import indi.muleisy.ra.pub.netty.packet.RequestPacket;
import lombok.Getter;

@Getter
public class IntoHardPointRequest extends RequestPacket {
    private final byte id;

    public IntoHardPointRequest(byte id) {
        super(PacketType.INTO_HARDPOINT_REQUEST);
        this.id = id;
    }
}
