package indi.muleisy.ra.battle.group;

import indi.muleisy.ra.pub.netty.packet.Packet;
import indi.muleisy.ra.pub.netty.packet.ResponsePacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelGroupFuture;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.nio.channels.SocketChannel;
import java.util.concurrent.ConcurrentHashMap;

public class BattleSessionGroupManager {
    public static final BattleSessionGroupManager INSTANCE = new BattleSessionGroupManager();

    private final ConcurrentHashMap<Integer, ChannelGroup> groups = new ConcurrentHashMap<>();

    public void addGroup(ChannelHandlerContext ctx) {
        Integer group = (Integer) ctx.channel().attr(AttributeKey.valueOf("battleFieldId")).get();
        ChannelGroup channelGroup;
        if(groups.containsKey(group)) {
            channelGroup = groups.get(group);
        }else {
            channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
            groups.put(group, channelGroup);
        }
        channelGroup.add(ctx.channel());
    }

    public void broadcast(ChannelHandlerContext ctx, Packet msg) {
        Integer group = (Integer) ctx.channel().attr(AttributeKey.valueOf("battleFieldId")).get();
        ChannelGroup channels = groups.get(group);
        channels.writeAndFlush(msg);
    }

    public void broadcast(Integer group, Packet msg) {
        ChannelGroup channels = groups.get(group);
        channels.writeAndFlush(msg);
    }

    public Integer getGroupId(ChannelHandlerContext ctx) {
        return (Integer) ctx.channel().attr(AttributeKey.valueOf("battleFieldId")).get();
    }
}
