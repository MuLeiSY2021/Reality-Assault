package indi.muleisy.ra.battle.handler.intospawn;

import indi.muleisy.ra.battle.data.BattleField;
import indi.muleisy.ra.battle.group.BattleSessionGroupManager;
import indi.muleisy.ra.battle.handler.AfterRegisterSessionInboundHandler;
import indi.muleisy.ra.battle.packet.request.IntoSpawnRequest;
import indi.muleisy.ra.battle.schedule.GameStartScheduler;
import indi.muleisy.ra.pub.utils.redis.RedisUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class SpawnPlayerIncreaseHandler extends AfterRegisterSessionInboundHandler<IntoSpawnRequest> {

    @Override
    protected void channelRead2(ChannelHandlerContext ctx, IntoSpawnRequest msg) throws Exception {
        Long num = BattleField.INSTANCE.incr(ctx, "spawnPlayerNum");
        if(num.equals(RedisUtil.getJedis().incr(BattleField.INSTANCE.compose(ctx, "playerNum")))
                && (Integer) BattleField.INSTANCE.get(ctx, "status") != 1) {
            //把状态改成倒计时
            BattleField.INSTANCE.set(ctx,"status",1);

            if((Integer) BattleField.INSTANCE.get(ctx,"status") == 1) {
                //添加游戏开始的十秒钟定时任务
                ScheduledFuture scheduledFuture = ctx.executor().schedule(new GameStartScheduler(ctx,BattleSessionGroupManager.INSTANCE.getGroupId(ctx)),10, TimeUnit.SECONDS);
                ctx.channel().attr(AttributeKey.valueOf("gameStart")).set(scheduledFuture);
            }

        }
    }
}
