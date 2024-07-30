package indi.muleisy.ra.pub.netty.packet;

public interface PacketTypeI {

    int getTypeId();

    Class<? extends Packet> getPacketClass();

    void put(PacketTypeI packet);
}
