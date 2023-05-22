package com.dwarfeng.familyhelper.webapi.sdk.bean.disp.life;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.life.sdk.bean.entity.FastJsonActivityTypeIndicator;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.system.FastJsonDispAccount;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispActivityTemplate;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 可展示活动模板。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
public class JSFixedFastJsonDispActivityTemplate implements Dto {

    private static final long serialVersionUID = 2417667008067359592L;

    public static JSFixedFastJsonDispActivityTemplate of(DispActivityTemplate dispActivityTemplate) {
        if (Objects.isNull(dispActivityTemplate)) {
            return null;
        } else {
            return new JSFixedFastJsonDispActivityTemplate(
                    JSFixedFastJsonLongIdKey.of(dispActivityTemplate.getKey()),
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
                    FastJsonDispAccount.of(dispActivityTemplate.getOwnerAccount()),
                    dispActivityTemplate.getPermissionLevel(),
                    FastJsonActivityTypeIndicator.of(dispActivityTemplate.getActivityTypeIndicator())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "activity_type", ordinal = 2)
    private String activityType;

    @JSONField(name = "name", ordinal = 3)
    private String name;

    @JSONField(name = "remark", ordinal = 4)
    private String remark;

    @JSONField(name = "activity_name_template", ordinal = 5)
    private String activityNameTemplate;

    @JSONField(name = "activity_remark_template", ordinal = 6)
    private String activityRemarkTemplate;

    @JSONField(name = "activity_start_date_template", ordinal = 7)
    private String activityStartDateTemplate;

    @JSONField(name = "activity_end_date_template", ordinal = 8)
    private String activityEndDateTemplate;

    @JSONField(name = "baseline_date", ordinal = 9)
    private Date baselineDate;

    @JSONField(name = "created_date", ordinal = 10)
    private Date createdDate;

    @JSONField(name = "modified_date", ordinal = 11)
    private Date modifiedDate;

    @JSONField(name = "inspected_date", ordinal = 12)
    private Date inspectedDate;

    @JSONField(name = "generated_count", ordinal = 13)
    private int generatedCount;

    @JSONField(name = "owner_account", ordinal = 14)
    private FastJsonDispAccount ownerAccount;

    @JSONField(name = "permission_level", ordinal = 15)
    private Integer permissionLevel;

    @JSONField(name = "activity_type_indicator", ordinal = 16)
    private FastJsonActivityTypeIndicator activityTypeIndicator;

    public JSFixedFastJsonDispActivityTemplate() {
    }

    public JSFixedFastJsonDispActivityTemplate(
            JSFixedFastJsonLongIdKey key, String activityType, String name, String remark, String activityNameTemplate,
            String activityRemarkTemplate, String activityStartDateTemplate, String activityEndDateTemplate,
            Date baselineDate, Date createdDate, Date modifiedDate, Date inspectedDate, int generatedCount,
            FastJsonDispAccount ownerAccount, Integer permissionLevel,
            FastJsonActivityTypeIndicator activityTypeIndicator
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
        return "JSFixedFastJsonDispActivityTemplate{" +
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
