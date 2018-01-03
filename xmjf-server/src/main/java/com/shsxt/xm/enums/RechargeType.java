package com.shsxt.xm.enums;

/**
 * Created by lp on 2017/11/14.
 */
public enum RechargeType {

    APP(1),
    ADMIN(2),
    WEB(3),
    WX(4);

    RechargeType(Integer type) {
        this.type = type;
    }

    private  Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
