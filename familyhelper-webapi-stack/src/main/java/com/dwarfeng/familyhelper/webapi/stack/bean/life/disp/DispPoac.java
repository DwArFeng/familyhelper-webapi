package com.dwarfeng.familyhelper.webapi.stack.bean.life.disp;

import com.dwarfeng.familyhelper.life.stack.bean.entity.Poac;
import com.dwarfeng.familyhelper.life.stack.bean.key.PoacKey;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.disp.DispAccount;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * 可展示活动模板权限。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
public class DispPoac implements Dto {

    private static final long serialVersionUID = 824408350012760961L;

    public static DispPoac of(Poac poac, DispActivity activity, DispAccount account) {
        if (Objects.isNull(poac)) {
            return null;
        } else {
            return new DispPoac(
                    poac.getKey(), poac.getPermissionLevel(), poac.getRemark(), activity, account
            );
        }
    }

    private PoacKey key;
    private int permissionLevel;
    private String remark;
    private DispActivity activity;
    private DispAccount account;

    public DispPoac() {
    }

    public DispPoac(
            PoacKey key, int permissionLevel, String remark, DispActivity activity, DispAccount account
    ) {
        this.key = key;
        this.permissionLevel = permissionLevel;
        this.remark = remark;
        this.activity = activity;
        this.account = account;
    }

    public PoacKey getKey() {
        return key;
    }

    public void setKey(PoacKey key) {
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

    public DispActivity getActivity() {
        return activity;
    }

    public void setActivity(DispActivity activity) {
        this.activity = activity;
    }

    public DispAccount getAccount() {
        return account;
    }

    public void setAccount(DispAccount account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "DispPoac{" +
                "key=" + key +
                ", permissionLevel=" + permissionLevel +
                ", remark='" + remark + '\'' +
                ", activity=" + activity +
                ", account=" + account +
                '}';
    }
}
