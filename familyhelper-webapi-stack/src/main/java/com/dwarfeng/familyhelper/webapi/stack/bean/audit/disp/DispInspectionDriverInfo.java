package com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp;

import com.dwarfeng.audit.stack.bean.entity.Inspection;
import com.dwarfeng.audit.stack.bean.entity.InspectionDriverInfo;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Objects;

/**
 * 可展示自动审计驱动器信息。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public class DispInspectionDriverInfo implements Dto {

    private static final long serialVersionUID = -4202733400760865101L;

    public static DispInspectionDriverInfo of(InspectionDriverInfo inspectionDriverInfo, Inspection inspection) {
        if (Objects.isNull(inspectionDriverInfo)) {
            return null;
        } else {
            return new DispInspectionDriverInfo(
                    inspectionDriverInfo.getKey(),
                    inspectionDriverInfo.getInspectionKey(),
                    inspectionDriverInfo.isEnabled(),
                    inspectionDriverInfo.getType(),
                    inspectionDriverInfo.getParam(),
                    inspectionDriverInfo.getRemark(),
                    inspection
            );
        }
    }

    private LongIdKey key;
    private LongIdKey inspectionKey;
    private boolean enabled;
    private String type;
    private String param;
    private String remark;
    private Inspection inspection;

    public DispInspectionDriverInfo() {
    }

    public DispInspectionDriverInfo(
            LongIdKey key, LongIdKey inspectionKey, boolean enabled, String type, String param, String remark,
            Inspection inspection
    ) {
        this.key = key;
        this.inspectionKey = inspectionKey;
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
        return "DispInspectionDriverInfo{" +
                "key=" + key +
                ", inspectionKey=" + inspectionKey +
                ", enabled=" + enabled +
                ", type='" + type + '\'' +
                ", param='" + param + '\'' +
                ", remark='" + remark + '\'' +
                ", inspection=" + inspection +
                '}';
    }
}
