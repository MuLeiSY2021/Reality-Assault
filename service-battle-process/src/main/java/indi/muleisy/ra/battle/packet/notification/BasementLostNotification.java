package indi.muleisy.ra.battle.packet.notification;

import indi.muleisy.ra.battle.packet.PacketType;
import indi.muleisy.ra.pub.netty.packet.Packet;
import indi.muleisy.ra.pub.netty.packet.PacketTypeI;
import lombok.Getter;

@Getter
public class BasementLostNotification extends Packet {
    private final int party;

    public BasementLostNotification(int party) {
        super(PacketType.BASEMENT_LOST_NOTIFICATION);
        this.party = party;
    }

}
