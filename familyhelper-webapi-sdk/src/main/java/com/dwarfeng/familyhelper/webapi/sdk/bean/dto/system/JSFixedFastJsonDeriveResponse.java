package com.dwarfeng.familyhelper.webapi.sdk.bean.dto.system;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.dwarfeng.familyhelper.webapi.stack.bean.dto.system.DeriveResponse;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed 派生响应。
 *
 * @author DwArFeng
 * @since 1.2.1
 */
public class JSFixedFastJsonDeriveResponse implements Dto {

    private static final long serialVersionUID = -3209955044249308322L;

    public static JSFixedFastJsonDeriveResponse of(DeriveResponse deriveResponse) {
        if (Objects.isNull(deriveResponse)) {
            return null;
        } else {
            return new JSFixedFastJsonDeriveResponse(
                    deriveResponse.getAccountId(),
                    deriveResponse.getToken(),
                    deriveResponse.getExpireDate()
            );
        }
    }

    @JSONField(name = "account_id", ordinal = 1)
    private String accountId;

    @JSONField(name = "token", ordinal = 2, serializeUsing = ToStringSerializer.class)
    private Long token;

    @JSONField(name = "expire_date", ordinal = 3)
    private Date expireDate;

    public JSFixedFastJsonDeriveResponse() {
    }

    public JSFixedFastJsonDeriveResponse(String accountId, Long token, Date expireDate) {
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
        return "JSFixedFastJsonDeriveResponse{" +
                "accountId='" + accountId + '\'' +
                ", token=" + token +
                ", expireDate=" + expireDate +
                '}';
    }
}
