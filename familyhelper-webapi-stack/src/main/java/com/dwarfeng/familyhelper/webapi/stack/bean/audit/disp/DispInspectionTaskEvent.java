package com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp;

import com.dwarfeng.audit.stack.bean.entity.InspectionTask;
import com.dwarfeng.audit.stack.bean.entity.InspectionTaskEvent;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;
import java.util.Objects;

/**
 * 可展示自动审计任务事件。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public class DispInspectionTaskEvent implements Dto {

    private static final long serialVersionUID = -2065407429636268864L;

    public static DispInspectionTaskEvent of(InspectionTaskEvent inspectionTaskEvent, InspectionTask inspectionTask) {
        if (Objects.isNull(inspectionTaskEvent)) {
            return null;
        } else {
            return new DispInspectionTaskEvent(
                    inspectionTaskEvent.getKey(),
                    inspectionTaskEvent.getInspectionTaskKey(),
                    inspectionTaskEvent.getHappenedDate(),
                    inspectionTaskEvent.getMessage(),
                    inspectionTask
            );
        }
    }

    private LongIdKey key;
    private LongIdKey inspectionTaskKey;
    private Date happenedDate;
    private String message;
    private InspectionTask inspectionTask;

    public DispInspectionTaskEvent() {
    }

    public DispInspectionTaskEvent(
            LongIdKey key, LongIdKey inspectionTaskKey, Date happenedDate, String message,
            InspectionTask inspectionTask
    ) {
        this.key = key;
        this.inspectionTaskKey = inspectionTaskKey;
        this.happenedDate = happenedDate;
        this.message = message;
        this.inspectionTask = inspectionTask;
    }

    public LongIdKey getKey() {
        return key;
    }

    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public LongIdKey getInspectionTaskKey() {
        return inspectionTaskKey;
    }

    public void setInspectionTaskKey(LongIdKey inspectionTaskKey) {
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

    public InspectionTask getInspectionTask() {
        return inspectionTask;
    }

    public void setInspectionTask(InspectionTask inspectionTask) {
        this.inspectionTask = inspectionTask;
    }

    @Override
    public String toString() {
        return "DispInspectionTaskEvent{" +
                "key=" + key +
                ", inspectionTaskKey=" + inspectionTaskKey +
                ", happenedDate=" + happenedDate +
                ", message='" + message + '\'' +
                ", inspectionTask=" + inspectionTask +
                '}';
    }
}
