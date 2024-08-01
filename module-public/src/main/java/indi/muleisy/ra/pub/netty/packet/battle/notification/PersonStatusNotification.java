package indi.muleisy.ra.pub.netty.packet.battle.notification;

import indi.muleisy.ra.pub.netty.packet.Packet;
import indi.muleisy.ra.pub.netty.packet.battle.PacketType;
import indi.muleisy.ra.pub.redis.BattleField;
import indi.muleisy.ra.pub.redis.PlayerBattleInfo;
import indi.muleisy.ra.pub.redis.dao.BattleFieldDao;
import indi.muleisy.ra.pub.redis.dao.PlayerBattleInfoDao;
import io.netty.channel.Channel;
import lombok.Getter;

@Getter
public class PersonStatusNotification extends Packet {

    private final PlayerBattleInfo playerBattleInfo;

    private final BattleField battleField;

    public PersonStatusNotification(Channel ctx) {
        super(PacketType.PERSON_STATUS_NOTIFICATION);
        this.playerBattleInfo = PlayerBattleInfoDao.INSTANCE.getData(ctx);
        this.battleField = BattleFieldDao.INSTANCE.getData(ctx);
    }

}
