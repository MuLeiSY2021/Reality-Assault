package indi.muleisy.ra.pub.redis.dao;

import indi.muleisy.ra.pub.config.RedisTopicConstant;
import indi.muleisy.ra.pub.decrp.RedisData;
import indi.muleisy.ra.pub.redis.BattleField;
import indi.muleisy.ra.pub.redis.RedisDao;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.util.concurrent.ConcurrentHashMap;

@RedisData(RedisTopicConstant.PLAYER_BATTLE_INFO)
public class BattleFieldDao extends RedisDao<BattleField> {
    public static final BattleFieldDao INSTANCE = new BattleFieldDao();

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
    public String getPrefix(Channel ctx) {
        return num2Byt(RedisTopicConstant.BATTLE_FIELD_INFO.getValue()) + ":"
                + num2Byt((Integer) ctx.attr(AttributeKey.valueOf("battleFieldId")).get()) + ":";
    }

    @Override
    public String getPrefix(Integer id) {
        return num2Byt(RedisTopicConstant.BATTLE_FIELD_INFO.getValue()) + ":"
                + num2Byt(id) + ":";
    }

    @Override
    public BattleField getData(Channel ctx) {
        BattleField battleField = new BattleField();

        // 从 ChannelHandlerContext 中提取属性并设置到 BattleField 对象中
        battleField.setId((Integer) ctx.attr(AttributeKey.valueOf("battleFieldId")).get());
        battleField.setStatus((Byte) get(ctx, "status"));
        battleField.setMapPosition((Double[]) get(ctx, "mapPosition"));
        battleField.setPlayerNum((Integer) get(ctx, "playerNum"));
        battleField.setTimestampStart((Long) get(ctx, "timestampStart"));
        battleField.setPlayersId1((Integer[]) get(ctx, "1playersId"));
        battleField.setPlayersId_1((Integer[]) get(ctx, "-1playersId"));
        battleField.setEconomy1((Integer) get(ctx, "1economy"));
        battleField.setEconomy_1((Integer) get(ctx, "-1economy"));
        battleField.setBasement1((Integer) get(ctx, "1basement"));
        battleField.setBasement_1((Integer) get(ctx, "-1basement"));
        battleField.setHardPointProcess0((Integer) get(ctx, "0hardPointProcess"));
        battleField.setHardPoint0((Byte) get(ctx, "0hardPoint"));
        battleField.setHardPointProcess1((Integer) get(ctx, "1hardPointProcess"));
        battleField.setHardPoint1((Byte) get(ctx, "1hardPoint"));
        battleField.setHardPointProcess2((Integer) get(ctx, "2hardPointProcess"));
        battleField.setHardPoint2((Byte) get(ctx, "2hardPoint"));
        battleField.setHardPointProcess3((Integer) get(ctx, "3hardPointProcess"));
        battleField.setHardPoint3((Byte) get(ctx, "3hardPoint"));
        battleField.setHardPointProcess4((Integer) get(ctx, "4hardPointProcess"));
        battleField.setHardPoint4((Byte) get(ctx, "4hardPoint"));
        battleField.setHardPointNum((Byte) get(ctx, "hardPointNum"));
        battleField.setSpawnPlayerNum((Byte) get(ctx, "spawnPlayerNum"));
        battleField.setResurrectionTime((Short) get(ctx, "resurrectionTime"));
        battleField.setBasementProgress1((Byte) get(ctx, "1basementProgress"));
        battleField.setBasementProgress_1((Byte) get(ctx, "-1basementProgress"));
        battleField.setHardPointProgress0((Byte) get(ctx, "0hardPointProgress"));
        battleField.setHardPointProgress1((Byte) get(ctx, "1hardPointProgress"));
        battleField.setHardPointProgress2((Byte) get(ctx, "2hardPointProgress"));
        battleField.setHardPointProgress3((Byte) get(ctx, "3hardPointProgress"));
        battleField.setHardPointProgress4((Byte) get(ctx, "4hardPointProgress"));

        return battleField;
    }
}
