package com.zyq.enums;

/**
 * Created by TK on 2016/12/9.
 */
public enum ApplyDealEnum {
    DEALED(1,"已处理"),
    NOTDEAL(0,"未处理");

    private int code;
    private String desc;

    ApplyDealEnum(int code, String desc) {
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
