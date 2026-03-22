package com.dwarfeng.familyhelper.webapi.stack.bean.acckeeper.disp;

import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.acckeeper.stack.bean.entity.DeriveHistory;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;
import java.util.Objects;

/**
 * 可展示的派生历史。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class DispDeriveHistory implements Dto {

    private static final long serialVersionUID = -8787043570226805943L;

    public static DispDeriveHistory of(DeriveHistory deriveHistory, Account account) {
        if (Objects.isNull(deriveHistory)) {
            return null;
        } else {
            return new DispDeriveHistory(
                    deriveHistory.getKey(),
                    deriveHistory.getAccountId(),
                    deriveHistory.getHappenedDate(),
                    deriveHistory.getResponseCode(),
                    deriveHistory.getDeriveRemark(),
                    account
            );
        }
    }

    private LongIdKey key;
    private String accountId;
    private Date happenedDate;
    private int responseCode;
    private String deriveRemark;
    private Account account;

    public DispDeriveHistory() {
    }

    public DispDeriveHistory(
            LongIdKey key, String accountId, Date happenedDate, int responseCode, String deriveRemark, Account account
    ) {
        this.key = key;
        this.accountId = accountId;
        this.happenedDate = happenedDate;
        this.responseCode = responseCode;
        this.deriveRemark = deriveRemark;
        this.account = account;
    }

    public LongIdKey getKey() {
        return key;
    }

    public void setKey(LongIdKey key) {
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "DispDeriveHistory{" +
                "key=" + key +
                ", accountId='" + accountId + '\'' +
                ", happenedDate=" + happenedDate +
                ", responseCode=" + responseCode +
                ", deriveRemark='" + deriveRemark + '\'' +
                ", account=" + account +
                '}';
    }
}
