package com.dwarfeng.familyhelper.webapi.stack.bean.acckeeper.disp;

import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginHistory;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;
import java.util.Objects;

/**
 * 可展示的登录历史。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class DispLoginHistory implements Dto {

    private static final long serialVersionUID = -1350354546970305545L;

    public static DispLoginHistory of(LoginHistory loginHistory, Account account) {
        if (Objects.isNull(loginHistory)) {
            return null;
        } else {
            return new DispLoginHistory(
                    loginHistory.getKey(),
                    loginHistory.getAccountId(),
                    loginHistory.getHappenedDate(),
                    loginHistory.getResponseCode(),
                    loginHistory.getMessage(),
                    loginHistory.getAlarmLevel(),
                    loginHistory.getLoginRemark(),
                    account
            );
        }
    }

    private LongIdKey key;
    private String accountId;
    private Date happenedDate;
    private int responseCode;
    private String message;
    private Integer alarmLevel;
    private String loginRemark;
    private Account account;

    public DispLoginHistory() {
    }

    public DispLoginHistory(
            LongIdKey key, String accountId, Date happenedDate, int responseCode, String message, Integer alarmLevel,
            String loginRemark, Account account
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "DispLoginHistory{" +
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
