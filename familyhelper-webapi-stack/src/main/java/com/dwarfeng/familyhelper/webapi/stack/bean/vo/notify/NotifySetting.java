package com.dwarfeng.familyhelper.webapi.stack.bean.vo.notify;

import com.dwarfeng.subgrade.stack.bean.Bean;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 通知设置。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public class NotifySetting implements Bean {

    private static final long serialVersionUID = -5295395196173119110L;

    private LongIdKey key;
    private String label;
    private boolean enabled;
    private String requiredPermission;
    private String remark;

    public NotifySetting() {
    }

    public NotifySetting(LongIdKey key, String label, boolean enabled, String requiredPermission, String remark) {
        this.key = key;
        this.label = label;
        this.enabled = enabled;
        this.requiredPermission = requiredPermission;
        this.remark = remark;
    }

    public LongIdKey getKey() {
        return key;
    }

    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRequiredPermission() {
        return requiredPermission;
    }

    public void setRequiredPermission(String requiredPermission) {
        this.requiredPermission = requiredPermission;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "NotifySetting{" +
                "key=" + key +
                ", label='" + label + '\'' +
                ", enabled=" + enabled +
                ", requiredPermission='" + requiredPermission + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
