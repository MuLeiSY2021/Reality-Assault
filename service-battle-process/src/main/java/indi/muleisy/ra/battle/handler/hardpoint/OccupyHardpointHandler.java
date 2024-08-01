package indi.muleisy.ra.battle.handler.hardpoint;

import indi.muleisy.ra.battle.handler.RegisterSessionInboundHandler;
import indi.muleisy.ra.pub.netty.packet.battle.request.IntoHardPointRequest;
import indi.muleisy.ra.pub.redis.dao.BattleFieldDao;
import indi.muleisy.ra.pub.redis.dao.PlayerBattleInfoDao;
import io.netty.channel.ChannelHandlerContext;

public class OccupyHardpointHandler extends RegisterSessionInboundHandler<IntoHardPointRequest> {
    @Override
    protected void channelRead2(ChannelHandlerContext ctx, IntoHardPointRequest intoHardPointRequest) throws Exception {
        Byte party = (Byte) PlayerBattleInfoDao.INSTANCE.get(ctx.channel(), "party");
        if(BattleFieldDao.INSTANCE.get(ctx.channel(), intoHardPointRequest.getId() + "hardPoint").equals(party)) {
            return;
        }
        if(party > 0) {
            BattleFieldDao.INSTANCE.incr(ctx.channel(), intoHardPointRequest.getId() + "hardPointProcess");
        } else {
            BattleFieldDao.INSTANCE.decr(ctx.channel(), intoHardPointRequest.getId() + "hardPointProcess");
        }
    }
}
