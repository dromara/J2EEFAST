package com.j2eefast.framework.shiro;

public enum LoginType {
    /**
     * 密码登录
     */
    NORMAL("User"),
    /**
     * 免密码登录
     */
    FREE("Free");

    private String desc;

    LoginType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
