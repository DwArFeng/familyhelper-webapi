package com.dwarfeng.familyhelper.webapi.stack.bean.disp.finance;

import com.dwarfeng.familyhelper.finance.stack.bean.entity.FundChange;
import com.dwarfeng.familyhelper.finance.stack.bean.entity.FundChangeTypeIndicator;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * 可展示资金变更。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class DispFundChange implements Dto {

    private static final long serialVersionUID = -7364559738649870267L;

    public static DispFundChange of(
            FundChange fundChange, DispAccountBook accountBook, FundChangeTypeIndicator typeIndicator) {
        if (Objects.isNull(fundChange)) {
            return null;
        } else {
            return new DispFundChange(
                    fundChange.getKey(),
                    fundChange.getAccountBookKey(),
                    fundChange.getDelta(),
                    fundChange.getChangeType(),
                    fundChange.getHappenedDate(),
                    fundChange.getRemark(),
                    fundChange.getRecordedDate(),
                    accountBook,
                    typeIndicator
            );
        }
    }

    private LongIdKey key;
    private LongIdKey accountBookKey;
    private BigDecimal delta;
    private String changeType;
    private Date happenedDate;
    private String remark;

    /**
     * @since 1.5.0
     */
    private Date recordedDate;

    private DispAccountBook accountBook;
    private FundChangeTypeIndicator typeIndicator;

    public DispFundChange() {
    }

    public DispFundChange(
            LongIdKey key, LongIdKey accountBookKey, BigDecimal delta, String changeType, Date happenedDate,
            String remark, Date recordedDate, DispAccountBook accountBook, FundChangeTypeIndicator typeIndicator
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

    public LongIdKey getKey() {
        return key;
    }

    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public LongIdKey getAccountBookKey() {
        return accountBookKey;
    }

    public void setAccountBookKey(LongIdKey accountBookKey) {
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

    public DispAccountBook getAccountBook() {
        return accountBook;
    }

    public void setAccountBook(DispAccountBook accountBook) {
        this.accountBook = accountBook;
    }

    public FundChangeTypeIndicator getTypeIndicator() {
        return typeIndicator;
    }

    public void setTypeIndicator(FundChangeTypeIndicator typeIndicator) {
        this.typeIndicator = typeIndicator;
    }

    @Override
    public String toString() {
        return "DispFundChange{" +
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
