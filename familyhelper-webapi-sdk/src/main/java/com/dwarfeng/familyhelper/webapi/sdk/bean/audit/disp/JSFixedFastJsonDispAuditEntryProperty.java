package com.dwarfeng.familyhelper.webapi.sdk.bean.audit.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.dwarfeng.audit.sdk.bean.entity.JSFixedFastJsonAuditEntry;
import com.dwarfeng.audit.sdk.bean.entity.JSFixedFastJsonAuditPropertyIndicator;
import com.dwarfeng.audit.sdk.bean.key.JSFixedFastJsonAuditEntryPropertyKey;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp.DispAuditEntryProperty;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 可展示审计条目属性。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public class JSFixedFastJsonDispAuditEntryProperty implements Dto {

    private static final long serialVersionUID = 2845756607292017774L;

    public static JSFixedFastJsonDispAuditEntryProperty of(DispAuditEntryProperty dispAuditEntryProperty) {
        if (Objects.isNull(dispAuditEntryProperty)) {
            return null;
        } else {
            return new JSFixedFastJsonDispAuditEntryProperty(
                    JSFixedFastJsonAuditEntryPropertyKey.of(dispAuditEntryProperty.getKey()),
                    dispAuditEntryProperty.getPropertyType(),
                    dispAuditEntryProperty.getStringValue(),
                    dispAuditEntryProperty.getLongValue(),
                    dispAuditEntryProperty.getDoubleValue(),
                    dispAuditEntryProperty.getBooleanValue(),
                    dispAuditEntryProperty.getDateValue(),
                    JSFixedFastJsonAuditEntry.of(dispAuditEntryProperty.getAuditEntry()),
                    JSFixedFastJsonAuditPropertyIndicator.of(dispAuditEntryProperty.getAuditPropertyIndicator())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonAuditEntryPropertyKey key;

    @JSONField(name = "property_type", ordinal = 2)
    private int propertyType;

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

    @JSONField(name = "audit_entry", ordinal = 8)
    private JSFixedFastJsonAuditEntry auditEntry;

    @JSONField(name = "audit_property_indicator", ordinal = 9)
    private JSFixedFastJsonAuditPropertyIndicator auditPropertyIndicator;

    public JSFixedFastJsonDispAuditEntryProperty() {
    }

    public JSFixedFastJsonDispAuditEntryProperty(
            JSFixedFastJsonAuditEntryPropertyKey key, int propertyType, String stringValue, Long longValue,
            Double doubleValue, Boolean booleanValue, Date dateValue, JSFixedFastJsonAuditEntry auditEntry,
            JSFixedFastJsonAuditPropertyIndicator auditPropertyIndicator
    ) {
        this.key = key;
        this.propertyType = propertyType;
        this.stringValue = stringValue;
        this.longValue = longValue;
        this.doubleValue = doubleValue;
        this.booleanValue = booleanValue;
        this.dateValue = dateValue;
        this.auditEntry = auditEntry;
        this.auditPropertyIndicator = auditPropertyIndicator;
    }

    public JSFixedFastJsonAuditEntryPropertyKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonAuditEntryPropertyKey key) {
        this.key = key;
    }

    public int getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(int propertyType) {
        this.propertyType = propertyType;
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

    public JSFixedFastJsonAuditEntry getAuditEntry() {
        return auditEntry;
    }

    public void setAuditEntry(JSFixedFastJsonAuditEntry auditEntry) {
        this.auditEntry = auditEntry;
    }

    public JSFixedFastJsonAuditPropertyIndicator getAuditPropertyIndicator() {
        return auditPropertyIndicator;
    }

    public void setAuditPropertyIndicator(JSFixedFastJsonAuditPropertyIndicator auditPropertyIndicator) {
        this.auditPropertyIndicator = auditPropertyIndicator;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispAuditEntryProperty{" +
                "key=" + key +
                ", propertyType=" + propertyType +
                ", stringValue='" + stringValue + '\'' +
                ", longValue=" + longValue +
                ", doubleValue=" + doubleValue +
                ", booleanValue=" + booleanValue +
                ", dateValue=" + dateValue +
                ", auditEntry=" + auditEntry +
                ", auditPropertyIndicator=" + auditPropertyIndicator +
                '}';
    }
}
