package com.zyq.model;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by TK on 2016/12/9.
 */
@Component
public class OrderFormModel {
    private int orderFormId;
    private String name;
    private int orderId;
    private User applyUser;
    private User dealUser;
    private int status;
    private int isDelete;
    private String createTime;
    private String modifiedTime;

    public OrderFormModel() {
    }

    public OrderFormModel(int orderFormId, String name, int orderId, User applyUser, User dealUser, int status, int isDelete, String createTime, String modifiedTime) {
        this.orderFormId = orderFormId;
        this.name = name;
        this.orderId = orderId;
        this.applyUser = applyUser;
        this.dealUser = dealUser;
        this.status = status;
        this.isDelete = isDelete;
        this.createTime = createTime;
        this.modifiedTime = modifiedTime;
    }

    public int getOrderFormId() {
        return orderFormId;
    }

    public void setOrderFormId(int orderFormId) {
        this.orderFormId = orderFormId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public User getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(User applyUser) {
        this.applyUser = applyUser;
    }

    public User getDealUser() {
        return dealUser;
    }

    public void setDealUser(User dealUser) {
        this.dealUser = dealUser;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}
