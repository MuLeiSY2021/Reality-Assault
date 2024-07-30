package indi.muleisy.ra.pub.netty.packet;

public class HeartbeatPacket extends RequestPacket {
    public HeartbeatPacket() {
        super(RawPacketType.HEARTBEAT);
    }
}