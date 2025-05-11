package com.dwarfeng.familyhelper.webapi.stack.bean.life.disp;

import com.dwarfeng.familyhelper.life.stack.bean.entity.Poatac;
import com.dwarfeng.familyhelper.life.stack.bean.key.PoatacKey;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.disp.DispAccount;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * 可展示活动模板活动权限。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
public class DispPoatac implements Dto {

    private static final long serialVersionUID = -5876679310107659914L;

    public static DispPoatac of(Poatac poatac, DispActivityTemplate activityTemplate, DispAccount account) {
        if (Objects.isNull(poatac)) {
            return null;
        } else {
            return new DispPoatac(
                    poatac.getKey(), poatac.getPermissionLevel(), poatac.getRemark(), activityTemplate, account
            );
        }
    }

    private PoatacKey key;
    private int permissionLevel;
    private String remark;
    private DispActivityTemplate activityTemplate;
    private DispAccount account;

    public DispPoatac() {
    }

    public DispPoatac(
            PoatacKey key, int permissionLevel, String remark, DispActivityTemplate activityTemplate, DispAccount account
    ) {
        this.key = key;
        this.permissionLevel = permissionLevel;
        this.remark = remark;
        this.activityTemplate = activityTemplate;
        this.account = account;
    }

    public PoatacKey getKey() {
        return key;
    }

    public void setKey(PoatacKey key) {
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

    public DispActivityTemplate getActivityTemplate() {
        return activityTemplate;
    }

    public void setActivityTemplate(DispActivityTemplate activityTemplate) {
        this.activityTemplate = activityTemplate;
    }

    public DispAccount getAccount() {
        return account;
    }

    public void setAccount(DispAccount account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "DispPoatac{" +
                "key=" + key +
                ", permissionLevel=" + permissionLevel +
                ", remark='" + remark + '\'' +
                ", activityTemplate=" + activityTemplate +
                ", account=" + account +
                '}';
    }
}
