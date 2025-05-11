package com.dwarfeng.familyhelper.webapi.sdk.bean.project.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.project.sdk.bean.key.JSFixedFastJsonPopKey;
import com.dwarfeng.familyhelper.webapi.sdk.bean.system.disp.FastJsonDispAccount;
import com.dwarfeng.familyhelper.webapi.stack.bean.project.disp.DispPop;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * JSFixed FastJson 可展示资产目录权限。
 *
 * @author DwArFeng
 * @since 1.0.2
 */
public class JSFixedFastJsonDispPop implements Dto {

    private static final long serialVersionUID = 8452146025217771619L;

    public static JSFixedFastJsonDispPop of(DispPop dispPop) {
        if (Objects.isNull(dispPop)) {
            return null;
        } else {
            return new JSFixedFastJsonDispPop(
                    JSFixedFastJsonPopKey.of(dispPop.getKey()),
                    dispPop.getPermissionLevel(),
                    dispPop.getRemark(),
                    JSFixedFastJsonDispProject.of(dispPop.getProject()),
                    FastJsonDispAccount.of(dispPop.getAccount())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonPopKey key;

    @JSONField(name = "permission_level", ordinal = 2)
    private int permissionLevel;

    @JSONField(name = "remark", ordinal = 3)
    private String remark;

    @JSONField(name = "project", ordinal = 4)
    private JSFixedFastJsonDispProject project;

    @JSONField(name = "account", ordinal = 5)
    private FastJsonDispAccount account;

    public JSFixedFastJsonDispPop() {
    }

    public JSFixedFastJsonDispPop(
            JSFixedFastJsonPopKey key, int permissionLevel, String remark, JSFixedFastJsonDispProject project,
            FastJsonDispAccount account
    ) {
        this.key = key;
        this.permissionLevel = permissionLevel;
        this.remark = remark;
        this.project = project;
        this.account = account;
    }

    public JSFixedFastJsonPopKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonPopKey key) {
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

    public JSFixedFastJsonDispProject getProject() {
        return project;
    }

    public void setProject(JSFixedFastJsonDispProject project) {
        this.project = project;
    }

    public FastJsonDispAccount getAccount() {
        return account;
    }

    public void setAccount(FastJsonDispAccount account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispPop{" +
                "key=" + key +
                ", permissionLevel=" + permissionLevel +
                ", remark='" + remark + '\'' +
                ", project=" + project +
                ", account=" + account +
                '}';
    }
}
