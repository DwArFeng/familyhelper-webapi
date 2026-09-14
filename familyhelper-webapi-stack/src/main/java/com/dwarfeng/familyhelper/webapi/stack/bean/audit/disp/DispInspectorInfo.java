package com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp;

import com.dwarfeng.audit.stack.bean.entity.Inspection;
import com.dwarfeng.audit.stack.bean.entity.InspectorInfo;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Objects;

/**
 * 可展示审计器信息。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public class DispInspectorInfo implements Dto {

    private static final long serialVersionUID = -1869625695216023813L;

    public static DispInspectorInfo of(InspectorInfo inspectorInfo, Inspection inspection) {
        if (Objects.isNull(inspectorInfo)) {
            return null;
        } else {
            return new DispInspectorInfo(
                    inspectorInfo.getKey(),
                    inspectorInfo.getInspectionKey(),
                    inspectorInfo.getIndex(),
                    inspectorInfo.isEnabled(),
                    inspectorInfo.getType(),
                    inspectorInfo.getParam(),
                    inspectorInfo.getRemark(),
                    inspection
            );
        }
    }

    private LongIdKey key;
    private LongIdKey inspectionKey;
    private int index;
    private boolean enabled;
    private String type;
    private String param;
    private String remark;
    private Inspection inspection;

    public DispInspectorInfo() {
    }

    public DispInspectorInfo(
            LongIdKey key, LongIdKey inspectionKey, int index, boolean enabled, String type, String param,
            String remark, Inspection inspection
    ) {
        this.key = key;
        this.inspectionKey = inspectionKey;
        this.index = index;
        this.enabled = enabled;
        this.type = type;
        this.param = param;
        this.remark = remark;
        this.inspection = inspection;
    }

    public LongIdKey getKey() {
        return key;
    }

    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public LongIdKey getInspectionKey() {
        return inspectionKey;
    }

    public void setInspectionKey(LongIdKey inspectionKey) {
        this.inspectionKey = inspectionKey;
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

    public Inspection getInspection() {
        return inspection;
    }

    public void setInspection(Inspection inspection) {
        this.inspection = inspection;
    }

    @Override
    public String toString() {
        return "DispInspectorInfo{" +
                "key=" + key +
                ", inspectionKey=" + inspectionKey +
                ", index=" + index +
                ", enabled=" + enabled +
                ", type='" + type + '\'' +
                ", param='" + param + '\'' +
                ", remark='" + remark + '\'' +
                ", inspection=" + inspection +
                '}';
    }
}
