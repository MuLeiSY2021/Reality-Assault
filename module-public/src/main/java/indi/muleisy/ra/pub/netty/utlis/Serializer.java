package indi.muleisy.ra.pub.netty.utlis;

import indi.muleisy.ra.pub.config.Config;
import indi.muleisy.ra.pub.netty.packet.ResponsePacket;
import lombok.Getter;

import java.util.concurrent.ConcurrentHashMap;

public abstract class Serializer {
    public static Serializer DEFAULT = Config.INSTANCE.getDefaultSerializer();

    @Getter
    private final byte algoId;


    private static ConcurrentHashMap<Byte,Serializer> serializers = new ConcurrentHashMap<>();

    Serializer(byte algoId) {
        this.algoId = algoId;
        serializers.put(algoId,this);
    }

    public static Serializer getSerializer(byte type) {
        return Serializer.serializers.get(type);
    }

    public abstract byte[] serialization(ResponsePacket msg);



    public abstract Object deserialization(Class requestType, byte[] bytes);

    public abstract Object deserialization(Class requestType, String string);


    public abstract byte[] serialize(Object obj);

    public abstract String serializeToString(Object obj);
}
