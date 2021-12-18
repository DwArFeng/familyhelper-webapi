package com.dwarfeng.familyhelper.webapi.stack.bean.disp.finance;

import com.dwarfeng.familyhelper.finance.stack.bean.entity.AccountBook;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.system.DispAccount;
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

    private static final long serialVersionUID = 5012447170219306828L;

    public static DispAccountBook of(
            AccountBook accountBook, DispAccount ownerAccount, Integer permissionLevel) {
        if (Objects.isNull(accountBook)) {
            return null;
        } else {
            return new DispAccountBook(
                    accountBook.getKey(), accountBook.getName(), accountBook.getLastRecordedDate(),
                    accountBook.getTotalValue(), accountBook.getRemark(), ownerAccount, permissionLevel
            );
        }
    }

    private LongIdKey key;
    private String name;
    private Date lastRecordedDate;
    private BigDecimal totalValue;
    private String remark;
    private DispAccount ownerAccount;
    /**
     * @since 1.0.2
     */
    private Integer permissionLevel;

    public DispAccountBook() {
    }

    public DispAccountBook(
            LongIdKey key, String name, Date lastRecordedDate, BigDecimal totalValue, String remark,
            DispAccount ownerAccount, Integer permissionLevel
    ) {
        this.key = key;
        this.name = name;
        this.lastRecordedDate = lastRecordedDate;
        this.totalValue = totalValue;
        this.remark = remark;
        this.ownerAccount = ownerAccount;
        this.permissionLevel = permissionLevel;
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

    @Override
    public String toString() {
        return "DispAccountBook{" +
                "key=" + key +
                ", name='" + name + '\'' +
                ", lastRecordedDate=" + lastRecordedDate +
                ", totalValue=" + totalValue +
                ", remark='" + remark + '\'' +
                ", ownerAccount=" + ownerAccount +
                ", permissionLevel=" + permissionLevel +
                '}';
    }
}
