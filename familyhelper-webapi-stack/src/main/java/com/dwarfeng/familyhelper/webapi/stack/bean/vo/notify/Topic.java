package com.dwarfeng.familyhelper.webapi.stack.bean.vo.notify;

import com.dwarfeng.subgrade.stack.bean.Bean;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

/**
 * 主题。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public class Topic implements Bean {

    private static final long serialVersionUID = -2139226997940758732L;

    private StringIdKey key;
    private String label;
    private boolean enabled;
    private int priority;
    private boolean preferred;
    private long coolDownDuration;
    private String remark;

    public Topic() {
    }

    public Topic(
            StringIdKey key, String label, boolean enabled, int priority, boolean preferred, long coolDownDuration,
            String remark
    ) {
        this.key = key;
        this.label = label;
        this.enabled = enabled;
        this.priority = priority;
        this.preferred = preferred;
        this.coolDownDuration = coolDownDuration;
        this.remark = remark;
    }

    public StringIdKey getKey() {
        return key;
    }

    public void setKey(StringIdKey key) {
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isPreferred() {
        return preferred;
    }

    public void setPreferred(boolean preferred) {
        this.preferred = preferred;
    }

    public long getCoolDownDuration() {
        return coolDownDuration;
    }

    public void setCoolDownDuration(long coolDownDuration) {
        this.coolDownDuration = coolDownDuration;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "key=" + key +
                ", label='" + label + '\'' +
                ", enabled=" + enabled +
                ", priority=" + priority +
                ", preferred=" + preferred +
                ", coolDownDuration=" + coolDownDuration +
                ", remark='" + remark + '\'' +
                '}';
    }
}
