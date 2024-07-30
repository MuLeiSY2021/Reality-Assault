package indi.muleisy.ra.battle.handler.exitspawn;

import indi.muleisy.ra.battle.data.PlayerBattleInfo;
import indi.muleisy.ra.battle.handler.AfterRegisterSessionInboundHandler;
import indi.muleisy.ra.battle.packet.request.IntoSpawnRequest;
import io.netty.channel.ChannelHandlerContext;

public class OutBornHandler extends AfterRegisterSessionInboundHandler<IntoSpawnRequest> {
    @Override
    protected void channelRead2(ChannelHandlerContext ctx, IntoSpawnRequest intoSpawnRequest) throws Exception {
        PlayerBattleInfo.INSTANCE.set(ctx, "inborn", false);
    }
}
