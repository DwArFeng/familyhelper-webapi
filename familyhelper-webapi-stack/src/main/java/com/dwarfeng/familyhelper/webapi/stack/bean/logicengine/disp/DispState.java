package com.dwarfeng.familyhelper.webapi.stack.bean.logicengine.disp;

import com.dwarfeng.logicengine.stack.bean.entity.Section;
import com.dwarfeng.logicengine.stack.bean.entity.State;
import com.dwarfeng.logicengine.stack.bean.key.StateKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * 可展示状态。
 *
 * @author DwArFeng
 * @since 2.4.0
 */
public class DispState implements Dto {

    private static final long serialVersionUID = -7209337853836856408L;

    public static DispState of(State state, Section section) {
        if (Objects.isNull(state)) {
            return null;
        } else {
            return new DispState(
                    state.getKey(),
                    state.getName(),
                    state.getType(),
                    state.getFirstSpinDelay(),
                    state.getSpinInterval(),
                    state.getRemark(),
                    section
            );
        }
    }

    private StateKey key;
    private String name;
    private int type;
    private long firstSpinDelay;
    private long spinInterval;
    private String remark;
    private Section section;

    public DispState() {
    }

    public DispState(
            StateKey key, String name, int type, long firstSpinDelay, long spinInterval, String remark,
            Section section
    ) {
        this.key = key;
        this.name = name;
        this.type = type;
        this.firstSpinDelay = firstSpinDelay;
        this.spinInterval = spinInterval;
        this.remark = remark;
        this.section = section;
    }

    public StateKey getKey() {
        return key;
    }

    public void setKey(StateKey key) {
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

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    @Override
    public String toString() {
        return "DispState{" +
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
