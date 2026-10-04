package com.dwarfeng.familyhelper.webapi.sdk.bean.logicengine.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.dwarfeng.familyhelper.webapi.stack.bean.logicengine.disp.DispTaskVariable;
import com.dwarfeng.logicengine.sdk.bean.entity.JSFixedFastJsonTask;
import com.dwarfeng.logicengine.sdk.bean.key.JSFixedFastJsonTaskVariableKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 可展示任务变量。
 *
 * @author DwArFeng
 * @since 2.4.0
 */
public class JSFixedFastJsonDispTaskVariable implements Dto {

    private static final long serialVersionUID = 3868467692236934775L;

    public static JSFixedFastJsonDispTaskVariable of(DispTaskVariable dispTaskVariable) {
        if (Objects.isNull(dispTaskVariable)) {
            return null;
        } else {
            return new JSFixedFastJsonDispTaskVariable(
                    JSFixedFastJsonTaskVariableKey.of(dispTaskVariable.getKey()),
                    dispTaskVariable.getValueType(),
                    dispTaskVariable.getStringValue(),
                    dispTaskVariable.getLongValue(),
                    dispTaskVariable.getDoubleValue(),
                    dispTaskVariable.getBooleanValue(),
                    dispTaskVariable.getDateValue(),
                    JSFixedFastJsonTask.of(dispTaskVariable.getTask())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonTaskVariableKey key;

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

    @JSONField(name = "task", ordinal = 8)
    private JSFixedFastJsonTask task;

    public JSFixedFastJsonDispTaskVariable() {
    }

    public JSFixedFastJsonDispTaskVariable(
            JSFixedFastJsonTaskVariableKey key, int valueType, String stringValue, Long longValue, Double doubleValue,
            Boolean booleanValue, Date dateValue, JSFixedFastJsonTask task
    ) {
        this.key = key;
        this.valueType = valueType;
        this.stringValue = stringValue;
        this.longValue = longValue;
        this.doubleValue = doubleValue;
        this.booleanValue = booleanValue;
        this.dateValue = dateValue;
        this.task = task;
    }

    public JSFixedFastJsonTaskVariableKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonTaskVariableKey key) {
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

    public JSFixedFastJsonTask getTask() {
        return task;
    }

    public void setTask(JSFixedFastJsonTask task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispTaskVariable{" +
                "key=" + key +
                ", valueType=" + valueType +
                ", stringValue='" + stringValue + '\'' +
                ", longValue=" + longValue +
                ", doubleValue=" + doubleValue +
                ", booleanValue=" + booleanValue +
                ", dateValue=" + dateValue +
                ", task=" + task +
                '}';
    }
}
