package com.dwarfeng.familyhelper.webapi.stack.bean.acckeeper.disp;

import com.dwarfeng.acckeeper.stack.bean.entity.LoginHistory;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginParamRecord;
import com.dwarfeng.acckeeper.stack.bean.key.RecordKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * 可展示的登录参数记录。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class DispLoginParamRecord implements Dto {

    private static final long serialVersionUID = 3625244569109006806L;

    public static DispLoginParamRecord of(LoginParamRecord loginParamRecord, LoginHistory loginHistory) {
        if (Objects.isNull(loginParamRecord)) {
            return null;
        } else {
            return new DispLoginParamRecord(
                    loginParamRecord.getKey(),
                    loginParamRecord.getValue(),
                    loginHistory
            );
        }
    }

    private RecordKey key;
    private String value;
    private LoginHistory loginHistory;

    public DispLoginParamRecord() {
    }

    public DispLoginParamRecord(RecordKey key, String value, LoginHistory loginHistory) {
        this.key = key;
        this.value = value;
        this.loginHistory = loginHistory;
    }

    public RecordKey getKey() {
        return key;
    }

    public void setKey(RecordKey key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LoginHistory getLoginHistory() {
        return loginHistory;
    }

    public void setLoginHistory(LoginHistory loginHistory) {
        this.loginHistory = loginHistory;
    }

    @Override
    public String toString() {
        return "DispLoginParamRecord{" +
                "key=" + key +
                ", value='" + value + '\'' +
                ", loginHistory=" + loginHistory +
                '}';
    }
}
