package com.xiaoniucr.common.vo;

import java.util.List;

/**
 * 分页返回参数
 */
public class PageVo<T> {

    private Integer code;

    private Integer draw = 1;

    private String msg;

    private Integer recordsTotal;

    private Integer recordsFiltered;

    private List<T> data;

    public PageVo() {
    }

    public PageVo(Integer recordsTotal, List<T> data) {
        this.code = 200;
        this.msg = "查询成功！";
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsTotal;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Integer recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public Integer getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(Integer recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
