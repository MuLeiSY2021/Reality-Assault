package indi.muleisy.ra.battle.data;

import indi.muleisy.ra.pub.config.*;
import indi.muleisy.ra.pub.utils.redis.RedisDao;
import indi.muleisy.ra.pub.utils.redis.RedisData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

import java.lang.reflect.Array;
import java.util.concurrent.ConcurrentHashMap;

@RedisData(RedisTopicConstant.USER_BATTLE_INFO)
public class PlayerBattleInfo extends RedisDao {
    public static final PlayerBattleInfo INSTANCE = new PlayerBattleInfo();

    private static final ConcurrentHashMap<String,Integer> attributes = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String,Class> attributesClass = new ConcurrentHashMap<>();

    static {
        attributes.put("id",0);
        attributesClass.put("id",Integer.class);

        attributes.put("battleFieldId",1);
        attributesClass.put("battleFieldId",Integer.class);

        attributes.put("position",2);
        attributesClass.put("position", Array.class);

        attributes.put("status",3);
        attributesClass.put("status",Byte.class);

        attributes.put("hp",4);
        attributesClass.put("hp",Byte.class);

        attributes.put("money",5);
        attributesClass.put("money",Integer.class);

        attributes.put("mainWeaponId",6);
        attributesClass.put("mainWeaponId",Integer.class);

        attributes.put("mainWeaponClipNum",7);
        attributesClass.put("mainWeaponClipNum",Short.class);

        attributes.put("mainWeaponAmmoNum",8);
        attributesClass.put("mainWeaponAmmoNum",Short.class);

        attributes.put("offWeaponId",9);
        attributesClass.put("offWeaponId",Integer.class);

        attributes.put("offWeaponClipNum",10);
        attributesClass.put("offWeaponClipNum",Short.class);

        attributes.put("offWeaponAmmoNum",11);
        attributesClass.put("offWeaponAmmoNum",Short.class);

        attributes.put("combatWeaponId",12);
        attributesClass.put("combatWeaponId",Integer.class);

        attributes.put("killNum",13);
        attributesClass.put("killNum",Short.class);

        attributes.put("deathNum",14);
        attributesClass.put("deathNum",Short.class);

        attributes.put("occupyNum",15);
        attributesClass.put("occupyNum",Short.class);

        attributes.put("party",16);
        attributesClass.put("party",Byte.class);

        attributes.put("inborn",17);
        attributesClass.put("inborn", Boolean.class);

        attributes.put("inHardPoint",18);
        attributesClass.put("inHardPoint",Byte.class);
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
        return num2Byt(RedisTopicConstant.USER_BATTLE_INFO.getValue()) + ":"
                + num2Byt((Integer) ctx.channel().attr(AttributeKey.valueOf("userId")).get()) + ":";
    }
}
