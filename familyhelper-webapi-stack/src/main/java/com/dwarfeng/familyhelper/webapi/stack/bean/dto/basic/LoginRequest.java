package com.dwarfeng.familyhelper.webapi.stack.bean.dto.basic;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

/**
 * 登录请求。
 *
 * @author DwArFeng
 * @since beta-1.0.0
 */
public class LoginRequest implements Dto {

    private static final long serialVersionUID = -6516650945613240111L;

    private String accountId;
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String accountId, String password) {
        this.accountId = accountId;
        this.password = password;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "accountId='" + accountId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
