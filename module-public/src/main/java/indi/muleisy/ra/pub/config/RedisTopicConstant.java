package indi.muleisy.ra.pub.config;

import lombok.Getter;

@Getter
public enum RedisTopicConstant {
    USER_BATTLE_INFO(0),

    BATTLE_FIELD_INFO(1);

    private final Integer value;

    RedisTopicConstant(Integer value) {
        this.value = value;
    }

}
