package indi.muleisy.ra.battle.packet.response;

import indi.muleisy.ra.battle.packet.PacketType;
import indi.muleisy.ra.pub.netty.packet.RequestPacket;
import indi.muleisy.ra.pub.netty.packet.ResponsePacket;
import indi.muleisy.ra.pub.rpc.ResultCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReloadResponse extends ResponsePacket {
    public boolean mainhand;

    public ReloadResponse() {
        super(PacketType.RELOAD_RESPONSE);
    }

    @Override
    public ResponsePacket failure() {
        this.code = ResultCode.NOT_ENOUGH_AMMO;
        return this;
    }
}
