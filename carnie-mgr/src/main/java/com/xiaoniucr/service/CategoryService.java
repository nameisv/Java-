package com.xiaoniucr.service;

import com.xiaoniucr.common.vo.PageVo;
import com.xiaoniucr.entity.Category;

import java.util.List;
import java.util.Map;

/**
 *
 * 项目服务类
 */
public interface CategoryService {




    /**
     * 项目分页
     * @param map
     * @return
     */
    PageVo page(Map map);

    /**
     * 添加项目
     * @param services
     * @return
     */
    Integer add(Category services);

    /**
     * 项目更新
     * @param services
     * @return
     */
    Integer update(Category services);


    /**
     * 删除项目
     * @param id
     * @return
     */
    Integer del(Integer id);



    /**
     * 根据ID查询项目
     * @param id
     * @return
     */
    Category selectById(Integer id);


    /**
     * 查询所有科室
     * @return
     */
    List<Category> findAll();


    /**
     * 查询员工负责的项目
     * @param empId
     * @return
     */
    List<Category> findByEmployee(Integer empId);


    /**
     * 统计总的项目数
     * @return
     */
    Integer countNum();



}
