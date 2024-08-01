package indi.muleisy.ra.battle.handler.group;

import indi.muleisy.ra.battle.group.BattleSessionGroupManager;
import indi.muleisy.ra.pub.netty.handler.AfterLoginInboundHandler;
import indi.muleisy.ra.pub.netty.packet.battle.RegisterBattleSessionRequest;
import indi.muleisy.ra.pub.netty.packet.battle.response.RegisterBattleSessionResponse;
import indi.muleisy.ra.pub.redis.dao.PlayerBattleInfoDao;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

public class BattleSessionRegisterationHandler extends AfterLoginInboundHandler<RegisterBattleSessionRequest> {
    @Override
    protected void channelRead1(ChannelHandlerContext ctx, RegisterBattleSessionRequest msg) throws Exception {
        if(PlayerBattleInfoDao.INSTANCE.get(ctx.channel(),"battleFieldId").equals(msg.getId())) {
            ctx.channel().attr(AttributeKey.valueOf("battleFieldId")).set(msg.getId());
            BattleSessionGroupManager.INSTANCE.addGroup(ctx.channel());
            ctx.channel().writeAndFlush(new RegisterBattleSessionResponse().success());
        } else {
            ctx.channel().writeAndFlush(new RegisterBattleSessionResponse().failure());
        }
    }
}
