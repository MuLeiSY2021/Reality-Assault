package indi.muleisy.ra.battle.packet;

import indi.muleisy.ra.pub.netty.packet.RequestPacket;
import lombok.Getter;

@Getter
public class RegisterBattleSessionRequest extends RequestPacket {
    private Integer id;

    public RegisterBattleSessionRequest() {
        super(PacketType.REGISTER_BATTLE_SESSION_REQUEST);
    }
}
