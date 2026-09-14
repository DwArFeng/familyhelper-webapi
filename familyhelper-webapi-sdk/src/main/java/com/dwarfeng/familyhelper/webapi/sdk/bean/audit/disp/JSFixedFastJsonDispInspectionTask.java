package com.dwarfeng.familyhelper.webapi.sdk.bean.audit.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.dwarfeng.audit.sdk.bean.entity.JSFixedFastJsonInspection;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp.DispInspectionTask;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 可展示自动审计任务。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public class JSFixedFastJsonDispInspectionTask implements Dto {

    private static final long serialVersionUID = -5075037231881574397L;

    public static JSFixedFastJsonDispInspectionTask of(DispInspectionTask dispInspectionTask) {
        if (Objects.isNull(dispInspectionTask)) {
            return null;
        } else {
            return new JSFixedFastJsonDispInspectionTask(
                    JSFixedFastJsonLongIdKey.of(dispInspectionTask.getKey()),
                    JSFixedFastJsonLongIdKey.of(dispInspectionTask.getInspectionKey()),
                    dispInspectionTask.getStatus(),
                    dispInspectionTask.getCreatedDate(),
                    dispInspectionTask.getStartedDate(),
                    dispInspectionTask.getEndedDate(),
                    dispInspectionTask.getDuration(),
                    dispInspectionTask.getShouldExpireDate(),
                    dispInspectionTask.getShouldDieDate(),
                    dispInspectionTask.getExpiredDate(),
                    dispInspectionTask.getDiedDate(),
                    dispInspectionTask.getAnchorMessage(),
                    JSFixedFastJsonInspection.of(dispInspectionTask.getInspection())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "inspection_key", ordinal = 2)
    private JSFixedFastJsonLongIdKey inspectionKey;

    @JSONField(name = "status", ordinal = 3)
    private int status;

    @JSONField(name = "created_date", ordinal = 4)
    private Date createdDate;

    @JSONField(name = "started_date", ordinal = 5)
    private Date startedDate;

    @JSONField(name = "ended_date", ordinal = 6)
    private Date endedDate;

    @JSONField(name = "duration", ordinal = 7, serializeUsing = ToStringSerializer.class)
    private Long duration;

    @JSONField(name = "should_expire_date", ordinal = 8)
    private Date shouldExpireDate;

    @JSONField(name = "should_die_date", ordinal = 9)
    private Date shouldDieDate;

    @JSONField(name = "expired_date", ordinal = 10)
    private Date expiredDate;

    @JSONField(name = "died_date", ordinal = 11)
    private Date diedDate;

    @JSONField(name = "anchor_message", ordinal = 12)
    private String anchorMessage;

    @JSONField(name = "inspection", ordinal = 13)
    private JSFixedFastJsonInspection inspection;

    public JSFixedFastJsonDispInspectionTask() {
    }

    public JSFixedFastJsonDispInspectionTask(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey inspectionKey, int status, Date createdDate,
            Date startedDate, Date endedDate, Long duration, Date shouldExpireDate, Date shouldDieDate,
            Date expiredDate, Date diedDate, String anchorMessage, JSFixedFastJsonInspection inspection
    ) {
        this.key = key;
        this.inspectionKey = inspectionKey;
        this.status = status;
        this.createdDate = createdDate;
        this.startedDate = startedDate;
        this.endedDate = endedDate;
        this.duration = duration;
        this.shouldExpireDate = shouldExpireDate;
        this.shouldDieDate = shouldDieDate;
        this.expiredDate = expiredDate;
        this.diedDate = diedDate;
        this.anchorMessage = anchorMessage;
        this.inspection = inspection;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(Date startedDate) {
        this.startedDate = startedDate;
    }

    public Date getEndedDate() {
        return endedDate;
    }

    public void setEndedDate(Date endedDate) {
        this.endedDate = endedDate;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Date getShouldExpireDate() {
        return shouldExpireDate;
    }

    public void setShouldExpireDate(Date shouldExpireDate) {
        this.shouldExpireDate = shouldExpireDate;
    }

    public Date getShouldDieDate() {
        return shouldDieDate;
    }

    public void setShouldDieDate(Date shouldDieDate) {
        this.shouldDieDate = shouldDieDate;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Date getDiedDate() {
        return diedDate;
    }

    public void setDiedDate(Date diedDate) {
        this.diedDate = diedDate;
    }

    public String getAnchorMessage() {
        return anchorMessage;
    }

    public void setAnchorMessage(String anchorMessage) {
        this.anchorMessage = anchorMessage;
    }

    public JSFixedFastJsonInspection getInspection() {
        return inspection;
    }

    public void setInspection(JSFixedFastJsonInspection inspection) {
        this.inspection = inspection;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispInspectionTask{" +
                "key=" + key +
                ", inspectionKey=" + inspectionKey +
                ", status=" + status +
                ", createdDate=" + createdDate +
                ", startedDate=" + startedDate +
                ", endedDate=" + endedDate +
                ", duration=" + duration +
                ", shouldExpireDate=" + shouldExpireDate +
                ", shouldDieDate=" + shouldDieDate +
                ", expiredDate=" + expiredDate +
                ", diedDate=" + diedDate +
                ", anchorMessage='" + anchorMessage + '\'' +
                ", inspection=" + inspection +
                '}';
    }
}
