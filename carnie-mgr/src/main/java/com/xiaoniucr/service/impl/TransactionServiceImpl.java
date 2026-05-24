package com.xiaoniucr.service.impl;

import com.xiaoniucr.common.vo.PageVo;
import com.xiaoniucr.entity.Transaction;
import com.xiaoniucr.mapper.TransactionMapper;
import com.xiaoniucr.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 交易流水服务实现类
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionMapper transactionMapper;

    @Override
    public int add(Transaction transaction) {
        return transactionMapper.insert(transaction);
    }

    @Override
    public PageVo page(Map map) {
        List<Transaction> list = transactionMapper.findList(map);
        Integer total = transactionMapper.findTotal(map);
        PageVo pageVo = new PageVo(total, list);
        if (map.get("draw") != null) {
            pageVo.setDraw(Integer.valueOf(map.get("draw").toString()));
        }
        return pageVo;
    }

    @Override
    public Double sumRechargeAmount(String beginTime, String endTime) {
        return transactionMapper.sumRechargeAmount(beginTime, endTime);
    }

    @Override
    public Double sumRefundAmount(String beginTime, String endTime) {
        return transactionMapper.sumRefundAmount(beginTime, endTime);
    }
}
