package com.dwarfeng.familyhelper.webapi.sdk.bean.logicengine.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.dwarfeng.familyhelper.webapi.stack.bean.logicengine.disp.DispState;
import com.dwarfeng.logicengine.sdk.bean.entity.JSFixedFastJsonSection;
import com.dwarfeng.logicengine.sdk.bean.key.JSFixedFastJsonStateKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * JSFixed FastJson 可展示状态。
 *
 * @author DwArFeng
 * @since 2.4.0
 */
public class JSFixedFastJsonDispState implements Dto {

    private static final long serialVersionUID = -24914111571820150L;

    public static JSFixedFastJsonDispState of(DispState dispState) {
        if (Objects.isNull(dispState)) {
            return null;
        } else {
            return new JSFixedFastJsonDispState(
                    JSFixedFastJsonStateKey.of(dispState.getKey()),
                    dispState.getName(),
                    dispState.getType(),
                    dispState.getFirstSpinDelay(),
                    dispState.getSpinInterval(),
                    dispState.getRemark(),
                    JSFixedFastJsonSection.of(dispState.getSection())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonStateKey key;

    @JSONField(name = "name", ordinal = 2)
    private String name;

    @JSONField(name = "type", ordinal = 3)
    private int type;

    @JSONField(name = "first_spin_delay", ordinal = 4, serializeUsing = ToStringSerializer.class)
    private long firstSpinDelay;

    @JSONField(name = "spin_interval", ordinal = 5, serializeUsing = ToStringSerializer.class)
    private long spinInterval;

    @JSONField(name = "remark", ordinal = 6)
    private String remark;

    @JSONField(name = "section", ordinal = 7)
    private JSFixedFastJsonSection section;

    public JSFixedFastJsonDispState() {
    }

    public JSFixedFastJsonDispState(
            JSFixedFastJsonStateKey key, String name, int type, long firstSpinDelay, long spinInterval, String remark,
            JSFixedFastJsonSection section
    ) {
        this.key = key;
        this.name = name;
        this.type = type;
        this.firstSpinDelay = firstSpinDelay;
        this.spinInterval = spinInterval;
        this.remark = remark;
        this.section = section;
    }

    public JSFixedFastJsonStateKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonStateKey key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getFirstSpinDelay() {
        return firstSpinDelay;
    }

    public void setFirstSpinDelay(long firstSpinDelay) {
        this.firstSpinDelay = firstSpinDelay;
    }

    public long getSpinInterval() {
        return spinInterval;
    }

    public void setSpinInterval(long spinInterval) {
        this.spinInterval = spinInterval;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public JSFixedFastJsonSection getSection() {
        return section;
    }

    public void setSection(JSFixedFastJsonSection section) {
        this.section = section;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispState{" +
                "key=" + key +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", firstSpinDelay=" + firstSpinDelay +
                ", spinInterval=" + spinInterval +
                ", remark='" + remark + '\'' +
                ", section=" + section +
                '}';
    }
}
