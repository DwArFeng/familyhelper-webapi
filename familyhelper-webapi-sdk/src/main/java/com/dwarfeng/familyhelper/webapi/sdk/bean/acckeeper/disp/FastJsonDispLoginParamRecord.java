package com.dwarfeng.familyhelper.webapi.sdk.bean.acckeeper.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.acckeeper.sdk.bean.entity.FastJsonLoginHistory;
import com.dwarfeng.acckeeper.sdk.bean.key.FastJsonRecordKey;
import com.dwarfeng.familyhelper.webapi.stack.bean.acckeeper.disp.DispLoginParamRecord;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * FastJson 可展示的登录参数记录。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonDispLoginParamRecord implements Dto {

    private static final long serialVersionUID = -5507159809411670393L;

    public static FastJsonDispLoginParamRecord of(DispLoginParamRecord dispLoginParamRecord) {
        if (Objects.isNull(dispLoginParamRecord)) {
            return null;
        } else {
            return new FastJsonDispLoginParamRecord(
                    FastJsonRecordKey.of(dispLoginParamRecord.getKey()),
                    dispLoginParamRecord.getValue(),
                    FastJsonLoginHistory.of(dispLoginParamRecord.getLoginHistory())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonRecordKey key;

    @JSONField(name = "value", ordinal = 2)
    private String value;

    @JSONField(name = "login_history", ordinal = 3)
    private FastJsonLoginHistory loginHistory;

    public FastJsonDispLoginParamRecord() {
    }

    public FastJsonDispLoginParamRecord(FastJsonRecordKey key, String value, FastJsonLoginHistory loginHistory) {
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
        return "FastJsonDispLoginParamRecord{" +
                "key=" + key +
                ", value='" + value + '\'' +
                ", loginHistory=" + loginHistory +
                '}';
    }
}
