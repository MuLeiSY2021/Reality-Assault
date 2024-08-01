package indi.muleisy.ra.battlesession.service;

import indi.muleisy.ra.battlesession.utli.Config;
import indi.muleisy.ra.battlesession.utli.ThreadPoolConfig;
import indi.muleisy.ra.pub.kafka.KafkaConsumerFactory;
import indi.muleisy.ra.pub.netty.packet.Packet;
import indi.muleisy.ra.pub.netty.packet.battle.notification.GameStartNotification;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

public class GameStartNotificationAcceptService{

    private static ThreadPoolExecutor pool = new ThreadPoolExecutor(
            ThreadPoolConfig.CORE_POOL_SIZE,
            ThreadPoolConfig.MAX_POOL_SIZE,
            ThreadPoolConfig.KEEP_ALIVE_TIME,
            ThreadPoolConfig.TIME_UNIT,
            new LinkedBlockingQueue<>(ThreadPoolConfig.QUEUE_CAPACITY),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
    );

    public static void run() {
        for (int i = 0; i < ThreadPoolConfig.CORE_POOL_SIZE; i++) {
            pool.execute(new GameStartNotificationAcceptJob());
        }
    }

    public static void close() {
        pool.shutdown();
    }

    private static class GameStartNotificationAcceptJob implements Runnable {
        private final Consumer<Integer, Packet> consumer;

        public GameStartNotificationAcceptJob() {
            this.consumer = KafkaConsumerFactory.createConsumer(Config.APPID);
            consumer.subscribe(Collections.singleton(GameStartNotification.TOPIC));
        }

        @Override
        public void run() {
            try {
                while (true) {
                    ConsumerRecords<Integer, Packet> records = consumer.poll(Duration.ofMillis(500));
                    for (ConsumerRecord<Integer, Packet> record : records) {
                        if (record.value() instanceof GameStartNotification) {
                            //TODO:游戏开始后的战局处理部分（暂无）
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                consumer.close();
            }
        }
    }
}
