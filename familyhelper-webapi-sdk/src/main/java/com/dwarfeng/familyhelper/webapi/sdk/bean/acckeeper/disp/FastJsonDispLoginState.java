package com.dwarfeng.familyhelper.webapi.sdk.bean.acckeeper.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.acckeeper.sdk.bean.entity.FastJsonAccount;
import com.dwarfeng.familyhelper.webapi.stack.bean.acckeeper.disp.DispLoginState;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * FastJson 可展示的登录状态。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonDispLoginState implements Dto {

    private static final long serialVersionUID = -143172650694929855L;

    public static FastJsonDispLoginState of(DispLoginState dispLoginState) {
        if (Objects.isNull(dispLoginState)) {
            return null;
        } else {
            return new FastJsonDispLoginState(
                    FastJsonStringIdKey.of(dispLoginState.getKey()),
                    FastJsonStringIdKey.of(dispLoginState.getAccountKey()),
                    dispLoginState.getExpireDate(),
                    dispLoginState.getSerialVersion(),
                    dispLoginState.getGeneratedDate(),
                    dispLoginState.getType(),
                    dispLoginState.getRemark(),
                    FastJsonAccount.of(dispLoginState.getAccount())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonStringIdKey key;

    @JSONField(name = "account_key", ordinal = 2)
    private FastJsonStringIdKey accountKey;

    @JSONField(name = "expire_date", ordinal = 3)
    private Date expireDate;

    @JSONField(name = "serial_version", ordinal = 4)
    private long serialVersion;

    @JSONField(name = "generated_date", ordinal = 5)
    private Date generatedDate;

    @JSONField(name = "type", ordinal = 6)
    private int type;

    @JSONField(name = "remark", ordinal = 7)
    private String remark;

    @JSONField(name = "account", ordinal = 8)
    private FastJsonAccount account;

    public FastJsonDispLoginState() {
    }

    public FastJsonDispLoginState(
            FastJsonStringIdKey key, FastJsonStringIdKey accountKey, Date expireDate, long serialVersion,
            Date generatedDate, int type, String remark, FastJsonAccount account
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

    public FastJsonStringIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonStringIdKey key) {
        this.key = key;
    }

    public FastJsonStringIdKey getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(FastJsonStringIdKey accountKey) {
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

    public FastJsonAccount getAccount() {
        return account;
    }

    public void setAccount(FastJsonAccount account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "FastJsonDispLoginState{" +
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
