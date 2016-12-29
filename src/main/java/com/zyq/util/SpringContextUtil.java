package com.zyq.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 * Created by TK on 2016/12/6.
 */
public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext ctx;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx=applicationContext;
    }
    public static Object getBean(String name){
        return ctx.getBean(name);
    }
    public static <T> Map<String,T> getBeansOfType(Class<T> t){
        return ctx.getBeansOfType(t);
    }
    public static <T> T getBean(Class<T> t){
        return ctx.getBean(t);
    }
    public static ApplicationContext getApplicationContext(){
        return ctx;
    }
}
