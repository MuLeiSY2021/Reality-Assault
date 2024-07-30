package indi.muleisy.ra.pub.utils.jwt;

import java.util.Objects;

public enum JwtConstant {
    SECRET_KEY("NK1Ywma08dtalwxCuQG6hOSAuXW0Qbw/"),
    ;

    final String name;

    JwtConstant(String name) {
        this.name = name;
    }

    public static JwtConstant nameOf(String name) {
        for (JwtConstant constant : JwtConstant.values()) {
            if(Objects.equals(constant.name.toLowerCase(), name.toLowerCase())) {
                return constant;
            }
        }
        return null;
    }
}
