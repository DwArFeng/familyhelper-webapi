package com.dwarfeng.familyhelper.webapi.sdk.bean.acckeeper.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.acckeeper.sdk.bean.entity.JSFixedFastJsonAccount;
import com.dwarfeng.familyhelper.webapi.stack.bean.acckeeper.disp.DispDeriveHistory;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 可展示的派生历史。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class JSFixedFastJsonDispDeriveHistory implements Dto {

    private static final long serialVersionUID = -6630945603120122135L;

    public static JSFixedFastJsonDispDeriveHistory of(DispDeriveHistory dispDeriveHistory) {
        if (Objects.isNull(dispDeriveHistory)) {
            return null;
        } else {
            return new JSFixedFastJsonDispDeriveHistory(
                    JSFixedFastJsonLongIdKey.of(dispDeriveHistory.getKey()),
                    dispDeriveHistory.getAccountId(),
                    dispDeriveHistory.getHappenedDate(),
                    dispDeriveHistory.getResponseCode(),
                    dispDeriveHistory.getDeriveRemark(),
                    JSFixedFastJsonAccount.of(dispDeriveHistory.getAccount())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "account_id", ordinal = 2)
    private String accountId;

    @JSONField(name = "happened_date", ordinal = 3)
    private Date happenedDate;

    @JSONField(name = "response_code", ordinal = 4)
    private int responseCode;

    @JSONField(name = "derive_remark", ordinal = 5)
    private String deriveRemark;

    @JSONField(name = "account", ordinal = 6)
    private JSFixedFastJsonAccount account;

    public JSFixedFastJsonDispDeriveHistory() {
    }

    public JSFixedFastJsonDispDeriveHistory(
            JSFixedFastJsonLongIdKey key, String accountId, Date happenedDate, int responseCode, String deriveRemark,
            JSFixedFastJsonAccount account
    ) {
        this.key = key;
        this.accountId = accountId;
        this.happenedDate = happenedDate;
        this.responseCode = responseCode;
        this.deriveRemark = deriveRemark;
        this.account = account;
    }

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
        this.key = key;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Date getHappenedDate() {
        return happenedDate;
    }

    public void setHappenedDate(Date happenedDate) {
        this.happenedDate = happenedDate;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getDeriveRemark() {
        return deriveRemark;
    }

    public void setDeriveRemark(String deriveRemark) {
        this.deriveRemark = deriveRemark;
    }

    public JSFixedFastJsonAccount getAccount() {
        return account;
    }

    public void setAccount(JSFixedFastJsonAccount account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispDeriveHistory{" +
                "key=" + key +
                ", accountId='" + accountId + '\'' +
                ", happenedDate=" + happenedDate +
                ", responseCode=" + responseCode +
                ", deriveRemark='" + deriveRemark + '\'' +
                ", account=" + account +
                '}';
    }
}
