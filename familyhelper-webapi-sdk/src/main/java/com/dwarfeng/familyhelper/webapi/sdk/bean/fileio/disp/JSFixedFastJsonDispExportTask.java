package com.dwarfeng.familyhelper.webapi.sdk.bean.fileio.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.sdk.bean.system.disp.FastJsonDispAccount;
import com.dwarfeng.familyhelper.webapi.stack.bean.fileio.disp.DispExportTask;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 可展示导出任务。
 *
 * @author DwArFeng
 * @since 2.1.0
 */
public class JSFixedFastJsonDispExportTask implements Dto {

    private static final long serialVersionUID = -2334982999518036050L;

    public static JSFixedFastJsonDispExportTask of(DispExportTask dispExportTask) {
        if (Objects.isNull(dispExportTask)) {
            return null;
        } else {
            return new JSFixedFastJsonDispExportTask(
                    JSFixedFastJsonLongIdKey.of(dispExportTask.getKey()),
                    JSFixedFastJsonLongIdKey.of(dispExportTask.getSettingKey()),
                    FastJsonStringIdKey.of(dispExportTask.getUserKey()),
                    dispExportTask.getStatus(),
                    dispExportTask.getCreateDate(),
                    dispExportTask.getStartDate(),
                    dispExportTask.getEndDate(),
                    dispExportTask.getDuration(),
                    dispExportTask.getDataCount(),
                    dispExportTask.getMessage(),
                    dispExportTask.getShouldExpireDate(),
                    dispExportTask.getShouldDeadDate(),
                    FastJsonDispAccount.of(dispExportTask.getAccount())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "setting_key", ordinal = 2)
    private JSFixedFastJsonLongIdKey settingKey;

    @JSONField(name = "user_key", ordinal = 3)
    private FastJsonStringIdKey userKey;

    @JSONField(name = "status", ordinal = 4)
    private int status;

    @JSONField(name = "create_date", ordinal = 5)
    private Date createDate;

    @JSONField(name = "start_date", ordinal = 6)
    private Date startDate;

    @JSONField(name = "end_date", ordinal = 7)
    private Date endDate;

    @JSONField(name = "duration", ordinal = 8)
    private Long duration;

    @JSONField(name = "data_count", ordinal = 9)
    private int dataCount;

    @JSONField(name = "message", ordinal = 10)
    private String message;

    @JSONField(name = "should_dead_date", ordinal = 11)
    private Date shouldExpireDate;

    @JSONField(name = "last_beat_date", ordinal = 12)
    private Date shouldDeadDate;

    @JSONField(name = "account", ordinal = 13)
    private FastJsonDispAccount account;

    public JSFixedFastJsonDispExportTask() {
    }

    public JSFixedFastJsonDispExportTask(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey settingKey, FastJsonStringIdKey userKey, int status,
            Date createDate, Date startDate, Date endDate, Long duration, int dataCount, String message,
            Date shouldExpireDate, Date shouldDeadDate, FastJsonDispAccount account
    ) {
        this.key = key;
        this.settingKey = settingKey;
        this.userKey = userKey;
        this.status = status;
        this.createDate = createDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.dataCount = dataCount;
        this.message = message;
        this.shouldExpireDate = shouldExpireDate;
        this.shouldDeadDate = shouldDeadDate;
        this.account = account;
    }

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
        this.key = key;
    }

    public JSFixedFastJsonLongIdKey getSettingKey() {
        return settingKey;
    }

    public void setSettingKey(JSFixedFastJsonLongIdKey settingKey) {
        this.settingKey = settingKey;
    }

    public FastJsonStringIdKey getUserKey() {
        return userKey;
    }

    public void setUserKey(FastJsonStringIdKey userKey) {
        this.userKey = userKey;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public int getDataCount() {
        return dataCount;
    }

    public void setDataCount(int dataCount) {
        this.dataCount = dataCount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getShouldExpireDate() {
        return shouldExpireDate;
    }

    public void setShouldExpireDate(Date shouldExpireDate) {
        this.shouldExpireDate = shouldExpireDate;
    }

    public Date getShouldDeadDate() {
        return shouldDeadDate;
    }

    public void setShouldDeadDate(Date shouldDeadDate) {
        this.shouldDeadDate = shouldDeadDate;
    }

    public FastJsonDispAccount getAccount() {
        return account;
    }

    public void setAccount(FastJsonDispAccount account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispExportTask{" +
                "key=" + key +
                ", settingKey=" + settingKey +
                ", userKey=" + userKey +
                ", status=" + status +
                ", createDate=" + createDate +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", duration=" + duration +
                ", dataCount=" + dataCount +
                ", message='" + message + '\'' +
                ", shouldExpireDate=" + shouldExpireDate +
                ", shouldDeadDate=" + shouldDeadDate +
                ", account=" + account +
                '}';
    }
}
