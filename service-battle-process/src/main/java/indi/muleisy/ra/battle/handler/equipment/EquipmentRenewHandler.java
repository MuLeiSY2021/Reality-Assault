package indi.muleisy.ra.battle.handler.equipment;

import indi.muleisy.ra.battle.BattleProcessServer;
import indi.muleisy.ra.battle.dao.spring.KnifeDao;
import indi.muleisy.ra.battle.dao.spring.SupportDao;
import indi.muleisy.ra.battle.dao.spring.WeaponDao;
import indi.muleisy.ra.battle.data.Knife;
import indi.muleisy.ra.battle.data.Support;
import indi.muleisy.ra.battle.data.Weapon;
import indi.muleisy.ra.battle.handler.RegisterSessionInboundHandler;
import indi.muleisy.ra.pub.redis.Good;
import indi.muleisy.ra.pub.netty.packet.battle.notification.EquipmentRenewNotification;
import indi.muleisy.ra.pub.redis.dao.PlayerBattleInfoDao;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class EquipmentRenewHandler extends RegisterSessionInboundHandler<EquipmentRenewNotification> {
    private final WeaponDao weaponDao = BattleProcessServer.DAO_MANAGER.getWeaponDao();

    private final KnifeDao knifeDao = BattleProcessServer.DAO_MANAGER.getKnifeDao();

    private final SupportDao supportDao = BattleProcessServer.DAO_MANAGER.getSupportDao();

    @Override
    protected void channelRead2(ChannelHandlerContext ctx, EquipmentRenewNotification equipmentRenewNotification) throws Exception {
        Good good = equipmentRenewNotification.getGood();
        Weapon weapon;
        switch (good.getType()) {
            case 0:
                weapon = weaponDao.get(good.getTableId());
                if((Integer) PlayerBattleInfoDao.INSTANCE.get(ctx.channel(),"offHeadWeaponId") == -1) {
                    PlayerBattleInfoDao.INSTANCE.set(ctx.channel(),"offWeaponId",weapon.getId());
                    PlayerBattleInfoDao.INSTANCE.set(ctx.channel(),"offWeaponClipNum",weapon.getClip());
                } else {
                    PlayerBattleInfoDao.INSTANCE.set(ctx.channel(),"mainWeaponId",weapon.getId());
                    PlayerBattleInfoDao.INSTANCE.set(ctx.channel(),"mainWeaponClipNum",weapon.getClip());
                }
                return;

            case 1:
                Knife knife = knifeDao.get(good.getTableId());
                PlayerBattleInfoDao.INSTANCE.set(ctx.channel(),"combatWeaponId",knife.getId());
                return;

            case 2:
                Support support = supportDao.get(good.getTableId());
                switch (support.getType()) {
                    case 0:
                        weapon = weaponDao.get((String) PlayerBattleInfoDao.INSTANCE.get(ctx.channel(),"offWeaponId"));
                        PlayerBattleInfoDao.INSTANCE.set(ctx.channel(),"offWeaponClipNum",weapon.getClip());

                    case 1:
                        PlayerBattleInfoDao.INSTANCE.set(ctx.channel(),"hp",100);

                    default:
                        log.warn("No such support type: " + support.getType());

                }
                return;

            default:
                log.warn("No such good type: ");
        }
    }
}
