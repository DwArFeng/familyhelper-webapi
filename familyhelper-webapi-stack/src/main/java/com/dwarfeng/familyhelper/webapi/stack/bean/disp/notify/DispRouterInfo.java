package com.dwarfeng.familyhelper.webapi.stack.bean.disp.notify;

import com.dwarfeng.notify.stack.bean.entity.NotifySetting;
import com.dwarfeng.notify.stack.bean.entity.RouterInfo;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Objects;

/**
 * 可展示路由信息。
 *
 * @author DwArFeng
 * @since 1.0.6
 */
public class DispRouterInfo implements Dto {

    private static final long serialVersionUID = -2640715697953319016L;

    public static DispRouterInfo of(RouterInfo routerInfo, NotifySetting notifySetting) {
        if (Objects.isNull(routerInfo)) {
            return null;
        } else {
            return new DispRouterInfo(
                    routerInfo.getKey(), routerInfo.getNotifySettingKey(), routerInfo.getLabel(), routerInfo.getType(),
                    routerInfo.getParam(), routerInfo.getRemark(), notifySetting
            );
        }
    }

    private LongIdKey key;
    private LongIdKey notifySettingKey;
    private String label;
    private String type;
    private String param;
    private String remark;
    private NotifySetting notifySetting;

    public DispRouterInfo() {
    }

    public DispRouterInfo(
            LongIdKey key, LongIdKey notifySettingKey, String label, String type, String param, String remark,
            NotifySetting notifySetting
    ) {
        this.key = key;
        this.notifySettingKey = notifySettingKey;
        this.label = label;
        this.type = type;
        this.param = param;
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

    public NotifySetting getNotifySetting() {
        return notifySetting;
    }

    public void setNotifySetting(NotifySetting notifySetting) {
        this.notifySetting = notifySetting;
    }

    @Override
    public String toString() {
        return "DispRouterInfo{" +
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
