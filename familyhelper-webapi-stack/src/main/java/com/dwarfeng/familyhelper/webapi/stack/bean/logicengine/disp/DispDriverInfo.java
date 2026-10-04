package com.dwarfeng.familyhelper.webapi.stack.bean.logicengine.disp;

import com.dwarfeng.logicengine.stack.bean.entity.DriverInfo;
import com.dwarfeng.logicengine.stack.bean.entity.Section;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Objects;

/**
 * 可展示驱动器信息。
 *
 * @author DwArFeng
 * @since 2.4.0
 */
public class DispDriverInfo implements Dto {

    private static final long serialVersionUID = -8394724980943749677L;

    public static DispDriverInfo of(DriverInfo driverInfo, Section section) {
        if (Objects.isNull(driverInfo)) {
            return null;
        } else {
            return new DispDriverInfo(
                    driverInfo.getKey(),
                    driverInfo.getSectionKey(),
                    driverInfo.isEnabled(),
                    driverInfo.getType(),
                    driverInfo.getParam(),
                    driverInfo.getRemark(),
                    section
            );
        }
    }

    private LongIdKey key;
    private LongIdKey sectionKey;
    private boolean enabled;
    private String type;
    private String param;
    private String remark;
    private Section section;

    public DispDriverInfo() {
    }

    public DispDriverInfo(
            LongIdKey key, LongIdKey sectionKey, boolean enabled, String type, String param, String remark,
            Section section
    ) {
        this.key = key;
        this.sectionKey = sectionKey;
        this.enabled = enabled;
        this.type = type;
        this.param = param;
        this.remark = remark;
        this.section = section;
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

    @Override
    public String toString() {
        return "DispDriverInfo{" +
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
