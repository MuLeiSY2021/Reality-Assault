package indi.muleisy.ra.pub.utils.redis;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Key {
    short value() default 0;
}
