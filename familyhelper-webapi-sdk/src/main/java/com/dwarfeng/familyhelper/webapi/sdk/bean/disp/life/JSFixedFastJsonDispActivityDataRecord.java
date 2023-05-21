package com.dwarfeng.familyhelper.webapi.sdk.bean.disp.life;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispActivityDataRecord;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 可展示活动数据记录。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
public class JSFixedFastJsonDispActivityDataRecord implements Dto {

    private static final long serialVersionUID = -5367171049542677119L;

    public static JSFixedFastJsonDispActivityDataRecord of(DispActivityDataRecord dispActivityDataRecord) {
        if (Objects.isNull(dispActivityDataRecord)) {
            return null;
        } else {
            return new JSFixedFastJsonDispActivityDataRecord(
                    JSFixedFastJsonLongIdKey.of(dispActivityDataRecord.getKey()),
                    JSFixedFastJsonLongIdKey.of(dispActivityDataRecord.getItemKey()),
                    JSFixedFastJsonLongIdKey.of(dispActivityDataRecord.getActivityKey()),
                    dispActivityDataRecord.getValue(),
                    dispActivityDataRecord.getRecordedDate(),
                    dispActivityDataRecord.getRemark(),
                    JSFixedFastJsonDispActivityDataItem.of(dispActivityDataRecord.getItem()),
                    JSFixedFastJsonDispActivity.of(dispActivityDataRecord.getActivity())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "item_key", ordinal = 2)
    private JSFixedFastJsonLongIdKey itemKey;

    @JSONField(name = "activity_key", ordinal = 3)
    private JSFixedFastJsonLongIdKey activityKey;

    @JSONField(name = "value", ordinal = 4)
    private BigDecimal value;

    @JSONField(name = "recorded_date", ordinal = 5)
    private Date recordedDate;

    @JSONField(name = "remark", ordinal = 6)
    private String remark;

    @JSONField(name = "item", ordinal = 7)
    private JSFixedFastJsonDispActivityDataItem item;

    @JSONField(name = "activity", ordinal = 8)
    private JSFixedFastJsonDispActivity activity;

    public JSFixedFastJsonDispActivityDataRecord() {
    }

    public JSFixedFastJsonDispActivityDataRecord(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey itemKey, JSFixedFastJsonLongIdKey activityKey,
            BigDecimal value, Date recordedDate, String remark, JSFixedFastJsonDispActivityDataItem item,
            JSFixedFastJsonDispActivity activity
    ) {
        this.key = key;
        this.itemKey = itemKey;
        this.activityKey = activityKey;
        this.value = value;
        this.recordedDate = recordedDate;
        this.remark = remark;
        this.item = item;
        this.activity = activity;
    }

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
        this.key = key;
    }

    public JSFixedFastJsonLongIdKey getItemKey() {
        return itemKey;
    }

    public void setItemKey(JSFixedFastJsonLongIdKey itemKey) {
        this.itemKey = itemKey;
    }

    public JSFixedFastJsonLongIdKey getActivityKey() {
        return activityKey;
    }

    public void setActivityKey(JSFixedFastJsonLongIdKey activityKey) {
        this.activityKey = activityKey;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Date getRecordedDate() {
        return recordedDate;
    }

    public void setRecordedDate(Date recordedDate) {
        this.recordedDate = recordedDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public JSFixedFastJsonDispActivityDataItem getItem() {
        return item;
    }

    public void setItem(JSFixedFastJsonDispActivityDataItem item) {
        this.item = item;
    }

    public JSFixedFastJsonDispActivity getActivity() {
        return activity;
    }

    public void setActivity(JSFixedFastJsonDispActivity activity) {
        this.activity = activity;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispActivityDataRecord{" +
                "key=" + key +
                ", itemKey=" + itemKey +
                ", activityKey=" + activityKey +
                ", value=" + value +
                ", recordedDate=" + recordedDate +
                ", remark='" + remark + '\'' +
                ", item=" + item +
                ", activity=" + activity +
                '}';
    }
}
