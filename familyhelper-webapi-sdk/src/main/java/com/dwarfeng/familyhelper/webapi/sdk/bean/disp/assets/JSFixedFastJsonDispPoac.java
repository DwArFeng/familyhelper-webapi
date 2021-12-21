package com.dwarfeng.familyhelper.webapi.sdk.bean.disp.assets;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.assets.sdk.bean.key.JSFixedFastJsonPoacKey;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.system.FastJsonDispAccount;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.assets.DispPoac;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * JSFixed FastJson 可展示资产目录权限。
 *
 * @author DwArFeng
 * @since 1.0.2
 */
public class JSFixedFastJsonDispPoac implements Dto {

    private static final long serialVersionUID = -1871092533001059371L;

    public static JSFixedFastJsonDispPoac of(DispPoac dispPoac) {
        if (Objects.isNull(dispPoac)) {
            return null;
        } else {
            return new JSFixedFastJsonDispPoac(
                    JSFixedFastJsonPoacKey.of(dispPoac.getKey()),
                    dispPoac.getPermissionLevel(),
                    dispPoac.getRemark(),
                    JSFixedFastJsonDispAssetCatalog.of(dispPoac.getAssetCatalog()),
                    FastJsonDispAccount.of(dispPoac.getAccount())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonPoacKey key;

    @JSONField(name = "permission_level", ordinal = 2)
    private int permissionLevel;

    @JSONField(name = "remark", ordinal = 3)
    private String remark;

    @JSONField(name = "asset_catalog", ordinal = 4)
    private JSFixedFastJsonDispAssetCatalog assetCatalog;

    @JSONField(name = "account", ordinal = 5)
    private FastJsonDispAccount account;

    public JSFixedFastJsonDispPoac() {
    }

    public JSFixedFastJsonDispPoac(
            JSFixedFastJsonPoacKey key, int permissionLevel, String remark, JSFixedFastJsonDispAssetCatalog assetCatalog,
            FastJsonDispAccount account
    ) {
        this.key = key;
        this.permissionLevel = permissionLevel;
        this.remark = remark;
        this.assetCatalog = assetCatalog;
        this.account = account;
    }

    public JSFixedFastJsonPoacKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonPoacKey key) {
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

    public JSFixedFastJsonDispAssetCatalog getAssetCatalog() {
        return assetCatalog;
    }

    public void setAssetCatalog(JSFixedFastJsonDispAssetCatalog assetCatalog) {
        this.assetCatalog = assetCatalog;
    }

    public FastJsonDispAccount getAccount() {
        return account;
    }

    public void setAccount(FastJsonDispAccount account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispPoac{" +
                "key=" + key +
                ", permissionLevel=" + permissionLevel +
                ", remark='" + remark + '\'' +
                ", assetCatalog=" + assetCatalog +
                ", account=" + account +
                '}';
    }
}
