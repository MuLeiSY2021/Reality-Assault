package indi.muleisy.ra.pub.netty.packet.battle.notification;

import indi.muleisy.ra.pub.netty.packet.Packet;
import indi.muleisy.ra.pub.netty.packet.battle.PacketType;
import lombok.Getter;

@Getter
public class BasementLostNotification extends Packet {
    public static final String TOPIC = "BasementLostNotification";

    private final int party;

    public BasementLostNotification(int party) {
        super(PacketType.BASEMENT_LOST_NOTIFICATION);
        this.party = party;
    }

}
