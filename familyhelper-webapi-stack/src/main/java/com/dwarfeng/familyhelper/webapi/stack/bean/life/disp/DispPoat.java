package com.dwarfeng.familyhelper.webapi.stack.bean.life.disp;

import com.dwarfeng.familyhelper.life.stack.bean.entity.Poat;
import com.dwarfeng.familyhelper.life.stack.bean.key.PoatKey;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.disp.DispAccount;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * 可展示活动模板权限。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
public class DispPoat implements Dto {

    private static final long serialVersionUID = -802247452029650851L;

    public static DispPoat of(Poat poat, DispActivityTemplate activityTemplate, DispAccount account) {
        if (Objects.isNull(poat)) {
            return null;
        } else {
            return new DispPoat(
                    poat.getKey(), poat.getPermissionLevel(), poat.getRemark(), activityTemplate, account
            );
        }
    }

    private PoatKey key;
    private int permissionLevel;
    private String remark;
    private DispActivityTemplate activityTemplate;
    private DispAccount account;

    public DispPoat() {
    }

    public DispPoat(
            PoatKey key, int permissionLevel, String remark, DispActivityTemplate activityTemplate, DispAccount account
    ) {
        this.key = key;
        this.permissionLevel = permissionLevel;
        this.remark = remark;
        this.activityTemplate = activityTemplate;
        this.account = account;
    }

    public PoatKey getKey() {
        return key;
    }

    public void setKey(PoatKey key) {
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
        return "DispPoat{" +
                "key=" + key +
                ", permissionLevel=" + permissionLevel +
                ", remark='" + remark + '\'' +
                ", activityTemplate=" + activityTemplate +
                ", account=" + account +
                '}';
    }
}
