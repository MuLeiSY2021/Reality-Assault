package indi.muleisy.ra.pub.netty.packet.battle.request;

import indi.muleisy.ra.pub.netty.packet.RequestPacket;
import indi.muleisy.ra.pub.netty.packet.battle.PacketType;

public class ExitSpawnRequest extends RequestPacket {

    public ExitSpawnRequest() {
        super(PacketType.EXIT_SPAWN_REQUEST);
    }
}
