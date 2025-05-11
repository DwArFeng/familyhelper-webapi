package com.dwarfeng.familyhelper.webapi.sdk.bean.life.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.life.sdk.bean.key.JSFixedFastJsonPoacKey;
import com.dwarfeng.familyhelper.webapi.sdk.bean.system.disp.FastJsonDispAccount;
import com.dwarfeng.familyhelper.webapi.stack.bean.life.disp.DispPoac;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * 可展示活动模板权限。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
public class JSFixedFastJsonDispPoac implements Dto {

    private static final long serialVersionUID = 5579809177138372601L;

    public static JSFixedFastJsonDispPoac of(DispPoac dispPoac) {
        if (Objects.isNull(dispPoac)) {
            return null;
        } else {
            return new JSFixedFastJsonDispPoac(
                    JSFixedFastJsonPoacKey.of(dispPoac.getKey()),
                    dispPoac.getPermissionLevel(),
                    dispPoac.getRemark(),
                    JSFixedFastJsonDispActivity.of(dispPoac.getActivity()),
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

    @JSONField(name = "activity", ordinal = 4)
    private JSFixedFastJsonDispActivity activity;

    @JSONField(name = "account", ordinal = 5)
    private FastJsonDispAccount account;

    public JSFixedFastJsonDispPoac() {
    }

    public JSFixedFastJsonDispPoac(
            JSFixedFastJsonPoacKey key, int permissionLevel, String remark,
            JSFixedFastJsonDispActivity activity, FastJsonDispAccount account
    ) {
        this.key = key;
        this.permissionLevel = permissionLevel;
        this.remark = remark;
        this.activity = activity;
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

    public JSFixedFastJsonDispActivity getActivity() {
        return activity;
    }

    public void setActivity(JSFixedFastJsonDispActivity activity) {
        this.activity = activity;
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
                ", activity=" + activity +
                ", account=" + account +
                '}';
    }
}
