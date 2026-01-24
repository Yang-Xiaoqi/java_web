package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.pojo.EmpExpr;

import java.util.List;

/**
 * 员工工作经历
 */
@Mapper
public interface EmpExprMapper {

    /**
     * 批量保存员工工作经历信息
     * @param exprList
     */

    void insertBatch(List<EmpExpr> exprList);

    void deleteByEmpIds(List<Integer> empIds);
}
