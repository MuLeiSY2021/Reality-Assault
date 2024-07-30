package indi.muleisy.ra.battle.data;

import indi.muleisy.ra.pub.config.RedisTopicConstant;
import indi.muleisy.ra.pub.utils.redis.RedisDao;
import indi.muleisy.ra.pub.utils.redis.RedisData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

import java.util.concurrent.ConcurrentHashMap;

@RedisData(RedisTopicConstant.USER_BATTLE_INFO)
public class BattleField extends RedisDao {
    public static final BattleField INSTANCE = new BattleField();

    private static final ConcurrentHashMap<String,Integer> attributes = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String,Class> attributesClass = new ConcurrentHashMap<>();

    static {
        attributes.put("id",0);
        attributesClass.put("id",Integer.class);

        attributes.put("status",1);
        attributesClass.put("status",Byte.class);

        attributes.put("mapPosition",2);
        attributesClass.put("status",Double[].class);

        attributes.put("playerNum",3);
        attributesClass.put("playerNum",Integer.class);

        attributes.put("timestampStart",4);
        attributesClass.put("timestampStart",Long.class);

        attributes.put("1playersId",5);
        attributesClass.put("1playersId",Integer[].class);

        attributes.put("-1playersId",6);
        attributesClass.put("-1playersId",Integer[].class);

        attributes.put("1economy",7);
        attributesClass.put("1economy",Integer.class);

        attributes.put("-1economy",8);
        attributesClass.put("-1economy",Integer.class);

        attributes.put("1basement",9);
        attributesClass.put("1basement",Integer.class);

        attributes.put("-1basement",10);
        attributesClass.put("-1basement",Integer.class);

        attributes.put("0hardPointProcess",11);
        attributesClass.put("0hardPointProcess",Integer.class);

        attributes.put("0hardPoint",12);
        attributesClass.put("0hardPoint",Byte.class);

        attributes.put("1hardPointProcess",13);
        attributesClass.put("0hardPointProcess",Integer.class);

        attributes.put("1hardPoint",14);
        attributesClass.put("0hardPoint",Byte.class);

        attributes.put("2hardPointProcess",15);
        attributesClass.put("0hardPointProcess",Integer.class);

        attributes.put("2hardPoint",16);
        attributesClass.put("0hardPoint",Byte.class);

        attributes.put("3hardPointProcess",17);
        attributesClass.put("0hardPointProcess",Integer.class);

        attributes.put("3hardPoint",18);
        attributesClass.put("0hardPoint",Byte.class);

        attributes.put("4hardPointProcess",19);
        attributesClass.put("0hardPointProcess",Integer.class);

        attributes.put("4hardPoint",20);
        attributesClass.put("0hardPoint",Byte.class);

        attributes.put("hardPointNum",21);
        attributesClass.put("hardPointNum",Byte.class);

        attributes.put("spawnPlayerNum",22);
        attributesClass.put("spawnPlayerNum",Byte.class);

        attributes.put("resurrectionTime",23);
        attributesClass.put("resurrectionTime",Short.class);

        attributes.put("1basementProgress",24);
        attributesClass.put("1basementProgress",Byte.class);

        attributes.put("-1basementProgress",25);
        attributesClass.put("-1basementProgress",Byte.class);

        attributes.put("0hardPointProgress",26);
        attributesClass.put("0hardPointProgress",Byte.class);

        attributes.put("1hardPointProgress",27);
        attributesClass.put("1hardPointProgress",Byte.class);

        attributes.put("2hardPointProgress",28);
        attributesClass.put("2hardPointProgress",Byte.class);

        attributes.put("3hardPointProgress",29);
        attributesClass.put("3hardPointProgress",Byte.class);

        attributes.put("4hardPointProgress",30);
        attributesClass.put("4hardPointProgress",Byte.class);
    }

    @Override
    public Integer find(String key) {
        return attributes.get(key);
    }

    @Override
    public Class findClass(String key) {
        return attributesClass.get(key);
    }

    @Override
    public String getPrefix(ChannelHandlerContext ctx) {
        return num2Byt(RedisTopicConstant.BATTLE_FIELD_INFO.getValue()) + ":"
                + num2Byt((Integer) ctx.channel().attr(AttributeKey.valueOf("battleFieldId")).get()) + ":";
    }


}
