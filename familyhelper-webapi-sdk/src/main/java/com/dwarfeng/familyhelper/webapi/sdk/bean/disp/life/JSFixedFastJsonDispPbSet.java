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

    private static final long serialVersionUID = 5620736247188398750L;

    public static JSFixedFastJsonDispPbSet of(DispPbSet dispPbSet) {
        if (Objects.isNull(dispPbSet)) {
            return null;
        } else {
            return new JSFixedFastJsonDispPbSet(
                    JSFixedFastJsonLongIdKey.of(dispPbSet.getKey()),
                    dispPbSet.getName(),
                    dispPbSet.getRemark(),
                    dispPbSet.getCreatedDate(),
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

    @JSONField(name = "owner_account", ordinal = 5)
    private FastJsonDispAccount ownerAccount;

    @JSONField(name = "permission_level", ordinal = 6)
    private Integer permissionLevel;

    public JSFixedFastJsonDispPbSet() {
    }

    public JSFixedFastJsonDispPbSet(
            JSFixedFastJsonLongIdKey key, String name, String remark, Date createdDate,
            FastJsonDispAccount ownerAccount, Integer permissionLevel
    ) {
        this.key = key;
        this.name = name;
        this.remark = remark;
        this.createdDate = createdDate;
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
                ", ownerAccount=" + ownerAccount +
                ", permissionLevel=" + permissionLevel +
                '}';
    }
}
