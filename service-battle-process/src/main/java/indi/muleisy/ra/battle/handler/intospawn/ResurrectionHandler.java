package indi.muleisy.ra.battle.handler.intospawn;

import indi.muleisy.ra.battle.data.PlayerBattleInfo;
import indi.muleisy.ra.battle.handler.AfterRegisterSessionInboundHandler;
import indi.muleisy.ra.battle.packet.request.IntoSpawnRequest;
import indi.muleisy.ra.battle.schedule.ResurrectionScheduler;
import io.netty.channel.ChannelHandlerContext;

public class ResurrectionHandler extends AfterRegisterSessionInboundHandler<IntoSpawnRequest> {
    @Override
    protected void channelRead2(ChannelHandlerContext ctx, IntoSpawnRequest intoSpawnRequest) throws Exception {
        if((Integer) PlayerBattleInfo.INSTANCE.get(ctx,"status") != 2) {
            return;
        }
        ResurrectionScheduler scheduler = new ResurrectionScheduler(ctx,getUserId(ctx));
        scheduler.run();
    }
}
