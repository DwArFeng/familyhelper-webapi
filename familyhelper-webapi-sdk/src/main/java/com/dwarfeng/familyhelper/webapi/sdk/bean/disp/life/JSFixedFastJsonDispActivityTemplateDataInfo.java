package com.dwarfeng.familyhelper.webapi.sdk.bean.disp.life;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispActivityTemplateDataInfo;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * JSFixed FastJson 可展示活动模板数据信息。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
public class JSFixedFastJsonDispActivityTemplateDataInfo implements Dto {

    private static final long serialVersionUID = 4509873344807952134L;

    public static JSFixedFastJsonDispActivityTemplateDataInfo of(DispActivityTemplateDataInfo disp) {
        if (Objects.isNull(disp)) {
            return null;
        } else {
            return new JSFixedFastJsonDispActivityTemplateDataInfo(
                    JSFixedFastJsonLongIdKey.of(disp.getKey()),
                    JSFixedFastJsonLongIdKey.of(disp.getActivityTemplateKey()),
                    JSFixedFastJsonLongIdKey.of(disp.getActivityDataItemKey()),
                    disp.getRemark(),
                    disp.getInitialValue(),
                    JSFixedFastJsonDispActivityTemplate.of(disp.getActivityTemplate()),
                    JSFixedFastJsonDispActivityDataItem.of(disp.getActivityDataItem())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "activity_template_key", ordinal = 2)
    private JSFixedFastJsonLongIdKey activityTemplateKey;

    @JSONField(name = "activity_data_item_key", ordinal = 3)
    private JSFixedFastJsonLongIdKey activityDataItemKey;

    @JSONField(name = "remark", ordinal = 4)
    private String remark;

    @JSONField(name = "initial_value", ordinal = 5)
    private BigDecimal initialValue;

    @JSONField(name = "activity_template", ordinal = 6)
    private JSFixedFastJsonDispActivityTemplate activityTemplate;

    @JSONField(name = "activity_data_item", ordinal = 7)
    private JSFixedFastJsonDispActivityDataItem activityDataItem;

    public JSFixedFastJsonDispActivityTemplateDataInfo() {
    }

    public JSFixedFastJsonDispActivityTemplateDataInfo(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey activityTemplateKey,
            JSFixedFastJsonLongIdKey activityDataItemKey, String remark, BigDecimal initialValue,
            JSFixedFastJsonDispActivityTemplate activityTemplate, JSFixedFastJsonDispActivityDataItem activityDataItem
    ) {
        this.key = key;
        this.activityTemplateKey = activityTemplateKey;
        this.activityDataItemKey = activityDataItemKey;
        this.remark = remark;
        this.initialValue = initialValue;
        this.activityTemplate = activityTemplate;
        this.activityDataItem = activityDataItem;
    }

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
        this.key = key;
    }

    public JSFixedFastJsonLongIdKey getActivityTemplateKey() {
        return activityTemplateKey;
    }

    public void setActivityTemplateKey(JSFixedFastJsonLongIdKey activityTemplateKey) {
        this.activityTemplateKey = activityTemplateKey;
    }

    public JSFixedFastJsonLongIdKey getActivityDataItemKey() {
        return activityDataItemKey;
    }

    public void setActivityDataItemKey(JSFixedFastJsonLongIdKey activityDataItemKey) {
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

    public JSFixedFastJsonDispActivityTemplate getActivityTemplate() {
        return activityTemplate;
    }

    public void setActivityTemplate(JSFixedFastJsonDispActivityTemplate activityTemplate) {
        this.activityTemplate = activityTemplate;
    }

    public JSFixedFastJsonDispActivityDataItem getActivityDataItem() {
        return activityDataItem;
    }

    public void setActivityDataItem(JSFixedFastJsonDispActivityDataItem activityDataItem) {
        this.activityDataItem = activityDataItem;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispActivityTemplateDataInfo{" +
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
