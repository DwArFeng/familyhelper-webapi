package com.dwarfeng.familyhelper.webapi.sdk.bean.disp.finance;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.sdk.bean.vo.system.FastJsonAccount;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.finance.DispAccountBook;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 可展示权限组。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class JSFixedFastJsonDispAccountBook implements Dto {

    private static final long serialVersionUID = 3983307944081409147L;

    public static JSFixedFastJsonDispAccountBook of(DispAccountBook dispAccountBook) {
        if (Objects.isNull(dispAccountBook)) {
            return null;
        } else {
            return new JSFixedFastJsonDispAccountBook(
                    JSFixedFastJsonLongIdKey.of(dispAccountBook.getKey()),
                    dispAccountBook.getName(),
                    dispAccountBook.getLastRecordedDate(),
                    dispAccountBook.getTotalValue(),
                    dispAccountBook.getRemark(),
                    FastJsonAccount.of(dispAccountBook.getOwnerAccount()),
                    dispAccountBook.isOwnerFlag(),
                    dispAccountBook.isGuestFlag()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "name", ordinal = 2)
    private String name;

    @JSONField(name = "last_recorded_date", ordinal = 3)
    private Date lastRecordedDate;

    @JSONField(name = "total_value", ordinal = 4)
    private BigDecimal totalValue;

    @JSONField(name = "remark", ordinal = 5)
    private String remark;

    @JSONField(name = "owner_account", ordinal = 6)
    private FastJsonAccount ownerAccount;

    @JSONField(name = "owner_flag", ordinal = 7)
    private boolean ownerFlag;

    @JSONField(name = "guest_flag", ordinal = 8)
    private boolean guestFlag;

    public JSFixedFastJsonDispAccountBook() {
    }

    public JSFixedFastJsonDispAccountBook(
            JSFixedFastJsonLongIdKey key, String name, Date lastRecordedDate, BigDecimal totalValue, String remark,
            FastJsonAccount ownerAccount, boolean ownerFlag, boolean guestFlag
    ) {
        this.key = key;
        this.name = name;
        this.lastRecordedDate = lastRecordedDate;
        this.totalValue = totalValue;
        this.remark = remark;
        this.ownerAccount = ownerAccount;
        this.ownerFlag = ownerFlag;
        this.guestFlag = guestFlag;
    }

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getLastRecordedDate() {
        return lastRecordedDate;
    }

    public void setLastRecordedDate(Date lastRecordedDate) {
        this.lastRecordedDate = lastRecordedDate;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public FastJsonAccount getOwnerAccount() {
        return ownerAccount;
    }

    public void setOwnerAccount(FastJsonAccount ownerAccount) {
        this.ownerAccount = ownerAccount;
    }

    public boolean isOwnerFlag() {
        return ownerFlag;
    }

    public void setOwnerFlag(boolean ownerFlag) {
        this.ownerFlag = ownerFlag;
    }

    public boolean isGuestFlag() {
        return guestFlag;
    }

    public void setGuestFlag(boolean guestFlag) {
        this.guestFlag = guestFlag;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispAccountBook{" +
                "key=" + key +
                ", name='" + name + '\'' +
                ", lastRecordedDate=" + lastRecordedDate +
                ", totalValue=" + totalValue +
                ", remark='" + remark + '\'' +
                ", ownerAccount=" + ownerAccount +
                ", ownerFlag=" + ownerFlag +
                ", guestFlag=" + guestFlag +
                '}';
    }
}
