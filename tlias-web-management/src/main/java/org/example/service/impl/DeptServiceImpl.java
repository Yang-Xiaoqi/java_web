package org.example.service.impl;

import org.example.exception.BusinessException;
import org.example.mapper.DeptMapper;
import org.example.mapper.EmpMapper;
import org.example.pojo.Dept;
import org.example.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private EmpMapper empMapper;

    /**
     * 查询部门信息

     */
    @Override
    public List<Dept> findAll() {
        return deptMapper.findAll();
    }

    /**
     * 删除部门
     */

    @Override
    public void deleteById(Integer id) {
        int num = empMapper.getInfoByDeptId(id);
        if(num>0) throw new BusinessException("对不起，当前部门下有员工，不能直接删除！");
        deptMapper.deleteById(id);
    }

    /**
     *
     添加部门
     */
    @Override
    public void add(Dept dept) {
//        补全基础属性--createTime,updateTime
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
//        调用mapper接口方法
        deptMapper.insert(dept);
    }

    /**
     *
     根据id查询部门数据
     */
    @Override
    public Dept getById(Integer id) {
        return deptMapper.getById(id);
    }

    /**
     *
     根据id修改
     */
    @Override
    public void update(Dept dept) {
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.update(dept);
    }


}
