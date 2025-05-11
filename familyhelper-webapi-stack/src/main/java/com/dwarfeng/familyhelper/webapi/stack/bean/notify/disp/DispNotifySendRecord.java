package com.dwarfeng.familyhelper.webapi.stack.bean.notify.disp;

import com.dwarfeng.familyhelper.webapi.stack.bean.system.disp.DispAccount;
import com.dwarfeng.notify.stack.bean.entity.NotifySendRecord;
import com.dwarfeng.notify.stack.bean.entity.Topic;
import com.dwarfeng.notify.stack.bean.key.NotifySendRecordKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * 可展示通知发送记录。
 *
 * @author DwArFeng
 * @since 1.0.9
 */
public class DispNotifySendRecord implements Dto {

    private static final long serialVersionUID = 1291117101057616806L;

    public static DispNotifySendRecord of(
            NotifySendRecord notifySendRecord, DispNotifyHistory notifyHistory, Topic topic, DispAccount account
    ) {
        if (Objects.isNull(notifySendRecord)) {
            return null;
        } else {
            return new DispNotifySendRecord(
                    notifySendRecord.getKey(), notifySendRecord.getSucceedFlag(), notifySendRecord.getSenderMessage(),
                    notifyHistory,
                    topic,
                    account
            );
        }
    }

    private NotifySendRecordKey key;
    private Boolean succeedFlag;
    private String senderMessage;
    private DispNotifyHistory notifyHistory;
    private Topic topic;
    private DispAccount account;

    public DispNotifySendRecord() {
    }

    public DispNotifySendRecord(
            NotifySendRecordKey key, Boolean succeedFlag, String senderMessage, DispNotifyHistory notifyHistory,
            Topic topic, DispAccount account
    ) {
        this.key = key;
        this.succeedFlag = succeedFlag;
        this.senderMessage = senderMessage;
        this.notifyHistory = notifyHistory;
        this.topic = topic;
        this.account = account;
    }

    public NotifySendRecordKey getKey() {
        return key;
    }

    public void setKey(NotifySendRecordKey key) {
        this.key = key;
    }

    public Boolean getSucceedFlag() {
        return succeedFlag;
    }

    public void setSucceedFlag(Boolean succeedFlag) {
        this.succeedFlag = succeedFlag;
    }

    public String getSenderMessage() {
        return senderMessage;
    }

    public void setSenderMessage(String senderMessage) {
        this.senderMessage = senderMessage;
    }

    public DispNotifyHistory getNotifyHistory() {
        return notifyHistory;
    }

    public void setNotifyHistory(DispNotifyHistory notifyHistory) {
        this.notifyHistory = notifyHistory;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public DispAccount getAccount() {
        return account;
    }

    public void setAccount(DispAccount account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "DispNotifySendRecord{" +
                "key=" + key +
                ", succeedFlag=" + succeedFlag +
                ", senderMessage='" + senderMessage + '\'' +
                ", notifyHistory=" + notifyHistory +
                ", topic=" + topic +
                ", account=" + account +
                '}';
    }
}
