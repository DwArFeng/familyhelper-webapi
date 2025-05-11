package com.dwarfeng.familyhelper.webapi.sdk.bean.life.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.life.sdk.bean.key.JSFixedFastJsonPoatKey;
import com.dwarfeng.familyhelper.webapi.sdk.bean.system.disp.FastJsonDispAccount;
import com.dwarfeng.familyhelper.webapi.stack.bean.life.disp.DispPoat;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * 可展示活动模板权限。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
public class JSFixedFastJsonDispPoat implements Dto {

    private static final long serialVersionUID = -4758838316899845032L;

    public static JSFixedFastJsonDispPoat of(DispPoat dispPoat) {
        if (Objects.isNull(dispPoat)) {
            return null;
        } else {
            return new JSFixedFastJsonDispPoat(
                    JSFixedFastJsonPoatKey.of(dispPoat.getKey()),
                    dispPoat.getPermissionLevel(),
                    dispPoat.getRemark(),
                    JSFixedFastJsonDispActivityTemplate.of(dispPoat.getActivityTemplate()),
                    FastJsonDispAccount.of(dispPoat.getAccount())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonPoatKey key;

    @JSONField(name = "permission_level", ordinal = 2)
    private int permissionLevel;

    @JSONField(name = "remark", ordinal = 3)
    private String remark;

    @JSONField(name = "activity_template", ordinal = 4)
    private JSFixedFastJsonDispActivityTemplate activityTemplate;

    @JSONField(name = "account", ordinal = 5)
    private FastJsonDispAccount account;

    public JSFixedFastJsonDispPoat() {
    }

    public JSFixedFastJsonDispPoat(
            JSFixedFastJsonPoatKey key, int permissionLevel, String remark,
            JSFixedFastJsonDispActivityTemplate activityTemplate, FastJsonDispAccount account
    ) {
        this.key = key;
        this.permissionLevel = permissionLevel;
        this.remark = remark;
        this.activityTemplate = activityTemplate;
        this.account = account;
    }

    public JSFixedFastJsonPoatKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonPoatKey key) {
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

    public JSFixedFastJsonDispActivityTemplate getActivityTemplate() {
        return activityTemplate;
    }

    public void setActivityTemplate(JSFixedFastJsonDispActivityTemplate activityTemplate) {
        this.activityTemplate = activityTemplate;
    }

    public FastJsonDispAccount getAccount() {
        return account;
    }

    public void setAccount(FastJsonDispAccount account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispPoat{" +
                "key=" + key +
                ", permissionLevel=" + permissionLevel +
                ", remark='" + remark + '\'' +
                ", activityTemplate=" + activityTemplate +
                ", account=" + account +
                '}';
    }
}
