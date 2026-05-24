package com.xiaoniucr.mapper;

import com.xiaoniucr.entity.Transaction;

import java.util.List;
import java.util.Map;

/**
 * 交易流水持久化层
 */
public interface TransactionMapper {

    /**
     * 添加流水记录
     */
    int insert(Transaction transaction);

    /**
     * 查询流水列表
     */
    List<Transaction> findList(Map map);

    /**
     * 统计流水总数
     */
    Integer findTotal(Map map);

    /**
     * 统计充值总额
     */
    Double sumRechargeAmount(String beginTime, String endTime);

    /**
     * 统计退票总额
     */
    Double sumRefundAmount(String beginTime, String endTime);
}
