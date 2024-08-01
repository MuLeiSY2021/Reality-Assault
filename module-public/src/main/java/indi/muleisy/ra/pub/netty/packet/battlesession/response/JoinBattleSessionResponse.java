package indi.muleisy.ra.pub.netty.packet.battlesession.response;

import indi.muleisy.ra.pub.netty.packet.ResponsePacket;
import indi.muleisy.ra.pub.netty.packet.battlesession.PacketType;
import indi.muleisy.ra.pub.rpc.ResultCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinBattleSessionResponse extends ResponsePacket {
    public JoinBattleSessionResponse() {
        super(PacketType.JOIN_IN_BATTLE_SESSION_RESPONSE);
    }

    @Override
    public ResponsePacket failure() {
        this.code = ResultCode.FULL_PEOPLE_ERROR;
        return this;
    }

}
