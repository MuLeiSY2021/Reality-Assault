package indi.muleisy.ra.battle.handler.death;

import indi.muleisy.ra.battle.handler.RegisterSessionInboundHandler;
import indi.muleisy.ra.battle.schedule.ResurrectionScheduler;
import indi.muleisy.ra.pub.netty.packet.battle.request.PlayerDeathNotification;
import indi.muleisy.ra.pub.redis.dao.BattleFieldDao;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.ScheduledFuture;

import java.util.concurrent.TimeUnit;

public class PlayerDeathHandler extends RegisterSessionInboundHandler<PlayerDeathNotification> {

    private ScheduledFuture<?> scheduledFuture;

    @Override
    protected void channelRead2(ChannelHandlerContext ctx, PlayerDeathNotification playerDeathNotification) throws Exception {
        // 在Channel激活时安排延时任务
        scheduledFuture = ctx.executor().schedule(new ResurrectionScheduler(ctx.channel()),
                (Long) BattleFieldDao.INSTANCE.get(ctx.channel(),"resurrectionTime"),
                TimeUnit.SECONDS);
    }
}
