package com.dwarfeng.familyhelper.webapi.stack.bean.disp.life;

import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityDataRecord;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * 可展示活动数据记录。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
public class DispActivityDataRecord implements Dto {

    private static final long serialVersionUID = 4464769307377638043L;

    public static DispActivityDataRecord of(
            ActivityDataRecord record, DispActivityDataItem item, DispActivity activity
    ) {
        if (Objects.isNull(record)) {
            return null;
        }
        return new DispActivityDataRecord(
                record.getKey(), record.getItemKey(), record.getActivityKey(), record.getValue(),
                record.getRecordedDate(), record.getRemark(), item, activity
        );
    }

    private LongIdKey key;
    private LongIdKey itemKey;
    private LongIdKey activityKey;
    private BigDecimal value;
    private Date recordedDate;
    private String remark;
    private DispActivityDataItem item;
    private DispActivity activity;

    public DispActivityDataRecord() {
    }

    public DispActivityDataRecord(
            LongIdKey key, LongIdKey itemKey, LongIdKey activityKey, BigDecimal value, Date recordedDate,
            String remark, DispActivityDataItem item, DispActivity activity
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

    public LongIdKey getKey() {
        return key;
    }

    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public LongIdKey getItemKey() {
        return itemKey;
    }

    public void setItemKey(LongIdKey itemKey) {
        this.itemKey = itemKey;
    }

    public LongIdKey getActivityKey() {
        return activityKey;
    }

    public void setActivityKey(LongIdKey activityKey) {
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

    public DispActivityDataItem getItem() {
        return item;
    }

    public void setItem(DispActivityDataItem item) {
        this.item = item;
    }

    public DispActivity getActivity() {
        return activity;
    }

    public void setActivity(DispActivity activity) {
        this.activity = activity;
    }

    @Override
    public String toString() {
        return "DispActivityDataRecord{" +
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
