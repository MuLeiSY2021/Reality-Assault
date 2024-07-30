package indi.muleisy.ra.battle.handler.shop;

import indi.muleisy.ra.battle.dao.GoodDao;
import indi.muleisy.ra.battle.data.Good;
import indi.muleisy.ra.battle.data.PlayerBattleInfo;
import indi.muleisy.ra.battle.handler.AfterRegisterSessionInboundHandler;
import indi.muleisy.ra.battle.packet.notification.EquipmentRenewNotification;
import indi.muleisy.ra.battle.packet.request.ShoppingRequest;
import indi.muleisy.ra.battle.packet.response.ShoppingResponse;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShoppingHandler extends AfterRegisterSessionInboundHandler<ShoppingRequest> {

    @Autowired
    private GoodDao goodDao;

    @Override
    protected void channelRead2(ChannelHandlerContext ctx, ShoppingRequest msg) throws Exception {
        if((boolean) PlayerBattleInfo.INSTANCE.get(ctx,"inborn")) {
            Good good = goodDao.get(msg.getId());
            if(good.getBuyable()) {
                Integer money = (Integer) PlayerBattleInfo.INSTANCE.get(ctx,"money");
                if(money - good.getCost() > 0) {
                    PlayerBattleInfo.INSTANCE.decrBy(ctx,"money", Long.valueOf(good.getCost()));
                    ctx.channel().writeAndFlush(new EquipmentRenewNotification(good));
                    ctx.channel().writeAndFlush(new ShoppingResponse().success());
                }
            }
        }

    }
}
