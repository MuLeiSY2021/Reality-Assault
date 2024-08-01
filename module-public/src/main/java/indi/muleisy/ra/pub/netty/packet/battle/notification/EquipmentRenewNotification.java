package indi.muleisy.ra.pub.netty.packet.battle.notification;

import indi.muleisy.ra.pub.netty.packet.Packet;
import indi.muleisy.ra.pub.netty.packet.battle.PacketType;
import indi.muleisy.ra.pub.redis.Good;
import lombok.Getter;

@Getter
public class EquipmentRenewNotification extends Packet {
    private final Good good;

    public EquipmentRenewNotification(Good good) {
        super(PacketType.EQUIPMENT_RENEW_NOTIFCATION);
        this.good = good;
    }

}
