package com.zyq.controller;

import com.zyq.constants.WebConstants;
import com.zyq.dao.UserDao;
import com.zyq.model.HostHolder;
import com.zyq.model.User;
import com.zyq.service.UserService;
import com.zyq.util.MaterialUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by TK on 2016/12/2.
 */
@Controller
@RequestMapping("/user")
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    UserDao userDao;

    @RequestMapping(path = "/toLogin",method = {RequestMethod.GET,RequestMethod.POST})
    public String toLogin(){
        logger.info("toLogin,去登录");
        return "user/login";
    }

//    @RequestMapping(path = "/toReg",method = {RequestMethod.GET,RequestMethod.POST})
//    public String toReg(ModelMap modelMap){
//        logger.info("toReg:去注册");
//        modelMap.put("redirectUrl","user/register");
//        return "user/register";
//    }

//    @RequestMapping(path = "/reg",method = {RequestMethod.POST,RequestMethod.GET})
//    public String reg(@RequestParam("name") String name,
//                      @RequestParam("password") String password,
//                      HttpServletResponse response, ModelMap modelMap){
//        try {
//            HashMap<String,Object> hashMap = userService.register(name,password);
//            if(hashMap.containsKey("ticket")){
//                Cookie cookie = new Cookie("ticket",hashMap.get("ticket").toString());
//                cookie.setPath("/");
//                response.addCookie(cookie);
//                modelMap.put("msg",0);
//                return "user/login";
//            }else{
//                modelMap.put("msg",hashMap);
//                return "error";
//            }
//        } catch (Exception e) {
//            logger.error("注册异常"+e.getMessage());
//            HashMap<String,Object> map = new HashMap<>();
//            map.put("msgException","注册异常");
//            modelMap.put("msg",map);
//            return "error";
//        }
//    }

    @RequestMapping(path = "/login",method = {RequestMethod.GET,RequestMethod.POST})
    public String login(@RequestParam("name") String name,
                        @RequestParam("password")String password,
                        @RequestParam(value = "remember",defaultValue = "0") int remember,
                        ModelMap modelMap, HttpSession session,HttpServletResponse response){
        try {
            HashMap<String,Object> hashMap = userService.login(name,password);
            if (hashMap.containsKey("ticket")){
                Cookie cookie = new Cookie("ticket",hashMap.get("ticket").toString());
                cookie.setPath("/");
                cookie.setMaxAge(3600*24*5);
                response.addCookie(cookie);
                modelMap.put("userName",name);
                if (hashMap.containsKey("authority")){
                    modelMap.put("authority",hashMap.get("authority"));
                }
                User user = userService.getUserByNameOne(name);
                session.setAttribute("user",user.getName());
//                hostHolder.setUsers(user);
                modelMap.addAttribute("vm","login_main.vm");
                return "login_main";
            }else {
                modelMap.put("msg",hashMap);
                return "error";
            }
        } catch (Exception e) {
            logger.error("登录异常",e.getMessage());
            HashMap<String,Object> map = new HashMap<>();
            map.put("msgException","登录异常");
            modelMap.put("msg",map);
            return "error";
        }
    }

    @RequestMapping(path = "/logout",method = {RequestMethod.POST,RequestMethod.GET})
    public String logout(@CookieValue("ticket")String ticket){
        userService.logout(ticket);
        return "user/login";
    }
}
