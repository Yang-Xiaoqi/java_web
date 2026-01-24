package org.example.controller;

import com.aliyun.core.annotation.Body;
import lombok.extern.slf4j.Slf4j;
import org.example.pojo.PageResult;
import org.example.pojo.Result;
import org.example.pojo.StuQueryParam;
import org.example.pojo.Student;
import org.example.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/students")
@RestController
public class StuController {
    @Autowired
    private StuService stuService;

    /**
     * 分页查询学员信息
     */
    @GetMapping
    public Result page(StuQueryParam stuQueryParam) {
        log.info("分页查询学员信息{}", stuQueryParam);
        PageResult<Student> pageResult = stuService.page(stuQueryParam);
        return Result.success(pageResult);
    }

    /**
     * 新增学生信息
     */
    @PostMapping
    public Result add(@RequestBody Student student) {
        log.info("新增学生{}", student);
        stuService.add(student);
        return Result.success();
    }

    /**
     * 根据id查询学生信息
     */
    @GetMapping("/{id}")
    public Result getInfoById(@PathVariable Integer id) {
        log.info("根据id查询学生信息{}", id);
        Student student = stuService.getInfoById(id);
        return Result.success(student);
    }

    /**
     * 修改学员信息
     */
    @PutMapping
    public Result update(@RequestBody Student student){
        log.info("修改学员信息{}",student);
        stuService.update(student);
        return Result.success();
    }
    /**
     * 删除学员
     */
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids){
        log.info("删除学员{}",ids);
        stuService.delete(ids);
        return Result.success();
    }
    /**
     * 学员违纪处理
     */
    @PutMapping("/violation/{id}/{score}")
    public Result violationProcess(@PathVariable Integer id,@PathVariable Integer score){
        log.info("学员{}违纪{}分",id,score);
        stuService.violationProcess(id,score);
        return Result.success();
    }
}
