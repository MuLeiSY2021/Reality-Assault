package indi.muleisy.ra.battle.packet.notification;

import indi.muleisy.ra.battle.data.Good;
import indi.muleisy.ra.battle.packet.PacketType;
import indi.muleisy.ra.pub.netty.packet.Packet;
import indi.muleisy.ra.pub.netty.packet.PacketTypeI;
import lombok.Getter;

@Getter
public class EquipmentRenewNotification extends Packet {
    private final Good good;

    public EquipmentRenewNotification(Good good) {
        super(PacketType.EQUIPMENT_RENEW_NOTIFCATION);
        this.good = good;
    }

}
