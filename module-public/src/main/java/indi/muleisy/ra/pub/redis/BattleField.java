package indi.muleisy.ra.pub.redis;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BattleField {

    private int id;
    private byte status;
    private Double[] mapPosition;
    private int playerNum;
    private long timestampStart;
    private Integer[] playersId1;
    private Integer[] playersId_1;
    private int economy1;
    private int economy_1;
    private int basement1;
    private int basement_1;
    private int hardPointProcess0;
    private byte hardPoint0;
    private int hardPointProcess1;
    private byte hardPoint1;
    private int hardPointProcess2;
    private byte hardPoint2;
    private int hardPointProcess3;
    private byte hardPoint3;
    private int hardPointProcess4;
    private byte hardPoint4;
    private byte hardPointNum;
    private byte spawnPlayerNum;
    private short resurrectionTime;
    private byte basementProgress1;
    private byte basementProgress_1;
    private byte hardPointProgress0;
    private byte hardPointProgress1;
    private byte hardPointProgress2;
    private byte hardPointProgress3;
    private byte hardPointProgress4;

}
