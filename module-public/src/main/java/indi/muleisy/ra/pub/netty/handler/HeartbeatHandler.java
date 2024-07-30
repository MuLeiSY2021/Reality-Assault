package indi.muleisy.ra.pub.netty.handler;

import indi.muleisy.ra.pub.netty.packet.HeartbeatPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class HeartbeatHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HeartbeatPacket) {
            System.out.println("Received heartbeat packet");
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                System.out.println("Read idle, closing connection");
                ctx.close();
            } else if (event.state() == IdleState.WRITER_IDLE) {
                System.out.println("Write idle, sending heartbeat");
                ctx.writeAndFlush(new HeartbeatPacket());
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}