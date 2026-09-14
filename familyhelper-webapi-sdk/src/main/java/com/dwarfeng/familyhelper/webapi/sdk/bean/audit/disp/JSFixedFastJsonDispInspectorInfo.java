package com.dwarfeng.familyhelper.webapi.sdk.bean.audit.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.sdk.bean.entity.JSFixedFastJsonInspection;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp.DispInspectorInfo;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * JSFixed FastJson 可展示审计器信息。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public class JSFixedFastJsonDispInspectorInfo implements Dto {

    private static final long serialVersionUID = 7649363819339343529L;

    public static JSFixedFastJsonDispInspectorInfo of(DispInspectorInfo dispInspectorInfo) {
        if (Objects.isNull(dispInspectorInfo)) {
            return null;
        } else {
            return new JSFixedFastJsonDispInspectorInfo(
                    JSFixedFastJsonLongIdKey.of(dispInspectorInfo.getKey()),
                    JSFixedFastJsonLongIdKey.of(dispInspectorInfo.getInspectionKey()),
                    dispInspectorInfo.getIndex(),
                    dispInspectorInfo.isEnabled(),
                    dispInspectorInfo.getType(),
                    dispInspectorInfo.getParam(),
                    dispInspectorInfo.getRemark(),
                    JSFixedFastJsonInspection.of(dispInspectorInfo.getInspection())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "inspection_key", ordinal = 2)
    private JSFixedFastJsonLongIdKey inspectionKey;

    @JSONField(name = "index", ordinal = 3)
    private int index;

    @JSONField(name = "enabled", ordinal = 4)
    private boolean enabled;

    @JSONField(name = "type", ordinal = 5)
    private String type;

    @JSONField(name = "param", ordinal = 6)
    private String param;

    @JSONField(name = "remark", ordinal = 7)
    private String remark;

    @JSONField(name = "inspection", ordinal = 8)
    private JSFixedFastJsonInspection inspection;

    public JSFixedFastJsonDispInspectorInfo() {
    }

    public JSFixedFastJsonDispInspectorInfo(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey inspectionKey, int index, boolean enabled,
            String type, String param, String remark, JSFixedFastJsonInspection inspection
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

    public JSFixedFastJsonInspection getInspection() {
        return inspection;
    }

    public void setInspection(JSFixedFastJsonInspection inspection) {
        this.inspection = inspection;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispInspectorInfo{" +
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
