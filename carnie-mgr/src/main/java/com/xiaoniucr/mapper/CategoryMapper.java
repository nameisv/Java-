package com.xiaoniucr.mapper;

import com.xiaoniucr.entity.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 项目信息持久化层
 */
public interface CategoryMapper {

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
    int insert(Category record);

    /**
     * 部分字段保存记录
     * @param record
     * @return
     */
    int insertSelective(Category record);

    /**
     * 根据ID查询记录
     * @param id
     * @return
     */
    Category selectByPrimaryKey(Integer id);

    /**
     * 部分字段更新记录
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Category record);

    /**
     * 全量字段更新记录信息
     * @param record
     * @return
     */
    int updateByPrimaryKey(Category record);



    /**
     * 查询记录列表
     * @param map
     * @return
     */
    List<Category> findList(Map map);


    /**
     * 统计记录
     * @param map
     * @return
     */
    Integer findTotal(Map map);


    /**
     * 查询所有
     * @return
     */
    List<Category> findAll();


    /**
     * 查询员工负责的项目
     * @param empId
     * @return
     */
    List<Category> findByEmployee(@Param(value = "empId")Integer empId);

    /**
     * 统计总的项目数
     * @return
     */
    Integer countNum();
}