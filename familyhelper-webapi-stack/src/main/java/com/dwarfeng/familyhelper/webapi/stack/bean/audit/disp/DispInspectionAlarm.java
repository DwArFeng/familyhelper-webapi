package com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp;

import com.dwarfeng.audit.stack.bean.entity.*;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;
import java.util.Objects;

/**
 * 可展示自动审计报警。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public class DispInspectionAlarm implements Dto {

    private static final long serialVersionUID = -8367790063064154936L;

    public static DispInspectionAlarm of(
            InspectionAlarm inspectionAlarm, Inspection inspection, InspectionTask inspectionTask,
            InspectorInfo inspectorInfo, InspectionAlarmTypeIndicator typeIndicator
    ) {
        if (Objects.isNull(inspectionAlarm)) {
            return null;
        } else {
            return new DispInspectionAlarm(
                    inspectionAlarm.getKey(),
                    inspectionAlarm.getInspectionKey(),
                    inspectionAlarm.getInspectionTaskKey(),
                    inspectionAlarm.getInspectorInfoKey(),
                    inspectionAlarm.getHappenedDate(),
                    inspectionAlarm.getType(),
                    inspectionAlarm.getMessage(),
                    inspection,
                    inspectionTask,
                    inspectorInfo,
                    typeIndicator
            );
        }
    }

    private LongIdKey key;
    private LongIdKey inspectionKey;
    private LongIdKey inspectionTaskKey;
    private LongIdKey inspectorInfoKey;
    private Date happenedDate;
    private String type;
    private String message;
    private Inspection inspection;
    private InspectionTask inspectionTask;
    private InspectorInfo inspectorInfo;
    private InspectionAlarmTypeIndicator typeIndicator;

    public DispInspectionAlarm() {
    }

    public DispInspectionAlarm(
            LongIdKey key, LongIdKey inspectionKey, LongIdKey inspectionTaskKey, LongIdKey inspectorInfoKey,
            Date happenedDate, String type, String message, Inspection inspection, InspectionTask inspectionTask,
            InspectorInfo inspectorInfo, InspectionAlarmTypeIndicator typeIndicator
    ) {
        this.key = key;
        this.inspectionKey = inspectionKey;
        this.inspectionTaskKey = inspectionTaskKey;
        this.inspectorInfoKey = inspectorInfoKey;
        this.happenedDate = happenedDate;
        this.type = type;
        this.message = message;
        this.inspection = inspection;
        this.inspectionTask = inspectionTask;
        this.inspectorInfo = inspectorInfo;
        this.typeIndicator = typeIndicator;
    }

    public LongIdKey getKey() {
        return key;
    }

    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public LongIdKey getInspectionKey() {
        return inspectionKey;
    }

    public void setInspectionKey(LongIdKey inspectionKey) {
        this.inspectionKey = inspectionKey;
    }

    public LongIdKey getInspectionTaskKey() {
        return inspectionTaskKey;
    }

    public void setInspectionTaskKey(LongIdKey inspectionTaskKey) {
        this.inspectionTaskKey = inspectionTaskKey;
    }

    public LongIdKey getInspectorInfoKey() {
        return inspectorInfoKey;
    }

    public void setInspectorInfoKey(LongIdKey inspectorInfoKey) {
        this.inspectorInfoKey = inspectorInfoKey;
    }

    public Date getHappenedDate() {
        return happenedDate;
    }

    public void setHappenedDate(Date happenedDate) {
        this.happenedDate = happenedDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Inspection getInspection() {
        return inspection;
    }

    public void setInspection(Inspection inspection) {
        this.inspection = inspection;
    }

    public InspectionTask getInspectionTask() {
        return inspectionTask;
    }

    public void setInspectionTask(InspectionTask inspectionTask) {
        this.inspectionTask = inspectionTask;
    }

    public InspectorInfo getInspectorInfo() {
        return inspectorInfo;
    }

    public void setInspectorInfo(InspectorInfo inspectorInfo) {
        this.inspectorInfo = inspectorInfo;
    }

    public InspectionAlarmTypeIndicator getTypeIndicator() {
        return typeIndicator;
    }

    public void setTypeIndicator(InspectionAlarmTypeIndicator typeIndicator) {
        this.typeIndicator = typeIndicator;
    }

    @Override
    public String toString() {
        return "DispInspectionAlarm{" +
                "key=" + key +
                ", inspectionKey=" + inspectionKey +
                ", inspectionTaskKey=" + inspectionTaskKey +
                ", inspectorInfoKey=" + inspectorInfoKey +
                ", happenedDate=" + happenedDate +
                ", type='" + type + '\'' +
                ", message='" + message + '\'' +
                ", inspection=" + inspection +
                ", inspectionTask=" + inspectionTask +
                ", inspectorInfo=" + inspectorInfo +
                ", typeIndicator=" + typeIndicator +
                '}';
    }
}
