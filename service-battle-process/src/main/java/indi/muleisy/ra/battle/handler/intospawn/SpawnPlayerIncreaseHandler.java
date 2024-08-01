package indi.muleisy.ra.battle.handler.intospawn;

import indi.muleisy.ra.battle.handler.RegisterSessionInboundHandler;
import indi.muleisy.ra.battle.schedule.GameStartScheduler;
import indi.muleisy.ra.pub.netty.packet.battle.request.IntoSpawnRequest;
import indi.muleisy.ra.pub.redis.dao.BattleFieldDao;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class SpawnPlayerIncreaseHandler extends RegisterSessionInboundHandler<IntoSpawnRequest> {

    @Override
    protected void channelRead2(ChannelHandlerContext ctx, IntoSpawnRequest msg) throws Exception {
        Long num = BattleFieldDao.INSTANCE.incr(ctx.channel(), "spawnPlayerNum");
        if(num.equals(BattleFieldDao.INSTANCE.incr(ctx.channel(), "playerNum"))
                && (Integer) BattleFieldDao.INSTANCE.get(ctx.channel(), "status") != 1) {
            //把状态改成倒计时
            BattleFieldDao.INSTANCE.set(ctx.channel(),"status",1);

            if((Integer) BattleFieldDao.INSTANCE.get(ctx.channel(),"status") == 1) {
                //添加游戏开始的十秒钟定时任务
                ScheduledFuture scheduledFuture = ctx.executor().schedule(
                        new GameStartScheduler(ctx.channel(), ctx.executor()),10, TimeUnit.SECONDS);
                ctx.channel().attr(AttributeKey.valueOf("gameStart")).set(scheduledFuture);
            }

        }
    }
}
