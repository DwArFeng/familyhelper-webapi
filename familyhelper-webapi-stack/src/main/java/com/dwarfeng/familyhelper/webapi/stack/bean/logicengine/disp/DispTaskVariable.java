package com.dwarfeng.familyhelper.webapi.stack.bean.logicengine.disp;

import com.dwarfeng.logicengine.stack.bean.entity.Task;
import com.dwarfeng.logicengine.stack.bean.entity.TaskVariable;
import com.dwarfeng.logicengine.stack.bean.key.TaskVariableKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * 可展示任务变量。
 *
 * @author DwArFeng
 * @since 2.4.0
 */
public class DispTaskVariable implements Dto {

    private static final long serialVersionUID = 4198646417021330250L;

    public static DispTaskVariable of(TaskVariable taskVariable, Task task) {
        if (Objects.isNull(taskVariable)) {
            return null;
        } else {
            return new DispTaskVariable(
                    taskVariable.getKey(),
                    taskVariable.getValueType(),
                    taskVariable.getStringValue(),
                    taskVariable.getLongValue(),
                    taskVariable.getDoubleValue(),
                    taskVariable.getBooleanValue(),
                    taskVariable.getDateValue(),
                    task
            );
        }
    }

    private TaskVariableKey key;
    private int valueType;
    private String stringValue;
    private Long longValue;
    private Double doubleValue;
    private Boolean booleanValue;
    private Date dateValue;
    private Task task;

    public DispTaskVariable() {
    }

    public DispTaskVariable(
            TaskVariableKey key, int valueType, String stringValue, Long longValue, Double doubleValue,
            Boolean booleanValue, Date dateValue, Task task
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

    public TaskVariableKey getKey() {
        return key;
    }

    public void setKey(TaskVariableKey key) {
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

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "DispTaskVariable{" +
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
