package indi.muleisy.ra.battle.packet.request;

import indi.muleisy.ra.battle.packet.PacketType;
import indi.muleisy.ra.pub.netty.packet.Packet;
import indi.muleisy.ra.pub.netty.packet.RequestPacket;

public class PlayerDeathNotification extends Packet {

    public PlayerDeathNotification() {
        super(PacketType.PLAYER_DEATH_NOTIFICATION);
    }
}
