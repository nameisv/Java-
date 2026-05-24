package com.xiaoniucr.common.vo;

/**
 * 技师统计
 */
public class OrderVo {

    /**
     * 订单状态：0已支付，1已出票，2已退票
     */
    private String name;


    /**
     * 订单数量
     */
    private Integer value;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
