package indi.muleisy.ra.battlesession.handler;

import indi.muleisy.ra.battlesession.handler.battlession.ExitBattleSessionHandler;
import indi.muleisy.ra.battlesession.handler.battlession.JoinBattleSessionHandler;
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
        // 读空闲60秒，写空闲30秒
        pipeline.addLast(new IdleStateHandler(60, 30, 0))
                .addLast(new HeartbeatHandler())
                .addLast(new PacketCodec())
                .addLast(new AuthHandler())
                .addLast(new JoinBattleSessionHandler())
                .addLast(new ExitBattleSessionHandler());
    }
}