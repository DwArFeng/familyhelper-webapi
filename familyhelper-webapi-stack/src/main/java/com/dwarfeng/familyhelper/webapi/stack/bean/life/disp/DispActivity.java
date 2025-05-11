package com.dwarfeng.familyhelper.webapi.stack.bean.life.disp;

import com.dwarfeng.familyhelper.life.stack.bean.entity.Activity;
import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityTypeIndicator;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.disp.DispAccount;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;
import java.util.Objects;

/**
 * 可展示活动。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
public class DispActivity implements Dto {

    private static final long serialVersionUID = 8196254451312488415L;

    public static DispActivity of(
            Activity activity,
            DispAccount ownerAccount, Integer permissionLevel, ActivityTypeIndicator activityTypeIndicator
    ) {
        if (Objects.isNull(activity)) {
            return null;
        } else {
            return new DispActivity(
                    activity.getKey(),
                    activity.getActivityType(),
                    activity.getName(),
                    activity.getScore(),
                    activity.getRemark(),
                    activity.isLocked(),
                    activity.getStartDate(),
                    activity.getEndDate(),
                    activity.getCreatedDate(),
                    activity.getModifiedDate(),
                    activity.getInspectedDate(),
                    activity.getLockedDate(),
                    ownerAccount,
                    permissionLevel,
                    activityTypeIndicator
            );
        }
    }

    private LongIdKey key;
    private String activityType;
    private String name;
    private int score;
    private String remark;
    private boolean locked;
    private Date startDate;
    private Date endDate;
    private Date createdDate;
    private Date modifiedDate;
    private Date inspectedDate;
    private Date lockedDate;
    private DispAccount ownerAccount;
    private Integer permissionLevel;
    private ActivityTypeIndicator activityTypeIndicator;

    public DispActivity() {
    }

    public DispActivity(
            LongIdKey key, String activityType, String name, int score, String remark, boolean locked,
            Date startDate, Date endDate, Date createdDate, Date modifiedDate, Date inspectedDate, Date lockedDate,
            DispAccount ownerAccount, Integer permissionLevel, ActivityTypeIndicator activityTypeIndicator
    ) {
        this.key = key;
        this.activityType = activityType;
        this.name = name;
        this.score = score;
        this.remark = remark;
        this.locked = locked;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.inspectedDate = inspectedDate;
        this.lockedDate = lockedDate;
        this.ownerAccount = ownerAccount;
        this.permissionLevel = permissionLevel;
        this.activityTypeIndicator = activityTypeIndicator;
    }

    public LongIdKey getKey() {
        return key;
    }

    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
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

    public Date getInspectedDate() {
        return inspectedDate;
    }

    public void setInspectedDate(Date inspectedDate) {
        this.inspectedDate = inspectedDate;
    }

    public Date getLockedDate() {
        return lockedDate;
    }

    public void setLockedDate(Date lockedDate) {
        this.lockedDate = lockedDate;
    }

    public DispAccount getOwnerAccount() {
        return ownerAccount;
    }

    public void setOwnerAccount(DispAccount ownerAccount) {
        this.ownerAccount = ownerAccount;
    }

    public Integer getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(Integer permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    public ActivityTypeIndicator getActivityTypeIndicator() {
        return activityTypeIndicator;
    }

    public void setActivityTypeIndicator(ActivityTypeIndicator activityTypeIndicator) {
        this.activityTypeIndicator = activityTypeIndicator;
    }

    @Override
    public String toString() {
        return "DispActivity{" +
                "key=" + key +
                ", activityType='" + activityType + '\'' +
                ", name='" + name + '\'' +
                ", score=" + score +
                ", remark='" + remark + '\'' +
                ", locked=" + locked +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                ", inspectedDate=" + inspectedDate +
                ", lockedDate=" + lockedDate +
                ", ownerAccount=" + ownerAccount +
                ", permissionLevel=" + permissionLevel +
                ", activityTypeIndicator=" + activityTypeIndicator +
                '}';
    }
}
