package com.dwarfeng.familyhelper.webapi.stack.bean.logicengine.disp;

import com.dwarfeng.logicengine.stack.bean.entity.Section;
import com.dwarfeng.logicengine.stack.bean.entity.State;
import com.dwarfeng.logicengine.stack.bean.entity.Task;
import com.dwarfeng.logicengine.stack.bean.key.StateKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;
import java.util.Objects;

/**
 * 可展示任务。
 *
 * @author DwArFeng
 * @since 2.4.0
 */
public class DispTask implements Dto {

    private static final long serialVersionUID = 1254883458442522353L;

    public static DispTask of(Task task, Section section, State currentState) {
        if (Objects.isNull(task)) {
            return null;
        } else {
            return new DispTask(
                    task.getKey(),
                    task.getSectionKey(),
                    task.getCurrentStateKey(),
                    task.getStatus(),
                    task.getCreatedDate(),
                    task.getStartedDate(),
                    task.getEndedDate(),
                    task.getDuration(),
                    task.getShouldExpireDate(),
                    task.getShouldDieDate(),
                    task.getExpiredDate(),
                    task.getDiedDate(),
                    task.getStateChangedDate(),
                    task.getAnchorMessage(),
                    section,
                    currentState
            );
        }
    }

    private LongIdKey key;
    private LongIdKey sectionKey;
    private StateKey currentStateKey;
    private int status;
    private Date createdDate;
    private Date startedDate;
    private Date endedDate;
    private Long duration;
    private Date shouldExpireDate;
    private Date shouldDieDate;
    private Date expiredDate;
    private Date diedDate;
    private Date stateChangedDate;
    private String anchorMessage;
    private Section section;
    private State currentState;

    public DispTask() {
    }

    public DispTask(
            LongIdKey key, LongIdKey sectionKey, StateKey currentStateKey, int status, Date createdDate,
            Date startedDate, Date endedDate, Long duration, Date shouldExpireDate, Date shouldDieDate,
            Date expiredDate, Date diedDate, Date stateChangedDate, String anchorMessage, Section section,
            State currentState
    ) {
        this.key = key;
        this.sectionKey = sectionKey;
        this.currentStateKey = currentStateKey;
        this.status = status;
        this.createdDate = createdDate;
        this.startedDate = startedDate;
        this.endedDate = endedDate;
        this.duration = duration;
        this.shouldExpireDate = shouldExpireDate;
        this.shouldDieDate = shouldDieDate;
        this.expiredDate = expiredDate;
        this.diedDate = diedDate;
        this.stateChangedDate = stateChangedDate;
        this.anchorMessage = anchorMessage;
        this.section = section;
        this.currentState = currentState;
    }

    public LongIdKey getKey() {
        return key;
    }

    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public LongIdKey getSectionKey() {
        return sectionKey;
    }

    public void setSectionKey(LongIdKey sectionKey) {
        this.sectionKey = sectionKey;
    }

    public StateKey getCurrentStateKey() {
        return currentStateKey;
    }

    public void setCurrentStateKey(StateKey currentStateKey) {
        this.currentStateKey = currentStateKey;
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

    public Date getStateChangedDate() {
        return stateChangedDate;
    }

    public void setStateChangedDate(Date stateChangedDate) {
        this.stateChangedDate = stateChangedDate;
    }

    public String getAnchorMessage() {
        return anchorMessage;
    }

    public void setAnchorMessage(String anchorMessage) {
        this.anchorMessage = anchorMessage;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    @Override
    public String toString() {
        return "DispTask{" +
                "key=" + key +
                ", sectionKey=" + sectionKey +
                ", currentStateKey=" + currentStateKey +
                ", status=" + status +
                ", createdDate=" + createdDate +
                ", startedDate=" + startedDate +
                ", endedDate=" + endedDate +
                ", duration=" + duration +
                ", shouldExpireDate=" + shouldExpireDate +
                ", shouldDieDate=" + shouldDieDate +
                ", expiredDate=" + expiredDate +
                ", diedDate=" + diedDate +
                ", stateChangedDate=" + stateChangedDate +
                ", anchorMessage='" + anchorMessage + '\'' +
                ", section=" + section +
                ", currentState=" + currentState +
                '}';
    }
}
