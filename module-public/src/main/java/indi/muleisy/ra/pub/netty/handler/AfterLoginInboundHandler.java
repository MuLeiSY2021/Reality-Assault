package indi.muleisy.ra.pub.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;

public abstract class AfterLoginInboundHandler<RequestPacket> extends SimpleChannelInboundHandler<RequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestPacket msg) throws Exception {
        if(!ctx.channel().hasAttr(AttributeKey.valueOf("userId"))) {
            return;
        }
        channelRead1(ctx, msg);
    }

    protected abstract void channelRead1(ChannelHandlerContext ctx, RequestPacket msg) throws Exception;

    public Integer getUserId(ChannelHandlerContext ctx) {
        return (Integer) ctx.channel().attr(AttributeKey.valueOf("userId")).get();
    }
}
