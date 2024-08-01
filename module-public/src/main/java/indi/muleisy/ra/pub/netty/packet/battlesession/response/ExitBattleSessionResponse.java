package indi.muleisy.ra.pub.netty.packet.battlesession.response;

import indi.muleisy.ra.pub.netty.packet.ResponsePacket;
import indi.muleisy.ra.pub.netty.packet.battlesession.PacketType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExitBattleSessionResponse extends ResponsePacket {
    public ExitBattleSessionResponse() {
        super(PacketType.EXIT_IN_BATTLE_SESSION_RESPONSE);
    }

    @Override
    public ResponsePacket failure() {
        return null;
    }
}
