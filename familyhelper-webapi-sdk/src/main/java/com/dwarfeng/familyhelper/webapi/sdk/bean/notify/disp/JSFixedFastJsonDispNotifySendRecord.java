package com.dwarfeng.familyhelper.webapi.sdk.bean.notify.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.sdk.bean.system.disp.FastJsonDispAccount;
import com.dwarfeng.familyhelper.webapi.stack.bean.notify.disp.DispNotifySendRecord;
import com.dwarfeng.notify.sdk.bean.entity.FastJsonTopic;
import com.dwarfeng.notify.sdk.bean.key.JSFixedFastJsonNotifySendRecordKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * JSFixed FastJson 可展示通知发送记录。
 *
 * @author DwArFeng
 * @since 1.0.9
 */
public class JSFixedFastJsonDispNotifySendRecord implements Dto {

    private static final long serialVersionUID = 8319627388959229207L;

    public static JSFixedFastJsonDispNotifySendRecord of(DispNotifySendRecord dispNotifySendRecord) {
        if (Objects.isNull(dispNotifySendRecord)) {
            return null;
        } else {
            return new JSFixedFastJsonDispNotifySendRecord(
                    JSFixedFastJsonNotifySendRecordKey.of(dispNotifySendRecord.getKey()),
                    dispNotifySendRecord.getSucceedFlag(), dispNotifySendRecord.getSenderMessage(),
                    JSFixedFastJsonDispNotifyHistory.of(dispNotifySendRecord.getNotifyHistory()),
                    FastJsonTopic.of(dispNotifySendRecord.getTopic()),
                    FastJsonDispAccount.of(dispNotifySendRecord.getAccount())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonNotifySendRecordKey key;

    @JSONField(name = "succeed_flag", ordinal = 2)
    private Boolean succeedFlag;

    @JSONField(name = "sender_message", ordinal = 3)
    private String senderMessage;

    @JSONField(name = "notify_history", ordinal = 4)
    private JSFixedFastJsonDispNotifyHistory notifyHistory;

    @JSONField(name = "topic", ordinal = 5)
    private FastJsonTopic topic;

    @JSONField(name = "account", ordinal = 6)
    private FastJsonDispAccount account;

    public JSFixedFastJsonDispNotifySendRecord() {
    }

    public JSFixedFastJsonDispNotifySendRecord(
            JSFixedFastJsonNotifySendRecordKey key, Boolean succeedFlag, String senderMessage,
            JSFixedFastJsonDispNotifyHistory notifyHistory, FastJsonTopic topic, FastJsonDispAccount account
    ) {
        this.key = key;
        this.succeedFlag = succeedFlag;
        this.senderMessage = senderMessage;
        this.notifyHistory = notifyHistory;
        this.topic = topic;
        this.account = account;
    }

    public JSFixedFastJsonNotifySendRecordKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonNotifySendRecordKey key) {
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

    public JSFixedFastJsonDispNotifyHistory getNotifyHistory() {
        return notifyHistory;
    }

    public void setNotifyHistory(JSFixedFastJsonDispNotifyHistory notifyHistory) {
        this.notifyHistory = notifyHistory;
    }

    public FastJsonTopic getTopic() {
        return topic;
    }

    public void setTopic(FastJsonTopic topic) {
        this.topic = topic;
    }

    public FastJsonDispAccount getAccount() {
        return account;
    }

    public void setAccount(FastJsonDispAccount account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispNotifySendRecord{" +
                "key=" + key +
                ", succeedFlag=" + succeedFlag +
                ", senderMessage='" + senderMessage + '\'' +
                ", notifyHistory=" + notifyHistory +
                ", topic=" + topic +
                ", account=" + account +
                '}';
    }
}
