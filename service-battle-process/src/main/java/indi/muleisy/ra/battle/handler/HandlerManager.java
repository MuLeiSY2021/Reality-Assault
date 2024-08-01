package indi.muleisy.ra.battle.handler;

import indi.muleisy.ra.battle.handler.death.PlayerDeathHandler;
import indi.muleisy.ra.battle.handler.equipment.EquipmentRenewHandler;
import indi.muleisy.ra.battle.handler.exitspawn.SpawnPlayerDecreaseHandler;
import indi.muleisy.ra.battle.handler.group.BattleSessionRegisterationHandler;
import indi.muleisy.ra.battle.handler.hardpoint.NoOccupyHardpointHandler;
import indi.muleisy.ra.battle.handler.hardpoint.OccupyHardpointHandler;
import indi.muleisy.ra.battle.handler.intospawn.ResurrectionHandler;
import indi.muleisy.ra.battle.handler.intospawn.SpawnPlayerIncreaseHandler;
import indi.muleisy.ra.battle.handler.postion.*;
import indi.muleisy.ra.battle.handler.reload.ReloadHandler;
import indi.muleisy.ra.battle.handler.shoot.ShootHandler;
import indi.muleisy.ra.battle.handler.shoot.UnderAttackHandler;
import indi.muleisy.ra.battle.handler.shop.ShoppingHandler;
import indi.muleisy.ra.pub.netty.codec.PacketCodec;
import indi.muleisy.ra.pub.netty.handler.AuthHandler;
import indi.muleisy.ra.pub.netty.handler.HeartbeatHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public abstract class HandlerManager extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        addHandlers(pipeline);
    }

    protected void addHandlers(ChannelPipeline pipeline) {
        // 读空闲60秒，写空闲30秒
        pipeline.addLast(new IdleStateHandler(60, 30, 0))
                .addLast(new HeartbeatHandler())
                .addLast(new PacketCodec())
                .addLast(new AuthHandler())
                .addLast(new BattleSessionRegisterationHandler())
                .addLast(new PositionUpdateHandler())
                .addLast(new IntoPartySpawnHandler())
                .addLast(new ExitPartySpawnHandler())
                .addLast(new IntoHardPotintHandler())
                .addLast(new ExitHardPointHandler())
                .addLast(new SpawnPlayerIncreaseHandler())
                .addLast(new SpawnPlayerDecreaseHandler())
                .addLast(new ResurrectionHandler())
                .addLast(new SpawnPlayerDecreaseHandler())
                .addLast(new ShoppingHandler())
                .addLast(new OccupyHardpointHandler())
                .addLast(new NoOccupyHardpointHandler())
                .addLast(new ShootHandler())
                .addLast(new ReloadHandler())
                .addLast(new UnderAttackHandler())
                .addLast(new PlayerDeathHandler())
                .addLast(new EquipmentRenewHandler());
    }
}