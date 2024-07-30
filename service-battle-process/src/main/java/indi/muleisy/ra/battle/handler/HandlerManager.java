package indi.muleisy.ra.battle.handler;

import indi.muleisy.ra.pub.netty.codec.PacketCodec;
import indi.muleisy.ra.pub.netty.handler.AuthHandler;
import indi.muleisy.ra.pub.netty.handler.HeartbeatHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public abstract class HandlerManager extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        addHandlers(pipeline);
    }

    protected void addHandlers(ChannelPipeline pipeline) {
        pipeline.addLast(new IdleStateHandler(60, 30, 0)); // 读空闲60秒，写空闲30秒
        pipeline.addLast(new HeartbeatHandler());
        pipeline.addLast(new PacketCodec());
        pipeline.addLast(new AuthHandler());
    }
}