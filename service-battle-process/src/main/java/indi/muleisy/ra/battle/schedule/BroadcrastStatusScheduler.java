package indi.muleisy.ra.battle.schedule;

import indi.muleisy.ra.battle.group.BattleSessionGroupManager;
import indi.muleisy.ra.battle.group.ChannelFunction;
import indi.muleisy.ra.pub.netty.packet.battle.notification.PersonStatusNotification;
import indi.muleisy.ra.pub.netty.schedule.Scheduler;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;

public class BroadcrastStatusScheduler extends Scheduler implements ChannelFunction {

    public BroadcrastStatusScheduler(Channel channel) {
        super(channel);
    }

    @Override
    public void run() {
        BattleSessionGroupManager.INSTANCE.broadcastByChannel(channel,this);
    }

    @Override
    public void sendPacket(ChannelGroup channels) {
        for(Channel channel:channels) {
            channel.writeAndFlush(new PersonStatusNotification(channel));
        }
    }
}
