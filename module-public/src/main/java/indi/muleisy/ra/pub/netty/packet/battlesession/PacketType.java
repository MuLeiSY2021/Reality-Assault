package indi.muleisy.ra.pub.netty.packet.battlesession;

import indi.muleisy.ra.pub.netty.packet.Packet;
import indi.muleisy.ra.pub.netty.packet.PacketTypeI;
import indi.muleisy.ra.pub.netty.packet.RawPacketType;
import indi.muleisy.ra.pub.netty.packet.battlesession.request.ExitBattleSessionRequest;
import indi.muleisy.ra.pub.netty.packet.battlesession.request.JoinBattleSessionRequest;
import indi.muleisy.ra.pub.netty.packet.battlesession.response.ExitBattleSessionResponse;
import indi.muleisy.ra.pub.netty.packet.battlesession.response.JoinBattleSessionResponse;

public enum PacketType implements PacketTypeI {

    JOIN_IN_BATTLE_SESSION_REQUEST((byte) 1, JoinBattleSessionRequest.class),
    JOIN_IN_BATTLE_SESSION_RESPONSE((byte) 2, JoinBattleSessionResponse.class),
    EXIT_IN_BATTLE_SESSION_REQUEST((byte) 3, ExitBattleSessionRequest.class),
    EXIT_IN_BATTLE_SESSION_RESPONSE((byte) 4, ExitBattleSessionResponse.class);

    private final int type;

    private final Class<? extends Packet> packetClass;


    PacketType(byte type, Class<? extends Packet> packetClass) {
        this.type = type+RawPacketType.CUSTOM_PACKET_TYPE_PREFIX+2000;
        this.packetClass = packetClass;
        this.put(this);
    }

    @Override
    public int getTypeId() {
        return this.type;
    }

    @Override
    public Class<? extends Packet> getPacketClass() {
        return this.packetClass;
    }

    @Override
    public void put(PacketTypeI packet) {
        RawPacketType.putInNew(packet);
    }

}