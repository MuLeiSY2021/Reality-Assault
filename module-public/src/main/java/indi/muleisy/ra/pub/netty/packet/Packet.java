package indi.muleisy.ra.pub.netty.packet;

import lombok.Getter;

@Getter
public abstract class Packet {

    private final int command;

    private final byte version = 1;

    public Packet(PacketTypeI command) {
        this.command = command.getTypeId();
    }

}