package com.cc.serve.common.enums;

public enum Role {
    ADMIN(0, "ADMIN"),
    USER(1, "USER");

    private Integer type;

    private String name;

    Role(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
