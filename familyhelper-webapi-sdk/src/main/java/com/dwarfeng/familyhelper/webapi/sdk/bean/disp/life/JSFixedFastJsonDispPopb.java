package com.dwarfeng.familyhelper.webapi.sdk.bean.disp.life;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.life.sdk.bean.key.JSFixedFastJsonPopbKey;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.system.FastJsonDispAccount;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispPopb;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * JSFixed FastJson 可展示个人记录权限。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public class JSFixedFastJsonDispPopb implements Dto {

    private static final long serialVersionUID = 17561494456975443L;

    public static JSFixedFastJsonDispPopb of(DispPopb dispPopb) {
        if (Objects.isNull(dispPopb)) {
            return null;
        } else {
            return new JSFixedFastJsonDispPopb(
                    JSFixedFastJsonPopbKey.of(dispPopb.getKey()),
                    dispPopb.getPermissionLevel(),
                    dispPopb.getRemark(),
                    JSFixedFastJsonDispPbSet.of(dispPopb.getPbSet()),
                    FastJsonDispAccount.of(dispPopb.getAccount())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonPopbKey key;

    @JSONField(name = "permission_level", ordinal = 2)
    private int permissionLevel;

    @JSONField(name = "remark", ordinal = 3)
    private String remark;

    @JSONField(name = "pb_set", ordinal = 4)
    private JSFixedFastJsonDispPbSet pbSet;

    @JSONField(name = "account", ordinal = 5)
    private FastJsonDispAccount account;

    public JSFixedFastJsonDispPopb() {
    }

    public JSFixedFastJsonDispPopb(
            JSFixedFastJsonPopbKey key, int permissionLevel, String remark, JSFixedFastJsonDispPbSet pbSet,
            FastJsonDispAccount account
    ) {
        this.key = key;
        this.permissionLevel = permissionLevel;
        this.remark = remark;
        this.pbSet = pbSet;
        this.account = account;
    }

    public JSFixedFastJsonPopbKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonPopbKey key) {
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

    public JSFixedFastJsonDispPbSet getPbSet() {
        return pbSet;
    }

    public void setPbSet(JSFixedFastJsonDispPbSet pbSet) {
        this.pbSet = pbSet;
    }

    public FastJsonDispAccount getAccount() {
        return account;
    }

    public void setAccount(FastJsonDispAccount account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispPopb{" +
                "key=" + key +
                ", permissionLevel=" + permissionLevel +
                ", remark='" + remark + '\'' +
                ", pbSet=" + pbSet +
                ", account=" + account +
                '}';
    }
}
