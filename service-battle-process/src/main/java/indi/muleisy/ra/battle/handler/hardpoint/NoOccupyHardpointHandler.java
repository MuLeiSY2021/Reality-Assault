package indi.muleisy.ra.battle.handler.hardpoint;

import indi.muleisy.ra.battle.handler.RegisterSessionInboundHandler;
import indi.muleisy.ra.pub.netty.packet.battle.request.OutHardPointRequest;
import indi.muleisy.ra.pub.redis.dao.BattleFieldDao;
import indi.muleisy.ra.pub.redis.dao.PlayerBattleInfoDao;
import io.netty.channel.ChannelHandlerContext;

public class NoOccupyHardpointHandler extends RegisterSessionInboundHandler<OutHardPointRequest> {

    @Override
    protected void channelRead2(ChannelHandlerContext ctx, OutHardPointRequest outHardPointRequest) throws Exception {
        Byte party = (Byte) PlayerBattleInfoDao.INSTANCE.get(ctx.channel(), "party");
        if(BattleFieldDao.INSTANCE.get(ctx.channel(), outHardPointRequest.getId() + "hardPoint").equals(party)) {
            return;
        }
        if(party > 0) {
            BattleFieldDao.INSTANCE.decr(ctx.channel(), outHardPointRequest.getId() + "hardPointProcess");
        } else {
            BattleFieldDao.INSTANCE.incr(ctx.channel(), outHardPointRequest.getId() + "hardPointProcess");
        }
    }
}
