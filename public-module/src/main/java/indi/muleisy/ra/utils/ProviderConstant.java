package indi.muleisy.ra.utils;

import java.util.Objects;

public enum ProviderConstant {
    QQ_PROVIDER_NAME("qq"),
    GITHUB_PROVIDER_NAME("github"),

    WECHAT_PROVIDER_NAME("wechat"),

    OTHER_PROVIDER_NAME("other"),
    ;

    final String name;

    ProviderConstant(String name) {
        this.name = name;
    }

    public static ProviderConstant nameOf(String name) {
        for (ProviderConstant constant : ProviderConstant.values()) {
            if(Objects.equals(constant.name.toLowerCase(), name.toLowerCase())) {
                return constant;
            }
        }
        return OTHER_PROVIDER_NAME;
    }
}
