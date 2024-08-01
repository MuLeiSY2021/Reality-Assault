package indi.muleisy.ra.pub.netty.packet.battle.response;

import indi.muleisy.ra.pub.netty.packet.ResponsePacket;
import indi.muleisy.ra.pub.netty.packet.battle.PacketType;
import indi.muleisy.ra.pub.rpc.ResultCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PositionUpdateResponse extends ResponsePacket {

    public PositionUpdateResponse() {
        super(PacketType.POSITION_UPDATE_RESPONSE);
    }

    @Override
    public ResponsePacket success() {
        super.code = ResultCode.SUCCESS;
        return this;
    }

    @Override
    public ResponsePacket failure() {
        super.code = ResultCode.SUCCESS;
        return this;
    }
}
