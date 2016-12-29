package com.zyq.dao;

import com.zyq.model.Components;
import com.zyq.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by TK on 2016/12/8.
 */
@Mapper
@Component
public interface ComponentsDao {
    String TABLE_NAME = "components";
    String INSERT_FIELD = "name,component_code,footprint,footprint_code,value,quantity,remark";
    String SELECT_FIELD = "component_id,name,footprint,value,quantity,remark,is_delete,component_code,footprint_code";

    @Select({"select",SELECT_FIELD,"from",TABLE_NAME,"where component_id=#{id} and is_delete=0"})
    Components selectById(int id);
    @Select({"select * ","from",TABLE_NAME,"where is_delete=0 group by value,footprint"})
    List<Components> selectAll();
    @Select({"select distinct name,component_code from",TABLE_NAME,"where is_delete=0"})
    List<Components> getAllDis();
    @Select({"select",SELECT_FIELD,"from",TABLE_NAME,"where name like #{name} and is_delete=0"})
    List<Components> selectByName(String name);
    @Select({"select distinct footprint,footprint_code from",TABLE_NAME,"where component_code=#{componentCode} and is_delete=0"})
    List<Components> selectFootprintByComponentCode(int componentCode);
    @Select({"select distinct value from",TABLE_NAME,"where footprint_code=#{footprintCode} and is_delete=0"})
    List<String> selectValueByFootprintCode(int footprintCode);

    @Select({"select * from",TABLE_NAME,"where is_delete=0 and footprint_code=#{footprintCode} and value=#{value}"})
    Components selectByFootprintCodeAndValue(Components components);

    @Select({"select distinct footprint from",TABLE_NAME,"where footprint_code=#{footprintCode} and is_delete=0"})
    String selectFootprintByFootprintCode(int footprintCode);

    @Update({"update",TABLE_NAME,"SET","name=#{name},component_code=#{componentCode},footprint=#{footprint},footprint_code=#{footprintCode},value=#{value},quantity=#{quantity},remark=#{remark} where component_id=#{componentId}"})
    void updateComponents(Components components);
    @Update({"update",TABLE_NAME,"SET","is_delete=1 where component_id=#{componentId}"})
    void deleteByComponentId(int componentId);

    @Insert({"insert into",TABLE_NAME,"(",INSERT_FIELD,") values(#{name},#{componentCode},#{footprint},#{footprintCode},#{value},#{quantity},#{remark})"})
    int addComponent(Components components);
}
