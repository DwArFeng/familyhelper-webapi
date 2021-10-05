package com.dwarfeng.familyhelper.webapi.sdk.bean.dto.system;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.dto.system.LoginRequest;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput登录请求。
 *
 * @author DwArFeng
 * @since beta-1.0.0
 */
public class WebInputLoginRequest implements Dto {

    private static final long serialVersionUID = 5022808678605140003L;

    public LoginRequest toStackBean(WebInputLoginRequest webInputLoginRequest) {
        if (Objects.isNull(webInputLoginRequest)) {
            return null;
        } else {
            return new LoginRequest(
                    webInputLoginRequest.getAccountId(),
                    webInputLoginRequest.getPassword()
            );
        }
    }

    @JSONField(name = "account_id", ordinal = 1)
    @NotNull
    private String accountId;

    @JSONField(name = "password", ordinal = 2)
    @NotNull
    private String password;

    public WebInputLoginRequest() {
    }

    public WebInputLoginRequest(String accountId, String password) {
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
        return "WebInputLoginRequest{" +
                "accountId='" + accountId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
