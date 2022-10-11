package com.dwarfeng.familyhelper.webapi.sdk.bean.vo.notify;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.vo.notify.Topic;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * FastJson 主题。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public class FastJsonTopic implements Bean {

    private static final long serialVersionUID = -5199468287168000582L;

    public static FastJsonTopic of(Topic topic) {
        if (Objects.isNull(topic)) {
            return null;
        } else {
            return new FastJsonTopic(
                    FastJsonStringIdKey.of(topic.getKey()),
                    topic.getLabel(), topic.isEnabled(), topic.getPriority(), topic.isPreferred(),
                    topic.getCoolDownDuration(), topic.getExecutorType(), topic.getExecutorParam(), topic.getRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonStringIdKey key;

    @JSONField(name = "label", ordinal = 2)
    private String label;

    @JSONField(name = "enabled", ordinal = 3)
    private boolean enabled;

    @JSONField(name = "priority", ordinal = 4)
    private int priority;

    @JSONField(name = "preferred", ordinal = 5)
    private boolean preferred;

    @JSONField(name = "cool_down_duration", ordinal = 6)
    private long coolDownDuration;

    @JSONField(name = "executor_type", ordinal = 7)
    private String executorType;

    @JSONField(name = "executor_param", ordinal = 8)
    private String executorParam;

    @JSONField(name = "remark", ordinal = 9)
    private String remark;

    public FastJsonTopic() {
    }

    public FastJsonTopic(
            FastJsonStringIdKey key, String label, boolean enabled, int priority, boolean preferred,
            long coolDownDuration, String executorType, String executorParam, String remark
    ) {
        this.key = key;
        this.label = label;
        this.enabled = enabled;
        this.priority = priority;
        this.preferred = preferred;
        this.coolDownDuration = coolDownDuration;
        this.executorType = executorType;
        this.executorParam = executorParam;
        this.remark = remark;
    }

    public FastJsonStringIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonStringIdKey key) {
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

    public String getExecutorType() {
        return executorType;
    }

    public void setExecutorType(String executorType) {
        this.executorType = executorType;
    }

    public String getExecutorParam() {
        return executorParam;
    }

    public void setExecutorParam(String executorParam) {
        this.executorParam = executorParam;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "FastJsonTopic{" +
                "key=" + key +
                ", label='" + label + '\'' +
                ", enabled=" + enabled +
                ", priority=" + priority +
                ", preferred=" + preferred +
                ", coolDownDuration=" + coolDownDuration +
                ", executorType='" + executorType + '\'' +
                ", executorParam='" + executorParam + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
