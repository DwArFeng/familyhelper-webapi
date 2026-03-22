package com.dwarfeng.familyhelper.webapi.sdk.bean.acckeeper.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.acckeeper.sdk.bean.entity.JSFixedFastJsonLoginHistory;
import com.dwarfeng.acckeeper.sdk.bean.key.JSFixedFastJsonRecordKey;
import com.dwarfeng.familyhelper.webapi.stack.bean.acckeeper.disp.DispLoginParamRecord;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * JSFixed FastJson 可展示的登录参数记录。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class JSFixedFastJsonDispLoginParamRecord implements Dto {

    private static final long serialVersionUID = 5699493740844670930L;

    public static JSFixedFastJsonDispLoginParamRecord of(DispLoginParamRecord dispLoginParamRecord) {
        if (Objects.isNull(dispLoginParamRecord)) {
            return null;
        } else {
            return new JSFixedFastJsonDispLoginParamRecord(
                    JSFixedFastJsonRecordKey.of(dispLoginParamRecord.getKey()),
                    dispLoginParamRecord.getValue(),
                    JSFixedFastJsonLoginHistory.of(dispLoginParamRecord.getLoginHistory())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonRecordKey key;

    @JSONField(name = "value", ordinal = 2)
    private String value;

    @JSONField(name = "login_history", ordinal = 3)
    private JSFixedFastJsonLoginHistory loginHistory;

    public JSFixedFastJsonDispLoginParamRecord() {
    }

    public JSFixedFastJsonDispLoginParamRecord(
            JSFixedFastJsonRecordKey key, String value, JSFixedFastJsonLoginHistory loginHistory
    ) {
        this.key = key;
        this.value = value;
        this.loginHistory = loginHistory;
    }

    public JSFixedFastJsonRecordKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonRecordKey key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public JSFixedFastJsonLoginHistory getLoginHistory() {
        return loginHistory;
    }

    public void setLoginHistory(JSFixedFastJsonLoginHistory loginHistory) {
        this.loginHistory = loginHistory;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispLoginParamRecord{" +
                "key=" + key +
                ", value='" + value + '\'' +
                ", loginHistory=" + loginHistory +
                '}';
    }
}
