package com.dwarfeng.familyhelper.webapi.sdk.bean.logicengine.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.dwarfeng.familyhelper.webapi.stack.bean.logicengine.disp.DispTask;
import com.dwarfeng.logicengine.sdk.bean.entity.JSFixedFastJsonSection;
import com.dwarfeng.logicengine.sdk.bean.entity.JSFixedFastJsonState;
import com.dwarfeng.logicengine.sdk.bean.key.JSFixedFastJsonStateKey;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 可展示任务。
 *
 * @author DwArFeng
 * @since 2.4.0
 */
public class JSFixedFastJsonDispTask implements Dto {

    private static final long serialVersionUID = -2708600596245465631L;

    public static JSFixedFastJsonDispTask of(DispTask dispTask) {
        if (Objects.isNull(dispTask)) {
            return null;
        } else {
            return new JSFixedFastJsonDispTask(
                    JSFixedFastJsonLongIdKey.of(dispTask.getKey()),
                    JSFixedFastJsonLongIdKey.of(dispTask.getSectionKey()),
                    JSFixedFastJsonStateKey.of(dispTask.getCurrentStateKey()),
                    dispTask.getStatus(),
                    dispTask.getCreatedDate(),
                    dispTask.getStartedDate(),
                    dispTask.getEndedDate(),
                    dispTask.getDuration(),
                    dispTask.getShouldExpireDate(),
                    dispTask.getShouldDieDate(),
                    dispTask.getExpiredDate(),
                    dispTask.getDiedDate(),
                    dispTask.getStateChangedDate(),
                    dispTask.getAnchorMessage(),
                    JSFixedFastJsonSection.of(dispTask.getSection()),
                    JSFixedFastJsonState.of(dispTask.getCurrentState())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "section_key", ordinal = 2)
    private JSFixedFastJsonLongIdKey sectionKey;

    @JSONField(name = "current_state_key", ordinal = 3)
    private JSFixedFastJsonStateKey currentStateKey;

    @JSONField(name = "status", ordinal = 4)
    private int status;

    @JSONField(name = "created_date", ordinal = 5)
    private Date createdDate;

    @JSONField(name = "started_date", ordinal = 6)
    private Date startedDate;

    @JSONField(name = "ended_date", ordinal = 7)
    private Date endedDate;

    @JSONField(name = "duration", ordinal = 8, serializeUsing = ToStringSerializer.class)
    private Long duration;

    @JSONField(name = "should_expire_date", ordinal = 9)
    private Date shouldExpireDate;

    @JSONField(name = "should_die_date", ordinal = 10)
    private Date shouldDieDate;

    @JSONField(name = "expired_date", ordinal = 11)
    private Date expiredDate;

    @JSONField(name = "died_date", ordinal = 12)
    private Date diedDate;

    @JSONField(name = "state_changed_date", ordinal = 13)
    private Date stateChangedDate;

    @JSONField(name = "anchor_message", ordinal = 14)
    private String anchorMessage;

    @JSONField(name = "section", ordinal = 15)
    private JSFixedFastJsonSection section;

    @JSONField(name = "current_state", ordinal = 16)
    private JSFixedFastJsonState currentState;

    public JSFixedFastJsonDispTask() {
    }

    public JSFixedFastJsonDispTask(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey sectionKey, JSFixedFastJsonStateKey currentStateKey,
            int status, Date createdDate, Date startedDate, Date endedDate, Long duration, Date shouldExpireDate,
            Date shouldDieDate, Date expiredDate, Date diedDate, Date stateChangedDate, String anchorMessage,
            JSFixedFastJsonSection section, JSFixedFastJsonState currentState
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

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
        this.key = key;
    }

    public JSFixedFastJsonLongIdKey getSectionKey() {
        return sectionKey;
    }

    public void setSectionKey(JSFixedFastJsonLongIdKey sectionKey) {
        this.sectionKey = sectionKey;
    }

    public JSFixedFastJsonStateKey getCurrentStateKey() {
        return currentStateKey;
    }

    public void setCurrentStateKey(JSFixedFastJsonStateKey currentStateKey) {
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

    public JSFixedFastJsonSection getSection() {
        return section;
    }

    public void setSection(JSFixedFastJsonSection section) {
        this.section = section;
    }

    public JSFixedFastJsonState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(JSFixedFastJsonState currentState) {
        this.currentState = currentState;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispTask{" +
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
