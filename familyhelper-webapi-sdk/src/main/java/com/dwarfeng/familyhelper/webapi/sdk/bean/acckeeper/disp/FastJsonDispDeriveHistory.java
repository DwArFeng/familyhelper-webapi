package com.dwarfeng.familyhelper.webapi.sdk.bean.acckeeper.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.acckeeper.sdk.bean.entity.FastJsonAccount;
import com.dwarfeng.familyhelper.webapi.stack.bean.acckeeper.disp.DispDeriveHistory;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * FastJson 可展示的派生历史。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonDispDeriveHistory implements Dto {

    private static final long serialVersionUID = -2521725034424721563L;

    public static FastJsonDispDeriveHistory of(DispDeriveHistory dispDeriveHistory) {
        if (Objects.isNull(dispDeriveHistory)) {
            return null;
        } else {
            return new FastJsonDispDeriveHistory(
                    FastJsonLongIdKey.of(dispDeriveHistory.getKey()),
                    dispDeriveHistory.getAccountId(),
                    dispDeriveHistory.getHappenedDate(),
                    dispDeriveHistory.getResponseCode(),
                    dispDeriveHistory.getDeriveRemark(),
                    FastJsonAccount.of(dispDeriveHistory.getAccount())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonLongIdKey key;

    @JSONField(name = "account_id", ordinal = 2)
    private String accountId;

    @JSONField(name = "happened_date", ordinal = 3)
    private Date happenedDate;

    @JSONField(name = "response_code", ordinal = 4)
    private int responseCode;

    @JSONField(name = "derive_remark", ordinal = 5)
    private String deriveRemark;

    @JSONField(name = "account", ordinal = 6)
    private FastJsonAccount account;

    public FastJsonDispDeriveHistory() {
    }

    public FastJsonDispDeriveHistory(
            FastJsonLongIdKey key, String accountId, Date happenedDate, int responseCode, String deriveRemark,
            FastJsonAccount account
    ) {
        this.key = key;
        this.accountId = accountId;
        this.happenedDate = happenedDate;
        this.responseCode = responseCode;
        this.deriveRemark = deriveRemark;
        this.account = account;
    }

    public FastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonLongIdKey key) {
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

    public FastJsonAccount getAccount() {
        return account;
    }

    public void setAccount(FastJsonAccount account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "FastJsonDispDeriveHistory{" +
                "key=" + key +
                ", accountId='" + accountId + '\'' +
                ", happenedDate=" + happenedDate +
                ", responseCode=" + responseCode +
                ", deriveRemark='" + deriveRemark + '\'' +
                ", account=" + account +
                '}';
    }
}
