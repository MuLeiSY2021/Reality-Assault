package indi.muleisy.ra.battlesession.handler.battlession;

import indi.muleisy.ra.battlesession.BattleProcessServer;
import indi.muleisy.ra.battlesession.dao.spring.MatchInfoDao;
import indi.muleisy.ra.battlesession.data.MatchInfo;
import indi.muleisy.ra.pub.netty.handler.AfterLoginInboundHandler;
import indi.muleisy.ra.pub.netty.packet.battlesession.request.ExitBattleSessionRequest;
import indi.muleisy.ra.pub.netty.packet.battlesession.response.ExitBattleSessionResponse;
import indi.muleisy.ra.pub.redis.dao.PlayerBattleInfoDao;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

import java.util.concurrent.ScheduledFuture;

public class ExitBattleSessionHandler extends AfterLoginInboundHandler<ExitBattleSessionRequest> {

    private final MatchInfoDao matchInfoDao = BattleProcessServer.DAO_MANAGER.getMatchInfoDao();

    @Override
    protected void channelRead1(ChannelHandlerContext ctx, ExitBattleSessionRequest msg) throws Exception {
        Integer userId = getUserId(ctx);
        Integer roomId = (Integer) PlayerBattleInfoDao.INSTANCE.get(ctx.channel(),"battleFieldId");;

        MatchInfo matchInfo = matchInfoDao.getMatchInfo(roomId);

        if (matchInfo == null) {
            // 如果房间不存在，可以做出相应的处理，比如返回错误信息
            ctx.writeAndFlush("Room not found.");
            return;
        }

        // 移除玩家
        matchInfoDao.removePlayer(roomId, userId);

        // 检查房间人数，如果人数少于10人并且状态是1（倒计时），则取消倒计时
        if (matchInfo.getPlayerNum() < 10 && matchInfo.getStatus() == 1) {
            // 获取之前存储的倒计时任务
            ScheduledFuture<?> scheduledFuture = ctx.channel().attr(AttributeKey.<ScheduledFuture<?>>valueOf("gameStart")).get();

            if (scheduledFuture != null && !scheduledFuture.isDone()) {
                // 取消定时任务
                scheduledFuture.cancel(false);
                ctx.channel().attr(AttributeKey.valueOf("gameStart")).set(null);

                // 更新房间状态回到等待中 (状态0)
                matchInfoDao.updateStatus(roomId, (byte) 0);
            }
        }

        ctx.channel().writeAndFlush(new ExitBattleSessionResponse().success());
    }
}
