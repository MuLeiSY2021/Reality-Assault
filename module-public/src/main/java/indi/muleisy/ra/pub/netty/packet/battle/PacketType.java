package indi.muleisy.ra.pub.netty.packet.battle;

import indi.muleisy.ra.pub.netty.packet.Packet;
import indi.muleisy.ra.pub.netty.packet.PacketTypeI;
import indi.muleisy.ra.pub.netty.packet.RawPacketType;
import indi.muleisy.ra.pub.netty.packet.battle.notification.*;
import indi.muleisy.ra.pub.netty.packet.battle.request.*;
import indi.muleisy.ra.pub.netty.packet.battle.response.RegisterBattleSessionResponse;
import indi.muleisy.ra.pub.netty.packet.battle.response.ReloadResponse;
import indi.muleisy.ra.pub.netty.packet.battle.response.ShoppingResponse;
import indi.muleisy.ra.pub.netty.packet.battle.response.UnderAttackResponse;

public enum PacketType implements PacketTypeI {

    POSITION_UPDATE_REQUEST((byte) 1, PositionUpdateRequest.class),

    POSITION_UPDATE_RESPONSE((byte) 2, PositionUpdateRequest.class),

    INTO_SPAWN_REQUEST((byte) 3, IntoSpawnRequest.class),

    EXIT_SPAWN_REQUEST((byte) 5, ExitSpawnRequest.class),

    INTO_HARDPOINT_REQUEST((byte) 7, OutHardPointRequest.class),

    OUT_HARDPOINT_REQUEST((byte) 9, OutHardPointRequest.class),

    REGISTER_BATTLE_SESSION_REQUEST((byte) 10, RegisterBattleSessionRequest.class),

    REGISTER_BATTLE_SESSION_RESPONSE((byte) 11, RegisterBattleSessionResponse.class),

    GAME_START_NOTIFICATION((byte) 12, GameStartNotification.class),
    SHOPPIN_REQUEST((byte) 13, ShoppingRequest.class),
    SHOPPIN_RESPONSE((byte) 14, ShoppingResponse.class),

    EQUIPMENT_RENEW_NOTIFCATION((byte) 16, EquipmentRenewNotification.class),

    SELLING_REQUEST((byte) 17, SellingRequest.class),

    SELLING_RESPONSE((byte) 18, ShoppingResponse.class),

    SHOOT_REQUEST((byte) 19, ShootRequest.class),

    SHOOT_RESPONSE((byte) 20, ShoppingResponse.class),

    RELOAD_REQUEST((byte) 21, ReloadRequest.class),


    RELOAD_RESPONSE((byte) 22, ReloadResponse.class),

    UNDER_ATTACK_REQUEST((byte) 23, UnderAttackRequest.class),

    UNDER_ATTACK_RESPONSE((byte) 24, UnderAttackResponse.class),

    PLAYER_DEATH_NOTIFICATION((byte) 25, PlayerDeathNotification.class),

    BASEMENT_LOST_NOTIFICATION((byte) 26, BasementLostNotification.class),

    HARDPOINT_OCCUPY_NOTIFICATION((byte) 27, HardpointOccupyNotification.class),

    PERSON_STATUS_NOTIFICATION((byte) 28, PersonStatusNotification.class),

    ;

    private final int type;

    private final Class<? extends Packet> packetClass;


    PacketType(byte type, Class<? extends Packet> packetClass) {
        this.type = type+RawPacketType.CUSTOM_PACKET_TYPE_PREFIX+1000;
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