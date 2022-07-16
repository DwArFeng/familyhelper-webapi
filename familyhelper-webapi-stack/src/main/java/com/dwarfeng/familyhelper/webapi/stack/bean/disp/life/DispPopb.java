package com.dwarfeng.familyhelper.webapi.stack.bean.disp.life;

import com.dwarfeng.familyhelper.life.stack.bean.entity.Popb;
import com.dwarfeng.familyhelper.life.stack.bean.key.PopbKey;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.system.DispAccount;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * 可展示个人记录权限。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public class DispPopb implements Dto {

    private static final long serialVersionUID = -1954606801141162754L;

    public static DispPopb of(
            Popb Popb, DispPbSet pbSet, DispAccount account
    ) {
        if (Objects.isNull(pbSet)) {
            return null;
        } else {
            return new DispPopb(
                    Popb.getKey(), Popb.getPermissionLevel(), Popb.getRemark(), pbSet, account
            );
        }
    }

    private PopbKey key;
    private int permissionLevel;
    private String remark;
    private DispPbSet pbSet;
    private DispAccount account;

    public DispPopb() {
    }

    public DispPopb(PopbKey key, int permissionLevel, String remark, DispPbSet pbSet, DispAccount account) {
        this.key = key;
        this.permissionLevel = permissionLevel;
        this.remark = remark;
        this.pbSet = pbSet;
        this.account = account;
    }

    public PopbKey getKey() {
        return key;
    }

    public void setKey(PopbKey key) {
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

    public DispPbSet getPbSet() {
        return pbSet;
    }

    public void setPbSet(DispPbSet pbSet) {
        this.pbSet = pbSet;
    }

    public DispAccount getAccount() {
        return account;
    }

    public void setAccount(DispAccount account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "DispPopb{" +
                "key=" + key +
                ", permissionLevel=" + permissionLevel +
                ", remark='" + remark + '\'' +
                ", pbSet=" + pbSet +
                ", account=" + account +
                '}';
    }
}
