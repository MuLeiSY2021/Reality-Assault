package indi.muleisy.ra.battlesession.group;

import io.netty.channel.group.ChannelGroup;

public interface ChannelFunction {


    void sendPacket(ChannelGroup channels);
}
