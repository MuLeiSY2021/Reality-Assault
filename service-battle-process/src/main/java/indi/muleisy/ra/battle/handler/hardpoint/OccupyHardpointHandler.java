package indi.muleisy.ra.battle.handler.hardpoint;

import indi.muleisy.ra.battle.data.BattleField;
import indi.muleisy.ra.battle.data.PlayerBattleInfo;
import indi.muleisy.ra.battle.handler.AfterRegisterSessionInboundHandler;
import indi.muleisy.ra.battle.packet.request.IntoHardPointRequest;
import io.netty.channel.ChannelHandlerContext;

public class OccupyHardpointHandler extends AfterRegisterSessionInboundHandler<IntoHardPointRequest> {
    @Override
    protected void channelRead2(ChannelHandlerContext ctx, IntoHardPointRequest intoHardPointRequest) throws Exception {
        Byte party = (Byte) PlayerBattleInfo.INSTANCE.get(ctx, "party");
        if(BattleField.INSTANCE.get(ctx, intoHardPointRequest.getId() + "hardPoint").equals(party)) {
            return;
        }
        if(party > 0) {
            BattleField.INSTANCE.incr(ctx, intoHardPointRequest.getId() + "hardPointProcess");
        } else {
            BattleField.INSTANCE.decr(ctx, intoHardPointRequest.getId() + "hardPointProcess");
        }
    }
}
