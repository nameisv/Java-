package com.xiaoniucr.common.dto;

/**
 * 修改密码请求参数
 */
public class UpdatePwdDto {

    /**
     * 原始密码
     */
    private String oldPwd;

    /**
     * 新的密码
     */
    private String password;


    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
