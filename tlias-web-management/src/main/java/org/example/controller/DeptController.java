package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.pojo.Dept;
import org.example.pojo.Result;
import org.example.service.DeptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/depts")
@RestController
public class DeptController {
//    private static final Logger log = LoggerFactory.getLogger(DeptController.class);

    @Autowired
    private DeptService deptService;
//    @RequestMapping(value = "/depts",method = RequestMethod.GET)

    /**
     * 查询部门列表
     */
    @GetMapping
    public Result list() {
//        System.out.println("查询全部的部门数据");
        log.info("查询部门数据");
        List<Dept> deptList = deptService.findAll();
        return Result.success(deptList);
    }

    /**
     * 删除部门--方式一通过原始HttpServletRequest接收参数
     */
//    @DeleteMapping("/depts")
//    public Result delete(HttpServletRequest request) {
//        System.out.println("删除");
//        String idStr = request.getParameter("id");
//        int id = Integer.parseInt(idStr);
//        System.out.println("根据ID删除部门" + id);
//        return Result.success();
//    }

    /**
     * 删除部门--方式二：@RequestParam
     */
//    @DeleteMapping("/depts")
//    public Result delete(@RequestParam(value = "id",required = false) Integer DeptId) {
//        System.out.println("根据id删除部门"+DeptId);
//        return Result.success();
//    }

    /**
     * 删除部门--方式三：省略@RequestParam，前端传递的请求参数名与服务端方法形参名一致
     */
    @DeleteMapping
    public Result delete(Integer id) {
//        System.out.println("根据id删除部门" + id);
        log.info("根据id删除部门:{}" ,id);
        deptService.deleteById(id);
        return Result.success();
    }

    /**
     *
     * 新增部门
     */
    @PostMapping
    public Result add(@RequestBody Dept dept) {
        log.info("新增部门{}", dept);
        deptService.add(dept);
        return Result.success();
    }

    /**
     *
     根据ID查询部门
     */
//    @GetMapping("/depts/{id}")
//    public Result getInfo(@PathVariable("id")Integer deptId){
//        System.out.println("根据id查询部门"+deptId);
////        deptService.getInfo(deptId);
//        return Result.success();
//    }

    /**
     *
     * 根据ID查询部门
     */
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id) {
        log.info("根据id查询部门{}",id);
        Dept dept = deptService.getById(id);
        return Result.success(dept);
    }

    /**
     * 修改部门
     */
    @PutMapping
    public Result update(@RequestBody Dept dept) {
        log.info("修改部门{}",dept);
        deptService.update(dept);
        return Result.success();
    }
}
