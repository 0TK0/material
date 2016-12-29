package com.zyq.controller;

import com.zyq.dao.UserDao;
import com.zyq.model.User;
import com.zyq.util.MaterialUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

/**
 * Created by TK on 2016/12/3.
 */
@Controller
public class HomeController {
    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    UserDao userDao;

    @RequestMapping(path = "/home",method = {RequestMethod.POST,RequestMethod.GET})
    public String home(){
        logger.info("home,主页");
        return "user/login";
    }
}
