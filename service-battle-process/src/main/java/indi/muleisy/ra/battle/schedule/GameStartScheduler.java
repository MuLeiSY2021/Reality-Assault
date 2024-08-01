package indi.muleisy.ra.battle.schedule;

import indi.muleisy.ra.battle.group.BattleSessionGroupManager;
import indi.muleisy.ra.battle.utli.KafkaUtil;
import indi.muleisy.ra.pub.netty.packet.battle.notification.BasementLostNotification;
import indi.muleisy.ra.pub.netty.packet.battle.notification.GameStartNotification;
import indi.muleisy.ra.pub.netty.schedule.Scheduler;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.EventExecutor;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.concurrent.TimeUnit;

public class GameStartScheduler extends Scheduler {

    private final EventExecutor executor;

    public GameStartScheduler(Channel channel, EventExecutor executor) {
        super(channel);
        this.executor = executor;
    }


    @Override
    public void run() {
        //发布三个定时任务
        executor.scheduleAtFixedRate(new EconomyScheduler(channel),0,
                20, TimeUnit.SECONDS);
        executor.scheduleAtFixedRate(new BroadcrastStatusScheduler(channel),0,
                100, TimeUnit.MICROSECONDS);
        executor.scheduleAtFixedRate(new HardPointOccupyScheduler(channel),0,
                1, TimeUnit.SECONDS);
        KafkaUtil.PRODUCER.send(new ProducerRecord<>(
                GameStartNotification.TOPIC,
                (Integer) channel.attr(AttributeKey.valueOf("battlefieldId")).get(),
                new BasementLostNotification(-1)));
        BattleSessionGroupManager.INSTANCE.broadcast(getBattleFieldId(),new GameStartNotification());
    }
}
