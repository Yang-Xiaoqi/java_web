package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pojo.Emp;
import org.example.pojo.EmpJob;
import org.example.pojo.EmpQueryParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 员工信息
 */
@Mapper
public interface EmpMapper {

    // 原始分页查询实现

    /**
     * 查询总记录数
     */
//    @Select("select count(*) from emp left join dept on emp.dept_id=dept.id")
//    public Long count();

    /**
     * 分页查询
     */
//    @Select("select emp.*,dept.name as deptName from emp left join dept on emp.dept_id=dept.id " +
//            "order by emp.update_time desc limit #{start},#{pageSize}")
//    public List<Emp> list(Integer start,Integer pageSize);

//        @Select("select emp.*,dept.name as deptName from emp left join dept on emp.dept_id=dept.id " +
//            "order by emp.update_time desc")
//    public List<Emp> list(String name, Integer gender, LocalDate begin, LocalDate end);
    public List<Emp> list(EmpQueryParam empQueryParam);

    /**
     * 新增员工基本信息
     *
     * @param emp
     */
    @Options(useGeneratedKeys = true,keyProperty = "id")  //获取到生成的主键
    @Insert("insert into emp(username, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time)" +
            "    values (#{username},#{name},#{gender},#{phone},#{job},#{salary},#{image},#{entryDate},#{deptId},#{createTime},#{updateTime})")
    void insert(Emp emp);

    /**
     * 根据id批量删除员工基本信息
     */

    void deleteByIds(List<Integer> ids);

    /**
     * 根据id查询员工信息
     */
    Emp getById(Integer id);

    /**
     * 根据id更新员工基本信息
     */
    void updateById(Emp emp);

    /**
     * 统计员工职位人数
     */

    List<EmpJob> countEmpJobData();

    List<Map<String, Object>> countEmpGenderData();

    @Select("select id,username,password,name,gender,image,job,salary,entry_date,dept_id,create_time,update_time\n" +
            "from emp")
    List<Emp> selectEmp();

    /**
     * 根据部门id查询该部门下员工数量
     */
    @Select("select count(*) from emp where emp.dept_id=#{id}")
    int getInfoByDeptId(Integer id);
}
