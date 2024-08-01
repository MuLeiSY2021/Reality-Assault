package indi.muleisy.ra.battle.handler.postion;

import indi.muleisy.ra.battle.handler.RegisterSessionInboundHandler;
import indi.muleisy.ra.pub.netty.packet.battle.request.IntoSpawnRequest;
import indi.muleisy.ra.pub.netty.packet.battle.request.PositionUpdateRequest;
import indi.muleisy.ra.pub.redis.dao.PlayerBattleInfoDao;
import indi.muleisy.ra.pub.geodb.RisegerUtil;
import io.netty.channel.ChannelHandlerContext;
import org.riseger.protocol.compiler.result.ResultSet;

public class IntoPartySpawnHandler extends RegisterSessionInboundHandler<PositionUpdateRequest> {
    @Override
    protected void channelRead2(ChannelHandlerContext ctx, PositionUpdateRequest msg) throws Exception {
        if((byte) PlayerBattleInfoDao.INSTANCE.get(ctx.channel(), "inBorn")==1) {
            return;
        }
        byte party = (byte) PlayerBattleInfoDao.INSTANCE.get(ctx.channel(), "party");
        ResultSet set = RisegerUtil.search("USE\n" +
                "  DATABASE 'reality_assault'|\n" +
                "  MAP 'battlefield_mp'|\n" +
                "  SCOPE RECT(\t\t\n" +
                "    ["+msg.getLongitude()+", "+msg.getLatitude()+"],\t\t\n" +
                "    1\n" +
                "  )|\n" +
                "  MODEL spawn_point\n" +
                "SEARCH\n" +
                "  spawn_point.*\n" +
                "WHERE\n" +
                "  IN RECT(\n" +
                "    ["+msg.getLongitude()+", "+msg.getLatitude()+"],\n" +
                "    1\n" +
                "  )AND spawn_point.party =" + party  +
                ";");
        if(set != null && set.getCount() > 0) {
            ctx.channel().write(new IntoSpawnRequest());
        }
    }
}
