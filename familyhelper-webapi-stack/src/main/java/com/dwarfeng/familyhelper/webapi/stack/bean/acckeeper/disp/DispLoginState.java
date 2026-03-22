package com.dwarfeng.familyhelper.webapi.stack.bean.acckeeper.disp;

import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

import java.util.Date;
import java.util.Objects;

/**
 * 可展示的登录状态。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class DispLoginState implements Dto {

    private static final long serialVersionUID = 7033164396893818941L;

    public static DispLoginState of(LoginState loginState, Account account) {
        if (Objects.isNull(loginState)) {
            return null;
        } else {
            return new DispLoginState(
                    loginState.getKey(),
                    loginState.getAccountKey(),
                    loginState.getExpireDate(),
                    loginState.getSerialVersion(),
                    loginState.getGeneratedDate(),
                    loginState.getType(),
                    loginState.getRemark(),
                    account
            );
        }
    }

    private StringIdKey key;
    private StringIdKey accountKey;
    private Date expireDate;
    private long serialVersion;
    private Date generatedDate;
    private int type;
    private String remark;
    private Account account;

    public DispLoginState() {
    }

    public DispLoginState(
            StringIdKey key, StringIdKey accountKey, Date expireDate, long serialVersion, Date generatedDate, int type,
            String remark, Account account
    ) {
        this.key = key;
        this.accountKey = accountKey;
        this.expireDate = expireDate;
        this.serialVersion = serialVersion;
        this.generatedDate = generatedDate;
        this.type = type;
        this.remark = remark;
        this.account = account;
    }

    public StringIdKey getKey() {
        return key;
    }

    public void setKey(StringIdKey key) {
        this.key = key;
    }

    public StringIdKey getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(StringIdKey accountKey) {
        this.accountKey = accountKey;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public long getSerialVersion() {
        return serialVersion;
    }

    public void setSerialVersion(long serialVersion) {
        this.serialVersion = serialVersion;
    }

    public Date getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(Date generatedDate) {
        this.generatedDate = generatedDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "DispLoginState{" +
                "key=" + key +
                ", accountKey=" + accountKey +
                ", expireDate=" + expireDate +
                ", serialVersion=" + serialVersion +
                ", generatedDate=" + generatedDate +
                ", type=" + type +
                ", remark='" + remark + '\'' +
                ", account=" + account +
                '}';
    }
}
