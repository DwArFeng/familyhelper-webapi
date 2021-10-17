package com.dwarfeng.familyhelper.webapi.stack.bean.disp.finance;

import com.dwarfeng.familyhelper.finance.stack.bean.entity.AccountBook;
import com.dwarfeng.familyhelper.webapi.stack.bean.vo.system.Account;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * 可展示账本。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class DispAccountBook implements Dto {

    private static final long serialVersionUID = 402264194446632476L;

    public static DispAccountBook of(
            AccountBook accountBook, Account ownerAccount, boolean ownerFlag, boolean guestFlag) {
        if (Objects.isNull(accountBook)) {
            return null;
        } else {
            return new DispAccountBook(
                    accountBook.getKey(), accountBook.getName(), accountBook.getLastRecordedDate(),
                    accountBook.getTotalValue(), accountBook.getRemark(), ownerAccount, ownerFlag, guestFlag
            );
        }
    }

    private LongIdKey key;
    private String name;
    private Date lastRecordedDate;
    private BigDecimal totalValue;
    private String remark;
    private Account ownerAccount;
    private boolean ownerFlag;
    private boolean guestFlag;

    public DispAccountBook() {
    }

    public DispAccountBook(
            LongIdKey key, String name, Date lastRecordedDate, BigDecimal totalValue, String remark,
            Account ownerAccount, boolean ownerFlag, boolean guestFlag
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

    public LongIdKey getKey() {
        return key;
    }

    public void setKey(LongIdKey key) {
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

    public Account getOwnerAccount() {
        return ownerAccount;
    }

    public void setOwnerAccount(Account ownerAccount) {
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
        return "DispAccountBook{" +
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
