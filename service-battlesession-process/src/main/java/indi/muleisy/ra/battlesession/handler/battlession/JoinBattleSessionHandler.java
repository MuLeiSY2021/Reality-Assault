package indi.muleisy.ra.battlesession.handler.battlession;

import indi.muleisy.ra.battlesession.dao.spring.MatchInfoDao;
import indi.muleisy.ra.battlesession.dao.spring.SpringDaoManager;
import indi.muleisy.ra.battlesession.data.MatchInfo;
import indi.muleisy.ra.battlesession.schedule.MatchCountdownScheduler;
import indi.muleisy.ra.pub.geodb.RisegerUtil;
import indi.muleisy.ra.pub.netty.handler.AfterLoginInboundHandler;
import indi.muleisy.ra.pub.netty.packet.battlesession.request.JoinBattleSessionRequest;
import indi.muleisy.ra.pub.netty.packet.battlesession.response.JoinBattleSessionResponse;
import indi.muleisy.ra.pub.redis.dao.PlayerBattleInfoDao;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import lombok.extern.log4j.Log4j2;
import org.riseger.protocol.compiler.result.ResultSet;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Log4j2
public class JoinBattleSessionHandler extends AfterLoginInboundHandler<JoinBattleSessionRequest> {

    private MatchInfoDao matchInfoDao = SpringDaoManager.INSTANCE.getMatchInfoDao();

    private static final int MAX_PLAYERS = 10;

    @Override
    protected void channelRead1(ChannelHandlerContext ctx, JoinBattleSessionRequest msg) throws Exception {
        ResultSet set = RisegerUtil.search("USE\n" +
                "  DATABASE 'reality_assault'|\n" +
                "  MAP 'battlefield_mp'|\n" +
                "  SCOPE RECT(\t\t\n" +
                "    ["+msg.getLongitude()+", "+msg.getLatitude()+"],\t\t\n" +
                "    1\n" +
                "  )|\n" +
                "  MODEL battle_field\n" +
                "SEARCH\n" +
                "  battle_field.id\n" +
                "WHERE\n" +
                "  IN RECT(\n" +
                "    ["+msg.getLongitude()+", "+msg.getLatitude()+"],\n" +
                "    1\n" +
                "  )" +
                ";");
        if(set == null || set.getCount() == 0) {
            return;
        }
        Integer userId = getUserId(ctx);
        Integer matchId = (Integer) set.getModelSet("battle_field").getResultElements().get(0).getColumn("id");
        MatchInfo matchInfo = matchInfoDao.getMatchInfo(matchId);

        if (matchInfo == null) {
            // 如果房间不存在，可以做出相应的处理，比如返回错误信息
            log.error("Room not found.");
            return;
        }

        // 检查是否已经满人
        if (matchInfo.getPlayerNum() >= MAX_PLAYERS) {
            ctx.writeAndFlush("Room is full.");
            ctx.channel().writeAndFlush(new JoinBattleSessionResponse().failure());
            return;
        }

        // 添加玩家到房间
        matchInfoDao.addPlayer(matchId, userId);
        PlayerBattleInfoDao.INSTANCE.set(userId,"battleFieldId",matchId);

        // 如果人数达到10人，改变房间状态为1（倒计时开始）
        if (matchInfo.getPlayerNum() == MAX_PLAYERS) {
            matchInfoDao.updateStatus(matchId, (byte) 1);

            // 添加游戏开始的10秒钟定时任务
            ScheduledFuture<?> scheduledFuture = ctx.executor().schedule(
                    new MatchCountdownScheduler(ctx.channel(), ctx.executor(),matchId),
                    10,
                    TimeUnit.SECONDS);

            // 将定时任务的句柄保存在Channel的属性中
            ctx.channel().attr(AttributeKey.valueOf("gameStart")).set(scheduledFuture);
        }

        ctx.channel().writeAndFlush(new JoinBattleSessionResponse().success());
    }
}
