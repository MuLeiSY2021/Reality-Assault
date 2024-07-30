package indi.muleisy.ra.battle.schedule;

import indi.muleisy.ra.battle.group.BattleSessionGroupManager;
import indi.muleisy.ra.battle.packet.GameStartNotification;
import io.netty.channel.ChannelHandlerContext;

public class GameStartScheduler implements Runnable {
    private final Integer groupId;

    private final ChannelHandlerContext ctx;


    public GameStartScheduler(ChannelHandlerContext ctx, Integer groupId) {
        this.ctx = ctx;
        this.groupId = groupId;
    }

    @Override
    public void run() {
        //TODO:发布三个定时任务

        BattleSessionGroupManager.INSTANCE.broadcast(groupId,new GameStartNotification());
    }
}
