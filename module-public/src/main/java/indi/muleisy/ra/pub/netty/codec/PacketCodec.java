package indi.muleisy.ra.pub.netty.codec;

import indi.muleisy.ra.pub.netty.packet.Packet;
import indi.muleisy.ra.pub.netty.packet.RawPacketType;
import indi.muleisy.ra.pub.netty.packet.ResponsePacket;
import indi.muleisy.ra.pub.netty.utlis.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

public class PacketCodec extends MessageToMessageCodec<ByteBuf, ResponsePacket> {

    private static final int MAGIC_NUMBER = 0x12345678;

    @Override
    protected void encode(ChannelHandlerContext ctx, ResponsePacket msg, List<Object> out) throws Exception {
        // 1. 构建 ByteBuf 对象
        // ioBuffer() 方法会返回适配 io 读写相关的内存，
        // 它会尽可能创建一个直接内存，
        // 直接内存可以理解为不受 jvm 堆管理的内存空间，写到 IO 缓冲区的效果更高。
        ByteBuf byteBuf = ctx.alloc().ioBuffer();
        // 2. 序列化 packet 对象
        byte[] bytes = Serializer.DEFAULT.serialization(msg);
        // 3. 开始编码
        // 魔数
        byteBuf.writeInt(MAGIC_NUMBER);
        // 版本号
        byteBuf.writeByte(msg.getVersion());
        // 序列化算法
        byteBuf.writeByte(Serializer.DEFAULT.getAlgoId());
        // 指令
        byteBuf.writeByte(msg.getCommand());
        // 数据长度
        byteBuf.writeInt(bytes.length);
        // 数据
        byteBuf.writeBytes(bytes);

        out.add(byteBuf);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        // 跳过魔数
        if(msg.readInt()!=MAGIC_NUMBER) {
            return;
        }
        // 跳过版本号
        msg.skipBytes(1);
        // 序列化算法标识
        byte serializationAlgorithm = msg.readByte();
        // 指令
        byte command = msg.readByte();
        // 数据包长度
        int length = msg.readInt();
        if(msg.readableBytes() != length) {
            return;
        }

        // 数据内容
        byte[] bytes = new byte[length];
        msg.readBytes(bytes);

        Class<? extends Packet> requestType = RawPacketType.getForCommand(command);
        Serializer serializer = Serializer.getSerializer(serializationAlgorithm);

        if (requestType != null && serializer != null) {
            out.add(serializer.deserialization(requestType, bytes));
        }
    }
}
