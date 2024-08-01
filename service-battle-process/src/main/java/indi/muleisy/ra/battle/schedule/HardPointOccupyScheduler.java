package indi.muleisy.ra.battle.schedule;

import indi.muleisy.ra.battle.group.BattleSessionGroupManager;
import indi.muleisy.ra.battle.utli.KafkaUtil;
import indi.muleisy.ra.pub.netty.packet.battle.notification.BasementLostNotification;
import indi.muleisy.ra.pub.netty.packet.battle.notification.HardpointOccupyNotification;
import indi.muleisy.ra.pub.netty.schedule.Scheduler;
import indi.muleisy.ra.pub.redis.dao.BattleFieldDao;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import org.apache.kafka.clients.producer.ProducerRecord;

public class HardPointOccupyScheduler extends Scheduler {

    public HardPointOccupyScheduler(Channel channel) {
        super(channel);
    }

    @Override
    public void run() {
        byte progress = (byte) BattleFieldDao.INSTANCE.get(channel,"1basementProgress");
        if(progress < 100) {
            BattleFieldDao.INSTANCE.incrBy(channel,"1basementProgress", (Long) BattleFieldDao.INSTANCE.get(channel,"1basement"));
            progress = (byte) BattleFieldDao.INSTANCE.get(channel,"1basementProgress");
            if(progress >= -100) {
                //发送游戏胜利
                KafkaUtil.PRODUCER.send(new ProducerRecord<>(
                        BasementLostNotification.TOPIC,
                        (Integer) channel.attr(AttributeKey.valueOf("battlefieldId")).get(),
                        new BasementLostNotification(-1)));
                BattleFieldDao.INSTANCE.set(channel,"1basementProgress",-1);
                return;
            }
        }

        progress = (byte) BattleFieldDao.INSTANCE.get(channel,"-1basementProgress");
        if(progress > -100) {
            BattleFieldDao.INSTANCE.incrBy(channel, "-1basementProgress", (Long) BattleFieldDao.INSTANCE.get(channel, "-1basement"));
            if (progress >= 100) {
                //发送游戏胜利
                KafkaUtil.PRODUCER.send(new ProducerRecord<>(
                        BasementLostNotification.TOPIC,
                        (Integer) channel.attr(AttributeKey.valueOf("battlefieldId")).get(),
                        new BasementLostNotification(1)));
                BattleFieldDao.INSTANCE.set(channel, "-1basementProgress", 1);
                return;
            }
        }

        for (int i = 0; i <5; i++) {
            progress = (byte) BattleFieldDao.INSTANCE.get(channel,i+"hardPointProgress");
            byte party = (byte) BattleFieldDao.INSTANCE.get(channel,i+"hardPoint");
            if(party > 0 && progress >= 100 || party < 0 && progress <= -100) {
                continue;
            }

            BattleFieldDao.INSTANCE.incrBy(channel,i+"hardPointProgress", (Long) BattleFieldDao.INSTANCE.get(channel,i+"hardPointProcess"));
            if(Math.abs(progress) >= 100) {
                if(progress > 0) {
                    BattleFieldDao.INSTANCE.set(channel,i+"hardPointProgress",100);

                    BattleFieldDao.INSTANCE.set(channel,i+"hardPoint",1);
                    BattleSessionGroupManager.INSTANCE.broadcast(channel,new HardpointOccupyNotification(1,i));
                    BattleFieldDao.INSTANCE.incrBy(channel,"1economy", 300L);
                } else {
                    BattleFieldDao.INSTANCE.set(channel,i+"hardPointProgress",-100);

                    BattleFieldDao.INSTANCE.set(channel,i+"hardPoint",-1);
                    BattleSessionGroupManager.INSTANCE.broadcast(channel,new HardpointOccupyNotification(-1,i));
                    BattleFieldDao.INSTANCE.incrBy(channel,"-1economy", 300L);
                }
            }
        }
    }
}
