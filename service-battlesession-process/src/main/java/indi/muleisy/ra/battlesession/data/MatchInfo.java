package indi.muleisy.ra.battlesession.data;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "match_info")
public class MatchInfo {

    @Id
    private int id;                 // 房间ID (MK)
    private Double[] mapPosition;   // 地图坐标
    private int playerNum;          // 玩家数量
    private byte status;            // 房间状态 (0: 等待中, 1: 开启倒计时, 2: 使用中)
    private Integer[] playerIds;    // 玩家ID列表

    public MatchInfo(int id, Double[] mapPosition, int playerNum, byte status, Integer[] playerIds) {
        this.id = id;
        this.mapPosition = mapPosition;
        this.playerNum = playerNum;
        this.status = status;
        this.playerIds = playerIds;
    }
}
