package indi.muleisy.ra.battle.handler.shoot;

import indi.muleisy.ra.battle.data.PlayerBattleInfo;
import indi.muleisy.ra.battle.handler.AfterRegisterSessionInboundHandler;
import indi.muleisy.ra.battle.packet.request.ShootRequest;
import indi.muleisy.ra.battle.packet.response.ShootResponse;
import io.netty.channel.ChannelHandlerContext;

public class ShootHandler extends AfterRegisterSessionInboundHandler<ShootRequest> {

    @Override
    protected void channelRead2(ChannelHandlerContext ctx, ShootRequest shootRequest) throws Exception {
        if(shootRequest.isMainhand()) {
            if((Integer) PlayerBattleInfo.INSTANCE.get(ctx,"mainWeaponClipNum") > 0) {
                PlayerBattleInfo.INSTANCE.decr(ctx,"mainWeaponClipNum");
                ctx.channel().writeAndFlush(new ShootResponse().success());
            }else {
                ctx.channel().writeAndFlush(new ShootResponse().failure());
            }
        } else {
            if((Integer) PlayerBattleInfo.INSTANCE.get(ctx,"offWeaponClipNum") > 0) {
                PlayerBattleInfo.INSTANCE.decr(ctx,"offWeaponClipNum");
                ctx.channel().writeAndFlush(new ShootResponse().success());
            }else {
                ctx.channel().writeAndFlush(new ShootResponse().failure());
            }
        }
    }
}
