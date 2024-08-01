package indi.muleisy.ra.battlesession;

import indi.muleisy.ra.battlesession.dao.spring.SpringDaoManager;
import indi.muleisy.ra.battlesession.handler.HandlerManager;
import indi.muleisy.ra.battlesession.service.BasementLostNotificationAcceptService;
import indi.muleisy.ra.battlesession.service.GameStartNotificationAcceptService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
@EnableCaching

public class BattleProcessServer implements CommandLineRunner {
    public static SpringDaoManager DAO_MANAGER;

    @Autowired
    private SpringDaoManager springDaoManager;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(BattleProcessServer.class, args);

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            BasementLostNotificationAcceptService.run();
            GameStartNotificationAcceptService.run();

            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new HandlerManager() {});

            ChannelFuture future = bootstrap.bind(8080).sync();
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            BasementLostNotificationAcceptService.close();
            GameStartNotificationAcceptService.close();
        }
    }

    @Override
    public void run(String... args) throws Exception {
        BattleProcessServer.DAO_MANAGER = this.springDaoManager;
    }
}