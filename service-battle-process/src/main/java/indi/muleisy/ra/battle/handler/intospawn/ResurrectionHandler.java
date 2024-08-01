package indi.muleisy.ra.battle.handler.intospawn;

import indi.muleisy.ra.battle.handler.RegisterSessionInboundHandler;
import indi.muleisy.ra.battle.schedule.ResurrectionScheduler;
import indi.muleisy.ra.pub.netty.packet.battle.request.IntoSpawnRequest;
import indi.muleisy.ra.pub.redis.dao.PlayerBattleInfoDao;
import io.netty.channel.ChannelHandlerContext;

public class ResurrectionHandler extends RegisterSessionInboundHandler<IntoSpawnRequest> {
    @Override
    protected void channelRead2(ChannelHandlerContext ctx, IntoSpawnRequest intoSpawnRequest) throws Exception {
        if((Integer) PlayerBattleInfoDao.INSTANCE.get(ctx.channel(),"status") != 2) {
            return;
        }
        ResurrectionScheduler scheduler = new ResurrectionScheduler(ctx.channel());
        scheduler.run();
    }
}
