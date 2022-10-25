package com.dwarfeng.familyhelper.webapi.sdk.bean.disp.notify;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.notify.DispRouterInfo;
import com.dwarfeng.notify.sdk.bean.entity.JSFixedFastJsonNotifySetting;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * JSFixed FastJson 可展示路由信息。
 *
 * @author DwArFeng
 * @since 1.0.6
 */
public class JSFixedFastJsonDispRouterInfo implements Dto {

    private static final long serialVersionUID = 6379121149348012743L;

    public static JSFixedFastJsonDispRouterInfo of(DispRouterInfo dispRouterInfo) {
        if (Objects.isNull(dispRouterInfo)) {
            return null;
        } else {
            return new JSFixedFastJsonDispRouterInfo(
                    JSFixedFastJsonLongIdKey.of(dispRouterInfo.getKey()),
                    JSFixedFastJsonLongIdKey.of(dispRouterInfo.getNotifySettingKey()),
                    dispRouterInfo.getLabel(), dispRouterInfo.getType(), dispRouterInfo.getParam(),
                    dispRouterInfo.getRemark(),
                    JSFixedFastJsonNotifySetting.of(dispRouterInfo.getNotifySetting())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "notify_setting_key", ordinal = 2)
    private JSFixedFastJsonLongIdKey notifySettingKey;

    @JSONField(name = "label", ordinal = 3)
    private String label;

    @JSONField(name = "type", ordinal = 4)
    private String type;

    @JSONField(name = "param", ordinal = 5)
    private String param;

    @JSONField(name = "remark", ordinal = 6)
    private String remark;

    @JSONField(name = "notify_setting", ordinal = 7)
    private JSFixedFastJsonNotifySetting notifySetting;

    public JSFixedFastJsonDispRouterInfo() {
    }

    public JSFixedFastJsonDispRouterInfo(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey notifySettingKey, String label, String type,
            String param, String remark, JSFixedFastJsonNotifySetting notifySetting
    ) {
        this.key = key;
        this.notifySettingKey = notifySettingKey;
        this.label = label;
        this.type = type;
        this.param = param;
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
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
        return "JSFixedFastJsonDispRouterInfo{" +
                "key=" + key +
                ", notifySettingKey=" + notifySettingKey +
                ", label='" + label + '\'' +
                ", type='" + type + '\'' +
                ", param='" + param + '\'' +
                ", remark='" + remark + '\'' +
                ", notifySetting=" + notifySetting +
                '}';
    }
}
