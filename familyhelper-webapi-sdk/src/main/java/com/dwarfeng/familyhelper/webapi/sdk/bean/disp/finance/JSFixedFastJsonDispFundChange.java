package com.dwarfeng.familyhelper.webapi.sdk.bean.disp.finance;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.finance.sdk.bean.entity.FastJsonFundChangeTypeIndicator;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.finance.DispFundChange;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 可展示资金变更。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class JSFixedFastJsonDispFundChange implements Dto {

    private static final long serialVersionUID = -2158661360426119847L;

    public static JSFixedFastJsonDispFundChange of(DispFundChange dispFundChange) {
        if (Objects.isNull(dispFundChange)) {
            return null;
        } else {
            return new JSFixedFastJsonDispFundChange(
                    JSFixedFastJsonLongIdKey.of(dispFundChange.getKey()),
                    JSFixedFastJsonLongIdKey.of(dispFundChange.getAccountBookKey()),
                    dispFundChange.getDelta(),
                    dispFundChange.getChangeType(),
                    dispFundChange.getHappenedDate(),
                    dispFundChange.getRemark(),
                    dispFundChange.getRecordedDate(),
                    JSFixedFastJsonDispAccountBook.of(dispFundChange.getAccountBook()),
                    FastJsonFundChangeTypeIndicator.of(dispFundChange.getTypeIndicator())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "account_book_key", ordinal = 2)
    private JSFixedFastJsonLongIdKey accountBookKey;

    @JSONField(name = "delta", ordinal = 3)
    private BigDecimal delta;

    @JSONField(name = "change_type", ordinal = 4)
    private String changeType;

    @JSONField(name = "happened_date", ordinal = 5)
    private Date happenedDate;

    @JSONField(name = "remark", ordinal = 6)
    private String remark;

    @JSONField(name = "recorded_date", ordinal = 7)
    private Date recordedDate;

    @JSONField(name = "account_book", ordinal = 8)
    private JSFixedFastJsonDispAccountBook accountBook;

    @JSONField(name = "type_indicator", ordinal = 9)
    private FastJsonFundChangeTypeIndicator typeIndicator;

    public JSFixedFastJsonDispFundChange() {
    }

    public JSFixedFastJsonDispFundChange(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey accountBookKey, BigDecimal delta, String changeType,
            Date happenedDate, String remark, Date recordedDate, JSFixedFastJsonDispAccountBook accountBook,
            FastJsonFundChangeTypeIndicator typeIndicator
    ) {
        this.key = key;
        this.accountBookKey = accountBookKey;
        this.delta = delta;
        this.changeType = changeType;
        this.happenedDate = happenedDate;
        this.remark = remark;
        this.recordedDate = recordedDate;
        this.accountBook = accountBook;
        this.typeIndicator = typeIndicator;
    }

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
        this.key = key;
    }

    public JSFixedFastJsonLongIdKey getAccountBookKey() {
        return accountBookKey;
    }

    public void setAccountBookKey(JSFixedFastJsonLongIdKey accountBookKey) {
        this.accountBookKey = accountBookKey;
    }

    public BigDecimal getDelta() {
        return delta;
    }

    public void setDelta(BigDecimal delta) {
        this.delta = delta;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public Date getHappenedDate() {
        return happenedDate;
    }

    public void setHappenedDate(Date happenedDate) {
        this.happenedDate = happenedDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getRecordedDate() {
        return recordedDate;
    }

    public void setRecordedDate(Date recordedDate) {
        this.recordedDate = recordedDate;
    }

    public JSFixedFastJsonDispAccountBook getAccountBook() {
        return accountBook;
    }

    public void setAccountBook(JSFixedFastJsonDispAccountBook accountBook) {
        this.accountBook = accountBook;
    }

    public FastJsonFundChangeTypeIndicator getTypeIndicator() {
        return typeIndicator;
    }

    public void setTypeIndicator(FastJsonFundChangeTypeIndicator typeIndicator) {
        this.typeIndicator = typeIndicator;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispFundChange{" +
                "key=" + key +
                ", accountBookKey=" + accountBookKey +
                ", delta=" + delta +
                ", changeType='" + changeType + '\'' +
                ", happenedDate=" + happenedDate +
                ", remark='" + remark + '\'' +
                ", recordedDate=" + recordedDate +
                ", accountBook=" + accountBook +
                ", typeIndicator=" + typeIndicator +
                '}';
    }
}
