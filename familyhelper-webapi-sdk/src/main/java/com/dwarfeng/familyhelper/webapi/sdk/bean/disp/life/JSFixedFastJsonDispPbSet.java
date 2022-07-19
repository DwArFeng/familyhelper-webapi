package com.dwarfeng.familyhelper.webapi.sdk.bean.disp.life;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.system.FastJsonDispAccount;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispPbSet;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 可展示个人记录集合。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public class JSFixedFastJsonDispPbSet implements Dto {

    private static final long serialVersionUID = -8317257153651792153L;

    public static JSFixedFastJsonDispPbSet of(DispPbSet dispPbSet) {
        if (Objects.isNull(dispPbSet)) {
            return null;
        } else {
            return new JSFixedFastJsonDispPbSet(
                    JSFixedFastJsonLongIdKey.of(dispPbSet.getKey()),
                    dispPbSet.getName(),
                    dispPbSet.getRemark(),
                    dispPbSet.getCreatedDate(),
                    dispPbSet.getItemCount(),
                    dispPbSet.getLastRecordedDate(),
                    FastJsonDispAccount.of(dispPbSet.getOwnerAccount()),
                    dispPbSet.getPermissionLevel()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "name", ordinal = 2)
    private String name;

    @JSONField(name = "remark", ordinal = 3)
    private String remark;

    @JSONField(name = "created_date", ordinal = 4)
    private Date createdDate;

    @JSONField(name = "item_count", ordinal = 5)
    private int itemCount;

    @JSONField(name = "last_recorded_date", ordinal = 6)
    private Date lastRecordedDate;

    @JSONField(name = "owner_account", ordinal = 7)
    private FastJsonDispAccount ownerAccount;

    @JSONField(name = "permission_level", ordinal = 8)
    private Integer permissionLevel;

    public JSFixedFastJsonDispPbSet() {
    }

    public JSFixedFastJsonDispPbSet(
            JSFixedFastJsonLongIdKey key, String name, String remark, Date createdDate,
            int itemCount, Date lastRecordedDate, FastJsonDispAccount ownerAccount, Integer permissionLevel
    ) {
        this.key = key;
        this.name = name;
        this.remark = remark;
        this.createdDate = createdDate;
        this.itemCount = itemCount;
        this.lastRecordedDate = lastRecordedDate;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public Date getLastRecordedDate() {
        return lastRecordedDate;
    }

    public void setLastRecordedDate(Date lastRecordedDate) {
        this.lastRecordedDate = lastRecordedDate;
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
        return "JSFixedFastJsonDispPbSet{" +
                "key=" + key +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", createdDate=" + createdDate +
                ", itemCount=" + itemCount +
                ", lastRecordedDate=" + lastRecordedDate +
                ", ownerAccount=" + ownerAccount +
                ", permissionLevel=" + permissionLevel +
                '}';
    }
}
