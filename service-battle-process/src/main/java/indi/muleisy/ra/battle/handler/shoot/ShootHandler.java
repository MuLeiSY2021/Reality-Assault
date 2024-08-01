package indi.muleisy.ra.battle.handler.shoot;

import indi.muleisy.ra.battle.handler.RegisterSessionInboundHandler;
import indi.muleisy.ra.pub.netty.packet.battle.request.ShootRequest;
import indi.muleisy.ra.pub.netty.packet.battle.response.ShootResponse;
import indi.muleisy.ra.pub.redis.dao.PlayerBattleInfoDao;
import io.netty.channel.ChannelHandlerContext;

public class ShootHandler extends RegisterSessionInboundHandler<ShootRequest> {

    @Override
    protected void channelRead2(ChannelHandlerContext ctx, ShootRequest shootRequest) throws Exception {
        if(shootRequest.isMainhand()) {
            if((Integer) PlayerBattleInfoDao.INSTANCE.get(ctx.channel(),"mainWeaponClipNum") > 0) {
                PlayerBattleInfoDao.INSTANCE.decr(ctx.channel(),"mainWeaponClipNum");
                ctx.channel().writeAndFlush(new ShootResponse().success());
            }else {
                ctx.channel().writeAndFlush(new ShootResponse().failure());
            }
        } else {
            if((Integer) PlayerBattleInfoDao.INSTANCE.get(ctx.channel(),"offWeaponClipNum") > 0) {
                PlayerBattleInfoDao.INSTANCE.decr(ctx.channel(),"offWeaponClipNum");
                ctx.channel().writeAndFlush(new ShootResponse().success());
            }else {
                ctx.channel().writeAndFlush(new ShootResponse().failure());
            }
        }
    }
}
