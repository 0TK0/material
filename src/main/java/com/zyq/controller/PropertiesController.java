package com.zyq.controller;

import com.zyq.properties.PropertiesBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tk on 2017/1/13.
 */
@RestController
public class PropertiesController {

    @Autowired
    PropertiesBean propertiesBean;

    @RequestMapping("testProperties")
    public String testProperties(){
        return propertiesBean.getName();
    }
}
