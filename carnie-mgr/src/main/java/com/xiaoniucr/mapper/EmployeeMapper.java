package com.xiaoniucr.mapper;

import com.xiaoniucr.entity.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 员工（技师）持久化层
 */
public interface EmployeeMapper {

    /**
     * 根据ID删除记录
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 全量字段保存记录
     * @param record
     * @return
     */
    int insert(Employee record);

    /**
     * 部分字段保存记录
     * @param record
     * @return
     */
    int insertSelective(Employee record);

    /**
     * 根据ID查询记录
     * @param id
     * @return
     */
    Employee selectByPrimaryKey(Integer id);

    /**
     * 部分字段更新记录
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Employee record);

    /**
     * 全量字段更新记录信息
     * @param record
     * @return
     */
    int updateByPrimaryKey(Employee record);


    /**
     * 查询记录列表
     * @param map
     * @return
     */
    List<Employee> findList(Map map);


    /**
     * 统计记录
     * @param map
     * @return
     */
    Integer findTotal(Map map);


    /**
     * 根据工号查询
     * @param username
     * @return
     */
    Employee selectByUsername(String username);


    /**
     * 根据服务项目查询技师
     * @param servicesId
     * @return
     */
    List<Employee> findByServices(@Param(value = "servicesId")Integer servicesId);


    /**
     * 统计各服务项目技师数量
     * @param servicesId
     * @return
     */
    Integer countByServices(@Param(value = "servicesId")Integer servicesId);



    /**
     * 统计技师总数
     * @return
     */
    Integer countNum();
}