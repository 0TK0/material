package com.zyq.service;

import com.zyq.dao.ComponentsDao;
import com.zyq.dao.OrderDao;
import com.zyq.model.Components;
import com.zyq.model.Order;
import com.zyq.model.OrderModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by TK on 2016/12/12.
 */
@Service
public class OrderService {

    @Autowired
    OrderDao orderDao;
    @Autowired
    ComponentsDao componentsDao;

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    public List<OrderModel> getOrderByOrderNum(int orderFormId){
        List<Order> list = null;
        try {
            list = orderDao.selectByOrderNum(orderFormId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        List<OrderModel> orderModelList = toModel(list);
        return orderModelList;
    }

    public void removeOrderById(int orderId){
        try {
            orderDao.removeOrderById(orderId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    public Order getOrderById(int orderId){
        Order order = null;
        try {
            order = orderDao.getOrderById(orderId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return order;
    }

    public HashMap<String,Object> addOrder(int componentId,int quantity,int orderFormId){
        HashMap<String,Object> map = new HashMap<>();
        Components components = null;
        try {
            components = componentsDao.selectById(componentId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        int left = components.getQuantity()-quantity;
        if (left < 0){
            map.put("msgLeft","库存不足");
            return map;
        }
        Order order = new Order();
        order.setComponentId(componentId);
        order.setQuantity(quantity);
        order.setOrderFormId(orderFormId);
        try {
            orderDao.insertOrder(order);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        components.setQuantity(left);
        try {
            componentsDao.updateComponents(components);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return map;
    }

    private List<OrderModel> toModel(List<Order> list){
        List<OrderModel> orderModels = new ArrayList<>();
        if(!CollectionUtils.isEmpty(list)){
            for(Order order:list){
                OrderModel orderModel = new OrderModel();
                assembly(order,orderModel);
                orderModels.add(orderModel);
            }
        }
        return orderModels;
    }

    private void assembly(Order order,OrderModel orderModel){
        if (orderModel!= null){
            orderModel.setOrderId(order.getOrderId());
            orderModel.setOrderFormId(order.getOrderFormId());
            Components components = componentsDao.selectById(order.getComponentId());
            orderModel.setComponentName(components.getName());
            orderModel.setFootprint(components.getFootprint());
            orderModel.setValue(components.getValue());
            orderModel.setRemark(components.getRemark());
            orderModel.setQuantity(order.getQuantity());
        }
    }
}
