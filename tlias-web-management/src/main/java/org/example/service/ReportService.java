package org.example.service;

import org.example.pojo.JobOption;
import org.example.pojo.StudentCount;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ReportService {
    /**
     * 班级学历情况统计
     */
    List<Map<String, Object>> getStudentDegreeData();

    JobOption getEmpJobData();

    List<Map<String, Object>> getEmpGenderData();


    StudentCount getStudentCountData();
}
