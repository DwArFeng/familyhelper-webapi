package com.dwarfeng.familyhelper.webapi.sdk.bean.life.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.life.sdk.bean.key.JSFixedFastJsonPoatacKey;
import com.dwarfeng.familyhelper.webapi.sdk.bean.system.disp.FastJsonDispAccount;
import com.dwarfeng.familyhelper.webapi.stack.bean.life.disp.DispPoatac;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * JSFixed FastJson 可展示活动模板活动权限。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
public class JSFixedFastJsonDispPoatac implements Dto {

    private static final long serialVersionUID = -1415855027497817510L;

    public static JSFixedFastJsonDispPoatac of(DispPoatac dispPoatac) {
        if (Objects.isNull(dispPoatac)) {
            return null;
        } else {
            return new JSFixedFastJsonDispPoatac(
                    JSFixedFastJsonPoatacKey.of(dispPoatac.getKey()),
                    dispPoatac.getPermissionLevel(),
                    dispPoatac.getRemark(),
                    JSFixedFastJsonDispActivityTemplate.of(dispPoatac.getActivityTemplate()),
                    FastJsonDispAccount.of(dispPoatac.getAccount())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonPoatacKey key;

    @JSONField(name = "permission_level", ordinal = 2)
    private int permissionLevel;

    @JSONField(name = "remark", ordinal = 3)
    private String remark;

    @JSONField(name = "activity_template", ordinal = 4)
    private JSFixedFastJsonDispActivityTemplate activityTemplate;

    @JSONField(name = "account", ordinal = 5)
    private FastJsonDispAccount account;

    public JSFixedFastJsonDispPoatac() {
    }

    public JSFixedFastJsonDispPoatac(
            JSFixedFastJsonPoatacKey key, int permissionLevel, String remark,
            JSFixedFastJsonDispActivityTemplate activityTemplate, FastJsonDispAccount account
    ) {
        this.key = key;
        this.permissionLevel = permissionLevel;
        this.remark = remark;
        this.activityTemplate = activityTemplate;
        this.account = account;
    }

    public JSFixedFastJsonPoatacKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonPoatacKey key) {
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
        return "JSFixedFastJsonDispPoatac{" +
                "key=" + key +
                ", permissionLevel=" + permissionLevel +
                ", remark='" + remark + '\'' +
                ", activityTemplate=" + activityTemplate +
                ", account=" + account +
                '}';
    }
}
