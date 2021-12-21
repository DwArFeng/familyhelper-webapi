package com.dwarfeng.familyhelper.webapi.sdk.bean.disp.assets;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.system.FastJsonDispAccount;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.assets.DispAssetCatalog;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 可展示账本。
 *
 * @author DwArFeng
 * @since 1.0.2
 */
public class JSFixedFastJsonDispAssetCatalog implements Dto {

    private static final long serialVersionUID = -301790000432486130L;

    public static JSFixedFastJsonDispAssetCatalog of(DispAssetCatalog dispAssetCatalog) {
        if (Objects.isNull(dispAssetCatalog)) {
            return null;
        } else {
            return new JSFixedFastJsonDispAssetCatalog(
                    JSFixedFastJsonLongIdKey.of(dispAssetCatalog.getKey()),
                    dispAssetCatalog.getName(),
                    dispAssetCatalog.getRemark(),
                    dispAssetCatalog.getItemCount(),
                    dispAssetCatalog.getCreatedDate(),
                    FastJsonDispAccount.of(dispAssetCatalog.getOwnerAccount()),
                    dispAssetCatalog.getPermissionLevel()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "name", ordinal = 2)
    private String name;

    @JSONField(name = "remark", ordinal = 3)
    private String remark;

    @JSONField(name = "item_count", ordinal = 4)
    private int itemCount;

    @JSONField(name = "created_date", ordinal = 5)
    private Date createdDate;

    @JSONField(name = "owner_account", ordinal = 6)
    private FastJsonDispAccount ownerAccount;

    @JSONField(name = "permission_level", ordinal = 7)
    private Integer permissionLevel;

    public JSFixedFastJsonDispAssetCatalog() {
    }

    public JSFixedFastJsonDispAssetCatalog(
            JSFixedFastJsonLongIdKey key, String name, String remark, int itemCount, Date createdDate,
            FastJsonDispAccount ownerAccount, Integer permissionLevel
    ) {
        this.key = key;
        this.name = name;
        this.remark = remark;
        this.itemCount = itemCount;
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

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
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
        return "JSFixedFastJsonDispAssetCatalog{" +
                "key=" + key +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", itemCount=" + itemCount +
                ", createdDate=" + createdDate +
                ", ownerAccount=" + ownerAccount +
                ", permissionLevel=" + permissionLevel +
                '}';
    }
}
