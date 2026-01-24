package org.example.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.exception.BusinessException;
import org.example.mapper.ClazzMapper;
import org.example.mapper.StuMapper;
import org.example.pojo.Clazz;
import org.example.pojo.ClazzQueryParam;
import org.example.pojo.Emp;
import org.example.pojo.PageResult;
import org.example.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClazzServiceImpl implements ClazzService {

    @Autowired
    private ClazzMapper clazzMapper;
    @Autowired
    private StuMapper stuMapper;

    @Override
    public PageResult<Clazz> page(ClazzQueryParam clazzQueryParam) {

//        设置分页参数
        PageHelper.startPage(clazzQueryParam.getPage(), clazzQueryParam.getPageSize());
//        执行查询
        List<Clazz> clazzList = clazzMapper.list(clazzQueryParam);
//        解析查询结果并封装
        Page<Clazz> p = (Page<Clazz>) clazzList;
        return new PageResult<Clazz>(p.getTotal(), p.getResult());
    }

    @Override
    public void add(Clazz clazz) {
        clazz.setCreateTime(LocalDateTime.now());
        clazz.setUpdateTime(LocalDateTime.now());
        clazzMapper.add(clazz);
    }

    @Override
    public Clazz getInfoById(Integer id) {
        return clazzMapper.selectById(id);
    }

    @Override
    public void update(Clazz clazz) {
        clazz.setUpdateTime(LocalDateTime.now());
        clazzMapper.update(clazz);
    }

    @Override
    public void delete(Integer id) {
        int cnt = stuMapper.getInfoByClazzId(id);

        if(cnt>0){
            throw new BusinessException("该班级下存在学生，无法删除");
        }
        clazzMapper.delete(id);
    }

    @Override
    public List<Clazz> getInfo() {
        return clazzMapper.getInfo();
    }
}
