package com.dwarfeng.familyhelper.webapi.stack.bean.disp.notify;

import com.dwarfeng.notify.stack.bean.entity.NotifySetting;
import com.dwarfeng.notify.stack.bean.entity.SenderInfo;
import com.dwarfeng.notify.stack.bean.entity.SenderRelation;
import com.dwarfeng.notify.stack.bean.entity.Topic;
import com.dwarfeng.notify.stack.bean.entity.key.SenderRelationKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Objects;

/**
 * 可展示发送器关联。
 *
 * @author DwArFeng
 * @since 1.0.6
 */
public class DispSenderRelation implements Dto {

    private static final long serialVersionUID = 8891744911308351956L;

    public static DispSenderRelation of(
            SenderRelation senderRelation, NotifySetting notifySetting, Topic topic, SenderInfo senderInfo
    ) {
        if (Objects.isNull(senderRelation)) {
            return null;
        } else {
            return new DispSenderRelation(
                    senderRelation.getKey(), senderRelation.getSenderInfoKey(), senderRelation.getRemark(),
                    notifySetting, topic, senderInfo
            );
        }
    }

    private SenderRelationKey key;
    private LongIdKey senderInfoKey;
    private String remark;
    private NotifySetting notifySetting;
    private Topic topic;
    private SenderInfo senderInfo;

    public DispSenderRelation() {
    }

    public DispSenderRelation(
            SenderRelationKey key, LongIdKey senderInfoKey, String remark, NotifySetting notifySetting, Topic topic,
            SenderInfo senderInfo
    ) {
        this.key = key;
        this.senderInfoKey = senderInfoKey;
        this.remark = remark;
        this.notifySetting = notifySetting;
        this.topic = topic;
        this.senderInfo = senderInfo;
    }

    public SenderRelationKey getKey() {
        return key;
    }

    public void setKey(SenderRelationKey key) {
        this.key = key;
    }

    public LongIdKey getSenderInfoKey() {
        return senderInfoKey;
    }

    public void setSenderInfoKey(LongIdKey senderInfoKey) {
        this.senderInfoKey = senderInfoKey;
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

    public SenderInfo getSenderInfo() {
        return senderInfo;
    }

    public void setSenderInfo(SenderInfo senderInfo) {
        this.senderInfo = senderInfo;
    }

    @Override
    public String toString() {
        return "DispSenderRelation{" +
                "key=" + key +
                ", senderInfoKey=" + senderInfoKey +
                ", remark='" + remark + '\'' +
                ", notifySetting=" + notifySetting +
                ", topic=" + topic +
                ", senderInfo=" + senderInfo +
                '}';
    }
}
