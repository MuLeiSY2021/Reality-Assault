package indi.muleisy.ra.battle.handler.exitspawn;

import indi.muleisy.ra.battle.handler.RegisterSessionInboundHandler;
import indi.muleisy.ra.pub.netty.packet.battle.request.IntoSpawnRequest;
import indi.muleisy.ra.pub.redis.dao.PlayerBattleInfoDao;
import io.netty.channel.ChannelHandlerContext;

public class OutBornHandler extends RegisterSessionInboundHandler<IntoSpawnRequest> {
    @Override
    protected void channelRead2(ChannelHandlerContext ctx, IntoSpawnRequest intoSpawnRequest) throws Exception {
        PlayerBattleInfoDao.INSTANCE.set(ctx.channel(), "inborn", false);
    }
}
