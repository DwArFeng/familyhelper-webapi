package com.dwarfeng.familyhelper.webapi.sdk.bean.logicengine.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.logicengine.disp.DispDriverInfo;
import com.dwarfeng.logicengine.sdk.bean.entity.JSFixedFastJsonSection;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * JSFixed FastJson 可展示驱动器信息。
 *
 * @author DwArFeng
 * @since 2.4.0
 */
public class JSFixedFastJsonDispDriverInfo implements Dto {

    private static final long serialVersionUID = 5359299217070951679L;

    public static JSFixedFastJsonDispDriverInfo of(DispDriverInfo dispDriverInfo) {
        if (Objects.isNull(dispDriverInfo)) {
            return null;
        } else {
            return new JSFixedFastJsonDispDriverInfo(
                    JSFixedFastJsonLongIdKey.of(dispDriverInfo.getKey()),
                    JSFixedFastJsonLongIdKey.of(dispDriverInfo.getSectionKey()),
                    dispDriverInfo.isEnabled(),
                    dispDriverInfo.getType(),
                    dispDriverInfo.getParam(),
                    dispDriverInfo.getRemark(),
                    JSFixedFastJsonSection.of(dispDriverInfo.getSection())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "section_key", ordinal = 2)
    private JSFixedFastJsonLongIdKey sectionKey;

    @JSONField(name = "enabled", ordinal = 3)
    private boolean enabled;

    @JSONField(name = "type", ordinal = 4)
    private String type;

    @JSONField(name = "param", ordinal = 5)
    private String param;

    @JSONField(name = "remark", ordinal = 6)
    private String remark;

    @JSONField(name = "section", ordinal = 7)
    private JSFixedFastJsonSection section;

    public JSFixedFastJsonDispDriverInfo() {
    }

    public JSFixedFastJsonDispDriverInfo(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey sectionKey, boolean enabled, String type,
            String param, String remark, JSFixedFastJsonSection section
    ) {
        this.key = key;
        this.sectionKey = sectionKey;
        this.enabled = enabled;
        this.type = type;
        this.param = param;
        this.remark = remark;
        this.section = section;
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

    @Override
    public String toString() {
        return "JSFixedFastJsonDispDriverInfo{" +
                "key=" + key +
                ", sectionKey=" + sectionKey +
                ", enabled=" + enabled +
                ", type='" + type + '\'' +
                ", param='" + param + '\'' +
                ", remark='" + remark + '\'' +
                ", section=" + section +
                '}';
    }
}
