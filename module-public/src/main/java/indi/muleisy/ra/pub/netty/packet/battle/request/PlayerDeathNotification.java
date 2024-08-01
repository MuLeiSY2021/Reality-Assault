package indi.muleisy.ra.pub.netty.packet.battle.request;

import indi.muleisy.ra.pub.netty.packet.Packet;
import indi.muleisy.ra.pub.netty.packet.battle.PacketType;

public class PlayerDeathNotification extends Packet {

    public PlayerDeathNotification() {
        super(PacketType.PLAYER_DEATH_NOTIFICATION);
    }
}
