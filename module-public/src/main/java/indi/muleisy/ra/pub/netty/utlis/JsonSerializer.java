package indi.muleisy.ra.pub.netty.utlis;

import com.google.gson.Gson;
import indi.muleisy.ra.pub.netty.packet.ResponsePacket;

import java.nio.charset.StandardCharsets;

public class JsonSerializer extends Serializer{
    private final Gson gson = new Gson();

    JsonSerializer(byte algoId) {
        super(algoId);
    }


    @Override
    public byte[] serialization(ResponsePacket msg) {
        return gson.toJson(msg).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public Object deserialization(Class requestType, byte[] bytes) {
        return gson.fromJson(new String(bytes), requestType);
    }

    @Override
    public Object deserialization(Class requestType, String string) {
        return gson.fromJson(string, requestType);
    }

    @Override
    public byte[] serialize(Object obj) {
        return gson.toJson(obj).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String serializeToString(Object obj) {
        return gson.toJson(obj);
    }
}
