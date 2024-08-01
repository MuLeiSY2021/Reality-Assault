package indi.muleisy.ra.battle.utli;

import indi.muleisy.ra.pub.kafka.KafkaProducerFactory;
import indi.muleisy.ra.pub.netty.packet.Packet;
import org.apache.kafka.clients.producer.Producer;

public class KafkaUtil {
    public static final Producer<Integer, Packet> PRODUCER = KafkaProducerFactory.createProducer();
}
