package com.dwarfeng.familyhelper.webapi.stack.bean.disp.project;

import com.dwarfeng.familyhelper.project.stack.bean.entity.Pop;
import com.dwarfeng.familyhelper.project.stack.bean.key.PopKey;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.system.DispAccount;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * 可展示工程权限。
 *
 * @author DwArFeng
 * @since 1.0.3
 */
public class DispPop implements Dto {

    private static final long serialVersionUID = -4590348496729095107L;

    public static DispPop of(
            Pop Pop, DispProject project, DispAccount account
    ) {
        if (Objects.isNull(project)) {
            return null;
        } else {
            return new DispPop(
                    Pop.getKey(), Pop.getPermissionLevel(), Pop.getRemark(), project, account
            );
        }
    }

    private PopKey key;
    private int permissionLevel;
    private String remark;
    private DispProject project;
    private DispAccount account;

    public DispPop() {
    }

    public DispPop(PopKey key, int permissionLevel, String remark, DispProject project, DispAccount account) {
        this.key = key;
        this.permissionLevel = permissionLevel;
        this.remark = remark;
        this.project = project;
        this.account = account;
    }

    public PopKey getKey() {
        return key;
    }

    public void setKey(PopKey key) {
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

    public DispProject getProject() {
        return project;
    }

    public void setProject(DispProject project) {
        this.project = project;
    }

    public DispAccount getAccount() {
        return account;
    }

    public void setAccount(DispAccount account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "DispPop{" +
                "key=" + key +
                ", permissionLevel=" + permissionLevel +
                ", remark='" + remark + '\'' +
                ", project=" + project +
                ", account=" + account +
                '}';
    }
}
