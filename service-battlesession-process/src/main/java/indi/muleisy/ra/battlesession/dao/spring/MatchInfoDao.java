package indi.muleisy.ra.battlesession.dao.spring;

import indi.muleisy.ra.battlesession.data.MatchInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class MatchInfoDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    // 获取MatchInfo
    public MatchInfo getMatchInfo(int id) {
        return mongoTemplate.findById(id, MatchInfo.class);
    }

    // 添加玩家
    public void addPlayer(int id, int playerId) {
        MatchInfo matchInfo = getMatchInfo(id);
        if (matchInfo != null) {
            Integer[] playerIds = matchInfo.getPlayerIds();
            playerIds = Arrays.copyOf(playerIds, playerIds.length + 1);
            playerIds[playerIds.length - 1] = playerId;
            matchInfo.setPlayerIds(playerIds);
            matchInfo.setPlayerNum(matchInfo.getPlayerNum() + 1);

            Query query = new Query(Criteria.where("id").is(id));
            Update update = new Update().set("playerIds", playerIds).set("playerNum", matchInfo.getPlayerNum());
            mongoTemplate.updateFirst(query, update, MatchInfo.class);
        }
    }

    // 删除玩家
    public void removePlayer(int id, int playerId) {
        MatchInfo matchInfo = getMatchInfo(id);
        if (matchInfo != null) {
            Integer[] playerIds = matchInfo.getPlayerIds();
            playerIds = Arrays.stream(playerIds)
                    .filter(pid -> pid != playerId)
                    .toArray(Integer[]::new);
            matchInfo.setPlayerIds(playerIds);
            matchInfo.setPlayerNum(playerIds.length);

            Query query = new Query(Criteria.where("id").is(id));
            Update update = new Update().set("playerIds", playerIds).set("playerNum", matchInfo.getPlayerNum());
            mongoTemplate.updateFirst(query, update, MatchInfo.class);
        }
    }

    // 修改房间状态
    public void updateStatus(int id, byte status) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update().set("status", status);
        mongoTemplate.updateFirst(query, update, MatchInfo.class);
    }
}
