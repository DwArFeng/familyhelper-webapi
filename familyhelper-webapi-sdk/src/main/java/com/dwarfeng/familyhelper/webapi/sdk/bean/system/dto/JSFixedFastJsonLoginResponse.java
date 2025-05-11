package com.dwarfeng.familyhelper.webapi.sdk.bean.system.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.dto.LoginResponse;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed 登录响应。
 *
 * @author DwArFeng
 * @since beta-1.0.0
 */
public class JSFixedFastJsonLoginResponse implements Dto {

    private static final long serialVersionUID = -1708197366177886496L;

    public static JSFixedFastJsonLoginResponse of(LoginResponse loginResponse) {
        if (Objects.isNull(loginResponse)) {
            return null;
        } else {
            return new JSFixedFastJsonLoginResponse(
                    loginResponse.getAccountId(),
                    loginResponse.getToken(),
                    loginResponse.getExpireDate()
            );
        }
    }

    @JSONField(name = "account_id", ordinal = 1)
    private String accountId;

    @JSONField(name = "token", ordinal = 2, serializeUsing = ToStringSerializer.class)
    private Long token;

    @JSONField(name = "expire_date", ordinal = 3)
    private Date expireDate;

    public JSFixedFastJsonLoginResponse() {
    }

    public JSFixedFastJsonLoginResponse(String accountId, Long token, Date expireDate) {
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
        return "JSFixedFastJsonLoginResponse{" +
                "accountId='" + accountId + '\'' +
                ", token=" + token +
                ", expireDate=" + expireDate +
                '}';
    }
}
