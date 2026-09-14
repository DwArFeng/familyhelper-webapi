package com.dwarfeng.familyhelper.webapi.sdk.bean.audit.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.sdk.bean.entity.FastJsonInspectionAlarmTypeIndicator;
import com.dwarfeng.audit.sdk.bean.entity.JSFixedFastJsonInspection;
import com.dwarfeng.audit.sdk.bean.entity.JSFixedFastJsonInspectionTask;
import com.dwarfeng.audit.sdk.bean.entity.JSFixedFastJsonInspectorInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp.DispInspectionAlarm;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 可展示自动审计报警。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public class JSFixedFastJsonDispInspectionAlarm implements Dto {

    private static final long serialVersionUID = -3406676163543337210L;

    public static JSFixedFastJsonDispInspectionAlarm of(DispInspectionAlarm dispInspectionAlarm) {
        if (Objects.isNull(dispInspectionAlarm)) {
            return null;
        } else {
            return new JSFixedFastJsonDispInspectionAlarm(
                    JSFixedFastJsonLongIdKey.of(dispInspectionAlarm.getKey()),
                    JSFixedFastJsonLongIdKey.of(dispInspectionAlarm.getInspectionKey()),
                    JSFixedFastJsonLongIdKey.of(dispInspectionAlarm.getInspectionTaskKey()),
                    JSFixedFastJsonLongIdKey.of(dispInspectionAlarm.getInspectorInfoKey()),
                    dispInspectionAlarm.getHappenedDate(),
                    dispInspectionAlarm.getType(),
                    dispInspectionAlarm.getMessage(),
                    JSFixedFastJsonInspection.of(dispInspectionAlarm.getInspection()),
                    JSFixedFastJsonInspectionTask.of(dispInspectionAlarm.getInspectionTask()),
                    JSFixedFastJsonInspectorInfo.of(dispInspectionAlarm.getInspectorInfo()),
                    FastJsonInspectionAlarmTypeIndicator.of(dispInspectionAlarm.getTypeIndicator())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "inspection_key", ordinal = 2)
    private JSFixedFastJsonLongIdKey inspectionKey;

    @JSONField(name = "inspection_task_key", ordinal = 3)
    private JSFixedFastJsonLongIdKey inspectionTaskKey;

    @JSONField(name = "inspector_info_key", ordinal = 4)
    private JSFixedFastJsonLongIdKey inspectorInfoKey;

    @JSONField(name = "happened_date", ordinal = 5)
    private Date happenedDate;

    @JSONField(name = "type", ordinal = 6)
    private String type;

    @JSONField(name = "message", ordinal = 7)
    private String message;

    @JSONField(name = "inspection", ordinal = 8)
    private JSFixedFastJsonInspection inspection;

    @JSONField(name = "inspection_task", ordinal = 9)
    private JSFixedFastJsonInspectionTask inspectionTask;

    @JSONField(name = "inspector_info", ordinal = 10)
    private JSFixedFastJsonInspectorInfo inspectorInfo;

    @JSONField(name = "type_indicator", ordinal = 11)
    private FastJsonInspectionAlarmTypeIndicator typeIndicator;

    public JSFixedFastJsonDispInspectionAlarm() {
    }

    public JSFixedFastJsonDispInspectionAlarm(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey inspectionKey,
            JSFixedFastJsonLongIdKey inspectionTaskKey, JSFixedFastJsonLongIdKey inspectorInfoKey, Date happenedDate,
            String type, String message, JSFixedFastJsonInspection inspection, JSFixedFastJsonInspectionTask inspectionTask,
            JSFixedFastJsonInspectorInfo inspectorInfo, FastJsonInspectionAlarmTypeIndicator typeIndicator
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

    public JSFixedFastJsonLongIdKey getInspectionTaskKey() {
        return inspectionTaskKey;
    }

    public void setInspectionTaskKey(JSFixedFastJsonLongIdKey inspectionTaskKey) {
        this.inspectionTaskKey = inspectionTaskKey;
    }

    public JSFixedFastJsonLongIdKey getInspectorInfoKey() {
        return inspectorInfoKey;
    }

    public void setInspectorInfoKey(JSFixedFastJsonLongIdKey inspectorInfoKey) {
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

    public JSFixedFastJsonInspection getInspection() {
        return inspection;
    }

    public void setInspection(JSFixedFastJsonInspection inspection) {
        this.inspection = inspection;
    }

    public JSFixedFastJsonInspectionTask getInspectionTask() {
        return inspectionTask;
    }

    public void setInspectionTask(JSFixedFastJsonInspectionTask inspectionTask) {
        this.inspectionTask = inspectionTask;
    }

    public JSFixedFastJsonInspectorInfo getInspectorInfo() {
        return inspectorInfo;
    }

    public void setInspectorInfo(JSFixedFastJsonInspectorInfo inspectorInfo) {
        this.inspectorInfo = inspectorInfo;
    }

    public FastJsonInspectionAlarmTypeIndicator getTypeIndicator() {
        return typeIndicator;
    }

    public void setTypeIndicator(FastJsonInspectionAlarmTypeIndicator typeIndicator) {
        this.typeIndicator = typeIndicator;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispInspectionAlarm{" +
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
