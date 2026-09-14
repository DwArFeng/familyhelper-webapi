package com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp;

import com.dwarfeng.audit.stack.bean.entity.Inspection;
import com.dwarfeng.audit.stack.bean.entity.InspectionTask;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;
import java.util.Objects;

/**
 * 可展示自动审计任务。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public class DispInspectionTask implements Dto {

    private static final long serialVersionUID = 7617156510377703246L;

    public static DispInspectionTask of(InspectionTask inspectionTask, Inspection inspection) {
        if (Objects.isNull(inspectionTask)) {
            return null;
        } else {
            return new DispInspectionTask(
                    inspectionTask.getKey(),
                    inspectionTask.getInspectionKey(),
                    inspectionTask.getStatus(),
                    inspectionTask.getCreatedDate(),
                    inspectionTask.getStartedDate(),
                    inspectionTask.getEndedDate(),
                    inspectionTask.getDuration(),
                    inspectionTask.getShouldExpireDate(),
                    inspectionTask.getShouldDieDate(),
                    inspectionTask.getExpiredDate(),
                    inspectionTask.getDiedDate(),
                    inspectionTask.getAnchorMessage(),
                    inspection
            );
        }
    }

    private LongIdKey key;
    private LongIdKey inspectionKey;
    private int status;
    private Date createdDate;
    private Date startedDate;
    private Date endedDate;
    private Long duration;
    private Date shouldExpireDate;
    private Date shouldDieDate;
    private Date expiredDate;
    private Date diedDate;
    private String anchorMessage;
    private Inspection inspection;

    public DispInspectionTask() {
    }

    public DispInspectionTask(
            LongIdKey key, LongIdKey inspectionKey, int status, Date createdDate, Date startedDate, Date endedDate,
            Long duration, Date shouldExpireDate, Date shouldDieDate, Date expiredDate, Date diedDate,
            String anchorMessage, Inspection inspection
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

    public Inspection getInspection() {
        return inspection;
    }

    public void setInspection(Inspection inspection) {
        this.inspection = inspection;
    }

    @Override
    public String toString() {
        return "DispInspectionTask{" +
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
