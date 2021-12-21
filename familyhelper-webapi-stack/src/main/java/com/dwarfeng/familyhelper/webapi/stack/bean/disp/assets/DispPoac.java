package com.dwarfeng.familyhelper.webapi.stack.bean.disp.assets;

import com.dwarfeng.familyhelper.assets.stack.bean.entity.Poac;
import com.dwarfeng.familyhelper.assets.stack.bean.key.PoacKey;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.system.DispAccount;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * 可展示资产目录权限。
 *
 * @author DwArFeng
 * @since 1.0.2
 */
public class DispPoac implements Dto {

    private static final long serialVersionUID = -5201646674647309429L;

    public static DispPoac of(
            Poac Poac, DispAssetCatalog assetCatalog, DispAccount account
    ) {
        if (Objects.isNull(assetCatalog)) {
            return null;
        } else {
            return new DispPoac(
                    Poac.getKey(), Poac.getPermissionLevel(), Poac.getRemark(), assetCatalog, account
            );
        }
    }

    private PoacKey key;
    private int permissionLevel;
    private String remark;
    private DispAssetCatalog assetCatalog;
    private DispAccount account;

    public DispPoac() {
    }

    public DispPoac(PoacKey key, int permissionLevel, String remark, DispAssetCatalog assetCatalog, DispAccount account) {
        this.key = key;
        this.permissionLevel = permissionLevel;
        this.remark = remark;
        this.assetCatalog = assetCatalog;
        this.account = account;
    }

    public PoacKey getKey() {
        return key;
    }

    public void setKey(PoacKey key) {
        this.key = key;
    }

    public int getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(int permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public DispAssetCatalog getAssetCatalog() {
        return assetCatalog;
    }

    public void setAssetCatalog(DispAssetCatalog assetCatalog) {
        this.assetCatalog = assetCatalog;
    }

    public DispAccount getAccount() {
        return account;
    }

    public void setAccount(DispAccount account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "DispPoac{" +
                "key=" + key +
                ", permissionLevel=" + permissionLevel +
                ", remark='" + remark + '\'' +
                ", assetCatalog=" + assetCatalog +
                ", account=" + account +
                '}';
    }
}
