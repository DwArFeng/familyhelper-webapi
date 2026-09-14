package com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp;

import com.dwarfeng.audit.stack.bean.entity.InspectorInfo;
import com.dwarfeng.audit.stack.bean.entity.InspectorVariable;
import com.dwarfeng.audit.stack.bean.key.InspectorVariableKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * 可展示审计器变量。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public class DispInspectorVariable implements Dto {

    private static final long serialVersionUID = 7333936176226964416L;

    public static DispInspectorVariable of(InspectorVariable inspectorVariable, InspectorInfo inspectorInfo) {
        if (Objects.isNull(inspectorVariable)) {
            return null;
        } else {
            return new DispInspectorVariable(
                    inspectorVariable.getKey(),
                    inspectorVariable.getValueType(),
                    inspectorVariable.getStringValue(),
                    inspectorVariable.getLongValue(),
                    inspectorVariable.getDoubleValue(),
                    inspectorVariable.getBooleanValue(),
                    inspectorVariable.getDateValue(),
                    inspectorInfo
            );
        }
    }

    private InspectorVariableKey key;
    private int valueType;
    private String stringValue;
    private Long longValue;
    private Double doubleValue;
    private Boolean booleanValue;
    private Date dateValue;
    private InspectorInfo inspectorInfo;

    public DispInspectorVariable() {
    }

    public DispInspectorVariable(
            InspectorVariableKey key, int valueType, String stringValue, Long longValue, Double doubleValue,
            Boolean booleanValue, Date dateValue, InspectorInfo inspectorInfo
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

    public InspectorVariableKey getKey() {
        return key;
    }

    public void setKey(InspectorVariableKey key) {
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

    public InspectorInfo getInspectorInfo() {
        return inspectorInfo;
    }

    public void setInspectorInfo(InspectorInfo inspectorInfo) {
        this.inspectorInfo = inspectorInfo;
    }

    @Override
    public String toString() {
        return "DispInspectorVariable{" +
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
