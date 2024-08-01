package indi.muleisy.ra.pub.netty.packet.battle.request;

import indi.muleisy.ra.pub.netty.packet.RequestPacket;
import indi.muleisy.ra.pub.netty.packet.battle.PacketType;

public class IntoSpawnRequest extends RequestPacket {

    public IntoSpawnRequest() {
        super(PacketType.INTO_SPAWN_REQUEST);
    }
}
