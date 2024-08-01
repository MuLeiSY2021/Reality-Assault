package indi.muleisy.ra.battle.handler.exitspawn;

import indi.muleisy.ra.battle.handler.RegisterSessionInboundHandler;
import indi.muleisy.ra.pub.netty.packet.battle.request.IntoSpawnRequest;
import indi.muleisy.ra.pub.redis.dao.BattleFieldDao;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

import java.util.concurrent.ScheduledFuture;

public class SpawnPlayerDecreaseHandler extends RegisterSessionInboundHandler<IntoSpawnRequest> {

    @Override
    protected void channelRead2(ChannelHandlerContext ctx, IntoSpawnRequest msg) throws Exception {
        BattleFieldDao.INSTANCE.decr(ctx.channel(), "spawnPlayerNum");
        if((Integer) BattleFieldDao.INSTANCE.get(ctx.channel(), "status") == 1) {
            ScheduledFuture scheduledFuture = (ScheduledFuture) ctx.channel().attr(AttributeKey.valueOf("gameStart")).get();
            scheduledFuture.cancel(true);
        }
    }
}
