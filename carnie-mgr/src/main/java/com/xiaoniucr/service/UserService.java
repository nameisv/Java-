package com.xiaoniucr.service;

import com.xiaoniucr.common.dto.LoginDto;
import com.xiaoniucr.common.vo.CountVo;
import com.xiaoniucr.common.vo.JSONReturn;
import com.xiaoniucr.common.vo.PageVo;
import com.xiaoniucr.entity.User;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 *
 * 用户相关服务类
 */
public interface UserService {



    /**
     * 登录
     * @param var1
     * @param var2
     * @return
     */
    JSONReturn login(LoginDto var1, HttpSession var2);


    /**
     * 用户分页
     * @param map
     * @return
     */
    PageVo page(Map map);

    /**
     * 添加用户用户
     * @param user
     * @return
     */
    JSONReturn add(User user);

    /**
     * 通用更新方法
     * @param user
     * @return
     */
    Integer update(User user);


    /**
     * 删除用户
     * @param id
     * @return
     */
    Integer del(Integer id);

    /**
     * 更新个人信息
     * @param user
     * @return
     */
    JSONReturn updateInfo(User user);



    /**
     * 根据ID查询用户信息
     * @param id
     * @return
     */
    User selectById(Integer id);


    /**
     * 查询所有用户
     * @return
     */
    List<User> findAll();


    /**
     * 统计新增
     * @param beginTime
     * @param endTime
     * @return
     */
    Integer countAddNum(String beginTime,String endTime);


    /**
     * 统计信息
     * @return
     */
    CountVo count();

    /**
     * 更新用户余额
     * @param userId 用户ID
     * @param amount 金额（正数为充值，负数为扣款）
     * @return
     */
    int updateAmount(Integer userId, Double amount);
    Integer invest(User user);



}
