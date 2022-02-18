package com.dwarfeng.familyhelper.webapi.stack.bean.disp.project;

import com.dwarfeng.familyhelper.project.stack.bean.entity.Task;
import com.dwarfeng.familyhelper.project.stack.bean.entity.TaskTypeIndicator;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;
import java.util.Objects;

/**
 * 可展示任务。
 *
 * @author DwArFeng
 * @since 1.0.3
 */
public class DispTask implements Dto {

    private static final long serialVersionUID = 8988149336622216253L;

    public static DispTask of(Task task, DispProject dispProject, TaskTypeIndicator typeIndicator) {
        if (Objects.isNull(task)) {
            return null;
        } else {
            return new DispTask(
                    task.getKey(), task.getProjectKey(), task.getType(), task.getName(),
                    task.getRemark(), task.getStatus(), task.getCreatedDate(), task.getModifiedDate(),
                    task.getFinishedDate(), task.getTotalMissionCount(), task.getFinishedMissionCount(),
                    dispProject, typeIndicator
            );
        }
    }

    private LongIdKey key;
    private LongIdKey projectKey;
    private String type;
    private String name;
    private String remark;
    private int status;
    private Date createdDate;
    private Date modifiedDate;
    private Date finishedDate;
    private int totalMissionCount;
    private int finishedMissionCount;
    private DispProject project;
    private TaskTypeIndicator typeIndicator;

    public DispTask() {
    }

    public DispTask(
            LongIdKey key, LongIdKey projectKey, String type, String name, String remark,
            int status, Date createdDate, Date modifiedDate, Date finishedDate, int totalMissionCount,
            int finishedMissionCount, DispProject project, TaskTypeIndicator typeIndicator
    ) {
        this.key = key;
        this.projectKey = projectKey;
        this.type = type;
        this.name = name;
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

    public LongIdKey getKey() {
        return key;
    }

    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public LongIdKey getProjectKey() {
        return projectKey;
    }

    public void setProjectKey(LongIdKey projectKey) {
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

    public DispProject getProject() {
        return project;
    }

    public void setProject(DispProject project) {
        this.project = project;
    }

    public TaskTypeIndicator getTypeIndicator() {
        return typeIndicator;
    }

    public void setTypeIndicator(TaskTypeIndicator typeIndicator) {
        this.typeIndicator = typeIndicator;
    }

    @Override
    public String toString() {
        return "DispTask{" +
                "key=" + key +
                ", projectKey=" + projectKey +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
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
