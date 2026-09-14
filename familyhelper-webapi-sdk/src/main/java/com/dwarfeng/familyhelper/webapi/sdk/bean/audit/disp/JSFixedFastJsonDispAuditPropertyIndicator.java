package com.dwarfeng.familyhelper.webapi.sdk.bean.audit.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.dwarfeng.audit.sdk.bean.entity.FastJsonAuditCategory;
import com.dwarfeng.audit.sdk.bean.key.JSFixedFastJsonAuditPropertyIndicatorKey;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp.DispAuditPropertyIndicator;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 可展示审计属性指示器。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public class JSFixedFastJsonDispAuditPropertyIndicator implements Dto {

    private static final long serialVersionUID = 974115362253644992L;

    public static JSFixedFastJsonDispAuditPropertyIndicator of(DispAuditPropertyIndicator dispAuditPropertyIndicator) {
        if (Objects.isNull(dispAuditPropertyIndicator)) {
            return null;
        } else {
            return new JSFixedFastJsonDispAuditPropertyIndicator(
                    JSFixedFastJsonAuditPropertyIndicatorKey.of(dispAuditPropertyIndicator.getKey()),
                    dispAuditPropertyIndicator.getLabel(),
                    dispAuditPropertyIndicator.getPropertyType(),
                    dispAuditPropertyIndicator.getDefaultStringValue(),
                    dispAuditPropertyIndicator.getDefaultLongValue(),
                    dispAuditPropertyIndicator.getDefaultDoubleValue(),
                    dispAuditPropertyIndicator.getDefaultBooleanValue(),
                    dispAuditPropertyIndicator.getDefaultDateValue(),
                    dispAuditPropertyIndicator.getOrder(),
                    FastJsonAuditCategory.of(dispAuditPropertyIndicator.getAuditCategory())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonAuditPropertyIndicatorKey key;

    @JSONField(name = "label", ordinal = 2)
    private String label;

    @JSONField(name = "property_type", ordinal = 3)
    private int propertyType;

    @JSONField(name = "default_string_value", ordinal = 4)
    private String defaultStringValue;

    @JSONField(name = "default_long_value", ordinal = 5, serializeUsing = ToStringSerializer.class)
    private Long defaultLongValue;

    @JSONField(name = "default_double_value", ordinal = 6)
    private Double defaultDoubleValue;

    @JSONField(name = "default_boolean_value", ordinal = 7)
    private Boolean defaultBooleanValue;

    @JSONField(name = "default_date_value", ordinal = 8)
    private Date defaultDateValue;

    @JSONField(name = "order", ordinal = 9)
    private int order;

    @JSONField(name = "audit_category", ordinal = 10)
    private FastJsonAuditCategory auditCategory;

    public JSFixedFastJsonDispAuditPropertyIndicator() {
    }

    public JSFixedFastJsonDispAuditPropertyIndicator(
            JSFixedFastJsonAuditPropertyIndicatorKey key, String label, int propertyType, String defaultStringValue,
            Long defaultLongValue, Double defaultDoubleValue, Boolean defaultBooleanValue, Date defaultDateValue,
            int order, FastJsonAuditCategory auditCategory
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

    public JSFixedFastJsonAuditPropertyIndicatorKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonAuditPropertyIndicatorKey key) {
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

    public FastJsonAuditCategory getAuditCategory() {
        return auditCategory;
    }

    public void setAuditCategory(FastJsonAuditCategory auditCategory) {
        this.auditCategory = auditCategory;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispAuditPropertyIndicator{" +
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
