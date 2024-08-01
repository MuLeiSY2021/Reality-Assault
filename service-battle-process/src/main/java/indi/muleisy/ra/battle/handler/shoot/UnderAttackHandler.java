package indi.muleisy.ra.battle.handler.shoot;

import indi.muleisy.ra.battle.handler.RegisterSessionInboundHandler;
import indi.muleisy.ra.pub.netty.packet.battle.request.PlayerDeathNotification;
import indi.muleisy.ra.pub.netty.packet.battle.request.UnderAttackRequest;
import indi.muleisy.ra.pub.netty.packet.battle.response.UnderAttackResponse;
import indi.muleisy.ra.pub.redis.dao.PlayerBattleInfoDao;
import io.netty.channel.ChannelHandlerContext;

public class UnderAttackHandler extends RegisterSessionInboundHandler<UnderAttackRequest> {
    @Override
    protected void channelRead2(ChannelHandlerContext ctx, UnderAttackRequest underAttackRequest) throws Exception {
        PlayerBattleInfoDao.INSTANCE.decrBy(ctx.channel(),"hp", (long) underAttackRequest.getDamage());
        if((byte) PlayerBattleInfoDao.INSTANCE.get(ctx.channel(),"hp") - underAttackRequest.getDamage() <= 0 &&
                (byte) PlayerBattleInfoDao.INSTANCE.get(ctx.channel(),"status") != 0) {
            //切换为死亡状态
            PlayerBattleInfoDao.INSTANCE.set(ctx.channel(),"status",1);
            ctx.channel().writeAndFlush(new PlayerDeathNotification());
        }

        ctx.writeAndFlush(new UnderAttackResponse());
    }
}
