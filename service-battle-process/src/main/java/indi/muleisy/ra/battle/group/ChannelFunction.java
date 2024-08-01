package indi.muleisy.ra.battle.group;

import io.netty.channel.group.ChannelGroup;

public interface ChannelFunction {


    void sendPacket(ChannelGroup channels);
}
