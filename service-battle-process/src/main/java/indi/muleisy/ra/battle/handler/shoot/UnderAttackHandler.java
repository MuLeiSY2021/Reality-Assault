package indi.muleisy.ra.battle.handler.shoot;

import indi.muleisy.ra.battle.data.PlayerBattleInfo;
import indi.muleisy.ra.battle.handler.AfterRegisterSessionInboundHandler;
import indi.muleisy.ra.battle.packet.request.PlayerDeathNotification;
import indi.muleisy.ra.battle.packet.request.UnderAttackRequest;
import indi.muleisy.ra.battle.packet.response.UnderAttackResponse;
import io.netty.channel.ChannelHandlerContext;

public class UnderAttackHandler extends AfterRegisterSessionInboundHandler<UnderAttackRequest> {
    @Override
    protected void channelRead2(ChannelHandlerContext ctx, UnderAttackRequest underAttackRequest) throws Exception {
        PlayerBattleInfo.INSTANCE.decrBy(ctx,"hp", (long) underAttackRequest.getDamage());
        if((byte)PlayerBattleInfo.INSTANCE.get(ctx,"hp") - underAttackRequest.getDamage() <= 0 &&
                (byte) PlayerBattleInfo.INSTANCE.get(ctx,"status") != 0) {
            //切换为死亡状态
            PlayerBattleInfo.INSTANCE.set(ctx,"status",1);
            ctx.channel().writeAndFlush(new PlayerDeathNotification());
        }

        ctx.writeAndFlush(new UnderAttackResponse());
    }
}
