package indi.muleisy.ra.battle.handler.equipment;

import indi.muleisy.ra.battle.dao.KnifeDao;
import indi.muleisy.ra.battle.dao.SupportDao;
import indi.muleisy.ra.battle.dao.WeaponDao;
import indi.muleisy.ra.battle.data.*;
import indi.muleisy.ra.battle.handler.AfterRegisterSessionInboundHandler;
import indi.muleisy.ra.battle.packet.notification.EquipmentRenewNotification;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class EquipmentRenewHandler extends AfterRegisterSessionInboundHandler<EquipmentRenewNotification> {

    @Autowired
    private WeaponDao weaponDao;

    @Autowired
    private KnifeDao knifeDao;

    @Autowired
    private SupportDao supportDao;
    @Override
    protected void channelRead2(ChannelHandlerContext ctx, EquipmentRenewNotification equipmentRenewNotification) throws Exception {
        Good good = equipmentRenewNotification.getGood();
        Weapon weapon;
        switch (good.getType()) {
            case 0:
                weapon = weaponDao.get(good.getTableId());
                if((Integer)PlayerBattleInfo.INSTANCE.get(ctx,"offHeadWeaponId") == -1) {
                    PlayerBattleInfo.INSTANCE.set(ctx,"offWeaponId",weapon.getId());
                    PlayerBattleInfo.INSTANCE.set(ctx,"offWeaponClipNum",weapon.getClip());
                } else {
                    PlayerBattleInfo.INSTANCE.set(ctx,"mainWeaponId",weapon.getId());
                    PlayerBattleInfo.INSTANCE.set(ctx,"mainWeaponClipNum",weapon.getClip());
                }
                return;

            case 1:
                Knife knife = knifeDao.get(good.getTableId());
                PlayerBattleInfo.INSTANCE.set(ctx,"combatWeaponId",knife.getId());
                return;

            case 2:
                Support support = supportDao.get(good.getTableId());
                switch (support.getType()) {
                    case 0:
                        weapon = weaponDao.get((String) PlayerBattleInfo.INSTANCE.get(ctx,"offWeaponId"));
                        PlayerBattleInfo.INSTANCE.set(ctx,"offWeaponClipNum",weapon.getClip());

                    case 1:
                        PlayerBattleInfo.INSTANCE.set(ctx,"hp",100);

                    default:
                        log.warn("No such support type: " + support.getType());

                }
                return;

            default:
                log.warn("No such good type: ");
        }
    }
}
