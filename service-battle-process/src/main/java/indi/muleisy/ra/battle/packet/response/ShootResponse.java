package indi.muleisy.ra.battle.packet.response;

import indi.muleisy.ra.battle.packet.PacketType;
import indi.muleisy.ra.pub.netty.packet.RequestPacket;
import indi.muleisy.ra.pub.netty.packet.ResponsePacket;
import indi.muleisy.ra.pub.rpc.ResultCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShootResponse extends ResponsePacket {

    public ShootResponse() {
        super(PacketType.SHOOT_RESPONSE);
    }

    @Override
    public ResponsePacket failure() {
        this.code = ResultCode.NOT_ENOUGH_CLIP;
        return this;
    }
}
