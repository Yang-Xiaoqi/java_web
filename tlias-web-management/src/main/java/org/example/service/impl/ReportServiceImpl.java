package org.example.service.impl;

import org.example.mapper.EmpMapper;
import org.example.mapper.StuMapper;
import org.example.pojo.EmpJob;
import org.example.pojo.JobOption;
import org.example.pojo.Student;
import org.example.pojo.StudentCount;
import org.example.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private StuMapper stuMapper;


    @Override
    public List<Map<String, Object>> getStudentDegreeData() {
        return stuMapper.getStudentDegreeData();
    }

    @Override
    public JobOption getEmpJobData() {
        // 调用mapper接口，获取统计数据
//        List<Map<String , Object>> list = empMapper.countEmpJobData();
        List<EmpJob> list = empMapper.countEmpJobData();
        // 组装结果
        List<String> jobList = list.stream()
                .map(EmpJob::getPos)
                .collect(Collectors.toList());
        List<Integer> dataList = list.stream()
                .map(EmpJob::getNum)
                .collect(Collectors.toList());
        return new JobOption(jobList,dataList);
    }

    @Override
    public List<Map<String, Object>> getEmpGenderData() {
        return empMapper.countEmpGenderData();
    }

    @Override
    public StudentCount getStudentCountData() {
        List<Map<String,Object>> studentCountList = stuMapper.getStudentCountData();
        List<Object> clazzList = studentCountList.stream().map(dataMap->dataMap.get("clazz_name")).toList();
        List<Object> dataList = studentCountList.stream().map(dataMap->dataMap.get("num")).toList();
        return new StudentCount(clazzList,dataList);
    }
}
