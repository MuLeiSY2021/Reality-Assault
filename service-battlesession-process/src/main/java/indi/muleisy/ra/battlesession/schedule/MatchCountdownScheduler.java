package indi.muleisy.ra.battlesession.schedule;

import indi.muleisy.ra.battlesession.dao.spring.MatchInfoDao;
import indi.muleisy.ra.battlesession.dao.spring.SpringDaoManager;
import indi.muleisy.ra.battlesession.data.MatchInfo;
import indi.muleisy.ra.pub.netty.schedule.Scheduler;
import io.netty.channel.Channel;
import io.netty.util.concurrent.EventExecutor;

public class MatchCountdownScheduler extends Scheduler {

    private final EventExecutor executor;

    private final Integer matchId;

    private final MatchInfoDao matchInfoDao = SpringDaoManager.INSTANCE.getMatchInfoDao();

    public MatchCountdownScheduler(Channel channel, EventExecutor executor, Integer matchId) {
        super(channel);
        this.executor = executor;
        this.matchId = matchId;
    }

    @Override
    public void run() {
        matchInfoDao.updateStatus(matchId, (byte) 2);
        MatchInfo matchInfo = matchInfoDao.getMatchInfo(matchId);
        for (Integer playerId :matchInfo.getPlayerIds()) {
            //TODO:Redis信息初始化（待完成）
        }

    }
}