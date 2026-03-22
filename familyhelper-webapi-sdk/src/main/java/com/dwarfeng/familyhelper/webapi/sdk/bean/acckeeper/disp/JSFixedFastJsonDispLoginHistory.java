package com.dwarfeng.familyhelper.webapi.sdk.bean.acckeeper.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.acckeeper.sdk.bean.entity.JSFixedFastJsonAccount;
import com.dwarfeng.familyhelper.webapi.stack.bean.acckeeper.disp.DispLoginHistory;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 可展示的登录历史。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class JSFixedFastJsonDispLoginHistory implements Dto {

    private static final long serialVersionUID = 3008821621716119355L;

    public static JSFixedFastJsonDispLoginHistory of(DispLoginHistory dispLoginHistory) {
        if (Objects.isNull(dispLoginHistory)) {
            return null;
        } else {
            return new JSFixedFastJsonDispLoginHistory(
                    JSFixedFastJsonLongIdKey.of(dispLoginHistory.getKey()),
                    dispLoginHistory.getAccountId(),
                    dispLoginHistory.getHappenedDate(),
                    dispLoginHistory.getResponseCode(),
                    dispLoginHistory.getMessage(),
                    dispLoginHistory.getAlarmLevel(),
                    dispLoginHistory.getLoginRemark(),
                    JSFixedFastJsonAccount.of(dispLoginHistory.getAccount())
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

    @JSONField(name = "message", ordinal = 5)
    private String message;

    @JSONField(name = "alarm_level", ordinal = 6)
    private Integer alarmLevel;

    @JSONField(name = "login_remark", ordinal = 7)
    private String loginRemark;

    @JSONField(name = "account", ordinal = 8)
    private JSFixedFastJsonAccount account;

    public JSFixedFastJsonDispLoginHistory() {
    }

    public JSFixedFastJsonDispLoginHistory(
            JSFixedFastJsonLongIdKey key, String accountId, Date happenedDate, int responseCode, String message,
            Integer alarmLevel, String loginRemark, JSFixedFastJsonAccount account
    ) {
        this.key = key;
        this.accountId = accountId;
        this.happenedDate = happenedDate;
        this.responseCode = responseCode;
        this.message = message;
        this.alarmLevel = alarmLevel;
        this.loginRemark = loginRemark;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(Integer alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public String getLoginRemark() {
        return loginRemark;
    }

    public void setLoginRemark(String loginRemark) {
        this.loginRemark = loginRemark;
    }

    public JSFixedFastJsonAccount getAccount() {
        return account;
    }

    public void setAccount(JSFixedFastJsonAccount account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispLoginHistory{" +
                "key=" + key +
                ", accountId='" + accountId + '\'' +
                ", happenedDate=" + happenedDate +
                ", responseCode=" + responseCode +
                ", message='" + message + '\'' +
                ", alarmLevel=" + alarmLevel +
                ", loginRemark='" + loginRemark + '\'' +
                ", account=" + account +
                '}';
    }
}
