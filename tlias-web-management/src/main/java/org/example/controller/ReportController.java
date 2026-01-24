package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.JobOption;
import org.example.pojo.Result;
import org.example.pojo.StudentCount;
import org.example.service.ReportService;
import org.example.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("report")
@RestController
public class ReportController {


    @Autowired
    private ReportService reportService;
    @Autowired
    private StuService stuService;

    /**
     * 统计员工职位人数
     */
    @GetMapping("/empJobData")
    public Result getEmpJobData() {
        log.info("统计员工职位人数");
        JobOption jobOption = reportService.getEmpJobData();
        return Result.success(jobOption);
    }

    /**
     * 统计性别及其对应人数
     */
    @GetMapping("/empGenderData")
    public Result getEmpGenderData() {
        log.info("统计员工性别及其对应人数");
        List<Map<String, Object>> genderList = reportService.getEmpGenderData();
        return Result.success(genderList);

    }

    /**
     * 班级人数统计
     */
    @GetMapping("/studentCountData")
    public Result getStudentCountData() {
        log.info("统计班级人数");
        StudentCount studentCount = reportService.getStudentCountData();
        return Result.success(studentCount);
    }
    /**
     * 班级学历情况统计
     */
    @GetMapping("/studentDegreeData")
    public Result getStudentDegreeData(){
        log.info("班级学历情况统计");
        List<Map<String ,Object>> degreeList= reportService.getStudentDegreeData();
        return Result.success(degreeList);
    }
}
