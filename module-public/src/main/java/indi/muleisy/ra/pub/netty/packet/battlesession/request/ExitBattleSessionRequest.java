package indi.muleisy.ra.pub.netty.packet.battlesession.request;

import indi.muleisy.ra.pub.netty.packet.RequestPacket;
import indi.muleisy.ra.pub.netty.packet.battlesession.PacketType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExitBattleSessionRequest extends RequestPacket {
    public ExitBattleSessionRequest() {
        super(PacketType.EXIT_IN_BATTLE_SESSION_REQUEST);
    }
}
