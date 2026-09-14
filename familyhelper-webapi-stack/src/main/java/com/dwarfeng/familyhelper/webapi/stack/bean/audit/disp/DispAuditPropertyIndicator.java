package com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp;

import com.dwarfeng.audit.stack.bean.entity.AuditCategory;
import com.dwarfeng.audit.stack.bean.entity.AuditPropertyIndicator;
import com.dwarfeng.audit.stack.bean.key.AuditPropertyIndicatorKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * 可展示审计属性指示器。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public class DispAuditPropertyIndicator implements Dto {

    private static final long serialVersionUID = 7603706791765455456L;

    public static DispAuditPropertyIndicator of(
            AuditPropertyIndicator auditPropertyIndicator, AuditCategory auditCategory
    ) {
        if (Objects.isNull(auditPropertyIndicator)) {
            return null;
        } else {
            return new DispAuditPropertyIndicator(
                    auditPropertyIndicator.getKey(),
                    auditPropertyIndicator.getLabel(),
                    auditPropertyIndicator.getPropertyType(),
                    auditPropertyIndicator.getDefaultStringValue(),
                    auditPropertyIndicator.getDefaultLongValue(),
                    auditPropertyIndicator.getDefaultDoubleValue(),
                    auditPropertyIndicator.getDefaultBooleanValue(),
                    auditPropertyIndicator.getDefaultDateValue(),
                    auditPropertyIndicator.getOrder(),
                    auditCategory
            );
        }
    }

    private AuditPropertyIndicatorKey key;
    private String label;
    private int propertyType;
    private String defaultStringValue;
    private Long defaultLongValue;
    private Double defaultDoubleValue;
    private Boolean defaultBooleanValue;
    private Date defaultDateValue;
    private int order;
    private AuditCategory auditCategory;

    public DispAuditPropertyIndicator() {
    }

    public DispAuditPropertyIndicator(
            AuditPropertyIndicatorKey key, String label, int propertyType, String defaultStringValue,
            Long defaultLongValue, Double defaultDoubleValue, Boolean defaultBooleanValue, Date defaultDateValue,
            int order, AuditCategory auditCategory
    ) {
        this.key = key;
        this.label = label;
        this.propertyType = propertyType;
        this.defaultStringValue = defaultStringValue;
        this.defaultLongValue = defaultLongValue;
        this.defaultDoubleValue = defaultDoubleValue;
        this.defaultBooleanValue = defaultBooleanValue;
        this.defaultDateValue = defaultDateValue;
        this.order = order;
        this.auditCategory = auditCategory;
    }

    public AuditPropertyIndicatorKey getKey() {
        return key;
    }

    public void setKey(AuditPropertyIndicatorKey key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(int propertyType) {
        this.propertyType = propertyType;
    }

    public String getDefaultStringValue() {
        return defaultStringValue;
    }

    public void setDefaultStringValue(String defaultStringValue) {
        this.defaultStringValue = defaultStringValue;
    }

    public Long getDefaultLongValue() {
        return defaultLongValue;
    }

    public void setDefaultLongValue(Long defaultLongValue) {
        this.defaultLongValue = defaultLongValue;
    }

    public Double getDefaultDoubleValue() {
        return defaultDoubleValue;
    }

    public void setDefaultDoubleValue(Double defaultDoubleValue) {
        this.defaultDoubleValue = defaultDoubleValue;
    }

    public Boolean getDefaultBooleanValue() {
        return defaultBooleanValue;
    }

    public void setDefaultBooleanValue(Boolean defaultBooleanValue) {
        this.defaultBooleanValue = defaultBooleanValue;
    }

    public Date getDefaultDateValue() {
        return defaultDateValue;
    }

    public void setDefaultDateValue(Date defaultDateValue) {
        this.defaultDateValue = defaultDateValue;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public AuditCategory getAuditCategory() {
        return auditCategory;
    }

    public void setAuditCategory(AuditCategory auditCategory) {
        this.auditCategory = auditCategory;
    }

    @Override
    public String toString() {
        return "DispAuditPropertyIndicator{" +
                "key=" + key +
                ", label='" + label + '\'' +
                ", propertyType=" + propertyType +
                ", defaultStringValue='" + defaultStringValue + '\'' +
                ", defaultLongValue=" + defaultLongValue +
                ", defaultDoubleValue=" + defaultDoubleValue +
                ", defaultBooleanValue=" + defaultBooleanValue +
                ", defaultDateValue=" + defaultDateValue +
                ", order=" + order +
                ", auditCategory=" + auditCategory +
                '}';
    }
}
