package indi.muleisy.ra.battle.handler.reload;

import indi.muleisy.ra.battle.dao.WeaponDao;
import indi.muleisy.ra.battle.data.PlayerBattleInfo;
import indi.muleisy.ra.battle.data.Weapon;
import indi.muleisy.ra.battle.handler.AfterRegisterSessionInboundHandler;
import indi.muleisy.ra.battle.packet.request.ReloadRequest;
import indi.muleisy.ra.battle.packet.response.ReloadResponse;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class ReloadHandler extends AfterRegisterSessionInboundHandler<ReloadRequest> {

    @Autowired
    private WeaponDao weaponDao;

    @Override
    protected void channelRead2(ChannelHandlerContext ctx, ReloadRequest reloadRequest) throws Exception {
        if(reloadRequest.isMainhand()) {
            if((Integer) PlayerBattleInfo.INSTANCE.get(ctx,"mainWeaponAmmoNum") > 0) {
                Weapon weapon = weaponDao.get((String) PlayerBattleInfo.INSTANCE.get(ctx,"mainWeaponId"));

                PlayerBattleInfo.INSTANCE.decr(ctx,"mainWeaponAmmoNum");
                PlayerBattleInfo.INSTANCE.set(ctx,"mainWeaponClipNum",weapon.getClip());
                ctx.channel().writeAndFlush(new ReloadResponse().success());
            }else {
                ctx.channel().writeAndFlush(new ReloadResponse().failure());
            }
        } else {
            if((Integer) PlayerBattleInfo.INSTANCE.get(ctx,"offWeaponAmmoNum") > 0) {
                Weapon weapon = weaponDao.get((String) PlayerBattleInfo.INSTANCE.get(ctx,"offWeaponId"));

                PlayerBattleInfo.INSTANCE.decr(ctx,"offWeaponAmmoNum");
                PlayerBattleInfo.INSTANCE.set(ctx,"offWeaponClipNum",weapon.getClip());
                ctx.channel().writeAndFlush(new ReloadResponse().success());
            }else {
                ctx.channel().writeAndFlush(new ReloadResponse().failure());
            }
        }
    }
}
