package com.dwarfeng.familyhelper.webapi.sdk.bean.disp.notify;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.notify.DispSenderRelation;
import com.dwarfeng.notify.sdk.bean.entity.FastJsonTopic;
import com.dwarfeng.notify.sdk.bean.entity.JSFixedFastJsonNotifySetting;
import com.dwarfeng.notify.sdk.bean.entity.JSFixedFastJsonSenderInfo;
import com.dwarfeng.notify.sdk.bean.entity.key.JSFixedFastJsonSenderRelationKey;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * JSFixed FastJson 可展示发送器关联。
 *
 * @author DwArFeng
 * @since 1.0.6
 */
public class JSFixedFastJsonDispSenderRelation implements Dto {

    private static final long serialVersionUID = -2464235074507322963L;

    public static JSFixedFastJsonDispSenderRelation of(DispSenderRelation dispSenderRelation) {
        if (Objects.isNull(dispSenderRelation)) {
            return null;
        } else {
            return new JSFixedFastJsonDispSenderRelation(
                    JSFixedFastJsonSenderRelationKey.of(dispSenderRelation.getKey()),
                    JSFixedFastJsonLongIdKey.of(dispSenderRelation.getSenderInfoKey()),
                    dispSenderRelation.getRemark(),
                    JSFixedFastJsonNotifySetting.of(dispSenderRelation.getNotifySetting()),
                    FastJsonTopic.of(dispSenderRelation.getTopic()),
                    JSFixedFastJsonSenderInfo.of(dispSenderRelation.getSenderInfo())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonSenderRelationKey key;

    @JSONField(name = "sender_info_key", ordinal = 2)
    private JSFixedFastJsonLongIdKey senderInfoKey;

    @JSONField(name = "remark", ordinal = 3)
    private String remark;

    @JSONField(name = "notify_setting", ordinal = 4)
    private JSFixedFastJsonNotifySetting notifySetting;

    @JSONField(name = "topic", ordinal = 5)
    private FastJsonTopic topic;

    @JSONField(name = "sender_info", ordinal = 6)
    private JSFixedFastJsonSenderInfo senderInfo;

    public JSFixedFastJsonDispSenderRelation() {
    }

    public JSFixedFastJsonDispSenderRelation(
            JSFixedFastJsonSenderRelationKey key, JSFixedFastJsonLongIdKey senderInfoKey, String remark,
            JSFixedFastJsonNotifySetting notifySetting, FastJsonTopic topic, JSFixedFastJsonSenderInfo senderInfo
    ) {
        this.key = key;
        this.senderInfoKey = senderInfoKey;
        this.remark = remark;
        this.notifySetting = notifySetting;
        this.topic = topic;
        this.senderInfo = senderInfo;
    }

    public JSFixedFastJsonSenderRelationKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonSenderRelationKey key) {
        this.key = key;
    }

    public JSFixedFastJsonLongIdKey getSenderInfoKey() {
        return senderInfoKey;
    }

    public void setSenderInfoKey(JSFixedFastJsonLongIdKey senderInfoKey) {
        this.senderInfoKey = senderInfoKey;
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

    public JSFixedFastJsonSenderInfo getSenderInfo() {
        return senderInfo;
    }

    public void setSenderInfo(JSFixedFastJsonSenderInfo senderInfo) {
        this.senderInfo = senderInfo;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispSenderRelation{" +
                "key=" + key +
                ", senderInfoKey=" + senderInfoKey +
                ", remark='" + remark + '\'' +
                ", notifySetting=" + notifySetting +
                ", topic=" + topic +
                ", senderInfo=" + senderInfo +
                '}';
    }
}
