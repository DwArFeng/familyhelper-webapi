package com.dwarfeng.familyhelper.webapi.stack.bean.disp.life;

import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityTemplate;
import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityTypeIndicator;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.system.DispAccount;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;
import java.util.Objects;

/**
 * 可展示活动模板。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
public class DispActivityTemplate implements Dto {

    private static final long serialVersionUID = -508077356221870674L;

    public static DispActivityTemplate of(
            ActivityTemplate dispActivityTemplate,
            DispAccount ownerAccount, Integer permissionLevel, ActivityTypeIndicator activityTypeIndicator
    ) {
        if (Objects.isNull(dispActivityTemplate)) {
            return null;
        } else {
            return new DispActivityTemplate(
                    dispActivityTemplate.getKey(),
                    dispActivityTemplate.getActivityType(),
                    dispActivityTemplate.getName(),
                    dispActivityTemplate.getRemark(),
                    dispActivityTemplate.getActivityNameTemplate(),
                    dispActivityTemplate.getActivityRemarkTemplate(),
                    dispActivityTemplate.getActivityStartDateTemplate(),
                    dispActivityTemplate.getActivityEndDateTemplate(),
                    dispActivityTemplate.getBaselineDate(),
                    dispActivityTemplate.getCreatedDate(),
                    dispActivityTemplate.getModifiedDate(),
                    dispActivityTemplate.getInspectedDate(),
                    dispActivityTemplate.getGeneratedCount(),
                    ownerAccount,
                    permissionLevel,
                    activityTypeIndicator
            );
        }
    }

    private LongIdKey key;
    private String activityType;
    private String name;
    private String remark;
    private String activityNameTemplate;
    private String activityRemarkTemplate;
    private String activityStartDateTemplate;
    private String activityEndDateTemplate;
    private Date baselineDate;
    private Date createdDate;
    private Date modifiedDate;
    private Date inspectedDate;
    private int generatedCount;
    private DispAccount ownerAccount;
    private Integer permissionLevel;
    private ActivityTypeIndicator activityTypeIndicator;

    public DispActivityTemplate() {
    }

    public DispActivityTemplate(
            LongIdKey key, String activityType, String name, String remark, String activityNameTemplate,
            String activityRemarkTemplate, String activityStartDateTemplate, String activityEndDateTemplate,
            Date baselineDate, Date createdDate, Date modifiedDate, Date inspectedDate, int generatedCount,
            DispAccount ownerAccount, Integer permissionLevel, ActivityTypeIndicator activityTypeIndicator
    ) {
        this.key = key;
        this.activityType = activityType;
        this.name = name;
        this.remark = remark;
        this.activityNameTemplate = activityNameTemplate;
        this.activityRemarkTemplate = activityRemarkTemplate;
        this.activityStartDateTemplate = activityStartDateTemplate;
        this.activityEndDateTemplate = activityEndDateTemplate;
        this.baselineDate = baselineDate;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.inspectedDate = inspectedDate;
        this.generatedCount = generatedCount;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getActivityNameTemplate() {
        return activityNameTemplate;
    }

    public void setActivityNameTemplate(String activityNameTemplate) {
        this.activityNameTemplate = activityNameTemplate;
    }

    public String getActivityRemarkTemplate() {
        return activityRemarkTemplate;
    }

    public void setActivityRemarkTemplate(String activityRemarkTemplate) {
        this.activityRemarkTemplate = activityRemarkTemplate;
    }

    public String getActivityStartDateTemplate() {
        return activityStartDateTemplate;
    }

    public void setActivityStartDateTemplate(String activityStartDateTemplate) {
        this.activityStartDateTemplate = activityStartDateTemplate;
    }

    public String getActivityEndDateTemplate() {
        return activityEndDateTemplate;
    }

    public void setActivityEndDateTemplate(String activityEndDateTemplate) {
        this.activityEndDateTemplate = activityEndDateTemplate;
    }

    public Date getBaselineDate() {
        return baselineDate;
    }

    public void setBaselineDate(Date baselineDate) {
        this.baselineDate = baselineDate;
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

    public int getGeneratedCount() {
        return generatedCount;
    }

    public void setGeneratedCount(int generatedCount) {
        this.generatedCount = generatedCount;
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
        return "DispActivityTemplate{" +
                "key=" + key +
                ", activityType='" + activityType + '\'' +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", activityNameTemplate='" + activityNameTemplate + '\'' +
                ", activityRemarkTemplate='" + activityRemarkTemplate + '\'' +
                ", activityStartDateTemplate='" + activityStartDateTemplate + '\'' +
                ", activityEndDateTemplate='" + activityEndDateTemplate + '\'' +
                ", baselineDate=" + baselineDate +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                ", inspectedDate=" + inspectedDate +
                ", generatedCount=" + generatedCount +
                ", ownerAccount=" + ownerAccount +
                ", permissionLevel=" + permissionLevel +
                ", activityTypeIndicator=" + activityTypeIndicator +
                '}';
    }
}
