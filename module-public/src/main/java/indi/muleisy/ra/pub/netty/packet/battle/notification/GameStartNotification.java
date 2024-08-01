package indi.muleisy.ra.pub.netty.packet.battle.notification;

import indi.muleisy.ra.pub.netty.packet.Packet;
import indi.muleisy.ra.pub.netty.packet.battle.PacketType;

public class GameStartNotification extends Packet {
    public static final String TOPIC = "game-start";

    public GameStartNotification() {
        super(PacketType.GAME_START_NOTIFICATION);
    }
}
