package com.cc.serve.common.enums;

public enum AuthType {
    AUTH_TYPE_DEFAULT(0, "default"),

    AUTH_TYPE_SMS(1, "sms"),

    AUTH_TYPE_GIT_HUB(2, "git_hub");

    private final Integer id;

    private final String authType;

    AuthType(int id, String authType) {
        this.id = id;
        this.authType = authType;
    }

    public Integer getId() {
        return id;
    }

    public String getAuthType() {
        return authType;
    }
}
