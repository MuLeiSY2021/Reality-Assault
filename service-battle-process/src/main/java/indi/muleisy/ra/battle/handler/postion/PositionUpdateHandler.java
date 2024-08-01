package indi.muleisy.ra.battle.handler.postion;

import indi.muleisy.ra.battle.handler.RegisterSessionInboundHandler;
import indi.muleisy.ra.pub.netty.packet.battle.request.PositionUpdateRequest;
import indi.muleisy.ra.pub.netty.packet.battle.response.PositionUpdateResponse;
import indi.muleisy.ra.pub.redis.dao.PlayerBattleInfoDao;
import io.netty.channel.ChannelHandlerContext;

public class PositionUpdateHandler extends RegisterSessionInboundHandler<PositionUpdateRequest> {
    @Override
    protected void channelRead2(ChannelHandlerContext ctx, PositionUpdateRequest msg) throws Exception {
        PlayerBattleInfoDao.INSTANCE.set(ctx.channel(),"position", new Double[]{msg.getLongitude(), msg.getLatitude()});
        ctx.channel().write(new PositionUpdateResponse());
    }
}
