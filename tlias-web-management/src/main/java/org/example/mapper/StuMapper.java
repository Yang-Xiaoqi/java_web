package org.example.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.pojo.PageResult;
import org.example.pojo.StuQueryParam;
import org.example.pojo.Student;

import java.util.List;
import java.util.Map;

@Mapper
public interface StuMapper {

    @Select("select count(*) from student where clazz_id = #{id}")
    int getInfoByClazzId(Integer id);


    /**
     * 分页查询学员信息
     */
    List<Student> list(StuQueryParam stuQueryParam);

    /**
     * 新增学生信息
     */
    @Insert("insert into student(name,no,gender,phone,id_card,is_college,address,degree,graduation_date,clazz_id,create_time,update_time) " +
            "values(#{name},#{no},#{gender},#{phone},#{idCard},#{isCollege},#{address},#{degree},#{graduationDate},#{clazzId},#{createTime},#{updateTime})")
    void add(Student student);

    /**
     * 根据id查询学生信息
     */
    @Select("select id,name,no,gender,phone,degree,id_card,is_college,address,graduation_date,violation_count,violation_score,clazz_id,create_time,update_time " +
            "from student" +
            " where id =#{id}")
    Student getInfoById(Integer id);


    /**
     * 修改学员信息
     */
    void update(Student student);

    /**
     * 删除学员
     */
    void delete(List<Integer> ids);

    /**
     * 根据id查询违纪扣分和违纪次数
     */
    @Select("select violation_count,violation_score from student where id=#{id}")
    Map<String,Integer> getVioById(Integer id, Integer score);

    /**
     *根据id修改违纪扣分和违纪次数
     */
    @Update("update student set violation_count=#{violationCount},violation_score=#{violationScore} where id = #{id}")
    void violationProcess(Integer id,Integer violationCount,Integer violationScore);


    List<Map<String, Object>> getStudentCountData();

    /**
     * 班级学历情况统计
     */
    List<Map<String, Object>> getStudentDegreeData();
}
