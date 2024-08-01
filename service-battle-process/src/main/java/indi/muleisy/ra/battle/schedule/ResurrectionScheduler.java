package indi.muleisy.ra.battle.schedule;

import indi.muleisy.ra.battle.dao.spring.SpringDaoManager;
import indi.muleisy.ra.battle.dao.spring.WeaponDao;
import indi.muleisy.ra.battle.data.Weapon;
import indi.muleisy.ra.pub.netty.schedule.Scheduler;
import indi.muleisy.ra.pub.redis.dao.PlayerBattleInfoDao;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class ResurrectionScheduler extends Scheduler {

    private WeaponDao weaponDao = SpringDaoManager.INSTANCE.getWeaponDao();

    public ResurrectionScheduler(Channel ctx) {
        super(ctx);
    }

    @Override
    public void run() {
        if((byte) PlayerBattleInfoDao.INSTANCE.get(channel, "inBorn") != 1) {
            PlayerBattleInfoDao.INSTANCE.set(channel,"status",2);
            return;
        }

        PlayerBattleInfoDao.INSTANCE.set(channel,"hp",100);
        Weapon weapon = weaponDao.get((String) PlayerBattleInfoDao.INSTANCE.get(channel,"mainWeaponId"));
        PlayerBattleInfoDao.INSTANCE.set(channel,"mainWeaponClipNum",weapon.getClip());

        weapon = weaponDao.get((String) PlayerBattleInfoDao.INSTANCE.get(channel,"offWeaponId"));
        PlayerBattleInfoDao.INSTANCE.set(channel,"offWeaponClipNum",weapon.getClip());
        PlayerBattleInfoDao.INSTANCE.set(channel,"status",0);
    }
}
