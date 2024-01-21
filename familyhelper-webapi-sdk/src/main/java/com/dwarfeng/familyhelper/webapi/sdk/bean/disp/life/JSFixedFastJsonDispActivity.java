package com.dwarfeng.familyhelper.webapi.sdk.bean.disp.life;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.life.sdk.bean.entity.FastJsonActivityTypeIndicator;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.system.FastJsonDispAccount;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispActivity;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 可展示活动。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
public class JSFixedFastJsonDispActivity implements Dto {

    private static final long serialVersionUID = -5329552316797828753L;

    public static JSFixedFastJsonDispActivity of(DispActivity dispActivity) {
        if (Objects.isNull(dispActivity)) {
            return null;
        } else {
            return new JSFixedFastJsonDispActivity(
                    JSFixedFastJsonLongIdKey.of(dispActivity.getKey()),
                    dispActivity.getActivityType(),
                    dispActivity.getName(),
                    dispActivity.getScore(),
                    dispActivity.getRemark(),
                    dispActivity.isLocked(),
                    dispActivity.getStartDate(),
                    dispActivity.getEndDate(),
                    dispActivity.getCreatedDate(),
                    dispActivity.getModifiedDate(),
                    dispActivity.getInspectedDate(),
                    dispActivity.getLockedDate(),
                    FastJsonDispAccount.of(dispActivity.getOwnerAccount()),
                    dispActivity.getPermissionLevel(),
                    FastJsonActivityTypeIndicator.of(dispActivity.getActivityTypeIndicator())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "activity_type", ordinal = 2)
    private String activityType;

    @JSONField(name = "name", ordinal = 3)
    private String name;

    @JSONField(name = "score", ordinal = 4)
    private int score;

    @JSONField(name = "remark", ordinal = 5)
    private String remark;

    @JSONField(name = "locked", ordinal = 6)
    private boolean locked;

    @JSONField(name = "start_date", ordinal = 7)
    private Date startDate;

    @JSONField(name = "end_date", ordinal = 8)
    private Date endDate;

    @JSONField(name = "created_date", ordinal = 9)
    private Date createdDate;

    @JSONField(name = "modified_date", ordinal = 10)
    private Date modifiedDate;

    @JSONField(name = "inspected_date", ordinal = 11)
    private Date inspectedDate;

    @JSONField(name = "locked_date", ordinal = 12)
    private Date lockedDate;

    @JSONField(name = "owner_account", ordinal = 13)
    private FastJsonDispAccount ownerAccount;

    @JSONField(name = "permission_level", ordinal = 14)
    private Integer permissionLevel;

    @JSONField(name = "activity_type_indicator", ordinal = 15)
    private FastJsonActivityTypeIndicator activityTypeIndicator;

    public JSFixedFastJsonDispActivity() {
    }

    public JSFixedFastJsonDispActivity(
            JSFixedFastJsonLongIdKey key, String activityType, String name, int score, String remark, boolean locked,
            Date startDate, Date endDate, Date createdDate, Date modifiedDate, Date inspectedDate, Date lockedDate,
            FastJsonDispAccount ownerAccount, Integer permissionLevel,
            FastJsonActivityTypeIndicator activityTypeIndicator
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

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
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

    public FastJsonDispAccount getOwnerAccount() {
        return ownerAccount;
    }

    public void setOwnerAccount(FastJsonDispAccount ownerAccount) {
        this.ownerAccount = ownerAccount;
    }

    public Integer getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(Integer permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    public FastJsonActivityTypeIndicator getActivityTypeIndicator() {
        return activityTypeIndicator;
    }

    public void setActivityTypeIndicator(FastJsonActivityTypeIndicator activityTypeIndicator) {
        this.activityTypeIndicator = activityTypeIndicator;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispActivity{" +
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
