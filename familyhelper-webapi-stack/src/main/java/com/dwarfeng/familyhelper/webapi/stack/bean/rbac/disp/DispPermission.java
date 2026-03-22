package com.dwarfeng.familyhelper.webapi.stack.bean.rbac.disp;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.entity.PermissionGroup;
import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * 可展示的权限。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class DispPermission implements Dto {

    private static final long serialVersionUID = -3745087618160108484L;

    public static DispPermission of(Permission permission, PermissionGroup group) {
        if (Objects.isNull(permission)) {
            return null;
        } else {
            return new DispPermission(
                    permission.getKey(),
                    permission.getGroupKey(),
                    permission.getName(),
                    permission.getRemark(),
                    permission.getLevel(),
                    permission.getGroupPath(),
                    group
            );
        }
    }

    private PermissionKey key;
    private PermissionGroupKey groupKey;
    private String name;
    private String remark;
    private int level;
    private String[] groupPath;
    private PermissionGroup group;

    public DispPermission() {
    }

    public DispPermission(
            PermissionKey key, PermissionGroupKey groupKey, String name, String remark, int level, String[] groupPath,
            PermissionGroup group
    ) {
        this.key = key;
        this.groupKey = groupKey;
        this.name = name;
        this.remark = remark;
        this.level = level;
        this.groupPath = groupPath;
        this.group = group;
    }

    public PermissionKey getKey() {
        return key;
    }

    public void setKey(PermissionKey key) {
        this.key = key;
    }

    public PermissionGroupKey getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(PermissionGroupKey groupKey) {
        this.groupKey = groupKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String[] getGroupPath() {
        return groupPath;
    }

    public void setGroupPath(String[] groupPath) {
        this.groupPath = groupPath;
    }

    public PermissionGroup getGroup() {
        return group;
    }

    public void setGroup(PermissionGroup group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "DispPermission{" +
                "key=" + key +
                ", groupKey=" + groupKey +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", level=" + level +
                ", groupPath=" + java.util.Arrays.toString(groupPath) +
                ", group=" + group +
                '}';
    }
}
