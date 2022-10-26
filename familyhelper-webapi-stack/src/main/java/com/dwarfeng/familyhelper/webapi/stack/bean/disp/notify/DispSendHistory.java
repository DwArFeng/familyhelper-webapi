package com.dwarfeng.familyhelper.webapi.stack.bean.disp.notify;

import com.dwarfeng.familyhelper.webapi.stack.bean.disp.system.DispAccount;
import com.dwarfeng.notify.stack.bean.entity.NotifySetting;
import com.dwarfeng.notify.stack.bean.entity.SendHistory;
import com.dwarfeng.notify.stack.bean.entity.Topic;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

import java.util.Date;
import java.util.Objects;

/**
 * 可展示发送历史。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public class DispSendHistory implements Dto {

    private static final long serialVersionUID = 5425388125887746731L;

    public static DispSendHistory of(
            SendHistory sendHistory, NotifySetting notifySetting, Topic topic, DispAccount account
    ) {
        if (Objects.isNull(sendHistory)) {
            return null;
        } else {
            return new DispSendHistory(
                    sendHistory.getKey(), sendHistory.getKey(), sendHistory.getTopicKey(), sendHistory.getUserKey(),
                    sendHistory.getHappenedDate(), sendHistory.getRouteInfo(), sendHistory.getDispatchInfo(),
                    sendHistory.getSendInfo(), sendHistory.isSucceedFlag(), sendHistory.getSenderMessage(),
                    sendHistory.getRemark(), notifySetting, topic, account
            );
        }
    }

    private LongIdKey key;
    private LongIdKey notifySettingKey;
    private StringIdKey topicKey;
    private StringIdKey userKey;
    private Date happenedDate;
    private String routeInfo;
    private String dispatchInfo;
    private String sendInfo;
    private boolean succeedFlag;
    private String senderMessage;
    private String remark;
    private NotifySetting notifySetting;
    private Topic topic;
    private DispAccount account;

    public DispSendHistory() {
    }

    public DispSendHistory(
            LongIdKey key, LongIdKey notifySettingKey, StringIdKey topicKey, StringIdKey userKey, Date happenedDate,
            String routeInfo, String dispatchInfo, String sendInfo, boolean succeedFlag, String senderMessage,
            String remark, NotifySetting notifySetting, Topic topic, DispAccount account
    ) {
        this.key = key;
        this.notifySettingKey = notifySettingKey;
        this.topicKey = topicKey;
        this.userKey = userKey;
        this.happenedDate = happenedDate;
        this.routeInfo = routeInfo;
        this.dispatchInfo = dispatchInfo;
        this.sendInfo = sendInfo;
        this.succeedFlag = succeedFlag;
        this.senderMessage = senderMessage;
        this.remark = remark;
        this.notifySetting = notifySetting;
        this.topic = topic;
        this.account = account;
    }

    public LongIdKey getKey() {
        return key;
    }

    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public LongIdKey getNotifySettingKey() {
        return notifySettingKey;
    }

    public void setNotifySettingKey(LongIdKey notifySettingKey) {
        this.notifySettingKey = notifySettingKey;
    }

    public StringIdKey getTopicKey() {
        return topicKey;
    }

    public void setTopicKey(StringIdKey topicKey) {
        this.topicKey = topicKey;
    }

    public StringIdKey getUserKey() {
        return userKey;
    }

    public void setUserKey(StringIdKey userKey) {
        this.userKey = userKey;
    }

    public Date getHappenedDate() {
        return happenedDate;
    }

    public void setHappenedDate(Date happenedDate) {
        this.happenedDate = happenedDate;
    }

    public String getRouteInfo() {
        return routeInfo;
    }

    public void setRouteInfo(String routeInfo) {
        this.routeInfo = routeInfo;
    }

    public String getDispatchInfo() {
        return dispatchInfo;
    }

    public void setDispatchInfo(String dispatchInfo) {
        this.dispatchInfo = dispatchInfo;
    }

    public String getSendInfo() {
        return sendInfo;
    }

    public void setSendInfo(String sendInfo) {
        this.sendInfo = sendInfo;
    }

    public boolean isSucceedFlag() {
        return succeedFlag;
    }

    public void setSucceedFlag(boolean succeedFlag) {
        this.succeedFlag = succeedFlag;
    }

    public String getSenderMessage() {
        return senderMessage;
    }

    public void setSenderMessage(String senderMessage) {
        this.senderMessage = senderMessage;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public NotifySetting getNotifySetting() {
        return notifySetting;
    }

    public void setNotifySetting(NotifySetting notifySetting) {
        this.notifySetting = notifySetting;
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
        return "DispSendHistory{" +
                "key=" + key +
                ", notifySettingKey=" + notifySettingKey +
                ", topicKey=" + topicKey +
                ", userKey=" + userKey +
                ", happenedDate=" + happenedDate +
                ", routeInfo='" + routeInfo + '\'' +
                ", dispatchInfo='" + dispatchInfo + '\'' +
                ", sendInfo='" + sendInfo + '\'' +
                ", succeedFlag=" + succeedFlag +
                ", senderMessage='" + senderMessage + '\'' +
                ", remark='" + remark + '\'' +
                ", notifySetting=" + notifySetting +
                ", topic=" + topic +
                ", account=" + account +
                '}';
    }
}
