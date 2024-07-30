package indi.muleisy.ra.battle.packet.response;

import indi.muleisy.ra.battle.packet.PacketType;
import indi.muleisy.ra.pub.netty.packet.PacketTypeI;
import indi.muleisy.ra.pub.netty.packet.ResponsePacket;
import indi.muleisy.ra.pub.rpc.ResultCode;

public class RegisterBattleSessionResponse extends ResponsePacket {

    public RegisterBattleSessionResponse() {
        super(PacketType.REGISTER_BATTLE_SESSION_RESPONSE);
    }

    @Override
    public ResponsePacket success() {
        super.code = ResultCode.SUCCESS;
        return this;
    }

    @Override
    public ResponsePacket failure() {
        super.code = ResultCode.NOT_IN_THIS_BATTLE;
        return this;
    }
}
