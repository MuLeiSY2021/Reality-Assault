package indi.muleisy.ra.battle.handler.hardpoint;

import indi.muleisy.ra.battle.data.BattleField;
import indi.muleisy.ra.battle.data.PlayerBattleInfo;
import indi.muleisy.ra.battle.handler.AfterRegisterSessionInboundHandler;
import indi.muleisy.ra.battle.packet.request.OutHardPointRequest;
import io.netty.channel.ChannelHandlerContext;

public class OutHardpointHandler extends AfterRegisterSessionInboundHandler<OutHardPointRequest> {

    @Override
    protected void channelRead2(ChannelHandlerContext ctx, OutHardPointRequest outHardPointRequest) throws Exception {
        Byte party = (Byte) PlayerBattleInfo.INSTANCE.get(ctx, "party");
        if(BattleField.INSTANCE.get(ctx, outHardPointRequest.getId() + "hardPoint").equals(party)) {
            return;
        }
        if(party > 0) {
            BattleField.INSTANCE.decr(ctx, outHardPointRequest.getId() + "hardPointProcess");
        } else {
            BattleField.INSTANCE.incr(ctx, outHardPointRequest.getId() + "hardPointProcess");
        }
    }
}
