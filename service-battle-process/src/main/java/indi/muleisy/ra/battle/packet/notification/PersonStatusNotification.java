package indi.muleisy.ra.battle.packet.notification;

import indi.muleisy.ra.battle.packet.PacketType;
import indi.muleisy.ra.pub.netty.packet.Packet;

public class PersonStatusNotification extends Packet {
    //TODO:属性很多，考虑做成dao类

    public PersonStatusNotification() {
        super(PacketType.PERSON_STATUS_NOTIFICATION);
    }

}
