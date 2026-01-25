package org.example.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.example.mapper.EmpExprMapper;
import org.example.mapper.EmpLogMapper;
import org.example.mapper.EmpMapper;
import org.example.pojo.*;
import org.example.service.EmpLogService;
import org.example.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private EmpExprMapper empExprMapper;
    @Autowired
    private EmpLogService empLogService;
//    原始分页查询的操作
//    @Override
//    public PageResult<Emp> page(Integer page,Integer pageSize) {
//
//        PageResult<Emp> pageResult = new PageResult<>();
//        pageResult.setTotal(empMapper.count());
//        int start = (page-1)*pageSize;
//        pageResult.setRows(empMapper.list(start,pageSize));
//        return pageResult;
//    }

    /**
     * 基于pageHelper分页查询
     *
     * @return
     */
//    @Override
//    public PageResult<Emp> page(Integer page, Integer pageSize, String name, Integer gender, LocalDate begin,LocalDate end) {
////        设置分页参数
//        PageHelper.startPage(page,pageSize);
////        执行查询
//        List<Emp> empList=empMapper.list(name,gender,begin,end);
////        解析查询结果并封装
//        Page<Emp> p = (Page<Emp>)empList;
//        return new PageResult<Emp>(p.getTotal(),p.getResult());
//    }
    @Override
    public PageResult<Emp> page(EmpQueryParam empQueryParam) {
//        设置分页参数
        PageHelper.startPage(empQueryParam.getPage(), empQueryParam.getPageSize());
//        执行查询
        List<Emp> empList = empMapper.list(empQueryParam);
//        解析查询结果并封装
        Page<Emp> p = (Page<Emp>) empList;
        return new PageResult<Emp>(p.getTotal(), p.getResult());
    }

    @Transactional(rollbackFor = {Exception.class}) //事务管理-------默认出现运行时异常才会回滚
    @Override
    public void save(Emp emp) {

        try {
//        保存员工基本信息
            emp.setCreateTime(LocalDateTime.now());
            emp.setUpdateTime(LocalDateTime.now());
            empMapper.insert(emp);
//        保存员工工作经历信息
            List<EmpExpr> exprList = emp.getExprList();
            if (!CollectionUtils.isEmpty(exprList)) {
//            遍历集合，为empID复制
                exprList.forEach(empExpr -> {
                    empExpr.setEmpId(emp.getId());
                });
                empExprMapper.insertBatch(exprList);
            }
        } finally {
            //记录操作日志
            EmpLog empLog = new EmpLog(null, LocalDateTime.now(), "新增员工：" + emp);
            empLogService.insertLog(empLog);
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void delete(List<Integer> ids) {
        // 删除员工基本信息
        empMapper.deleteByIds(ids);
        //删除员工经历信息
        empExprMapper.deleteByEmpIds(ids);
    }

    @Override
    public Emp getInfo(Integer id) {
        return empMapper.getById(id);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void update(Emp emp) {
//        根据id修改员工基本信息
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateById(emp);
//        根据id修改员工工作经历信息
//        1.现根据员工ID删除原有的工作经历
        empExprMapper.deleteByEmpIds(Arrays.asList(emp.getId()));
//        2.添加新的工作经历
        List<EmpExpr> exprList = emp.getExprList();
        if (!CollectionUtils.isEmpty(exprList)) {
            exprList.forEach(empExpr -> empExpr.setEmpId(emp.getId()));
            empExprMapper.insertBatch(exprList);
        }
    }

    @Override
    public List<Emp> list() {
        return empMapper.selectEmp();
    }

    @Override
    public LoginInfo login(Emp emp) {
        //1.调用mapper接口，根据用户名和密码查询员工信息
        Emp e = empMapper.selectByUsernameAndPassword(emp);
        //2.判断是否存在这个员工，组装登陆成功信息
        if (e != null) {
            log.info("登陆成功,员工信息:{}", e);
            return new LoginInfo(e.getId(), e.getUsername(), e.getName(), "");
        }
        //3.不存在，返回null
        return null;
    }


}
