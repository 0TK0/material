package com.zyq.dao;

import com.zyq.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by TK on 2016/12/2.
 */
@Mapper
@Component
public interface UserDao {
    String TABLE_NAME = "user";
    String INSERT_FIELD = "name,password,salt,type";
    String SELECT_FIELD = "user_id,name,password,salt,type";

    @Select({"select",SELECT_FIELD,"from",TABLE_NAME,"where user_id=#{id} and is_delete=0"})
    User selectById(int id);
    @Select({"select *","from",TABLE_NAME,"where is_delete=0"})
    List<User> selectAll();
    @Select({"select",SELECT_FIELD,"from",TABLE_NAME,"where name like #{name} and is_delete=0"})
    List<User> selectByName(String name);

    @Select({"select",SELECT_FIELD,"from",TABLE_NAME,"where name=#{name} and is_delete=0"})
    User selectByNameOne(String name);

    @Update({"update",TABLE_NAME,"SET","name=#{name},type=#{type} where user_id=#{userId}"})
    void updateUser(User user);
    @Update({"update",TABLE_NAME,"SET","is_delete=1 where user_id=#{userId}"})
    void deleteByUserId(int userId);

    @Insert({"insert into",TABLE_NAME,"(",INSERT_FIELD,") values(#{name},#{password},#{salt},#{type})"})
    int addUser(User user);
}
