package com.zyq.model;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by TK on 2016/12/9.
 */
@Component
public class OrderForm {
    private int orderFormId;
    private String name;
    private int userIdApply;
    private int userIdDeal;
    private int status;
    private int isDelete;
    private Date createTime;
    private Date modifiedTime;

    public OrderForm() {
    }

    public OrderForm(int orderFormId, String name, int userIdApply, int userIdDeal, int status, int isDelete, Date createTime, Date modifiedTime) {
        this.orderFormId = orderFormId;
        this.name = name;
        this.userIdApply = userIdApply;
        this.userIdDeal = userIdDeal;
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

    public int getUserIdApply() {
        return userIdApply;
    }

    public void setUserIdApply(int userIdApply) {
        this.userIdApply = userIdApply;
    }

    public int getUserIdDeal() {
        return userIdDeal;
    }

    public void setUserIdDeal(int userIdDeal) {
        this.userIdDeal = userIdDeal;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}
