package indi.muleisy.ra.pub.utils.redis;

import indi.muleisy.ra.pub.config.RedisTopicConstant;
import indi.muleisy.ra.pub.netty.utlis.JsonSerializer;
import indi.muleisy.ra.pub.netty.utlis.Serializer;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import redis.clients.jedis.Jedis;

public abstract class RedisDao {

    public abstract Integer find(String key);

    public abstract Class findClass(String key);

    public abstract String getPrefix(ChannelHandlerContext ctx);

    public String compose(ChannelHandlerContext ctx,String attribute) {
        return getPrefix(ctx) + num2Byt(find(attribute));
    }

    public Long incr(ChannelHandlerContext ctx,String attribute) {
        return RedisUtil.getJedis().incr(compose(ctx, attribute));
    }

    public Object get(ChannelHandlerContext ctx, String attribute) {
        return Serializer.DEFAULT.deserialization(findClass(attribute), RedisUtil.getJedis().get(compose(ctx, attribute)));
    }

    public Object set(ChannelHandlerContext ctx, String attribute,Object value) {
        return  RedisUtil.getJedis().set(compose(ctx, attribute),Serializer.DEFAULT.serializeToString(value));
    }

    public static String num2Byt(int value) {
        return Unpooled.buffer().writeInt(value).toString();
    }

    public Long decr(ChannelHandlerContext ctx, String attribute) {
        return RedisUtil.getJedis().decr(compose(ctx, attribute));
    };

    public Long decrBy(ChannelHandlerContext ctx, String attribute,Long value) {
        return RedisUtil.getJedis().decrBy(compose(ctx, attribute),value);
    };

    public Long incrBy(ChannelHandlerContext ctx, String attribute,Long value) {
        return RedisUtil.getJedis().incrBy(compose(ctx, attribute),value);

    }
}
