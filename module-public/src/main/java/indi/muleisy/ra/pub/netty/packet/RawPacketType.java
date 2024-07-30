package indi.muleisy.ra.pub.netty.packet;

import indi.muleisy.ra.pub.netty.packet.request.LoginRequest;
import lombok.Getter;

import java.util.concurrent.ConcurrentHashMap;

public enum RawPacketType implements PacketTypeI {
    HEARTBEAT(0,HeartbeatPacket.class),

    LOGIN_RESPONSE(1,ResponsePacket.class),

    LOGIN_REQUEST(2, LoginRequest.class),
    ;

    private static ConcurrentHashMap<Integer,Class<? extends Packet>> MAP;

    public static final Integer CUSTOM_PACKET_TYPE_PREFIX = 100;

    @Getter
    private final int type;

    @Getter
    private final Class<? extends Packet> packetClass;

    RawPacketType(int type, Class<? extends Packet> packetClass) {
        this.type = type;
        this.packetClass = packetClass;
        this.put(this);
    }

    public static void putInNew(PacketTypeI packet) {
        if(MAP == null) {
            MAP = new ConcurrentHashMap<>();
        }
        MAP.put(packet.getTypeId(), packet.getPacketClass());
    }

    public static Class<? extends Packet> getForCommand(int type) {
        return MAP.get(type);
    }

    @Override
    public int getTypeId() {
        return this.type;
    }

    @Override
    public void put(PacketTypeI packet) {
        RawPacketType.putInNew(packet);
    }


}