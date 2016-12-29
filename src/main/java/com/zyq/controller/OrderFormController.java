package com.zyq.controller;

import com.zyq.model.*;
import com.zyq.service.ComponentsService;
import com.zyq.service.OrderFormService;
import com.zyq.service.OrderService;
import com.zyq.service.UserService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by TK on 2016/12/9.
 */
@Controller
@RequestMapping("/orderForm")
public class OrderFormController {

    @Autowired
    OrderFormService orderFormService;
    @Autowired
    UserService userService;
    @Autowired
    OrderService orderServic;
    @Autowired
    ComponentsService componentsService;
    @Autowired
    HostHolder hostHolder;

    @RequestMapping(path = "/list",method = {RequestMethod.POST,RequestMethod.GET})
    public String list(ModelMap modelMap){
        User user = hostHolder.getUser();
        int type = user.getType();
        List<OrderFormModel> list = new ArrayList<>();
        if(type == 0){
            list = orderFormService.getAll();
            modelMap.addAttribute("auth",0);
        }else{
            list = orderFormService.getOrderFormByUserName(user.getName());
            modelMap.addAttribute("auth",1);
        }
        modelMap.addAttribute("list",list);
        modelMap.addAttribute("userName",user.getName());
        return "orderForm/allOrderForm";
    }

    @RequestMapping(path = "/toSearchDealed/{flag}",method = {RequestMethod.GET,RequestMethod.POST})
    public String toSearchDealed(@PathVariable("flag") int flag, ModelMap modelMap){
        User user = hostHolder.getUser();
        List<OrderFormModel> list = orderFormService.getAllFlag(flag);
        modelMap.addAttribute("list",list);
        modelMap.addAttribute("userName",user.getName());
        return "orderForm/allOrderForm";
    }

    @RequestMapping(path = "/toSearch",method = {RequestMethod.POST,RequestMethod.GET})
    public String toSearch(ModelMap modelMap){
        User user = hostHolder.getUser();
        modelMap.addAttribute("userName",user.getName());
        return "orderForm/search";
    }

    @RequestMapping(path = "/search",method = {RequestMethod.GET,RequestMethod.POST})
    public String search(@RequestParam("name") String name,ModelMap modelMap){
        User user = hostHolder.getUser();
        List<OrderFormModel> list = orderFormService.getOrderFormByName(name);
        modelMap.addAttribute("userName",user.getName());
        modelMap.addAttribute("orderFormModelList",list);
        return "orderForm/searchResult";
    }

    @RequestMapping(path = "/toAddOrder",method = {RequestMethod.POST,RequestMethod.GET})
    public String toAddOrder(ModelMap modelMap,HttpSession session){
        User user = hostHolder.getUser();
        User applyUser = userService.getUserByNameOne(session.getAttribute("user").toString());
        modelMap.addAttribute("applyUserId",applyUser.getUserId());
        modelMap.addAttribute("userName",user.getName());
        return "orderForm/addOrderForm";
    }

    @RequestMapping(path = "/addOrderForm",method = {RequestMethod.GET,RequestMethod.POST})
    public String addOrderForm(@RequestParam("userIdApply") int userIdApply,
                               @RequestParam("name") String name,
                               ModelMap modelMap){
        User user = hostHolder.getUser();
        int orderFormId = orderFormService.getLastOrderFormId();
        HashMap<String,Object> map = orderFormService.addOrderForm(userIdApply,name);

        if (map == null || map.size()==0){
            List<Components> list = componentsService.getAllDis();
            modelMap.addAttribute("componentList",list);
            modelMap.addAttribute("userName",user.getName());
            List<OrderModel> orderModelList = orderServic.getOrderByOrderNum(orderFormId);
            modelMap.addAttribute("orderModelList",orderModelList);
            modelMap.addAttribute("orderFormId",orderFormId);
            return "orderForm/addOrder";
        }else{
            modelMap.addAttribute("msg",map);
            return "error";
        }
    }

    @RequestMapping(path = "/deleteOrderForm",method = {RequestMethod.POST,RequestMethod.GET})
    public String deleteOrderForm(@RequestParam("orderFormId") int orderFormId){
        User user = hostHolder.getUser();
        if (user.getType() == 1){
            return "authError";
        }
        orderFormService.removeOrderFormById(orderFormId);
        return "redirect:/orderForm/list";
    }

    @RequestMapping(path = "/deleteOrder",method = {RequestMethod.GET,RequestMethod.POST})
    public String deleteOrder(@RequestParam("orderId") int orderId,ModelMap modelMap){
        User user = hostHolder.getUser();
        int orderFormId = orderServic.getOrderById(orderId).getOrderFormId();
        orderServic.removeOrderById(orderId);
        List<OrderModel> orderModelList = orderServic.getOrderByOrderNum(orderFormId);
        List<Components> list = componentsService.getAllDis();
        modelMap.addAttribute("componentList",list);
        modelMap.addAttribute("orderModelList",orderModelList);
        modelMap.addAttribute("userName",user.getName());
        modelMap.addAttribute("orderFormId",orderFormId);
        return "orderForm/addOrder";
    }

