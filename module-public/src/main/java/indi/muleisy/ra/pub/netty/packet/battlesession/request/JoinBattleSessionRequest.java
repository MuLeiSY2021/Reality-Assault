package indi.muleisy.ra.pub.netty.packet.battlesession.request;

import indi.muleisy.ra.pub.netty.packet.RequestPacket;
import indi.muleisy.ra.pub.netty.packet.battlesession.PacketType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinBattleSessionRequest extends RequestPacket {

    private Double latitude, longitude;

    public JoinBattleSessionRequest() {
        super(PacketType.JOIN_IN_BATTLE_SESSION_REQUEST);
    }
}
