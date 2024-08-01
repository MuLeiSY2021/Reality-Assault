package indi.muleisy.ra.battle.handler.shop;

import indi.muleisy.ra.battle.dao.spring.GoodDao;
import indi.muleisy.ra.battle.handler.RegisterSessionInboundHandler;
import indi.muleisy.ra.battle.BattleProcessServer;
import indi.muleisy.ra.pub.redis.Good;
import indi.muleisy.ra.pub.netty.packet.battle.notification.EquipmentRenewNotification;
import indi.muleisy.ra.pub.netty.packet.battle.request.ShoppingRequest;
import indi.muleisy.ra.pub.netty.packet.battle.response.ShoppingResponse;
import indi.muleisy.ra.pub.redis.dao.PlayerBattleInfoDao;
import io.netty.channel.ChannelHandlerContext;

public class ShoppingHandler extends RegisterSessionInboundHandler<ShoppingRequest> {

    private GoodDao goodDao = BattleProcessServer.DAO_MANAGER.getGoodDao();

    @Override
    protected void channelRead2(ChannelHandlerContext ctx, ShoppingRequest msg) throws Exception {
        if((boolean) PlayerBattleInfoDao.INSTANCE.get(ctx.channel(),"inborn")) {
            Good good = goodDao.get(msg.getId());
            if(good.getBuyable()) {
                Integer money = (Integer) PlayerBattleInfoDao.INSTANCE.get(ctx.channel(),"money");
                if(money - good.getCost() > 0) {
                    PlayerBattleInfoDao.INSTANCE.decrBy(ctx.channel(),"money", Long.valueOf(good.getCost()));
                    ctx.channel().writeAndFlush(new EquipmentRenewNotification(good));
                    ctx.channel().writeAndFlush(new ShoppingResponse().success());
                }
            }
        }

    }
}
