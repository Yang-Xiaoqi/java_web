package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.Clazz;
import org.example.pojo.ClazzQueryParam;
import org.example.pojo.PageResult;
import org.example.pojo.Result;
import org.example.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/clazzs")
@RestController
public class ClazzController {
    @Autowired
    private ClazzService clazzService;

    /**
     * 分页查询班级列表
     */
    @GetMapping
    public Result page(ClazzQueryParam clazzQueryParam){
        PageResult<Clazz> pageResult = clazzService.page(clazzQueryParam);
        return Result.success(pageResult);
    }

    /**
     * 添加班级
     */
    @PostMapping
    public Result add(@RequestBody Clazz clazz){
        log.info("新增班级{}",clazz);
        clazzService.add(clazz);
        return Result.success();
    }

    /**
     * 根据id查询班级信息
     */
    @GetMapping("/{id}")
    public Result getInfoById(@PathVariable Integer id){
        log.info("根据id查询班级信息{}",id);
        Clazz clazz=clazzService.getInfoById(id);
        return Result.success(clazz);
    }

    /**
     * 修改班级信息
     */
    @PutMapping
    public Result update(@RequestBody Clazz clazz){
        log.info("修改班级信息{}",clazz);
        clazzService.update(clazz);
        return Result.success();
    }

    /**
     * 删除班级
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        log.info("删除班级{}",id);
        clazzService.delete(id);
        return Result.success();
    }

    /**
     * 查询所有班级
     */
    @GetMapping("/list")
    public Result list(){
        log.info("查询所有班级");
        List<Clazz> clazzList=clazzService.getInfo();
        return Result.success(clazzList);
    }
}
