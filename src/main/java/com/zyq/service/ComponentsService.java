package com.zyq.service;

import com.zyq.controller.LoginController;
import com.zyq.dao.ComponentsDao;
import com.zyq.model.Components;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by TK on 2016/12/8.
 */
@Service
public class ComponentsService {
    @Autowired
    ComponentsDao componentsDao;

    private static final Logger logger = LoggerFactory.getLogger(ComponentsService.class);
    public List<Components> getAll(){
        List<Components> list = new ArrayList<>();
        try {
            list = componentsDao.selectAll();
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.getStackTrace();
        }
        return list;
    }

    public List<Components> getAllDis(){
        List<Components> list = new ArrayList<>();
        try {
            list = componentsDao.getAllDis();
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.getStackTrace();
        }
        return list;
    }

    public HashMap<String,Object> addComponent(Components components){
        HashMap<String,Object> map = new HashMap<>();
        if (components != null){
            if (StringUtils.isBlank(components.getName())){
                map.put("msgName","元件名不能为空");
                return map;
            }
            if (components.getComponentCode()==0){
                map.put("msgComponentCode","元件名Code不能为0");
                return map;
            }
            if (StringUtils.isBlank(components.getFootprint())){
                map.put("msgFootPrint","封装名不能为空");
                return map;
            }
            if (components.getFootprintCode()==0){
                map.put("msgFootprintCode","封装名Code不能为0");
                return map;
            }
            if (StringUtils.isBlank(components.getValue())){
                map.put("msgValue","元件值不能为空");
                return map;
            }
            if (components.getQuantity() < 0){
                map.put("msgQuantity","库存数量不能为负数");
            }

        }else{
            map.put("msgPost","提交内容不能为空");
        }

        try {
            componentsDao.addComponent(components);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.getStackTrace();
        }
        return map;
    }

    public List<Components> getComponentByName(String name){
        List<Components> componentsList = new ArrayList<>();
        try {
            componentsList = componentsDao.selectByName("%"+name+"%");
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.getStackTrace();
        }
        return componentsList;
    }

    public Components getComponentByComponentId(int componentId){
        Components components = null;
        try {
            components = componentsDao.selectById(componentId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return components;
    }

    public HashMap<String,Object> modifyComponent(Components components){
        HashMap<String,Object> map = new HashMap<>();
        if (components != null){
            if (StringUtils.isBlank(components.getName())){
                map.put("msgName","元件名不能为空");
                return map;
            }
            if (StringUtils.isBlank(components.getFootprint())){
                map.put("msgFootPrint","封装名不能为空");
                return map;
            }
            if (StringUtils.isBlank(components.getValue())){
                map.put("msgValue","元件值不能为空");
                return map;
            }
        }else {
            map.put("msgPost","提交的内容不能为空");
        }
//        if (!StringUtils.isBlank(components.getName())){
//            String name = components.getName();
//            switch (name){
//                case "电容": components.setComponentCode(1);break;
//                case "电阻": components.setComponentCode(2);break;
//                case "电感": components.setComponentCode(3);break;
//                case "接口": components.setComponentCode(4);break;
//                case "电源": components.setComponentCode(5);break;
//                case "芯片": components.setComponentCode(6);break;
//                default:components.setComponentCode(7);
//            }
//        }
        try {
            componentsDao.updateComponents(components);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return map;
    }

    public void removeComponentBycomponentId(int componentId){

        try {
            componentsDao.deleteByComponentId(componentId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    public String getFootprintByFootprintCode(int footprintCode){
        String footprint = null;
        try {
            footprint = componentsDao.selectFootprintByFootprintCode(footprintCode);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return footprint;
    }

    public List<Components> getFootprintByComponentCode(int componentCode){
        List<Components> list = null;
        try {
            list = componentsDao.selectFootprintByComponentCode(componentCode);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return list;
    }

    public List<String> getValueByFootprintCode(int footprintCode){
        List<String> list = null;
        try {
            list = componentsDao.selectValueByFootprintCode(footprintCode);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return list;
    }

    public Components getComponentByFootprintCodeAndValue(int footprintCode,String value){
        Components components2 = new Components();
        components2.setFootprintCode(footprintCode);
        components2.setValue(value);
        Components components = null;
        try {
            components = componentsDao.selectByFootprintCodeAndValue(components2);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return components;
    }

    public int getquantityLeft(int footprintCode,String value){
        Components components2 = new Components();
        components2.setFootprintCode(footprintCode);
        components2.setValue(value);
        Components components = null;
        try {
            components = componentsDao.selectByFootprintCodeAndValue(components2);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        if (components != null){
            return components.getQuantity();
        }else{
            return -1;
        }
    }
}
