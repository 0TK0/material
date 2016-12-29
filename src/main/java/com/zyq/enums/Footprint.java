package com.zyq.enums;

/**
 * Created by TK on 2016/12/15.
 */
public enum  Footprint {
    C0402(100,"c0402"),
    C0603(101,"c0603"),
    C0805(102,"c0805"),
    C1210(103,"c1210"),

    R0402(200,"r0402"),
    R0603(201,"r0603"),
    R0805(202,"r0805");

    /*
    其他暂时不写
     */

    private int code;
    private String desc;

    Footprint(int code, String desc) {
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
