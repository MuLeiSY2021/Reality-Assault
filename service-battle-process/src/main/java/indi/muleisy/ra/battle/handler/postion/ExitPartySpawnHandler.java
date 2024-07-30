package indi.muleisy.ra.battle.handler.postion;

import indi.muleisy.ra.battle.data.PlayerBattleInfo;
import indi.muleisy.ra.battle.handler.AfterRegisterSessionInboundHandler;
import indi.muleisy.ra.battle.packet.request.ExitSpawnRequest;
import indi.muleisy.ra.battle.packet.request.PositionUpdateRequest;
import indi.muleisy.ra.pub.utils.geodb.RisegerUtil;
import io.netty.channel.ChannelHandlerContext;
import org.riseger.protocol.compiler.result.ResultSet;

public class ExitPartySpawnHandler extends AfterRegisterSessionInboundHandler<PositionUpdateRequest> {
    @Override
    protected void channelRead2(ChannelHandlerContext ctx, PositionUpdateRequest msg) throws Exception {
        if((byte) PlayerBattleInfo.INSTANCE.get(ctx, "inBorn")==0) {
            return;
        }
        byte party = (byte) PlayerBattleInfo.INSTANCE.get(ctx, "party");
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
        if(set != null && set.getCount() == 0) {
            ctx.channel().write(new ExitSpawnRequest());
        }
    }
}
