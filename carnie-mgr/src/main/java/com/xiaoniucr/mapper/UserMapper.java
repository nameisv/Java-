package com.xiaoniucr.mapper;

import com.xiaoniucr.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户持久化层
 */
public interface UserMapper {
    /**
     * 根据ID删除记录
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 全量字段保存记录
     */
    int insert(User record);

    /**
     * 部门字段保存记录
     */
    int insertSelective(User record);

    /**
     * 根据ID查询记录
     */
    User selectByPrimaryKey(Integer id);

    /**
     * 部分字段更新记录
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * 全量字段更新记录信息
     */
    int updateByPrimaryKey(User record);


    /**
     * 查询记录列表
     * @param map
     * @return
     */
    List<User> findList(Map map);


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
    User selectByUsername(String username);


    /**
     * 查询所有记录
     * @return
     */
    List<User> findAll();


    /**
     * 统计新增
     * @param beginTime
     * @param endTime
     * @return
     */
    Integer countAddNum(@Param(value = "beginTime")String beginTime,@Param(value = "endTime")String endTime);

    /**
     * 更新用户余额
     * @param userId
     * @param amount
     * @return
     */
    int updateAmount(@Param(value = "userId")Integer userId, @Param(value = "amount")Double amount);
}