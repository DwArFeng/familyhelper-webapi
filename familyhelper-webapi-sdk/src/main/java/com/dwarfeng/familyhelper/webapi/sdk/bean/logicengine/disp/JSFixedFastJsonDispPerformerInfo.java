package com.dwarfeng.familyhelper.webapi.sdk.bean.logicengine.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.logicengine.disp.DispPerformerInfo;
import com.dwarfeng.logicengine.sdk.bean.entity.JSFixedFastJsonSection;
import com.dwarfeng.logicengine.sdk.bean.entity.JSFixedFastJsonState;
import com.dwarfeng.logicengine.sdk.bean.key.JSFixedFastJsonStateKey;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * JSFixed FastJson 可展示执行器信息。
 *
 * @author DwArFeng
 * @since 2.4.0
 */
public class JSFixedFastJsonDispPerformerInfo implements Dto {

    private static final long serialVersionUID = -3742696738702165076L;

    public static JSFixedFastJsonDispPerformerInfo of(DispPerformerInfo dispPerformerInfo) {
        if (Objects.isNull(dispPerformerInfo)) {
            return null;
        } else {
            return new JSFixedFastJsonDispPerformerInfo(
                    JSFixedFastJsonLongIdKey.of(dispPerformerInfo.getKey()),
                    JSFixedFastJsonLongIdKey.of(dispPerformerInfo.getSectionKey()),
                    JSFixedFastJsonStateKey.of(dispPerformerInfo.getAnchorStateKey()),
                    JSFixedFastJsonStateKey.of(dispPerformerInfo.getTargetStateKey()),
                    dispPerformerInfo.getIndex(),
                    dispPerformerInfo.isEnabled(),
                    dispPerformerInfo.getType(),
                    dispPerformerInfo.getParam(),
                    dispPerformerInfo.getRemark(),
                    JSFixedFastJsonSection.of(dispPerformerInfo.getSection()),
                    JSFixedFastJsonState.of(dispPerformerInfo.getAnchorState()),
                    JSFixedFastJsonState.of(dispPerformerInfo.getTargetState())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "section_key", ordinal = 2)
    private JSFixedFastJsonLongIdKey sectionKey;

    @JSONField(name = "anchor_state_key", ordinal = 3)
    private JSFixedFastJsonStateKey anchorStateKey;

    @JSONField(name = "target_state_key", ordinal = 4)
    private JSFixedFastJsonStateKey targetStateKey;

    @JSONField(name = "index", ordinal = 5)
    private int index;

    @JSONField(name = "enabled", ordinal = 6)
    private boolean enabled;

    @JSONField(name = "type", ordinal = 7)
    private String type;

    @JSONField(name = "param", ordinal = 8)
    private String param;

    @JSONField(name = "remark", ordinal = 9)
    private String remark;

    @JSONField(name = "section", ordinal = 10)
    private JSFixedFastJsonSection section;

    @JSONField(name = "anchor_state", ordinal = 11)
    private JSFixedFastJsonState anchorState;

    @JSONField(name = "target_state", ordinal = 12)
    private JSFixedFastJsonState targetState;

    public JSFixedFastJsonDispPerformerInfo() {
    }

    public JSFixedFastJsonDispPerformerInfo(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey sectionKey, JSFixedFastJsonStateKey anchorStateKey,
            JSFixedFastJsonStateKey targetStateKey, int index, boolean enabled, String type, String param,
            String remark, JSFixedFastJsonSection section, JSFixedFastJsonState anchorState,
            JSFixedFastJsonState targetState
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

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
        this.key = key;
    }

    public JSFixedFastJsonLongIdKey getSectionKey() {
        return sectionKey;
    }

    public void setSectionKey(JSFixedFastJsonLongIdKey sectionKey) {
        this.sectionKey = sectionKey;
    }

    public JSFixedFastJsonStateKey getAnchorStateKey() {
        return anchorStateKey;
    }

    public void setAnchorStateKey(JSFixedFastJsonStateKey anchorStateKey) {
        this.anchorStateKey = anchorStateKey;
    }

    public JSFixedFastJsonStateKey getTargetStateKey() {
        return targetStateKey;
    }

    public void setTargetStateKey(JSFixedFastJsonStateKey targetStateKey) {
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

    public JSFixedFastJsonSection getSection() {
        return section;
    }

    public void setSection(JSFixedFastJsonSection section) {
        this.section = section;
    }

    public JSFixedFastJsonState getAnchorState() {
        return anchorState;
    }

    public void setAnchorState(JSFixedFastJsonState anchorState) {
        this.anchorState = anchorState;
    }

    public JSFixedFastJsonState getTargetState() {
        return targetState;
    }

    public void setTargetState(JSFixedFastJsonState targetState) {
        this.targetState = targetState;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispPerformerInfo{" +
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
