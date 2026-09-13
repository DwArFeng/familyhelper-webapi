package com.dwarfeng.familyhelper.webapi.stack.bean.fileio.disp;

import com.dwarfeng.familyhelper.webapi.stack.bean.system.disp.DispAccount;
import com.dwarfeng.fileio.stack.bean.entity.ImportTask;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

import java.util.Date;
import java.util.Objects;

/**
 * 可展示导入任务。
 *
 * @author DwArFeng
 * @since 2.1.0
 */
public class DispImportTask implements Dto {

    private static final long serialVersionUID = -1741801151195579067L;

    public static DispImportTask of(ImportTask importTask, DispAccount account) {
        if (Objects.isNull(importTask)) {
            return null;
        } else {
            return new DispImportTask(
                    importTask.getKey(), importTask.getSettingKey(), importTask.getUserKey(),
                    importTask.getStatus(), importTask.getCreateDate(), importTask.getStartDate(),
                    importTask.getEndDate(), importTask.getDuration(), importTask.getDataCount(),
                    importTask.getMessage(), importTask.getShouldExpireDate(), importTask.getShouldDeadDate(),
                    account
            );
        }
    }

    private LongIdKey key;
    private LongIdKey settingKey;
    private StringIdKey userKey;
    private int status;
    private Date createDate;
    private Date startDate;
    private Date endDate;
    private Long duration;
    private int dataCount;
    private String message;
    private Date shouldExpireDate;
    private Date shouldDeadDate;
    private DispAccount account;

    public DispImportTask() {
    }

    public DispImportTask(
            LongIdKey key, LongIdKey settingKey, StringIdKey userKey, int status, Date createDate, Date startDate,
            Date endDate, Long duration, int dataCount, String message, Date shouldExpireDate, Date shouldDeadDate,
            DispAccount account
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

    public LongIdKey getKey() {
        return key;
    }

    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public LongIdKey getSettingKey() {
        return settingKey;
    }

    public void setSettingKey(LongIdKey settingKey) {
        this.settingKey = settingKey;
    }

    public StringIdKey getUserKey() {
        return userKey;
    }

    public void setUserKey(StringIdKey userKey) {
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

    public DispAccount getAccount() {
        return account;
    }

    public void setAccount(DispAccount account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "DispImportTask{" +
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
