package com.dwarfeng.familyhelper.webapi.stack.bean.disp.life;

import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityTemplateDataInfo;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.math.BigDecimal;

/**
 * 可展示活动模板数据信息。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
public class DispActivityTemplateDataInfo implements Dto {

    private static final long serialVersionUID = -671360667419787054L;

    public static DispActivityTemplateDataInfo of(
            ActivityTemplateDataInfo activityTemplateDataInfo, DispActivityTemplate activityTemplate,
            DispActivityDataItem activityDataItem
    ) {
        if (activityTemplateDataInfo == null) {
            return null;
        } else {
            return new DispActivityTemplateDataInfo(
                    activityTemplateDataInfo.getKey(),
                    activityTemplateDataInfo.getActivityTemplateKey(),
                    activityTemplateDataInfo.getActivityDataItemKey(),
                    activityTemplateDataInfo.getRemark(),
                    activityTemplateDataInfo.getInitialValue(),
                    activityTemplate,
                    activityDataItem
            );
        }
    }

    private LongIdKey key;
    private LongIdKey activityTemplateKey;
    private LongIdKey activityDataItemKey;
    private String remark;
    private BigDecimal initialValue;
    private DispActivityTemplate activityTemplate;
    private DispActivityDataItem activityDataItem;

    public DispActivityTemplateDataInfo() {
    }

    public DispActivityTemplateDataInfo(
            LongIdKey key, LongIdKey activityTemplateKey, LongIdKey activityDataItemKey, String remark,
            BigDecimal initialValue, DispActivityTemplate activityTemplate, DispActivityDataItem activityDataItem
    ) {
        this.key = key;
        this.activityTemplateKey = activityTemplateKey;
        this.activityDataItemKey = activityDataItemKey;
        this.remark = remark;
        this.initialValue = initialValue;
        this.activityTemplate = activityTemplate;
        this.activityDataItem = activityDataItem;
    }

    public LongIdKey getKey() {
        return key;
    }

    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public LongIdKey getActivityTemplateKey() {
        return activityTemplateKey;
    }

    public void setActivityTemplateKey(LongIdKey activityTemplateKey) {
        this.activityTemplateKey = activityTemplateKey;
    }

    public LongIdKey getActivityDataItemKey() {
        return activityDataItemKey;
    }

    public void setActivityDataItemKey(LongIdKey activityDataItemKey) {
        this.activityDataItemKey = activityDataItemKey;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getInitialValue() {
        return initialValue;
    }

    public void setInitialValue(BigDecimal initialValue) {
        this.initialValue = initialValue;
    }

    public DispActivityTemplate getActivityTemplate() {
        return activityTemplate;
    }

    public void setActivityTemplate(DispActivityTemplate activityTemplate) {
        this.activityTemplate = activityTemplate;
    }

    public DispActivityDataItem getActivityDataItem() {
        return activityDataItem;
    }

    public void setActivityDataItem(DispActivityDataItem activityDataItem) {
        this.activityDataItem = activityDataItem;
    }

    @Override
    public String toString() {
        return "DispActivityTemplateDataInfo{" +
                "key=" + key +
                ", activityTemplateKey=" + activityTemplateKey +
                ", activityDataItemKey=" + activityDataItemKey +
                ", remark='" + remark + '\'' +
                ", initialValue=" + initialValue +
                ", activityTemplate=" + activityTemplate +
                ", activityDataItem=" + activityDataItem +
                '}';
    }
}
