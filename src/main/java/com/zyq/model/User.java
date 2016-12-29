package com.zyq.model;

import org.springframework.stereotype.Component;

/**
 * Created by TK on 2016/12/2.
 */
@Component
public class User {
    private int userId;
    private String name;
    private String password;
    private String salt;
    private int type;
    private int isDelete;

    public User() {
    }

    public User(int userId, String name, String password, String salt, int type, int isDelete) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.salt = salt;
        this.type = type;
        this.isDelete = isDelete;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return isDelete;
    }

    public void setStatus(int isDelete) {
        this.isDelete = isDelete;
    }
}
