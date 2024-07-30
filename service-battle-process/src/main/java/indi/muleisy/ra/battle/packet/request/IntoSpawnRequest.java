package indi.muleisy.ra.battle.packet.request;

import indi.muleisy.ra.battle.packet.PacketType;
import indi.muleisy.ra.pub.netty.packet.PacketTypeI;
import indi.muleisy.ra.pub.netty.packet.RequestPacket;

public class IntoSpawnRequest extends RequestPacket {

    public IntoSpawnRequest() {
        super(PacketType.INTO_SPAWN_REQUEST);
    }
}
