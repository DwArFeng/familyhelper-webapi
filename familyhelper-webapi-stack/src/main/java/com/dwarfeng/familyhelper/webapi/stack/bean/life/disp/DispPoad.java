package com.dwarfeng.familyhelper.webapi.stack.bean.life.disp;

import com.dwarfeng.familyhelper.life.stack.bean.entity.Poad;
import com.dwarfeng.familyhelper.life.stack.bean.key.PoadKey;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.disp.DispAccount;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * 可展示活动数据权限。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
public class DispPoad implements Dto {

    private static final long serialVersionUID = 1157625762011929428L;

    public static DispPoad of(Poad poad, DispActivityDataSet activityDataSet, DispAccount account) {
        if (Objects.isNull(poad)) {
            return null;
        } else {
            return new DispPoad(
                    poad.getKey(), poad.getPermissionLevel(), poad.getRemark(), activityDataSet, account
            );
        }
    }

    private PoadKey key;
    private int permissionLevel;
    private String remark;
    private DispActivityDataSet activityDataSet;
    private DispAccount account;

    public DispPoad() {
    }

    public DispPoad(
            PoadKey key, int permissionLevel, String remark, DispActivityDataSet activityDataSet, DispAccount account
    ) {
        this.key = key;
        this.permissionLevel = permissionLevel;
        this.remark = remark;
        this.activityDataSet = activityDataSet;
        this.account = account;
    }

    public PoadKey getKey() {
        return key;
    }

    public void setKey(PoadKey key) {
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

    public DispActivityDataSet getActivityDataSet() {
        return activityDataSet;
    }

    public void setActivityDataSet(DispActivityDataSet activityDataSet) {
        this.activityDataSet = activityDataSet;
    }

    public DispAccount getAccount() {
        return account;
    }

    public void setAccount(DispAccount account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "DispPoad{" +
                "key=" + key +
                ", permissionLevel=" + permissionLevel +
                ", remark='" + remark + '\'' +
                ", activityDataSet=" + activityDataSet +
                ", account=" + account +
                '}';
    }
}