    @RequestMapping(path = "/addOrder/{flag}",method = {RequestMethod.POST,RequestMethod.GET})
    public String addOrder(@RequestParam("orderFormId") int orderFormId,
                           @RequestParam("componentCode") int componentCode,
                           @RequestParam("footprintCode" ) int footprintCode,
                           @RequestParam("value") String value,
                           @RequestParam("quantity") int quantity,
                           @PathVariable("flag") int flag,
                           ModelMap modelMap){
        User user = hostHolder.getUser();
        Components components = componentsService.getComponentByFootprintCodeAndValue(footprintCode,value);
        HashMap<String,Object> map = new HashMap<>();
        if (components!=null) {
            map = orderServic.addOrder(components.getComponentId(), quantity, orderFormId);
            if (map == null || map.size() == 0){
                OrderFormModel orderFormModel = orderFormService.getOrderFormById(orderFormId);
                modelMap.addAttribute("orderFormModel",orderFormModel);
                List<Components> list = componentsService.getAllDis();
                modelMap.addAttribute("componentList", list);
                modelMap.addAttribute("userName", user.getName());
                List<OrderModel> orderModelList = orderServic.getOrderByOrderNum(orderFormId);
                modelMap.addAttribute("orderModelList", orderModelList);
                modelMap.addAttribute("orderFormId", orderFormId);
                //flag == 0:新增订单页面
                //flag == 1:修改订单页面
                if (flag==0){
                    return "orderForm/addOrder";
                }else{
                    return "orderForm/modify";
                }

            }else{
                modelMap.addAttribute("msg",map);
                return "error";
            }
        }else{
            map.put("msgNull","不存在你要的元器件 请重新输入或找管理员申请新元件");
            modelMap.addAttribute("msg",map);
            return "error";
        }
    }

    @RequestMapping(path = "/toModify",method = {RequestMethod.POST,RequestMethod.GET})
    public String toModify(@RequestParam("orderFormId") int orderFormId,
                           ModelMap modelMap){
        User user = hostHolder.getUser();
        OrderFormModel orderFormModel = orderFormService.getOrderFormById(orderFormId);
        List<OrderModel> orderModelList = orderServic.getOrderByOrderNum(orderFormId);
        List<Components> list = componentsService.getAllDis();
        modelMap.addAttribute("componentList", list);
        modelMap.addAttribute("orderFormId", orderFormId);
        modelMap.addAttribute("orderModelList",orderModelList);
        modelMap.addAttribute("orderFormModel",orderFormModel);
        modelMap.addAttribute("userName",user.getName());
        return "orderForm/modify";
    }

    @RequestMapping(path = "/modify",method = {RequestMethod.GET,RequestMethod.POST})
    public String modify(@RequestParam("name") String name,
                         @RequestParam("applyName") String applyName,
                         @RequestParam("orderFormId") int orderFormId,
                         @RequestParam(value = "dealName",required = false) String dealName,
                         ModelMap modelMap){
        User user = hostHolder.getUser();
        HashMap<String,Object> map = orderFormService.modify(orderFormId,name,applyName,dealName);
        if (map == null || map.size() == 0){
            modelMap.addAttribute("userName",user.getName());
            return "redirect:/orderForm/list";
        }else{
            modelMap.addAttribute("msg",map);
            return "error";
        }
    }

    @RequestMapping(path = "/download",method = {RequestMethod.POST,RequestMethod.GET})
    public String download(@RequestParam("orderFormId") int orderFormId,
                           HttpSession session,
                           HttpServletResponse response){
        User user1 = hostHolder.getUser();
        if (user1.getType() == 1){
            return "authError";
        }
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        try {
            String orderFormName = orderFormService.getOrderFormById(orderFormId).getName();
            String fileName = orderFormName + ".xls";
            String name = session.getAttribute("user").toString();
            User user = userService.getUserByNameOne(name);
            HSSFWorkbook workbook = orderFormService.getComponentEXCEL(orderFormId,user.getName());
            response.setHeader("Content-Disposition","attachment;fileName=\"" + new String(fileName.getBytes("utf-8"),"ISO-8859-1")+ "\"");//设置一下让浏览器弹出下载提示框，而不是直接在浏览器中打开
            response.setContentType("application/vnd.ms-excel");
            OutputStream fileOutputStream = response.getOutputStream();

            workbook.write(fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
