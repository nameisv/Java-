package com.xiaoniucr.service;

import com.xiaoniucr.common.vo.JSONReturn;
import com.xiaoniucr.common.vo.PageVo;
import com.xiaoniucr.entity.Employee;

import java.util.List;
import java.util.Map;

/**
 *
 * 员工（技师）相关服务类
 */
public interface EmployeeService {

    

    /**
     * 医生分页
     * @param map
     * @return
     */
    PageVo page(Map map);

    /**
     * 添加医生
     * @param employee
     * @return
     */
    JSONReturn add(Employee employee);

    /**
     * 更新医生信息
     * @param employee
     * @return
     */
    Integer update(Employee employee);


    /**
     * 删除医生
     * @param id
     * @return
     */
    Integer del(Integer id);



    /**
     * 根据ID查询医生信息
     * @param id
     * @return
     */
    Employee selectById(Integer id);


    /**
     * 根据服务查询技师
     * @return
     */
    List<Employee> findByServices(Integer servicesId);



    /**
     * 统计技师总数
     * @return
     */
    Integer countNum();
}
