package indi.muleisy.ra.battle.schedule;

import indi.muleisy.ra.battle.dao.WeaponDao;
import indi.muleisy.ra.battle.data.PlayerBattleInfo;
import indi.muleisy.ra.battle.data.Weapon;
import indi.muleisy.ra.battle.packet.response.ReloadResponse;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResurrectionScheduler implements Runnable{

    @Autowired
    private WeaponDao weaponDao;

    private final Integer userId;

    private final ChannelHandlerContext ctx;

    public ResurrectionScheduler(ChannelHandlerContext ctx, Integer userId) {
        this.ctx = ctx;
        this.userId = userId;
    }

    @Override
    public void run() {
        if((byte) PlayerBattleInfo.INSTANCE.get(ctx, "inBorn") != 1) {
            PlayerBattleInfo.INSTANCE.set(ctx,"status",2);
            return;
        }

        PlayerBattleInfo.INSTANCE.set(ctx,"hp",100);
        Weapon weapon = weaponDao.get((String) PlayerBattleInfo.INSTANCE.get(ctx,"mainWeaponId"));
        PlayerBattleInfo.INSTANCE.set(ctx,"mainWeaponClipNum",weapon.getClip());

        weapon = weaponDao.get((String) PlayerBattleInfo.INSTANCE.get(ctx,"offWeaponId"));
        PlayerBattleInfo.INSTANCE.set(ctx,"offWeaponClipNum",weapon.getClip());
        PlayerBattleInfo.INSTANCE.set(ctx,"status",0);
    }
}
