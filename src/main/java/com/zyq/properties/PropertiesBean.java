package com.zyq.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by tk on 2017/1/13.
 */
@Component
public class PropertiesBean {

    @Value("${com.zyq.name}")
    private String name;

    public String getName() {
        return name;
    }
}
