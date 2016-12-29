package com.zyq.model;

import org.springframework.stereotype.Component;

/**
 * Created by TK on 2016/12/12.
 */
@Component
public class OrderModel {
    private int orderId;
    private int orderFormId;
    private String componentName;
    private String footprint;
    private String value;
    private String remark;
    private int quantity;

    public OrderModel() {
    }

    public OrderModel(int orderId, int orderFormId, String componentName, String footprint, String value, String remark, int quantity) {
        this.orderId = orderId;
        this.orderFormId = orderFormId;
        this.componentName = componentName;
        this.footprint = footprint;
        this.value = value;
        this.remark = remark;
        this.quantity = quantity;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderFormId() {
        return orderFormId;
    }

    public void setOrderFormId(int orderFormId) {
        this.orderFormId = orderFormId;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
