package indi.muleisy.ra.pub.netty.packet.battle.notification;

import indi.muleisy.ra.pub.netty.packet.Packet;
import indi.muleisy.ra.pub.netty.packet.battle.PacketType;

public class HardpointOccupyNotification extends Packet {
    public static final String TOPIC = "HardpointOccupyNotification";

    private final int party;

    private final int hardPointId;

    public HardpointOccupyNotification(int party, int hardPointId) {
        super(PacketType.HARDPOINT_OCCUPY_NOTIFICATION);
        this.party = party;
        this.hardPointId = hardPointId;
    }
}
