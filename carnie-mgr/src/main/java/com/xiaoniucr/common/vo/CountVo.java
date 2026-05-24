package com.xiaoniucr.common.vo;

import java.util.List;

/**
 * 统计
 */
public class CountVo {

    /**
     * 技师统计
     */
    private List<OrderVo> orderList;


    /**
     * 过去12个月
     */
    private String[] monthList;


    /**
     * 过去12个月销售额
     */
    private List<Double> amountList;


    public List<OrderVo> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderVo> orderList) {
        this.orderList = orderList;
    }

    public String[] getMonthList() {
        return monthList;
    }

    public void setMonthList(String[] monthList) {
        this.monthList = monthList;
    }

    public List<Double> getAmountList() {
        return amountList;
    }

    public void setAmountList(List<Double> amountList) {
        this.amountList = amountList;
    }
}
