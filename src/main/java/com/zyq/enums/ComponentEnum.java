package com.zyq.enums;

/**
 * Created by TK on 2016/12/15.
 */
public enum ComponentEnum {

    CAP(1,"电容"),
    RES(2,"电阻"),
    PORT(3,"接口"),
    POWER(4,"电源"),
    CHIP(5,"芯片"),
    IND(6,"电感"),
    OTHERS(7,"其他");


    private int code;
    private String desc;

    ComponentEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
