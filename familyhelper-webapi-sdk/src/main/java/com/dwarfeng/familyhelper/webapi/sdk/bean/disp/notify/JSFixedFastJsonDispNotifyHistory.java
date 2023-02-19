package com.dwarfeng.familyhelper.webapi.sdk.bean.disp.notify;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.notify.DispNotifyHistory;
import com.dwarfeng.notify.sdk.bean.entity.JSFixedFastJsonNotifySetting;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 可展示通知历史。
 *
 * @author DwArFeng
 * @since 1.0.9
 */
public class JSFixedFastJsonDispNotifyHistory implements Dto {

    private static final long serialVersionUID = -1961288200614749403L;

    public static JSFixedFastJsonDispNotifyHistory of(DispNotifyHistory dispNotifyHistory) {
        if (Objects.isNull(dispNotifyHistory)) {
            return null;
        } else {
            return new JSFixedFastJsonDispNotifyHistory(
                    JSFixedFastJsonLongIdKey.of(dispNotifyHistory.getKey()),
                    JSFixedFastJsonLongIdKey.of(dispNotifyHistory.getNotifySettingKey()),
                    dispNotifyHistory.getHappenedDate(), dispNotifyHistory.getRemark(),
                    JSFixedFastJsonNotifySetting.of(dispNotifyHistory.getNotifySetting())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "notify_setting_key", ordinal = 2)
    private JSFixedFastJsonLongIdKey notifySettingKey;

    @JSONField(name = "happened_date", ordinal = 3)
    private Date happenedDate;

    @JSONField(name = "remark", ordinal = 4)
    private String remark;

    @JSONField(name = "notify_setting", ordinal = 5)
    private JSFixedFastJsonNotifySetting notifySetting;

    public JSFixedFastJsonDispNotifyHistory() {
    }

    public JSFixedFastJsonDispNotifyHistory(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey notifySettingKey, Date happenedDate, String remark,
            JSFixedFastJsonNotifySetting notifySetting
    ) {
        this.key = key;
        this.notifySettingKey = notifySettingKey;
        this.happenedDate = happenedDate;
        this.remark = remark;
        this.notifySetting = notifySetting;
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

    public JSFixedFastJsonNotifySetting getNotifySetting() {
        return notifySetting;
    }

    public void setNotifySetting(JSFixedFastJsonNotifySetting notifySetting) {
        this.notifySetting = notifySetting;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispNotifyHistory{" +
                "key=" + key +
                ", notifySettingKey=" + notifySettingKey +
                ", happenedDate=" + happenedDate +
                ", remark='" + remark + '\'' +
                ", notifySetting=" + notifySetting +
                '}';
    }
}
