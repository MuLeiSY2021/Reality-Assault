package indi.muleisy.ra.battle.packet.request;

import indi.muleisy.ra.pub.netty.packet.PacketTypeI;
import indi.muleisy.ra.pub.netty.packet.RequestPacket;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class InBattleRequestPacket extends RequestPacket {
    private Long timestamp;

    public InBattleRequestPacket(PacketTypeI command) {
        super(command);
    }
}
