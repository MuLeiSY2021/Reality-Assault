package indi.muleisy.ra.battle.handler;

import indi.muleisy.ra.pub.netty.handler.AfterLoginInboundHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

public abstract class RegisterSessionInboundHandler<Msg> extends AfterLoginInboundHandler<Msg> {
    @Override
    protected void channelRead1(ChannelHandlerContext ctx, Msg msg) throws Exception {
        if(!ctx.channel().hasAttr(AttributeKey.valueOf("battleFieldId"))) {
            return;
        }
        channelRead2(ctx, msg);
    }

    protected abstract void channelRead2(ChannelHandlerContext ctx, Msg msg) throws Exception;

    public Integer getBattleFieldId(ChannelHandlerContext ctx) {
        return (Integer) ctx.channel().attr(AttributeKey.valueOf("battleFieldId")).get();
    }
}
