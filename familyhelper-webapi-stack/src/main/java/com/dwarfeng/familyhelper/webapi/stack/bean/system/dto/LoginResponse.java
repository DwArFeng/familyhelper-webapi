package com.dwarfeng.familyhelper.webapi.stack.bean.system.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;

/**
 * 登录响应。
 *
 * @author DwArFeng
 * @since beta-1.0.0
 */
public class LoginResponse implements Dto {

    private static final long serialVersionUID = 169682014152048340L;
    private String accountId;
    private Long token;
    private Date expireDate;

    public LoginResponse() {
    }

    public LoginResponse(String accountId, Long token, Date expireDate) {
        this.accountId = accountId;
        this.token = token;
        this.expireDate = expireDate;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Long getToken() {
        return token;
    }

    public void setToken(Long token) {
        this.token = token;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "accountId='" + accountId + '\'' +
                ", token=" + token +
                ", expireDate=" + expireDate +
                '}';
    }
}
