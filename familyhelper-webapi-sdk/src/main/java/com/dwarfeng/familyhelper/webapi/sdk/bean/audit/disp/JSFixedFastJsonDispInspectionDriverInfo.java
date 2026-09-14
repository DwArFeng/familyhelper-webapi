package com.dwarfeng.familyhelper.webapi.sdk.bean.audit.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.sdk.bean.entity.JSFixedFastJsonInspection;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp.DispInspectionDriverInfo;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * JSFixed FastJson 可展示自动审计驱动器信息。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public class JSFixedFastJsonDispInspectionDriverInfo implements Dto {

    private static final long serialVersionUID = 2769243460427615797L;

    public static JSFixedFastJsonDispInspectionDriverInfo of(DispInspectionDriverInfo dispInspectionDriverInfo) {
        if (Objects.isNull(dispInspectionDriverInfo)) {
            return null;
        } else {
            return new JSFixedFastJsonDispInspectionDriverInfo(
                    JSFixedFastJsonLongIdKey.of(dispInspectionDriverInfo.getKey()),
                    JSFixedFastJsonLongIdKey.of(dispInspectionDriverInfo.getInspectionKey()),
                    dispInspectionDriverInfo.isEnabled(),
                    dispInspectionDriverInfo.getType(),
                    dispInspectionDriverInfo.getParam(),
                    dispInspectionDriverInfo.getRemark(),
                    JSFixedFastJsonInspection.of(dispInspectionDriverInfo.getInspection())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "inspection_key", ordinal = 2)
    private JSFixedFastJsonLongIdKey inspectionKey;

    @JSONField(name = "enabled", ordinal = 3)
    private boolean enabled;

    @JSONField(name = "type", ordinal = 4)
    private String type;

    @JSONField(name = "param", ordinal = 5)
    private String param;

    @JSONField(name = "remark", ordinal = 6)
    private String remark;

    @JSONField(name = "inspection", ordinal = 7)
    private JSFixedFastJsonInspection inspection;

    public JSFixedFastJsonDispInspectionDriverInfo() {
    }

    public JSFixedFastJsonDispInspectionDriverInfo(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey inspectionKey, boolean enabled, String type,
            String param, String remark, JSFixedFastJsonInspection inspection
    ) {
        this.key = key;
        this.inspectionKey = inspectionKey;
        this.enabled = enabled;
        this.type = type;
        this.param = param;
        this.remark = remark;
        this.inspection = inspection;
    }

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
        this.key = key;
    }

    public JSFixedFastJsonLongIdKey getInspectionKey() {
        return inspectionKey;
    }

    public void setInspectionKey(JSFixedFastJsonLongIdKey inspectionKey) {
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

    public JSFixedFastJsonInspection getInspection() {
        return inspection;
    }

    public void setInspection(JSFixedFastJsonInspection inspection) {
        this.inspection = inspection;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispInspectionDriverInfo{" +
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
