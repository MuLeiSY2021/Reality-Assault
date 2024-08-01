package indi.muleisy.ra.battle.handler.reload;

import indi.muleisy.ra.battle.BattleProcessServer;
import indi.muleisy.ra.battle.dao.spring.WeaponDao;
import indi.muleisy.ra.battle.data.Weapon;
import indi.muleisy.ra.battle.handler.RegisterSessionInboundHandler;
import indi.muleisy.ra.pub.netty.packet.battle.request.ReloadRequest;
import indi.muleisy.ra.pub.netty.packet.battle.response.ReloadResponse;
import indi.muleisy.ra.pub.redis.dao.PlayerBattleInfoDao;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ReloadHandler extends RegisterSessionInboundHandler<ReloadRequest> {

    private final WeaponDao weaponDao = BattleProcessServer.DAO_MANAGER.getWeaponDao();

    @Override
    protected void channelRead2(ChannelHandlerContext ctx, ReloadRequest reloadRequest) throws Exception {
        if(reloadRequest.isMainhand()) {
            if((Integer) PlayerBattleInfoDao.INSTANCE.get(ctx.channel(),"mainWeaponAmmoNum") > 0) {
                Weapon weapon = weaponDao.get((String) PlayerBattleInfoDao.INSTANCE.get(ctx.channel(),"mainWeaponId"));

                PlayerBattleInfoDao.INSTANCE.decr(ctx.channel(),"mainWeaponAmmoNum");
                PlayerBattleInfoDao.INSTANCE.set(ctx.channel(),"mainWeaponClipNum",weapon.getClip());
                ctx.channel().writeAndFlush(new ReloadResponse().success());
            }else {
                ctx.channel().writeAndFlush(new ReloadResponse().failure());
            }
        } else {
            if((Integer) PlayerBattleInfoDao.INSTANCE.get(ctx.channel(),"offWeaponAmmoNum") > 0) {
                Weapon weapon = weaponDao.get((String) PlayerBattleInfoDao.INSTANCE.get(ctx.channel(),"offWeaponId"));

                PlayerBattleInfoDao.INSTANCE.decr(ctx.channel(),"offWeaponAmmoNum");
                PlayerBattleInfoDao.INSTANCE.set(ctx.channel(),"offWeaponClipNum",weapon.getClip());
                ctx.channel().writeAndFlush(new ReloadResponse().success());
            }else {
                ctx.channel().writeAndFlush(new ReloadResponse().failure());
            }
        }
    }
}
