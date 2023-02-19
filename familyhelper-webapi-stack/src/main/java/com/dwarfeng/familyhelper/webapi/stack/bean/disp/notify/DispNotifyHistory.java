package com.dwarfeng.familyhelper.webapi.stack.bean.disp.notify;

import com.dwarfeng.notify.stack.bean.entity.NotifyHistory;
import com.dwarfeng.notify.stack.bean.entity.NotifySetting;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;
import java.util.Objects;

/**
 * 可展示通知历史。
 *
 * @author DwArFeng
 * @since 1.0.9
 */
public class DispNotifyHistory implements Dto {

    private static final long serialVersionUID = -5683391897428696425L;

    public static DispNotifyHistory of(NotifyHistory notifyHistory, NotifySetting notifySetting) {
        if (Objects.isNull(notifyHistory)) {
            return null;
        } else {
            return new DispNotifyHistory(
                    notifyHistory.getKey(), notifyHistory.getNotifySettingKey(), notifyHistory.getHappenedDate(),
                    notifyHistory.getRemark(),
                    notifySetting
            );
        }
    }

    private LongIdKey key;
    private LongIdKey notifySettingKey;
    private Date happenedDate;
    private String remark;
    private NotifySetting notifySetting;

    public DispNotifyHistory() {
    }

    public DispNotifyHistory(
            LongIdKey key, LongIdKey notifySettingKey, Date happenedDate, String remark, NotifySetting notifySetting
    ) {
        this.key = key;
        this.notifySettingKey = notifySettingKey;
        this.happenedDate = happenedDate;
        this.remark = remark;
        this.notifySetting = notifySetting;
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

    public Date getHappenedDate() {
        return happenedDate;
    }

    public void setHappenedDate(Date happenedDate) {
        this.happenedDate = happenedDate;
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

    @Override
    public String toString() {
        return "DispNotifyHistory{" +
                "key=" + key +
                ", notifySettingKey=" + notifySettingKey +
                ", happenedDate=" + happenedDate +
                ", remark='" + remark + '\'' +
                ", notifySetting=" + notifySetting +
                '}';
    }
}
