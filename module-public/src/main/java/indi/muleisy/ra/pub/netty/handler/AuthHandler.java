package indi.muleisy.ra.pub.netty.handler;

import indi.muleisy.ra.pub.netty.packet.request.LoginRequest;
import indi.muleisy.ra.pub.netty.packet.request.LoginResponse;
import indi.muleisy.ra.pub.redis.RedisUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import lombok.extern.log4j.Log4j2;
import redis.clients.jedis.Jedis;

@Log4j2
public class AuthHandler extends SimpleChannelInboundHandler<LoginRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequest msg) throws Exception {
        String rsaToken = msg.getRsaToken(); // 假设 Packet 类有 getRsaToken() 方法

        try (Jedis jedis = RedisUtil.getJedis()) {
            String userId = jedis.get(rsaToken);
            if (userId == null) {
                // 如果 token 不存在，关闭连接
                log.warn("User not Login");
                ctx.close();
                return;
            } else {
                // 将用户 ID 绑定到 Channel 上，供后续使用
                ctx.channel().attr(AttributeKey.valueOf("userId")).set(userId);
            }
        }

        ctx.fireChannelRead(new LoginResponse().success());
    }
}
