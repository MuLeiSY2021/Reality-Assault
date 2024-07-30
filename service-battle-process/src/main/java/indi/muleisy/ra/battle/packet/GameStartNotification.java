package indi.muleisy.ra.battle.packet;

import indi.muleisy.ra.pub.netty.packet.Packet;
import indi.muleisy.ra.pub.netty.packet.PacketTypeI;

public class GameStartNotification extends Packet {
    public GameStartNotification() {
        super(PacketType.GAME_START_NOTIFICATION);
    }
}
