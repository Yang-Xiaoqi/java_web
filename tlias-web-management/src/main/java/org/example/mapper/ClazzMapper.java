package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pojo.Clazz;
import org.example.pojo.ClazzQueryParam;

import java.util.List;

@Mapper
public interface ClazzMapper {
    /**
     * 分页查询班级列表
     */
    List<Clazz> list(ClazzQueryParam clazzQueryParam);

    /**
     * 添加班级
     */
    @Insert("insert into clazz(name,room,begin_date,end_date,master_id,subject,create_time,update_time) values(#{name},#{room},#{beginDate},#{endDate},#{masterId},#{subject},#{createTime},#{updateTime})")
    void add(Clazz clazz);

    /**
     * 根据id查询班级信息
     */
    @Select("select id,name,room,begin_date,end_date,master_id,subject,create_time,update_time from clazz where id = #{id}")
    Clazz selectById(Integer id);

    /**
     * 修改班级信息
     */

    void update(Clazz clazz);

    /**
     * 删除班级
     */
    @Delete("delete from clazz where id=#{id}")
    void delete(Integer id);

    /**
     * 查询所有班级
     */
    @Select("select id,name,room,begin_date,end_date,master_id,subject,create_time,update_time from clazz")
    List<Clazz> getInfo();
}
