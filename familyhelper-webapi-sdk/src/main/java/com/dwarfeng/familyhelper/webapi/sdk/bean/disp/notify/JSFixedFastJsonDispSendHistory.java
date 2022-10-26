package com.dwarfeng.familyhelper.webapi.sdk.bean.disp.notify;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.system.FastJsonDispAccount;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.notify.DispSendHistory;
import com.dwarfeng.notify.sdk.bean.entity.FastJsonTopic;
import com.dwarfeng.notify.sdk.bean.entity.JSFixedFastJsonNotifySetting;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * 可展示发送历史。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public class JSFixedFastJsonDispSendHistory implements Dto {

    private static final long serialVersionUID = 4515400258735780946L;

    public static JSFixedFastJsonDispSendHistory of(DispSendHistory dispSendHistory) {
        if (Objects.isNull(dispSendHistory)) {
            return null;
        } else {
            return new JSFixedFastJsonDispSendHistory(
                    JSFixedFastJsonLongIdKey.of(dispSendHistory.getKey()),
                    JSFixedFastJsonLongIdKey.of(dispSendHistory.getNotifySettingKey()),
                    FastJsonStringIdKey.of(dispSendHistory.getTopicKey()),
                    FastJsonStringIdKey.of(dispSendHistory.getUserKey()),
                    dispSendHistory.getHappenedDate(), dispSendHistory.getRouteInfo(),
                    dispSendHistory.getDispatchInfo(), dispSendHistory.getSendInfo(), dispSendHistory.isSucceedFlag(),
                    dispSendHistory.getSenderMessage(), dispSendHistory.getRemark(),
                    JSFixedFastJsonNotifySetting.of(dispSendHistory.getNotifySetting()),
                    FastJsonTopic.of(dispSendHistory.getTopic()),
                    FastJsonDispAccount.of(dispSendHistory.getAccount())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "notify_setting_key", ordinal = 2)
    private JSFixedFastJsonLongIdKey notifySettingKey;

    @JSONField(name = "topic_key", ordinal = 3)
    private FastJsonStringIdKey topicKey;

    @JSONField(name = "user_key", ordinal = 4)
    private FastJsonStringIdKey userKey;

    @JSONField(name = "happened_date", ordinal = 5)
    private Date happenedDate;

    @JSONField(name = "route_info", ordinal = 6)
    private String routeInfo;

    @JSONField(name = "dispatch_info", ordinal = 7)
    private String dispatchInfo;

    @JSONField(name = "send_info", ordinal = 8)
    private String sendInfo;

    @JSONField(name = "succeed_flag", ordinal = 9)
    private boolean succeedFlag;

    @JSONField(name = "sender_message", ordinal = 10)
    private String senderMessage;

    @JSONField(name = "remark", ordinal = 11)
    private String remark;

    @JSONField(name = "notify_setting", ordinal = 12)
    private JSFixedFastJsonNotifySetting notifySetting;

    @JSONField(name = "topic", ordinal = 13)
    private FastJsonTopic topic;

    @JSONField(name = "account", ordinal = 14)
    private FastJsonDispAccount account;

    public JSFixedFastJsonDispSendHistory() {
    }

    public JSFixedFastJsonDispSendHistory(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey notifySettingKey, FastJsonStringIdKey topicKey,
            FastJsonStringIdKey userKey, Date happenedDate, String routeInfo, String dispatchInfo, String sendInfo,
            boolean succeedFlag, String senderMessage, String remark, JSFixedFastJsonNotifySetting notifySetting,
            FastJsonTopic topic, FastJsonDispAccount account
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

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
        this.key = key;
    }

    public JSFixedFastJsonLongIdKey getNotifySettingKey() {
        return notifySettingKey;
    }

    public void setNotifySettingKey(JSFixedFastJsonLongIdKey notifySettingKey) {
        this.notifySettingKey = notifySettingKey;
    }

    public FastJsonStringIdKey getTopicKey() {
        return topicKey;
    }

    public void setTopicKey(FastJsonStringIdKey topicKey) {
        this.topicKey = topicKey;
    }

    public FastJsonStringIdKey getUserKey() {
        return userKey;
    }

    public void setUserKey(FastJsonStringIdKey userKey) {
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

    public JSFixedFastJsonNotifySetting getNotifySetting() {
        return notifySetting;
    }

    public void setNotifySetting(JSFixedFastJsonNotifySetting notifySetting) {
        this.notifySetting = notifySetting;
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
        return "JSFixedFastJsonDispSendHistory{" +
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
