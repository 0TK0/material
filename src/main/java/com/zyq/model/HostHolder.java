package com.zyq.model;

import org.springframework.stereotype.Component;

/**
 * Created by TK on 2016/12/5.
 */
@Component
public class HostHolder {
    private static ThreadLocal<User> users = new ThreadLocal<>();
    public User getUser(){
        return users.get();
    }

    public void setUsers(User user){
        users.set(user);
    }

    public void clear(){
        users.remove();
    }
}
