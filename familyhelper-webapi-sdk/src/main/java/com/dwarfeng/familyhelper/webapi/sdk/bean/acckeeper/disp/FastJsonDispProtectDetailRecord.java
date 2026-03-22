package com.dwarfeng.familyhelper.webapi.sdk.bean.acckeeper.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.acckeeper.sdk.bean.entity.FastJsonLoginHistory;
import com.dwarfeng.acckeeper.sdk.bean.key.FastJsonRecordKey;
import com.dwarfeng.familyhelper.webapi.stack.bean.acckeeper.disp.DispProtectDetailRecord;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * FastJson 可展示的保护详细信息记录。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonDispProtectDetailRecord implements Dto {

    private static final long serialVersionUID = -1274612678764701516L;

    public static FastJsonDispProtectDetailRecord of(DispProtectDetailRecord dispProtectDetailRecord) {
        if (Objects.isNull(dispProtectDetailRecord)) {
            return null;
        } else {
            return new FastJsonDispProtectDetailRecord(
                    FastJsonRecordKey.of(dispProtectDetailRecord.getKey()),
                    dispProtectDetailRecord.getValue(),
                    FastJsonLoginHistory.of(dispProtectDetailRecord.getLoginHistory())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonRecordKey key;

    @JSONField(name = "value", ordinal = 2)
    private String value;

    @JSONField(name = "login_history", ordinal = 3)
    private FastJsonLoginHistory loginHistory;

    public FastJsonDispProtectDetailRecord() {
    }

    public FastJsonDispProtectDetailRecord(FastJsonRecordKey key, String value, FastJsonLoginHistory loginHistory) {
        this.key = key;
        this.value = value;
        this.loginHistory = loginHistory;
    }

    public FastJsonRecordKey getKey() {
        return key;
    }

    public void setKey(FastJsonRecordKey key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public FastJsonLoginHistory getLoginHistory() {
        return loginHistory;
    }

    public void setLoginHistory(FastJsonLoginHistory loginHistory) {
        this.loginHistory = loginHistory;
    }

    @Override
    public String toString() {
        return "FastJsonDispProtectDetailRecord{" +
                "key=" + key +
                ", value='" + value + '\'' +
                ", loginHistory=" + loginHistory +
                '}';
    }
}
