package com.dwarfeng.familyhelper.webapi.sdk.bean.audit.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.dwarfeng.audit.sdk.bean.entity.JSFixedFastJsonInspectorInfo;
import com.dwarfeng.audit.sdk.bean.key.JSFixedFastJsonInspectorVariableKey;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp.DispInspectorVariable;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 可展示审计器变量。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public class JSFixedFastJsonDispInspectorVariable implements Dto {

    private static final long serialVersionUID = -6522309931668574475L;

    public static JSFixedFastJsonDispInspectorVariable of(DispInspectorVariable dispInspectorVariable) {
        if (Objects.isNull(dispInspectorVariable)) {
            return null;
        } else {
            return new JSFixedFastJsonDispInspectorVariable(
                    JSFixedFastJsonInspectorVariableKey.of(dispInspectorVariable.getKey()),
                    dispInspectorVariable.getValueType(),
                    dispInspectorVariable.getStringValue(),
                    dispInspectorVariable.getLongValue(),
                    dispInspectorVariable.getDoubleValue(),
                    dispInspectorVariable.getBooleanValue(),
                    dispInspectorVariable.getDateValue(),
                    JSFixedFastJsonInspectorInfo.of(dispInspectorVariable.getInspectorInfo())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonInspectorVariableKey key;

    @JSONField(name = "value_type", ordinal = 2)
    private int valueType;

    @JSONField(name = "string_value", ordinal = 3)
    private String stringValue;

    @JSONField(name = "long_value", ordinal = 4, serializeUsing = ToStringSerializer.class)
    private Long longValue;

    @JSONField(name = "double_value", ordinal = 5)
    private Double doubleValue;

    @JSONField(name = "boolean_value", ordinal = 6)
    private Boolean booleanValue;

    @JSONField(name = "date_value", ordinal = 7)
    private Date dateValue;

    @JSONField(name = "inspector_info", ordinal = 8)
    private JSFixedFastJsonInspectorInfo inspectorInfo;

    public JSFixedFastJsonDispInspectorVariable() {
    }

    public JSFixedFastJsonDispInspectorVariable(
            JSFixedFastJsonInspectorVariableKey key, int valueType, String stringValue, Long longValue,
            Double doubleValue, Boolean booleanValue, Date dateValue, JSFixedFastJsonInspectorInfo inspectorInfo
    ) {
        this.key = key;
        this.valueType = valueType;
        this.stringValue = stringValue;
        this.longValue = longValue;
        this.doubleValue = doubleValue;
        this.booleanValue = booleanValue;
        this.dateValue = dateValue;
        this.inspectorInfo = inspectorInfo;
    }

    public JSFixedFastJsonInspectorVariableKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonInspectorVariableKey key) {
        this.key = key;
    }

    public int getValueType() {
        return valueType;
    }

    public void setValueType(int valueType) {
        this.valueType = valueType;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public Long getLongValue() {
        return longValue;
    }

    public void setLongValue(Long longValue) {
        this.longValue = longValue;
    }

    public Double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(Double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public Boolean getBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(Boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public Date getDateValue() {
        return dateValue;
    }

    public void setDateValue(Date dateValue) {
        this.dateValue = dateValue;
    }

    public JSFixedFastJsonInspectorInfo getInspectorInfo() {
        return inspectorInfo;
    }

    public void setInspectorInfo(JSFixedFastJsonInspectorInfo inspectorInfo) {
        this.inspectorInfo = inspectorInfo;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispInspectorVariable{" +
                "key=" + key +
                ", valueType=" + valueType +
                ", stringValue='" + stringValue + '\'' +
                ", longValue=" + longValue +
                ", doubleValue=" + doubleValue +
                ", booleanValue=" + booleanValue +
                ", dateValue=" + dateValue +
                ", inspectorInfo=" + inspectorInfo +
                '}';
    }
}
