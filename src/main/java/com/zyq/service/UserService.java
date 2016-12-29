package com.zyq.service;

import com.zyq.dao.LoginTicketDao;
import com.zyq.dao.UserDao;
import com.zyq.model.LoginTicket;
import com.zyq.model.User;
import com.zyq.util.MaterialUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by TK on 2016/12/2.
 */
@Service("userService")
public class UserService {
    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;
    @Autowired
    private LoginTicketDao loginTicketDao;

//    public HashMap<String,Object> register(String name,String password){
//        HashMap<String,Object> hashMap = new HashMap<>();
//        if(StringUtils.isBlank(name)){
//            hashMap.put("msgName","用户名不能为空");
//        }
//        if(StringUtils.isBlank(password)){
//            hashMap.put("msgPassword","密码不能为空");
//        }
//        User user = userDao.selectByName(name);
//        if(user != null){
//            hashMap.put("msgName","用户已经被注册");
//        }
//
//        user = new User();
//        user.setName(name);
//        user.setSalt(UUID.randomUUID().toString().substring(0,5));
//        user.setPassword(MaterialUtil.MD5(password+user.getSalt()));
//        userDao.addUser(user);
//        logger.info("注册成功");
//        //注册完登录
//        String ticket = addLoginTicket(user.getUserId());
//        hashMap.put("ticket",ticket);
//        return hashMap;
//    }

    public HashMap<String,Object> login(String name,String password){
        HashMap<String,Object> hashMap = new HashMap<>();
        if(StringUtils.isBlank(name)){
            hashMap.put("msgName","用户名不能为空");
            return hashMap;
        }
        if(StringUtils.isBlank(password)){
            hashMap.put("msgPassword","密码不能为空");
        }
        User user = userDao.selectByNameOne(name);
        if(user == null){
            hashMap.put("msgName","用户名不存在");
            return hashMap;
        }
        if(!MaterialUtil.MD5(password+user.getSalt()).equals(user.getPassword())){
            hashMap.put("msgPassword","密码不正确");
            return hashMap;
        }

        String ticket = addLoginTicket(user.getUserId());
        hashMap.put("ticket",ticket);
        hashMap.put("authority",user.getType());
        return hashMap;
    }

    public void logout(String ticket){
        try {
            loginTicketDao.updateStatus(ticket);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    public List<User> getAllUsers(){
        List<User> list = null;
        try {
            list = userDao.selectAll();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return list;

    }

    public User getUserByUserId(int userId){
        User user = null;
        try {
            user = userDao.selectById(userId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return user;
    }

    public List<User> getUserByName(String name){
        List<User> userList = null;
        try {
            userList = userDao.selectByName("%"+name+"%");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return userList;
    }

    public User getUserByNameOne(String name){
        User user = null;
        try {
            user = userDao.selectByNameOne(name);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return user;
    }
    public HashMap<String, Object> addUser(User user){
        HashMap<String,Object> map = new HashMap<>();
        if (user != null){
            if (StringUtils.isBlank(user.getName())){
                map.put("msgName","用户名不能为空");
                return map;
            }
            if (StringUtils.isBlank(user.getPassword())){
                map.put("msgPassword","密码不能为空");
            }
            User user1 = userDao.selectByNameOne(user.getName());
            if (user1 != null){
                map.put("msgDupli","用户名已存在");
            }else{
                user.setSalt(UUID.randomUUID().toString().substring(0,5));
                user.setPassword(MaterialUtil.MD5(user.getPassword()+user.getSalt()));
                try {
                    userDao.addUser(user);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error(e.getMessage());
                }
            }
        }else{
            map.put("msgPost","提交内容不能为空");
        }
        return map;
    }

    public HashMap<String,Object> modifyUser(User user){
        HashMap<String,Object> map = new HashMap<>();
        if(user != null){
            if(StringUtils.isBlank(user.getName())){
                map.put("msgName","用户名不能为空");
                return map;
            }
        }else{
            map.put("msgPost","提交内容不能为空");
            return map;
        }
        try {
            userDao.updateUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return map;
    }
    public void removeUserByUserId(int userId){
        userDao.deleteByUserId(userId);
    }
    private  String addLoginTicket(int userId){
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime()+1000*3600*24);
        ticket.setExpired(date);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replace("-",""));
        try {
            loginTicketDao.addTicket(ticket);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return ticket.getTicket();
    }
}
