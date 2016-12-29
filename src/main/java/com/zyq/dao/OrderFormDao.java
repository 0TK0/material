package com.zyq.dao;

import com.zyq.model.OrderForm;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by TK on 2016/12/9.
 */
@Mapper
@Component
public interface OrderFormDao {
    String TABLE_NAME = "order_form";
    String INSERT_FIELD = "name,user_id_apply,status,is_delete,create_time,modified_time";
    String SELECT_FIELD = "order_form_id,name,user_id_apply,user_id_deal,status,is_delete,create_time,modified_time";

    @Select({"select * from ",TABLE_NAME,"where is_delete=0"})
    List<OrderForm> selectAll();

    @Select({"select * from ",TABLE_NAME,"where is_delete=0 and status=1"})
    List<OrderForm> selectALLDealed();

    @Select({"select * from ",TABLE_NAME,"where is_delete=0 and status=0"})
    List<OrderForm> selectALLNotDealed();

    @Select({"select * from ",TABLE_NAME,"where name like #{name} and is_delete=0"})
    List<OrderForm> selectOrderFormByName(String name);

    @Select({"select * from",TABLE_NAME,"where user_id_apply=#{userIdApply} and is_delete=0"})
    List<OrderForm> selectOrderFormByUserIdApply(int userIdApply);

    @Select({"select * from",TABLE_NAME,"where name=#{name} and is_delete=0"})
    OrderForm selectOrderFormByNameOne(String name);

    @Select({"select * from",TABLE_NAME,"where order_form_id=#{orderFormId}"})
    OrderForm selectOrderFormById(int orderFormId);

    @Select({"select * from ",TABLE_NAME,"order by order_form_id desc limit 0,1"})
    OrderForm selectLastOrderFormId();

    @Insert({"insert into",TABLE_NAME,"(",INSERT_FIELD,") values (#{name},#{userIdApply},#{status},#{isDelete},#{createTime},#{modifiedTime})"})
    void inserOrderForm(OrderForm orderForm);

    @Update({"update",TABLE_NAME,"set","is_delete=1 where order_form_id=#{orderFormId}"})
    void removeOrderFormById(int orderFormId);

    @Update({"update",TABLE_NAME,"set","name=#{name},user_id_apply=#{userIdApply},user_id_deal=#{userIdDeal},status=#{status},modified_time=#{modifiedTime} where order_form_id=#{orderFormId}"})
    void update(OrderForm orderForm);
}
