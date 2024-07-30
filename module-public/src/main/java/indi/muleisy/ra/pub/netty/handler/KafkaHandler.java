package indi.muleisy.ra.pub.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.log4j.Log4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Log4j
public class KafkaHandler extends ChannelInboundHandlerAdapter {

    private KafkaConsumer<String, String> consumer;
    private ExecutorService executorService;
    private volatile boolean running = true;

    public KafkaHandler(String bootstrapServers, String groupId, String topic) {
        // 配置Kafka消费者属性
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        // 创建Kafka消费者
        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topic));

        // 创建线程池用于运行Kafka消费者
        executorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 启动Kafka消费者线程
        executorService.submit(() -> {
            try {
                while (running) {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                    for (ConsumerRecord<String, String> record : records) {
                        // 将Kafka消息传递给Netty的管道
                        ctx.executor().execute(() -> {
                            ctx.fireChannelRead(record.value());
                        });
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage(),e);
            } finally {
                consumer.close();
            }
        });

        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 停止Kafka消费者线程
        running = false;
        executorService.shutdown();
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 处理异常，关闭Channel
        log.error(cause.getMessage(),cause);
        ctx.close();
    }
}
