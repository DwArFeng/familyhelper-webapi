package com.dwarfeng.familyhelper.webapi.stack.bean.logicengine.disp;

import com.dwarfeng.logicengine.stack.bean.entity.PerformerInfo;
import com.dwarfeng.logicengine.stack.bean.entity.Section;
import com.dwarfeng.logicengine.stack.bean.entity.State;
import com.dwarfeng.logicengine.stack.bean.key.StateKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Objects;

/**
 * 可展示执行器信息。
 *
 * @author DwArFeng
 * @since 2.4.0
 */
public class DispPerformerInfo implements Dto {

    private static final long serialVersionUID = -4231149245190380958L;

    public static DispPerformerInfo of(
            PerformerInfo performerInfo, Section section, State anchorState, State targetState
    ) {
        if (Objects.isNull(performerInfo)) {
            return null;
        } else {
            return new DispPerformerInfo(
                    performerInfo.getKey(),
                    performerInfo.getSectionKey(),
                    performerInfo.getAnchorStateKey(),
                    performerInfo.getTargetStateKey(),
                    performerInfo.getIndex(),
                    performerInfo.isEnabled(),
                    performerInfo.getType(),
                    performerInfo.getParam(),
                    performerInfo.getRemark(),
                    section,
                    anchorState,
                    targetState
            );
        }
    }

    private LongIdKey key;
    private LongIdKey sectionKey;
    private StateKey anchorStateKey;
    private StateKey targetStateKey;
    private int index;
    private boolean enabled;
    private String type;
    private String param;
    private String remark;
    private Section section;
    private State anchorState;
    private State targetState;

    public DispPerformerInfo() {
    }

    public DispPerformerInfo(
            LongIdKey key, LongIdKey sectionKey, StateKey anchorStateKey, StateKey targetStateKey, int index,
            boolean enabled, String type, String param, String remark, Section section, State anchorState,
            State targetState
    ) {
        this.key = key;
        this.sectionKey = sectionKey;
        this.anchorStateKey = anchorStateKey;
        this.targetStateKey = targetStateKey;
        this.index = index;
        this.enabled = enabled;
        this.type = type;
        this.param = param;
        this.remark = remark;
        this.section = section;
        this.anchorState = anchorState;
        this.targetState = targetState;
    }

    public LongIdKey getKey() {
        return key;
    }

    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public LongIdKey getSectionKey() {
        return sectionKey;
    }

    public void setSectionKey(LongIdKey sectionKey) {
        this.sectionKey = sectionKey;
    }

    public StateKey getAnchorStateKey() {
        return anchorStateKey;
    }

    public void setAnchorStateKey(StateKey anchorStateKey) {
        this.anchorStateKey = anchorStateKey;
    }

    public StateKey getTargetStateKey() {
        return targetStateKey;
    }

    public void setTargetStateKey(StateKey targetStateKey) {
        this.targetStateKey = targetStateKey;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public State getAnchorState() {
        return anchorState;
    }

    public void setAnchorState(State anchorState) {
        this.anchorState = anchorState;
    }

    public State getTargetState() {
        return targetState;
    }

    public void setTargetState(State targetState) {
        this.targetState = targetState;
    }

    @Override
    public String toString() {
        return "DispPerformerInfo{" +
                "key=" + key +
                ", sectionKey=" + sectionKey +
                ", anchorStateKey=" + anchorStateKey +
                ", targetStateKey=" + targetStateKey +
                ", index=" + index +
                ", enabled=" + enabled +
                ", type='" + type + '\'' +
                ", param='" + param + '\'' +
                ", remark='" + remark + '\'' +
                ", section=" + section +
                ", anchorState=" + anchorState +
                ", targetState=" + targetState +
                '}';
    }
}
