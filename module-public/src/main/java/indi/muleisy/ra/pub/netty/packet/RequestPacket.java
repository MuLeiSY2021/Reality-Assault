package indi.muleisy.ra.pub.netty.packet;

import lombok.Getter;

@Getter
public abstract class RequestPacket extends Packet{

    public RequestPacket(PacketTypeI command) {
        super(command);
    }

}
