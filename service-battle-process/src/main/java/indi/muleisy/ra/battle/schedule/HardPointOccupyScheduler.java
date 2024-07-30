package indi.muleisy.ra.battle.schedule;

import indi.muleisy.ra.battle.data.BattleField;
import indi.muleisy.ra.battle.group.BattleSessionGroupManager;
import indi.muleisy.ra.battle.packet.notification.HardpointOccupyNotification;
import indi.muleisy.ra.pub.utils.kafka.KafkaConsumerFactory;
import io.netty.channel.ChannelHandlerContext;

public class HardPointOccupyScheduler implements Runnable {

    private final ChannelHandlerContext ctx;

    public HardPointOccupyScheduler(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }


    @Override
    public void run() {
        KafkaConsumerFactory.createConsumer("")
        byte progress = (byte)BattleField.INSTANCE.get(ctx,"1basementProgress");
        if(progress < 100) {
            BattleField.INSTANCE.incrBy(ctx,"1basementProgress", (Long) BattleField.INSTANCE.get(ctx,"1basement"));
            progress = (byte)BattleField.INSTANCE.get(ctx,"1basementProgress");
            if(progress >= -100) {
                //TODO:发送游戏胜利
                BattleField.INSTANCE.set(ctx,"1basementProgress",-1);
                return;
            }
        }

        progress = (byte)BattleField.INSTANCE.get(ctx,"-1basementProgress");
        if(progress > -100) {
            BattleField.INSTANCE.incrBy(ctx, "-1basementProgress", (Long) BattleField.INSTANCE.get(ctx, "-1basement"));
            if (progress >= 100) {
                //TODO:发送游戏胜利
                BattleField.INSTANCE.set(ctx, "-1basementProgress", 1);
                return;
            }
        }

        for (int i = 0; i <5; i++) {
            progress = (byte)BattleField.INSTANCE.get(ctx,i+"hardPointProgress");
            byte party = (byte) BattleField.INSTANCE.get(ctx,i+"hardPoint");
            if(party > 0 && progress >= 100 || party < 0 && progress <= -100) {
                continue;
            }

            BattleField.INSTANCE.incrBy(ctx,i+"hardPointProgress", (Long) BattleField.INSTANCE.get(ctx,i+"hardPointProcess"));
            if(Math.abs(progress) >= 100) {
                if(progress > 0) {
                    BattleField.INSTANCE.set(ctx,i+"hardPointProgress",100);

                    BattleField.INSTANCE.set(ctx,i+"hardPoint",1);
                    BattleSessionGroupManager.INSTANCE.broadcast(ctx,new HardpointOccupyNotification(1,i));
                    BattleField.INSTANCE.incrBy(ctx,"1economy", 300L);
                } else {
                    BattleField.INSTANCE.set(ctx,i+"hardPointProgress",-100);

                    BattleField.INSTANCE.set(ctx,i+"hardPoint",-1);
                    BattleSessionGroupManager.INSTANCE.broadcast(ctx,new HardpointOccupyNotification(-1,i));
                    BattleField.INSTANCE.incrBy(ctx,"-1economy", 300L);
                }
            }
        }
    }
}
