package org.example.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.PreDestroy;
import org.example.mapper.StuMapper;
import org.example.pojo.ClazzQueryParam;
import org.example.pojo.PageResult;
import org.example.pojo.StuQueryParam;
import org.example.pojo.Student;
import org.example.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class StuServiceImpl implements StuService {

    @Autowired
    private StuMapper stuMapper;

    @Override
    public PageResult<Student> page(StuQueryParam stuQueryParam) {
        PageHelper.startPage(stuQueryParam.getPage(), stuQueryParam.getPageSize());
        List<Student> studentList = stuMapper.list(stuQueryParam);
        Page<Student> p = (Page<Student>) studentList;
        return new PageResult<Student>(p.getTotal(), p.getResult());
    }

    @Override
    public void add(Student student) {
        student.setCreateTime(LocalDateTime.now());
        student.setUpdateTime(LocalDateTime.now());
        stuMapper.add(student);
    }

    @Override
    public Student getInfoById(Integer id) {
        return stuMapper.getInfoById(id);
    }

    @Override
    public void update(Student student) {
        student.setUpdateTime(LocalDateTime.now());
        stuMapper.update(student);
    }

    @Override
    public void delete(List<Integer> ids) {
        stuMapper.delete(ids);
    }

    @Override
    public void violationProcess(Integer id, Integer score) {
        Map<String, Integer> stringIntegerMap = stuMapper.getVioById(id, score);
        Integer violationCount = stringIntegerMap.get("violation_count");
        Integer violationScore = stringIntegerMap.get("violation_score");
        stuMapper.violationProcess(id,violationCount+1,violationScore+score);
    }
}
