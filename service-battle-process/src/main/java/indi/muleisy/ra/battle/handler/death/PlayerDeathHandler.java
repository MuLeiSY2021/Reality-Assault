package indi.muleisy.ra.battle.handler.death;

import indi.muleisy.ra.battle.data.BattleField;
import indi.muleisy.ra.battle.handler.AfterRegisterSessionInboundHandler;
import indi.muleisy.ra.battle.packet.request.PlayerDeathNotification;
import indi.muleisy.ra.battle.schedule.ResurrectionScheduler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.ScheduledFuture;

import java.util.concurrent.TimeUnit;

public class PlayerDeathHandler extends AfterRegisterSessionInboundHandler<PlayerDeathNotification> {

    private ScheduledFuture<?> scheduledFuture;

    @Override
    protected void channelRead2(ChannelHandlerContext ctx, PlayerDeathNotification playerDeathNotification) throws Exception {
        // 在Channel激活时安排延时任务
        scheduledFuture = ctx.executor().schedule(new ResurrectionScheduler(ctx, getUserId(ctx)),
                (Long) BattleField.INSTANCE.get(ctx,"resurrectionTime"),
                TimeUnit.SECONDS);
    }
}
