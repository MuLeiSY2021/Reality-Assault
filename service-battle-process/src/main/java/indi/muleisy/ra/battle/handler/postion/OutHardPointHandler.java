package indi.muleisy.ra.battle.handler.postion;

import indi.muleisy.ra.battle.data.PlayerBattleInfo;
import indi.muleisy.ra.battle.handler.AfterRegisterSessionInboundHandler;
import indi.muleisy.ra.battle.packet.request.OutHardPointRequest;
import indi.muleisy.ra.battle.packet.request.PositionUpdateRequest;
import indi.muleisy.ra.pub.utils.geodb.RisegerUtil;
import io.netty.channel.ChannelHandlerContext;
import org.riseger.protocol.compiler.result.ResultSet;

public class OutHardPointHandler extends AfterRegisterSessionInboundHandler<PositionUpdateRequest> {
    @Override
    protected void channelRead2(ChannelHandlerContext ctx, PositionUpdateRequest msg) throws Exception {
        if((byte) PlayerBattleInfo.INSTANCE.get(ctx, "inHardPoint")==-1) {
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
                "  hard_point.*\n" +
                "WHERE\n" +
                "  IN RECT(\n" +
                "    ["+msg.getLongitude()+", "+msg.getLatitude()+"],\n" +
                "    1\n" +
                "  )"+
                ";");
        if(set != null && set.getCount() == 0) {
            byte id = (Byte) PlayerBattleInfo.INSTANCE.get(ctx, "inHardPoint");
            PlayerBattleInfo.INSTANCE.set(ctx, "inHardPoint",-1);
            ctx.channel().write(new OutHardPointRequest(id));
        }
    }
}
