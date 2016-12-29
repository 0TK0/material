package com.zyq.model;

import org.springframework.stereotype.Component;

/**
 * Created by TK on 2016/12/8.
 */
@Component
public class Components {
    private int componentId;
    private String name;
    private String footprint;
    private String value;
    private String remark;
    private int isDelete;
    private int componentCode;
    private int footprintCode;
    private int quantity;

    public Components() {
    }

    public Components(int componentId, String name, String footprint, String value, String remark, int isDelete, int componentCode, int footprintCode, int quantity) {
        this.componentId = componentId;
        this.name = name;
        this.footprint = footprint;
        this.value = value;
        this.remark = remark;
        this.isDelete = isDelete;
        this.componentCode = componentCode;
        this.footprintCode = footprintCode;
        this.quantity = quantity;
    }

    public int getComponentId() {
        return componentId;
    }

    public void setComponentId(int componentId) {
        this.componentId = componentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFootprint() {
        return footprint;
    }

    public void setFootprint(String footprint) {
        this.footprint = footprint;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public int getComponentCode() {
        return componentCode;
    }

    public void setComponentCode(int componentCode) {
        this.componentCode = componentCode;
    }

    public int getFootprintCode() {
        return footprintCode;
    }

    public void setFootprintCode(int footprintCode) {
        this.footprintCode = footprintCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
