package indi.muleisy.ra.pub.redis;

import indi.muleisy.ra.pub.netty.utlis.Serializer;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

public abstract class RedisDao<Data> {

    public abstract Integer find(String key);

    public abstract Class findClass(String key);

    public abstract String getPrefix(Channel channel);

    public abstract String getPrefix(Integer id);

    public abstract Data getData(Channel ctx);


    public String compose(Channel channel,String attribute) {
        return getPrefix(channel) + num2Byt(find(attribute));
    }

    public String compose(Integer id, String attribute) {
        return getPrefix(id) + num2Byt(find(attribute));
    }

    public Long incr(Channel channel,String attribute) {
        return RedisUtil.getJedis().incr(compose(channel, attribute));
    }

    public Object get(Channel ctx, String attribute) {
        return Serializer.DEFAULT.deserialization(findClass(attribute), RedisUtil.getJedis().get(compose(ctx, attribute)));
    }

    public String set(Channel ctx, String attribute,Object value) {
        return RedisUtil.getJedis().set(compose(ctx, attribute),Serializer.DEFAULT.serializeToString(value));
    }

    public String set(Integer playerId, String attribute,Object value) {
        return RedisUtil.getJedis().set(compose(playerId, attribute),Serializer.DEFAULT.serializeToString(value));
    }

    public static String num2Byt(int value) {
        return Unpooled.buffer().writeInt(value).toString();
    }

    public Long decr(Channel ctx, String attribute) {
        return RedisUtil.getJedis().decr(compose(ctx, attribute));
    };

    public Long decrBy(Channel ctx, String attribute,Long value) {
        return RedisUtil.getJedis().decrBy(compose(ctx, attribute),value);
    };

    public Long incrBy(Channel ctx, String attribute,Long value) {
        return RedisUtil.getJedis().incrBy(compose(ctx, attribute),value);

    }

    public Long incrBy(Integer id, String attribute, Long value) {
        return RedisUtil.getJedis().incrBy(compose(id, attribute),value);
    }


}
