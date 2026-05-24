package com.xiaoniucr.service;

import com.xiaoniucr.common.vo.JSONReturn;
import com.xiaoniucr.common.vo.PageVo;
import com.xiaoniucr.entity.Order;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 门票购买服务类
 */
public interface OrderService {

    /**
     * 门票购买分页
     * @param map
     * @return
     */
    PageVo page(Map map);

    /**
     * 门票购买保存
     * @param order
     * @return
     */
    JSONReturn add(Order order);

    /**
     * 门票购买更新
     * @param order
     * @return
     */
    Integer update(Order order);

    /**
     * 门票购买删除（带权限控制）
     * @param id 订单ID
     * @param role 当前用户角色
     * @param userId 当前用户ID
     * @return
     */
    JSONReturn del(Integer id, Integer role, Integer userId);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    Order selectById(Integer id);

    /**
     * 退票（用户端）
     * @param id
     * @return
     */
    JSONReturn cancel(Integer id);

    /**
     * 出票（员工端）
     * @param id 订单ID
     * @param role 当前用户角色
     * @param userId 当前用户ID
     * @return
     */
    JSONReturn issue(Integer id, Integer role, Integer userId);

    /**
     * 统计新增
     * @param beginTime
     * @param endTime
     * @return
     */
    Integer countAddNum(@Param(value = "beginTime")String beginTime, @Param(value = "endTime")String endTime);

    /**
     * 统计营业总额
     * @param beginTime
     * @param endTime
     * @return
     */
    Double countAmount(@Param(value = "beginTime")String beginTime, @Param(value = "endTime")String endTime);

    /**
     * 统计退票金额（今天购买的订单中已退票的金额）
     * @param beginTime
     * @param endTime
     * @return
     */
    Double countRefundAmount(@Param(value = "beginTime")String beginTime, @Param(value = "endTime")String endTime);

}