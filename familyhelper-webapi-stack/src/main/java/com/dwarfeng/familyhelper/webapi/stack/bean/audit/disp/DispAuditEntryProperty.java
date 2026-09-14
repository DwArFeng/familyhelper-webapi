package com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp;

import com.dwarfeng.audit.stack.bean.entity.AuditEntry;
import com.dwarfeng.audit.stack.bean.entity.AuditEntryProperty;
import com.dwarfeng.audit.stack.bean.entity.AuditPropertyIndicator;
import com.dwarfeng.audit.stack.bean.key.AuditEntryPropertyKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * 可展示审计条目属性。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public class DispAuditEntryProperty implements Dto {

    private static final long serialVersionUID = 3329063923498936890L;

    public static DispAuditEntryProperty of(
            AuditEntryProperty auditEntryProperty, AuditEntry auditEntry, AuditPropertyIndicator auditPropertyIndicator
    ) {
        if (Objects.isNull(auditEntryProperty)) {
            return null;
        } else {
            return new DispAuditEntryProperty(
                    auditEntryProperty.getKey(),
                    auditEntryProperty.getPropertyType(),
                    auditEntryProperty.getStringValue(),
                    auditEntryProperty.getLongValue(),
                    auditEntryProperty.getDoubleValue(),
                    auditEntryProperty.getBooleanValue(),
                    auditEntryProperty.getDateValue(),
                    auditEntry,
                    auditPropertyIndicator
            );
        }
    }

    private AuditEntryPropertyKey key;
    private int propertyType;
    private String stringValue;
    private Long longValue;
    private Double doubleValue;
    private Boolean booleanValue;
    private Date dateValue;
    private AuditEntry auditEntry;
    private AuditPropertyIndicator auditPropertyIndicator;

    public DispAuditEntryProperty() {
    }

    public DispAuditEntryProperty(
            AuditEntryPropertyKey key, int propertyType, String stringValue, Long longValue, Double doubleValue,
            Boolean booleanValue, Date dateValue, AuditEntry auditEntry, AuditPropertyIndicator auditPropertyIndicator
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

    public AuditEntryPropertyKey getKey() {
        return key;
    }

    public void setKey(AuditEntryPropertyKey key) {
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

    public AuditEntry getAuditEntry() {
        return auditEntry;
    }

    public void setAuditEntry(AuditEntry auditEntry) {
        this.auditEntry = auditEntry;
    }

    public AuditPropertyIndicator getAuditPropertyIndicator() {
        return auditPropertyIndicator;
    }

    public void setAuditPropertyIndicator(AuditPropertyIndicator auditPropertyIndicator) {
        this.auditPropertyIndicator = auditPropertyIndicator;
    }

    @Override
    public String toString() {
        return "DispAuditEntryProperty{" +
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
