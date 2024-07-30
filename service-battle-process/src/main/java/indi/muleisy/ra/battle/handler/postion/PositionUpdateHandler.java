package indi.muleisy.ra.battle.handler.postion;

import indi.muleisy.ra.battle.data.PlayerBattleInfo;
import indi.muleisy.ra.battle.handler.AfterRegisterSessionInboundHandler;
import indi.muleisy.ra.battle.packet.request.PositionUpdateRequest;
import indi.muleisy.ra.battle.packet.response.PositionUpdateResponse;
import io.netty.channel.ChannelHandlerContext;

public class PositionUpdateHandler extends AfterRegisterSessionInboundHandler<PositionUpdateRequest> {
    @Override
    protected void channelRead2(ChannelHandlerContext ctx, PositionUpdateRequest msg) throws Exception {
        PlayerBattleInfo.INSTANCE.set(ctx,"position", new Double[]{msg.getLongitude(), msg.getLatitude()});
        ctx.channel().write(new PositionUpdateResponse());
    }
}
