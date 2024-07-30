package indi.muleisy.ra.battle.packet.request;

import indi.muleisy.ra.battle.packet.PacketType;
import indi.muleisy.ra.pub.netty.packet.RequestPacket;

public class ExitSpawnRequest extends RequestPacket {

    public ExitSpawnRequest() {
        super(PacketType.EXIT_SPAWN_REQUEST);
    }
}
