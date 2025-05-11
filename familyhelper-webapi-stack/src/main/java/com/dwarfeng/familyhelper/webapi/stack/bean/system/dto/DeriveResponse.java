package com.dwarfeng.familyhelper.webapi.stack.bean.system.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;

/**
 * 派生响应。
 *
 * @author DwArFeng
 * @since 1.2.1
 */
public class DeriveResponse implements Dto {

    private static final long serialVersionUID = 5496930852504966659L;

    private String accountId;
    private Long token;
    private Date expireDate;

    public DeriveResponse() {
    }

    public DeriveResponse(String accountId, Long token, Date expireDate) {
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
