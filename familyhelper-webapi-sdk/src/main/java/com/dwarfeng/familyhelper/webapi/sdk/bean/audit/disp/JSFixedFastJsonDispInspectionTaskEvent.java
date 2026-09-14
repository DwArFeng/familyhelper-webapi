package com.dwarfeng.familyhelper.webapi.sdk.bean.audit.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.sdk.bean.entity.JSFixedFastJsonInspectionTask;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp.DispInspectionTaskEvent;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 可展示自动审计任务事件。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public class JSFixedFastJsonDispInspectionTaskEvent implements Dto {

    private static final long serialVersionUID = 4892038015923868882L;

    public static JSFixedFastJsonDispInspectionTaskEvent of(DispInspectionTaskEvent dispInspectionTaskEvent) {
        if (Objects.isNull(dispInspectionTaskEvent)) {
            return null;
        } else {
            return new JSFixedFastJsonDispInspectionTaskEvent(
                    JSFixedFastJsonLongIdKey.of(dispInspectionTaskEvent.getKey()),
                    JSFixedFastJsonLongIdKey.of(dispInspectionTaskEvent.getInspectionTaskKey()),
                    dispInspectionTaskEvent.getHappenedDate(),
                    dispInspectionTaskEvent.getMessage(),
                    JSFixedFastJsonInspectionTask.of(dispInspectionTaskEvent.getInspectionTask())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "inspection_task_key", ordinal = 2)
    private JSFixedFastJsonLongIdKey inspectionTaskKey;

    @JSONField(name = "happened_date", ordinal = 3)
    private Date happenedDate;

    @JSONField(name = "message", ordinal = 4)
    private String message;

    @JSONField(name = "inspection_task", ordinal = 5)
    private JSFixedFastJsonInspectionTask inspectionTask;

    public JSFixedFastJsonDispInspectionTaskEvent() {
    }

    public JSFixedFastJsonDispInspectionTaskEvent(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey inspectionTaskKey, Date happenedDate,
            String message, JSFixedFastJsonInspectionTask inspectionTask
    ) {
        this.key = key;
        this.inspectionTaskKey = inspectionTaskKey;
        this.happenedDate = happenedDate;
        this.message = message;
        this.inspectionTask = inspectionTask;
    }

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
        this.key = key;
    }

    public JSFixedFastJsonLongIdKey getInspectionTaskKey() {
        return inspectionTaskKey;
    }

    public void setInspectionTaskKey(JSFixedFastJsonLongIdKey inspectionTaskKey) {
        this.inspectionTaskKey = inspectionTaskKey;
    }

    public Date getHappenedDate() {
        return happenedDate;
    }

    public void setHappenedDate(Date happenedDate) {
        this.happenedDate = happenedDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JSFixedFastJsonInspectionTask getInspectionTask() {
        return inspectionTask;
    }

    public void setInspectionTask(JSFixedFastJsonInspectionTask inspectionTask) {
        this.inspectionTask = inspectionTask;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispInspectionTaskEvent{" +
                "key=" + key +
                ", inspectionTaskKey=" + inspectionTaskKey +
                ", happenedDate=" + happenedDate +
                ", message='" + message + '\'' +
                ", inspectionTask=" + inspectionTask +
                '}';
    }
}
