package com.zyq.dao;

import com.zyq.model.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by TK on 2016/12/12.
 */
@Mapper
@Component
public interface OrderDao {

    String TABLE_NAME = "orders";
    String INSERT_FIELD = "component_id,order_form_id,quantity";

    @Select({"select * from",TABLE_NAME,"where order_form_id=#{orderFormId} and is_delete=0"})
    List<Order> selectByOrderNum(int orderFormId);

    @Select({"select * from",TABLE_NAME,"where order_id=#{orderId} and is_delete=0"})
    Order getOrderById(int orderId);

    @Select({"select distinct component_id from",TABLE_NAME,"where is_delete=0 and order_form_id=#{orderFormId}"})
    List<Integer> selectNameByOrderFormIdDis(int orderFormId);

    @Select({"select quantity from",TABLE_NAME,"where is_delete=0 and component_id=#{componentId} and order_form_id=#{orderFormId}"})
    int selectOrderByComponentIdAndOrderFormId(Order order);

    @Update({"update",TABLE_NAME,"set","is_delete=1 where order_id=#{orderId}"})
    void removeOrderById(int orderId);

    @Insert({"insert into",TABLE_NAME,"(",INSERT_FIELD,") values(#{componentId},#{orderFormId},#{quantity})"})
    void insertOrder(Order order);
}
