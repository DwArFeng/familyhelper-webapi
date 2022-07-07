package com.dwarfeng.familyhelper.webapi.sdk.bean.disp.clannad;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.system.FastJsonDispAccount;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.clannad.DispCertificate;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * JSFixed FastJson 可展示证件。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public class JSFixedFastJsonDispCertificate implements Dto {

    private static final long serialVersionUID = 4123092073395439275L;

    public static JSFixedFastJsonDispCertificate of(DispCertificate dispCertificate) {
        if (Objects.isNull(dispCertificate)) {
            return null;
        } else {
            return new JSFixedFastJsonDispCertificate(
                    JSFixedFastJsonLongIdKey.of(dispCertificate.getKey()),
                    dispCertificate.getName(),
                    dispCertificate.getRemark(),
                    FastJsonDispAccount.of(dispCertificate.getOwnerAccount()),
                    dispCertificate.getPermissionLevel()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "name", ordinal = 2)
    private String name;

    @JSONField(name = "remark", ordinal = 3)
    private String remark;

    @JSONField(name = "owner_account", ordinal = 4)
    private FastJsonDispAccount ownerAccount;

    @JSONField(name = "permission_level", ordinal = 5)
    private Integer permissionLevel;

    public JSFixedFastJsonDispCertificate() {
    }

    public JSFixedFastJsonDispCertificate(
            JSFixedFastJsonLongIdKey key, String name, String remark, FastJsonDispAccount ownerAccount,
            Integer permissionLevel
    ) {
        this.key = key;
        this.name = name;
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
        return "JSFixedFastJsonDispCertificate{" +
                "key=" + key +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", ownerAccount=" + ownerAccount +
                ", permissionLevel=" + permissionLevel +
                '}';
    }
}
