package indi.muleisy.ra.pub.netty.handler;

import indi.muleisy.ra.pub.netty.packet.request.LoginRequest;
import indi.muleisy.ra.pub.netty.packet.request.LoginResponse;
import indi.muleisy.ra.pub.utils.log.LogUtli;
import indi.muleisy.ra.pub.utils.redis.RedisUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import redis.clients.jedis.Jedis;

public class AuthHandler extends SimpleChannelInboundHandler<LoginRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequest msg) throws Exception {
        String rsaToken = msg.getRsaToken(); // 假设 Packet 类有 getRsaToken() 方法

        try (Jedis jedis = RedisUtil.getJedis()) {
            String userId = jedis.get(rsaToken);
            if (userId == null) {
                // 如果 token 不存在，关闭连接
                LogUtli.Logging(this.getClass()).warn("User not Login");
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
