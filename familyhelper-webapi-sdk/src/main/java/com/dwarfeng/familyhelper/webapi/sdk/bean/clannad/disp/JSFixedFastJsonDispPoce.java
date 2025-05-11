package com.dwarfeng.familyhelper.webapi.sdk.bean.clannad.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.clannad.sdk.bean.key.JSFixedFastJsonPoceKey;
import com.dwarfeng.familyhelper.webapi.sdk.bean.system.disp.FastJsonDispAccount;
import com.dwarfeng.familyhelper.webapi.stack.bean.clannad.disp.DispPoce;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * JSFixed FastJson 可展示证件权限。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public class JSFixedFastJsonDispPoce implements Dto {

    private static final long serialVersionUID = 833809211994229979L;

    public static JSFixedFastJsonDispPoce of(DispPoce dispPoce) {
        if (Objects.isNull(dispPoce)) {
            return null;
        } else {
            return new JSFixedFastJsonDispPoce(
                    JSFixedFastJsonPoceKey.of(dispPoce.getKey()),
                    dispPoce.getPermissionLevel(),
                    dispPoce.getRemark(),
                    JSFixedFastJsonDispCertificate.of(dispPoce.getCertificate()),
                    FastJsonDispAccount.of(dispPoce.getAccount())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonPoceKey key;

    @JSONField(name = "permission_level", ordinal = 2)
    private int permissionLevel;

    @JSONField(name = "remark", ordinal = 3)
    private String remark;

    @JSONField(name = "certificate", ordinal = 4)
    private JSFixedFastJsonDispCertificate certificate;

    @JSONField(name = "account", ordinal = 5)
    private FastJsonDispAccount account;

    public JSFixedFastJsonDispPoce() {
    }

    public JSFixedFastJsonDispPoce(
            JSFixedFastJsonPoceKey key, int permissionLevel, String remark, JSFixedFastJsonDispCertificate certificate,
            FastJsonDispAccount account
    ) {
        this.key = key;
        this.permissionLevel = permissionLevel;
        this.remark = remark;
        this.certificate = certificate;
        this.account = account;
    }

    public JSFixedFastJsonPoceKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonPoceKey key) {
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

    public JSFixedFastJsonDispCertificate getCertificate() {
        return certificate;
    }

    public void setCertificate(JSFixedFastJsonDispCertificate certificate) {
        this.certificate = certificate;
    }

    public FastJsonDispAccount getAccount() {
        return account;
    }

    public void setAccount(FastJsonDispAccount account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispPoce{" +
                "key=" + key +
                ", permissionLevel=" + permissionLevel +
                ", remark='" + remark + '\'' +
                ", certificate=" + certificate +
                ", account=" + account +
                '}';
    }
}
