package indi.muleisy.ra.pub.netty.packet.battle.request;

import indi.muleisy.ra.pub.netty.packet.battle.PacketType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PositionUpdateRequest extends InBattleRequestPacket {
    private Double latitude, longitude;

    public PositionUpdateRequest(Double latitude, Double longitude) {
        super(PacketType.POSITION_UPDATE_REQUEST);
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
