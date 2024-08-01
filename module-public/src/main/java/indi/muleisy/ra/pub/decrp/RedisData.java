package indi.muleisy.ra.pub.decrp;

import indi.muleisy.ra.pub.config.RedisTopicConstant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RedisData {
    RedisTopicConstant value();
}

