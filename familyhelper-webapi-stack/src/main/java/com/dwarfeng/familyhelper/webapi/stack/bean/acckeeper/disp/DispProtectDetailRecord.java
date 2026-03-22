package com.dwarfeng.familyhelper.webapi.stack.bean.acckeeper.disp;

import com.dwarfeng.acckeeper.stack.bean.entity.LoginHistory;
import com.dwarfeng.acckeeper.stack.bean.entity.ProtectDetailRecord;
import com.dwarfeng.acckeeper.stack.bean.key.RecordKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * 可展示的保护详细信息记录。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class DispProtectDetailRecord implements Dto {

    private static final long serialVersionUID = -2896389663503794413L;

    public static DispProtectDetailRecord of(ProtectDetailRecord protectDetailRecord, LoginHistory loginHistory) {
        if (Objects.isNull(protectDetailRecord)) {
            return null;
        } else {
            return new DispProtectDetailRecord(
                    protectDetailRecord.getKey(),
                    protectDetailRecord.getValue(),
                    loginHistory
            );
        }
    }

    private RecordKey key;
    private String value;
    private LoginHistory loginHistory;

    public DispProtectDetailRecord() {
    }

    public DispProtectDetailRecord(RecordKey key, String value, LoginHistory loginHistory) {
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
        return "DispProtectDetailRecord{" +
                "key=" + key +
                ", value='" + value + '\'' +
                ", loginHistory=" + loginHistory +
                '}';
    }
}
