package com.xiaoniucr.service;

import com.xiaoniucr.common.vo.PageVo;
import com.xiaoniucr.entity.Transaction;

import java.util.Map;

/**
 * 交易流水服务接口
 */
public interface TransactionService {

    /**
     * 添加流水记录
     */
    int add(Transaction transaction);

    /**
     * 分页查询流水
     */
    PageVo page(Map map);

    /**
     * 统计充值总额
     */
    Double sumRechargeAmount(String beginTime, String endTime);

    /**
     * 统计退票总额
     */
    Double sumRefundAmount(String beginTime, String endTime);
}
