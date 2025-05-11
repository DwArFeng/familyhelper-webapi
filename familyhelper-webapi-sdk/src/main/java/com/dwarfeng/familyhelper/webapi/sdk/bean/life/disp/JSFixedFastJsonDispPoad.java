package com.dwarfeng.familyhelper.webapi.sdk.bean.life.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.life.sdk.bean.key.JSFixedFastJsonPoadKey;
import com.dwarfeng.familyhelper.webapi.sdk.bean.system.disp.FastJsonDispAccount;
import com.dwarfeng.familyhelper.webapi.stack.bean.life.disp.DispPoad;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * 可展示活动数据权限。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
public class JSFixedFastJsonDispPoad implements Dto {

    private static final long serialVersionUID = -1866420254870747422L;

    public static JSFixedFastJsonDispPoad of(DispPoad dispPoad) {
        if (Objects.isNull(dispPoad)) {
            return null;
        } else {
            return new JSFixedFastJsonDispPoad(
                    JSFixedFastJsonPoadKey.of(dispPoad.getKey()),
                    dispPoad.getPermissionLevel(),
                    dispPoad.getRemark(),
                    JSFixedFastJsonDispActivityDataSet.of(dispPoad.getActivityDataSet()),
                    FastJsonDispAccount.of(dispPoad.getAccount())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonPoadKey key;

    @JSONField(name = "permission_level", ordinal = 2)
    private int permissionLevel;

    @JSONField(name = "remark", ordinal = 3)
    private String remark;

    @JSONField(name = "activity_data_set", ordinal = 4)
    private JSFixedFastJsonDispActivityDataSet activityDataSet;

    @JSONField(name = "account", ordinal = 5)
    private FastJsonDispAccount account;

    public JSFixedFastJsonDispPoad() {
    }

    public JSFixedFastJsonDispPoad(
            JSFixedFastJsonPoadKey key, int permissionLevel, String remark,
            JSFixedFastJsonDispActivityDataSet activityDataSet, FastJsonDispAccount account
    ) {
        this.key = key;
        this.permissionLevel = permissionLevel;
        this.remark = remark;
        this.activityDataSet = activityDataSet;
        this.account = account;
    }

    public JSFixedFastJsonPoadKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonPoadKey key) {
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

    public JSFixedFastJsonDispActivityDataSet getActivityDataSet() {
        return activityDataSet;
    }

    public void setActivityDataSet(JSFixedFastJsonDispActivityDataSet activityDataSet) {
        this.activityDataSet = activityDataSet;
    }

    public FastJsonDispAccount getAccount() {
        return account;
    }

    public void setAccount(FastJsonDispAccount account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispPoad{" +
                "key=" + key +
                ", permissionLevel=" + permissionLevel +
                ", remark='" + remark + '\'' +
                ", activityDataSet=" + activityDataSet +
                ", account=" + account +
                '}';
    }
}
