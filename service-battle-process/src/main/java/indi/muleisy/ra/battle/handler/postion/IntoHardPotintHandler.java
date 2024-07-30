package indi.muleisy.ra.battle.handler.postion;

import indi.muleisy.ra.battle.data.PlayerBattleInfo;
import indi.muleisy.ra.battle.handler.AfterRegisterSessionInboundHandler;
import indi.muleisy.ra.battle.packet.request.IntoHardPointRequest;
import indi.muleisy.ra.battle.packet.request.PositionUpdateRequest;
import indi.muleisy.ra.pub.utils.geodb.RisegerUtil;
import io.netty.channel.ChannelHandlerContext;
import org.riseger.protocol.compiler.result.ResultSet;

public class IntoHardPotintHandler extends AfterRegisterSessionInboundHandler<PositionUpdateRequest> {
    @Override
    protected void channelRead2(ChannelHandlerContext ctx, PositionUpdateRequest msg) throws Exception {
        if((byte) PlayerBattleInfo.INSTANCE.get(ctx, "inHardPoint")!=-1) {
            return;
        }
        ResultSet set = RisegerUtil.search("USE\n" +
                "  DATABASE 'reality_assault'|\n" +
                "  MAP 'battlefield_mp'|\n" +
                "  SCOPE RECT(\t\t\n" +
                "    ["+msg.getLongitude()+", "+msg.getLatitude()+"],\t\t\n" +
                "    1\n" +
                "  )|\n" +
                "  MODEL hard_point\n" +
                "SEARCH\n" +
                "  hard_point.id\n" +
                "WHERE\n" +
                "  IN RECT(\n" +
                "    ["+msg.getLongitude()+", "+msg.getLatitude()+"],\n" +
                "    1\n" +
                "  )"+
                ";");
        if(set != null && set.getCount() > 0) {
            byte id = (byte) set.getModelSetMap().get("hard_point").getResultElements().get(0).getColumn("id");
            PlayerBattleInfo.INSTANCE.set(ctx, "inHardPoint",id);
            ctx.channel().write(new IntoHardPointRequest(id));
        }
    }
}
