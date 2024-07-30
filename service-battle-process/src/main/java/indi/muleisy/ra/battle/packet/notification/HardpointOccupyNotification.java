package indi.muleisy.ra.battle.packet.notification;

import indi.muleisy.ra.battle.packet.PacketType;
import indi.muleisy.ra.pub.netty.packet.Packet;

public class HardpointOccupyNotification extends Packet {

    private final int party;

    private final int hardPointId;

    public HardpointOccupyNotification(int party, int hardPointId) {
        super(PacketType.HARDPOINT_OCCUPY_NOTIFICATION);
        this.party = party;
        this.hardPointId = hardPointId;
    }
}
