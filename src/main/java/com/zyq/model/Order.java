package com.zyq.model;

import org.springframework.stereotype.Component;

/**
 * Created by TK on 2016/12/9.
 */
@Component
public class Order {
    private int orderId;
    private int orderFormId;
    private int componentId;
    private int quantity;
    private int is_delete;

    public Order() {
    }

    public Order(int orderId, int orderFormId, int componentId, int quantity, int is_delete) {
        this.orderId = orderId;
        this.orderFormId = orderFormId;
        this.componentId = componentId;
        this.quantity = quantity;
        this.is_delete = is_delete;
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

    public int getComponentId() {
        return componentId;
    }

    public void setComponentId(int componentId) {
        this.componentId = componentId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(int is_delete) {
        this.is_delete = is_delete;
    }
}
