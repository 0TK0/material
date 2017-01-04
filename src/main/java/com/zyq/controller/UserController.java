package com.zyq.controller;

import com.zyq.model.HostHolder;
import com.zyq.model.User;
import com.zyq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * Created by TK on 2016/12/6.
 */
@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    HostHolder hostHolder;


    @RequestMapping(path = "/userList",method = {RequestMethod.POST,RequestMethod.GET})
    public String userList(ModelMap modelMap){
        User user = hostHolder.getUser();
        List<User> userList = userService.getAllUsers();
        modelMap.addAttribute("userList",userList);
//        modelMap.addAttribute("vm","/user/allUsers.vm");
        modelMap.addAttribute("userName",user.getName());
        return "user/allUsers";
    }

    @RequestMapping(path = "/toAddUser",method = {RequestMethod.GET,RequestMethod.POST})
    public String toAddUser(ModelMap modelMap){
        User user = hostHolder.getUser();
        if (user.getType() == 1){
            return "authError";
        }
        modelMap.addAttribute("userName",user.getName());
        return "user/addUser";
    }

    @RequestMapping(path = "/addUser",method = {RequestMethod.POST,RequestMethod.GET})
    public String addUser(User user,ModelMap modelMap){
        HashMap<String,Object> map = userService.addUser(user);
        if (map == null || map.size()==0){
            return "redirect:/users/userList";
        }else{
            modelMap.put("msg",map);
            return "error";
        }

    }
    @RequestMapping(path = "/toSearch",method = {RequestMethod.GET,RequestMethod.POST})
    public String toSearch(ModelMap modelMap){
        User user = hostHolder.getUser();
        modelMap.addAttribute("userName",user.getName());
        return "user/search";
    }

    @RequestMapping(path = "/search",method = {RequestMethod.POST,RequestMethod.GET})
    public String search(@RequestParam("name") String name,ModelMap modelMap){
        List<User> users = userService.getUserByName(name);
        modelMap.addAttribute("users",users);
        User user = hostHolder.getUser();
        modelMap.addAttribute("userName",user.getName());
        return "user/searchResult";
    }
    @RequestMapping(path = "/toModify",method = {RequestMethod.GET,RequestMethod.POST})
    public String toModify(@RequestParam("userId") int userId,ModelMap modelMap){
        User user1 = hostHolder.getUser();
        if (user1.getType() == 1){
            return "authError";
        }
        User user = userService.getUserByUserId(userId);
        modelMap.addAttribute("userMod",user);
        modelMap.addAttribute("userName",user1.getName());
        return "user/modifyUser";
    }

    @RequestMapping(path = "/modify",method = {RequestMethod.POST,RequestMethod.GET})
    public String modify(User user,ModelMap modelMap){
        HashMap<String,Object> map = userService.modifyUser(user);
        if (map == null || map.size() == 0){
            return "redirect:/users/userList";
        }else{
            modelMap.addAttribute("msg",map);
            return "error";
        }

    }

    @RequestMapping(path = "/delete",method = {RequestMethod.GET,RequestMethod.POST})
    public String delete(@RequestParam("userId") int userId){
        User user = hostHolder.getUser();
        if (user.getType() == 1){
            return "authError";
        }
        userService.removeUserByUserId(userId);
        return "redirect:/users/userList";
    }
}
