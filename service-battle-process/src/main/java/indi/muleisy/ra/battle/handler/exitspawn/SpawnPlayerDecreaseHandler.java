package indi.muleisy.ra.battle.handler.exitspawn;

import indi.muleisy.ra.battle.data.BattleField;
import indi.muleisy.ra.battle.handler.AfterRegisterSessionInboundHandler;
import indi.muleisy.ra.battle.packet.request.IntoSpawnRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

import java.util.concurrent.ScheduledFuture;

public class SpawnPlayerDecreaseHandler extends AfterRegisterSessionInboundHandler<IntoSpawnRequest> {

    @Override
    protected void channelRead2(ChannelHandlerContext ctx, IntoSpawnRequest msg) throws Exception {
        BattleField.INSTANCE.decr(ctx, "spawnPlayerNum");
        if((Integer) BattleField.INSTANCE.get(ctx, "status") == 1) {
            ScheduledFuture scheduledFuture = (ScheduledFuture) ctx.channel().attr(AttributeKey.valueOf("gameStart")).get();
            scheduledFuture.cancel(true);
        }
    }
}
