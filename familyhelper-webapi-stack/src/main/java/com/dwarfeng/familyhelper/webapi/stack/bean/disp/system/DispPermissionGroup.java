package com.dwarfeng.familyhelper.webapi.stack.bean.disp.system;

import com.dwarfeng.rbacds.stack.bean.entity.PermissionGroup;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

import java.util.Objects;

/**
 * 可展示权限组。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class DispPermissionGroup implements Dto {

    private static final long serialVersionUID = 4460929009520516451L;

    public static DispPermissionGroup of(
            PermissionGroup permissionGroup, PermissionGroup parentPermissionGroup, boolean hasNoChild) {
        if (Objects.isNull(permissionGroup)) {
            return null;
        } else {
            return new DispPermissionGroup(
                    permissionGroup.getKey(),
                    parentPermissionGroup,
                    permissionGroup.getName(),
                    permissionGroup.getRemark(),
                    hasNoChild
            );
        }
    }

    private StringIdKey key;
    private PermissionGroup parentPermissionGroup;
    private String name;
    private String remark;
    private boolean hasNoChild;

    public DispPermissionGroup() {
    }

    public DispPermissionGroup(
            StringIdKey key, PermissionGroup parentPermissionGroup, String name, String remark, boolean hasNoChild) {
        this.key = key;
        this.parentPermissionGroup = parentPermissionGroup;
        this.name = name;
        this.remark = remark;
        this.hasNoChild = hasNoChild;
    }

    public StringIdKey getKey() {
        return key;
    }

    public void setKey(StringIdKey key) {
        this.key = key;
    }

    public PermissionGroup getParentPermissionGroup() {
        return parentPermissionGroup;
    }

    public void setParentPermissionGroup(PermissionGroup parentPermissionGroup) {
        this.parentPermissionGroup = parentPermissionGroup;
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

    public boolean isHasNoChild() {
        return hasNoChild;
    }

    public void setHasNoChild(boolean hasNoChild) {
        this.hasNoChild = hasNoChild;
    }

    @Override
    public String toString() {
        return "DispPermissionGroup{" +
                "key=" + key +
                ", parentPermissionGroup=" + parentPermissionGroup +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", hasNoChild=" + hasNoChild +
                '}';
    }
}
