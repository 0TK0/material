package com.zyq.controller;

import com.zyq.model.Components;
import com.zyq.model.HostHolder;
import com.zyq.model.User;
import com.zyq.service.ComponentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * Created by TK on 2016/12/8.
 */
@Controller
@RequestMapping("/components")
public class ComponentsController {

    @Autowired
    ComponentsService componentsService;
    @Autowired
    HostHolder hostHolder;

    @RequestMapping(path = "/list",method = {RequestMethod.POST,RequestMethod.GET})
    public String list(ModelMap modelMap){
        User user = hostHolder.getUser();
        List<Components> list = componentsService.getAll();
        System.out.println("list"+list);
        for (Components components : list){
            System.out.println(components.getName());
        }
        modelMap.addAttribute("userName",user.getName());
        modelMap.addAttribute("list",list);
        return "component/allComponents";
    }

    @RequestMapping(path = "/toAdd",method = {RequestMethod.GET,RequestMethod.POST})
    public String toAdd(ModelMap modelMap, HttpSession session){
        User user = hostHolder.getUser();
        if (user.getType() == 1){
            return "authError";
        }
        modelMap.addAttribute("userName",user.getName());
        return "component/add";
    }

    @RequestMapping(path = "add",method = {RequestMethod.GET,RequestMethod.POST})
    public String add(Components components,ModelMap modelMap){
        HashMap<String,Object> map = componentsService.addComponent(components);
        if (map == null || map.size()==0 ){
            return "redirect:/components/list";
        }else {
            modelMap.addAttribute("msg",map);
            return "error";
        }
    }

    @RequestMapping(path = "/toSearch",method = {RequestMethod.GET,RequestMethod.POST})
    public String toSearch(ModelMap modelMap, HttpSession session){
        User user = hostHolder.getUser();
        modelMap.addAttribute("userName",user.getName());
        return "component/search";
    }

    @RequestMapping(path = "/search",method = {RequestMethod.POST,RequestMethod.GET})
    public String search(@RequestParam("name") String name, ModelMap modelMap){
        User user = hostHolder.getUser();
        List<Components> componentsList = componentsService.getComponentByName(name);
        modelMap.addAttribute("ComponentsList",componentsList);
        modelMap.addAttribute("userName",user.getName());
        return "component/searchResult";
    }
    @RequestMapping(path = "/toModify",method = {RequestMethod.GET,RequestMethod.POST})
    public String toModify(@RequestParam("componentId") int componentId,ModelMap modelMap){
        User user = hostHolder.getUser();
        if (user.getType() == 1){
            return "authError";
        }
        Components components = componentsService.getComponentByComponentId(componentId);
        modelMap.addAttribute("componentMod",components);
        modelMap.addAttribute("userName",user.getName());
        return "component/modifyComponent";
    }

    @RequestMapping(path = "/modify",method = {RequestMethod.POST,RequestMethod.GET})
    public String modify(Components components,ModelMap modelMap){
        HashMap<String,Object> map = componentsService.modifyComponent(components);
        if (map == null || map.size() == 0){
            return "redirect:/components/list";
        }else{
            modelMap.addAttribute("msg",map);
            return "error";
        }

    }

    @RequestMapping(path = "/delete",method = {RequestMethod.GET,RequestMethod.POST})
    public String delete(@RequestParam("componentId") int componentId){
        User user = hostHolder.getUser();
        if (user.getType() == 1){
            return "authError";
        }
        componentsService.removeComponentBycomponentId(componentId);
        return "redirect:/components/list";
    }

    @RequestMapping(path = "/getFootPrintByComponentCode/{code}",method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody List<Components> getFootPrintByComponentCode(@PathVariable("code") int componentCode){
        List<Components> list = componentsService.getFootprintByComponentCode(componentCode);
        return list;
    }

    @RequestMapping(path = "/getValueByFootprintCode/{code}",method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody List<String> getValueByFootprintCode(@PathVariable("code") int footprintCode){
        List<String> list = componentsService.getValueByFootprintCode(footprintCode);
        return list;
    }

    @RequestMapping(path = "/checkQuantityLeft")
    public @ResponseBody int checkQuantityLeft(@RequestParam("quantity") int quantity,
                                               @RequestParam("footprintCode") int footprintCode,
                                               @RequestParam("value") String value){
        int quantityLeft = componentsService.getquantityLeft(footprintCode,value);
        return quantityLeft-quantity;
    }
}
