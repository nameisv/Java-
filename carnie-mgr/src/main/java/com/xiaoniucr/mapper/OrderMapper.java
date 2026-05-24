package com.xiaoniucr.mapper;

import com.xiaoniucr.entity.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderMapper {

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
    int insert(Order record);

    /**
     * 部分字段保存记录
     * @param record
     * @return
     */
    int insertSelective(Order record);

    /**
     * 根据ID查询记录
     * @param id
     * @return
     */
    Order selectByPrimaryKey(Integer id);

    /**
     * 部分字段更新记录
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Order record);

    /**
     * 全量字段更新记录信息
     * @param record
     * @return
     */
    int updateByPrimaryKey(Order record);


    /**
     * 查询记录列表
     * @param map
     * @return
     */
    List<Order> findList(Map map);


    /**
     * 统计记录
     * @param map
     * @return
     */
    Integer findTotal(Map map);


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


    /**
     * 统计各订单状态下的订单数量
     * @param status
     * @return
     */
    Integer countByStatus(@Param(value = "status")Integer status);
}