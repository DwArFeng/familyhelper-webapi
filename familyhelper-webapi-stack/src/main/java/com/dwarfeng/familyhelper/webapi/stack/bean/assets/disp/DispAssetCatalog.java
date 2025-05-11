package com.dwarfeng.familyhelper.webapi.stack.bean.assets.disp;

import com.dwarfeng.familyhelper.assets.stack.bean.entity.AssetCatalog;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.disp.DispAccount;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;
import java.util.Objects;

/**
 * 可展示资产目录。
 *
 * @author DwArFeng
 * @since 1.0.2
 */
public class DispAssetCatalog implements Dto {

    private static final long serialVersionUID = 1757270426233689297L;

    public static DispAssetCatalog of(
            AssetCatalog assetCatalog, DispAccount ownerAccount, Integer permissionLevel) {
        if (Objects.isNull(assetCatalog)) {
            return null;
        } else {
            return new DispAssetCatalog(
                    assetCatalog.getKey(), assetCatalog.getName(), assetCatalog.getRemark(),
                    assetCatalog.getItemCount(), assetCatalog.getCreatedDate(), ownerAccount, permissionLevel
            );
        }
    }

    private LongIdKey key;
    private String name;
    private String remark;
    private int itemCount;
    private Date createdDate;
    private DispAccount ownerAccount;
    private Integer permissionLevel;

    public DispAssetCatalog() {
    }

    public DispAssetCatalog(
            LongIdKey key, String name, String remark, int itemCount, Date createdDate, DispAccount ownerAccount,
            Integer permissionLevel
    ) {
        this.key = key;
        this.name = name;
        this.remark = remark;
        this.itemCount = itemCount;
        this.createdDate = createdDate;
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
        return "DispAssetCatalog{" +
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
