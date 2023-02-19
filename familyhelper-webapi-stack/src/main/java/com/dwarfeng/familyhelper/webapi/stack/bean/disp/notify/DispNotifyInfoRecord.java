package com.dwarfeng.familyhelper.webapi.stack.bean.disp.notify;

import com.dwarfeng.notify.stack.bean.entity.NotifyInfoRecord;
import com.dwarfeng.notify.stack.bean.key.NotifyInfoRecordKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * 可展示通知信息记录。
 *
 * @author DwArFeng
 * @since 1.0.9
 */
public class DispNotifyInfoRecord implements Dto {

    private static final long serialVersionUID = -2697375055473636658L;

    public static DispNotifyInfoRecord of(NotifyInfoRecord notifyInfoRecord, DispNotifyHistory dispNotifyHistory) {
        if (Objects.isNull(notifyInfoRecord)) {
            return null;
        } else {
            return new DispNotifyInfoRecord(
                    notifyInfoRecord.getKey(), notifyInfoRecord.getValue(),
                    dispNotifyHistory
            );
        }
    }

    private NotifyInfoRecordKey key;
    private String value;
    private DispNotifyHistory notifyHistory;

    public DispNotifyInfoRecord() {
    }

    public DispNotifyInfoRecord(NotifyInfoRecordKey key, String value, DispNotifyHistory notifyHistory) {
        this.key = key;
        this.value = value;
        this.notifyHistory = notifyHistory;
    }

    public NotifyInfoRecordKey getKey() {
        return key;
    }

    public void setKey(NotifyInfoRecordKey key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public DispNotifyHistory getNotifyHistory() {
        return notifyHistory;
    }

    public void setNotifyHistory(DispNotifyHistory notifyHistory) {
        this.notifyHistory = notifyHistory;
    }

    @Override
    public String toString() {
        return "DispNotifyInfoRecord{" +
                "key=" + key +
                ", value='" + value + '\'' +
                ", notifyHistory=" + notifyHistory +
                '}';
    }
}
