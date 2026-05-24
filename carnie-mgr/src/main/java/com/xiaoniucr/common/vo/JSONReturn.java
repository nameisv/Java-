package com.xiaoniucr.common.vo;

import java.io.Serializable;


/**
 * 统一数据返回
 */
public class JSONReturn implements Serializable {

    private static final long serialVersionUID = -9040660305368048213L;

    //成功
    private static final int CODE_SUCCESS = 200;

    //失败
    private static final int CODE_FAILED = 500;

    //返回状态码
    private int code;

    //响应消息
    private String msg;

    //返回数据
    private Object data;

    /**
     * 原始构造
     */
    public JSONReturn() {
    }

    /**
     * 带参构造
     * @param code
     * @param msg
     * @param data
     */
    public JSONReturn(int code, String msg, Object data) {
        super();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    /**
     * 根据状态码和内容返回消息
     * @param code  状态码
     * @param message 消息
     * @param body  消息主体
     * @return
     */
    public static JSONReturn build(int code, String message, Object body){
        return new JSONReturn(code,message,body);
    }

    /**
     * 简单返回成功消息
     * @return
     */
    public static JSONReturn success(){
        return new JSONReturn(CODE_SUCCESS,"操作成功！",null);
    }

    /**
     * 构建返回成功消息
     * @param message 消息
     * @return
     */
    public static JSONReturn success(String message){
        return new JSONReturn(CODE_SUCCESS,message,null);
    }


    /**
     * 构建返回成功消息
     * @param message 消息
     * @param body 成功消息主体
     * @return
     */
    public static JSONReturn success(String message, Object body){
        return new JSONReturn(CODE_SUCCESS,message,body);
    }


    /**
     * 简单返回失败消息
     * @return
     */
    public static JSONReturn fail(){
        return new JSONReturn(CODE_FAILED,"操作失败！",null);
    }


    /**
     * 构建返回失败消息
     * @param message 消息
     * @return
     */
    public static JSONReturn fail(String message){
        return new JSONReturn(CODE_FAILED,message,null);
    }




    /**
     * 构建返回失败消息
     * @param message 消息
     * @param body 失败消息主体
     * @return
     */
    public static JSONReturn fail(String message, Object body){
        return new JSONReturn(CODE_FAILED,message,body);
    }




    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
