package indi.muleisy.ra.pub.netty.packet;

import indi.muleisy.ra.pub.rpc.ResultCode;

public abstract class ResponsePacket extends Packet{
    protected ResultCode code;

    public ResponsePacket(PacketTypeI command) {
        super(command);
    }

    public ResponsePacket(PacketTypeI command, ResultCode code) {
        super(command);
        this.code = code;
    }

    public ResponsePacket success() {
        this.code = ResultCode.SUCCESS;
        return this;
    }

    public ResponsePacket failure(ResultCode code) {
        this.code = code;
        return this;
    }

    public abstract ResponsePacket failure();
}
