package com.xiaoniucr.mapper;

import com.xiaoniucr.entity.Admin;

import java.util.List;
import java.util.Map;

/**
 * 管理员数据操作层
 */
public interface AdminMapper {
    /**
     * 根据ID删除记录
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 全量字段保存记录
     */
    int insert(Admin record);

    /**
     * 部门字段保存记录
     */
    int insertSelective(Admin record);

    /**
     * 根据ID查询记录
     */
    Admin selectByPrimaryKey(Integer id);

    /**
     * 部分字段更新记录
     */
    int updateByPrimaryKeySelective(Admin record);

    /**
     * 全量字段更新记录信息
     */
    int updateByPrimaryKey(Admin record);


    /**
     * 查询记录列表
     * @param map
     * @return
     */
    List<Admin> findList(Map map);


    /**
     * 统计记录
     * @param map
     * @return
     */
    Integer findTotal(Map map);

    /**
     * 根据账户查询
     * @param username
     * @return
     */
    Admin selectByUsername(String username);
}