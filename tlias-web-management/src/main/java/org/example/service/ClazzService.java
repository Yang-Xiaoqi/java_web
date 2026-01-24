package org.example.service;

import org.example.pojo.Clazz;
import org.example.pojo.ClazzQueryParam;
import org.example.pojo.PageResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClazzService {
    /**
     * 分页查询班级列表
     */
    PageResult<Clazz> page(ClazzQueryParam clazzQueryParam);

    /**
     * 添加班级
     */
    void add(Clazz clazz);

    /**
     * 根据id查询班级信息
     */
    Clazz getInfoById(Integer id);

    /**
     * 修改班级信息
     */
    void update(Clazz clazz);

    /**
     * 删除班级
     */
    void delete(Integer id);
    /**
     * 查询所有班级
     */
    List<Clazz> getInfo();
}
