package indi.muleisy.ra.battle.schedule;

import indi.muleisy.ra.pub.netty.schedule.Scheduler;
import indi.muleisy.ra.pub.redis.dao.BattleFieldDao;
import indi.muleisy.ra.pub.redis.dao.PlayerBattleInfoDao;
import io.netty.channel.Channel;

public class EconomyScheduler extends Scheduler {

    public EconomyScheduler(Channel channel) {
        super(channel);
    }

    @Override
    public void run() {
        Integer[] party1Idlist = (Integer[]) BattleFieldDao.INSTANCE.get(channel,"1playersId");
        Integer economy1 = (Integer) BattleFieldDao.INSTANCE.get(channel,"1economy");
        for (Integer party1Id :party1Idlist) {
            PlayerBattleInfoDao.INSTANCE.incrBy(party1Id,"economy", Long.valueOf(economy1));
        }

        Integer[] party_1Idlist = (Integer[]) BattleFieldDao.INSTANCE.get(channel,"-1playersId");
        Integer economy_1 = (Integer) BattleFieldDao.INSTANCE.get(channel,"-1economy");
        for (Integer party_1Id :party_1Idlist) {
            PlayerBattleInfoDao.INSTANCE.incrBy(party_1Id,"economy", Long.valueOf(economy_1));
        }

    }
}
