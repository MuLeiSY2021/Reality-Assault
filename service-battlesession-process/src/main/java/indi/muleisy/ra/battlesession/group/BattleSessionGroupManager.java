package indi.muleisy.ra.battlesession.group;

import indi.muleisy.ra.pub.netty.packet.Packet;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.concurrent.ConcurrentHashMap;

public class BattleSessionGroupManager {
    public static final BattleSessionGroupManager INSTANCE = new BattleSessionGroupManager();

    private final ConcurrentHashMap<Integer, ChannelGroup> groups = new ConcurrentHashMap<>();

    public void addGroup(Channel ctx) {
        Integer group = (Integer) ctx.attr(AttributeKey.valueOf("battleFieldId")).get();
        ChannelGroup channelGroup;
        if(groups.containsKey(group)) {
            channelGroup = groups.get(group);
        }else {
            channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
            groups.put(group, channelGroup);
        }
        channelGroup.add(ctx);
    }

    public void broadcast(Channel ctx, Packet msg) {
        Integer group = (Integer) ctx.attr(AttributeKey.valueOf("battleFieldId")).get();
        ChannelGroup channels = groups.get(group);
        channels.writeAndFlush(msg);
    }

    public void broadcast(Integer group, Packet msg) {
        ChannelGroup channels = groups.get(group);
        channels.writeAndFlush(msg);
    }

    public Integer getGroupId(Channel ctx) {
        return (Integer) ctx.attr(AttributeKey.valueOf("battleFieldId")).get();
    }

    public void broadcastByChannel(Channel ctx, ChannelFunction channelFunction) {
        Integer group = (Integer) ctx.attr(AttributeKey.valueOf("battleFieldId")).get();
        ChannelGroup channels = groups.get(group);
        channelFunction.sendPacket(channels);
    }
}
