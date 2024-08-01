package indi.muleisy.ra.pub.redis;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerBattleInfo {

    private int id;
    private int battleFieldId;
    private Object[] position;  // Assuming position is an array of Objects, can be adjusted to specific type
    private byte status;
    private byte hp;
    private int money;
    private int mainWeaponId;
    private short mainWeaponClipNum;
    private short mainWeaponAmmoNum;
    private int offWeaponId;
    private short offWeaponClipNum;
    private short offWeaponAmmoNum;
    private int combatWeaponId;
    private short killNum;
    private short deathNum;
    private short occupyNum;
    private byte party;
    private boolean inborn;
    private byte inHardPoint;
}
