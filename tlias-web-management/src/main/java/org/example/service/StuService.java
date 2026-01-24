package org.example.service;

import org.example.pojo.PageResult;
import org.example.pojo.StuQueryParam;
import org.example.pojo.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StuService {

    /**
     * 分页查询学员信息
     */
    PageResult<Student> page(StuQueryParam stuQueryParam);

    /**
     * 新增学生信息
     */
    void add(Student student);

    /**
     * 根据id查询学生信息
     */
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
     * 学员违纪处理
     */
    void violationProcess(Integer id, Integer score);
}
