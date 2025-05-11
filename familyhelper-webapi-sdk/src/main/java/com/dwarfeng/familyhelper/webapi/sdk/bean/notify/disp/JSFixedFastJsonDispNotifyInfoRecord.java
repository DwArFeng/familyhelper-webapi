package com.dwarfeng.familyhelper.webapi.sdk.bean.notify.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.notify.disp.DispNotifyInfoRecord;
import com.dwarfeng.notify.sdk.bean.key.JSFixedFastJsonNotifyInfoRecordKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * JSFixed FastJson 可展示通知信息记录。
 *
 * @author DwArFeng
 * @since 1.0.9
 */
public class JSFixedFastJsonDispNotifyInfoRecord implements Dto {

    private static final long serialVersionUID = 1693609754646412915L;

    public static JSFixedFastJsonDispNotifyInfoRecord of(DispNotifyInfoRecord dispNotifyInfoRecord) {
        if (Objects.isNull(dispNotifyInfoRecord)) {
            return null;
        } else {
            return new JSFixedFastJsonDispNotifyInfoRecord(
                    JSFixedFastJsonNotifyInfoRecordKey.of(dispNotifyInfoRecord.getKey()),
                    dispNotifyInfoRecord.getValue(),
                    JSFixedFastJsonDispNotifyHistory.of(dispNotifyInfoRecord.getNotifyHistory())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonNotifyInfoRecordKey key;

    @JSONField(name = "value", ordinal = 2)
    private String value;

    @JSONField(name = "notify_history", ordinal = 3)
    private JSFixedFastJsonDispNotifyHistory notifyHistory;

    public JSFixedFastJsonDispNotifyInfoRecord() {
    }

    public JSFixedFastJsonDispNotifyInfoRecord(
            JSFixedFastJsonNotifyInfoRecordKey key, String value, JSFixedFastJsonDispNotifyHistory notifyHistory
    ) {
        this.key = key;
        this.value = value;
        this.notifyHistory = notifyHistory;
    }

    public JSFixedFastJsonNotifyInfoRecordKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonNotifyInfoRecordKey key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public JSFixedFastJsonDispNotifyHistory getNotifyHistory() {
        return notifyHistory;
    }

    public void setNotifyHistory(JSFixedFastJsonDispNotifyHistory notifyHistory) {
        this.notifyHistory = notifyHistory;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispNotifyInfoRecord{" +
                "key=" + key +
                ", value='" + value + '\'' +
                ", notifyHistory=" + notifyHistory +
                '}';
    }
}
