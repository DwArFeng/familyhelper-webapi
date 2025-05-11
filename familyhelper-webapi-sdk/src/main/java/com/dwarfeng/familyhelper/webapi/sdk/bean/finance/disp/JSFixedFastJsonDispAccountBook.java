package com.dwarfeng.familyhelper.webapi.sdk.bean.finance.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.sdk.bean.system.disp.FastJsonDispAccount;
import com.dwarfeng.familyhelper.webapi.stack.bean.finance.disp.DispAccountBook;
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

    private static final long serialVersionUID = 6472766868856219550L;

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
                    FastJsonDispAccount.of(dispAccountBook.getOwnerAccount()),
                    dispAccountBook.getPermissionLevel()
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
    private FastJsonDispAccount ownerAccount;

    /**
     * @since 1.0.2
     */
    @JSONField(name = "permission_level", ordinal = 7)
    private Integer permissionLevel;

    public JSFixedFastJsonDispAccountBook() {
    }

    public JSFixedFastJsonDispAccountBook(
            JSFixedFastJsonLongIdKey key, String name, Date lastRecordedDate, BigDecimal totalValue, String remark,
            FastJsonDispAccount ownerAccount, Integer permissionLevel
    ) {
        this.key = key;
        this.name = name;
        this.lastRecordedDate = lastRecordedDate;
        this.totalValue = totalValue;
        this.remark = remark;
        this.ownerAccount = ownerAccount;
        this.permissionLevel = permissionLevel;
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

    @Override
    public String toString() {
        return "JSFixedFastJsonDispAccountBook{" +
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
