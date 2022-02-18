package com.dwarfeng.familyhelper.webapi.sdk.bean.disp.project;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.project.sdk.bean.entity.FastJsonTaskTypeIndicator;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.project.DispTask;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 可展示任务。
 *
 * @author DwArFeng
 * @since 1.0.3
 */
public class JSFixedFastJsonDispTask implements Dto {

    private static final long serialVersionUID = -1042109530908058414L;

    public static JSFixedFastJsonDispTask of(DispTask dispTask) {
        if (Objects.isNull(dispTask)) {
            return null;
        } else {
            return new JSFixedFastJsonDispTask(
                    JSFixedFastJsonLongIdKey.of(dispTask.getKey()),
                    JSFixedFastJsonLongIdKey.of(dispTask.getProjectKey()),
                    dispTask.getType(),
                    dispTask.getName(),
                    dispTask.getDescription(),
                    dispTask.getRemark(),
                    dispTask.getStatus(),
                    dispTask.getCreatedDate(),
                    dispTask.getModifiedDate(),
                    dispTask.getFinishedDate(),
                    dispTask.getTotalMissionCount(),
                    dispTask.getFinishedMissionCount(),
                    JSFixedFastJsonDispProject.of(dispTask.getProject()),
                    FastJsonTaskTypeIndicator.of(dispTask.getTypeIndicator())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "project_key", ordinal = 2)
    private JSFixedFastJsonLongIdKey projectKey;

    @JSONField(name = "type", ordinal = 3)
    private String type;

    @JSONField(name = "name", ordinal = 4)
    private String name;

    @JSONField(name = "description", ordinal = 5)
    private String description;

    @JSONField(name = "remark", ordinal = 6)
    private String remark;

    @JSONField(name = "status", ordinal = 7)
    private int status;

    @JSONField(name = "created_date", ordinal = 8)
    private Date createdDate;

    @JSONField(name = "modified_date", ordinal = 9)
    private Date modifiedDate;

    @JSONField(name = "finished_date", ordinal = 10)
    private Date finishedDate;

    @JSONField(name = "total_mission_count", ordinal = 11)
    private int totalMissionCount;

    @JSONField(name = "finished_mission_count", ordinal = 12)
    private int finishedMissionCount;

    @JSONField(name = "project", ordinal = 13)
    private JSFixedFastJsonDispProject project;

    @JSONField(name = "type_indicator", ordinal = 14)
    private FastJsonTaskTypeIndicator typeIndicator;

    public JSFixedFastJsonDispTask() {
    }

    public JSFixedFastJsonDispTask(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey projectKey, String type, String name,
            String description, String remark, int status, Date createdDate, Date modifiedDate, Date finishedDate,
            int totalMissionCount, int finishedMissionCount, JSFixedFastJsonDispProject project,
            FastJsonTaskTypeIndicator typeIndicator
    ) {
        this.key = key;
        this.projectKey = projectKey;
        this.type = type;
        this.name = name;
        this.description = description;
        this.remark = remark;
        this.status = status;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.finishedDate = finishedDate;
        this.totalMissionCount = totalMissionCount;
        this.finishedMissionCount = finishedMissionCount;
        this.project = project;
        this.typeIndicator = typeIndicator;
    }

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
        this.key = key;
    }

    public JSFixedFastJsonLongIdKey getProjectKey() {
        return projectKey;
    }

    public void setProjectKey(JSFixedFastJsonLongIdKey projectKey) {
        this.projectKey = projectKey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Date getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(Date finishedDate) {
        this.finishedDate = finishedDate;
    }

    public int getTotalMissionCount() {
        return totalMissionCount;
    }

    public void setTotalMissionCount(int totalMissionCount) {
        this.totalMissionCount = totalMissionCount;
    }

    public int getFinishedMissionCount() {
        return finishedMissionCount;
    }

    public void setFinishedMissionCount(int finishedMissionCount) {
        this.finishedMissionCount = finishedMissionCount;
    }

    public JSFixedFastJsonDispProject getProject() {
        return project;
    }

    public void setProject(JSFixedFastJsonDispProject project) {
        this.project = project;
    }

    public FastJsonTaskTypeIndicator getTypeIndicator() {
        return typeIndicator;
    }

    public void setTypeIndicator(FastJsonTaskTypeIndicator typeIndicator) {
        this.typeIndicator = typeIndicator;
    }

    @Override
    public String toString() {
        return "DispTask{" +
                "key=" + key +
                ", projectKey=" + projectKey +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                ", finishedDate=" + finishedDate +
                ", totalMissionCount=" + totalMissionCount +
                ", finishedMissionCount=" + finishedMissionCount +
                ", project=" + project +
                ", typeIndicator=" + typeIndicator +
                '}';
    }
}
