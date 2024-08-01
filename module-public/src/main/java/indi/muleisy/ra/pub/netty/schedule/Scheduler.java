package indi.muleisy.ra.pub.netty.schedule;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

public abstract class Scheduler implements Runnable{

    protected final Channel channel;


    public Scheduler(Channel channel) {
        this.channel = channel;
    }

    public Integer getBattleFieldId() {
        return (Integer) channel.attr(AttributeKey.valueOf("battleFieldId")).get();
    }

    public Integer getUserId() {
        return (Integer) channel.attr(AttributeKey.valueOf("userId")).get();
    }
}
