package com.dwarfeng.familyhelper.webapi.stack.bean.rbac.disp;

import com.dwarfeng.rbacds.stack.bean.entity.PermissionGroup;
import com.dwarfeng.rbacds.stack.bean.entity.Scope;
import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * 可展示的权限组。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class DispPermissionGroup implements Dto {

    private static final long serialVersionUID = 6857914971989534633L;

    public static DispPermissionGroup of(
            PermissionGroup permissionGroup, Scope scope, PermissionGroup parent, boolean hasNoChild
    ) {
        if (Objects.isNull(permissionGroup)) {
            return null;
        } else {
            return new DispPermissionGroup(
                    permissionGroup.getKey(),
                    permissionGroup.getParentKey(),
                    permissionGroup.getName(),
                    permissionGroup.getRemark(),
                    scope,
                    parent,
                    Objects.isNull(permissionGroup.getParentKey()),
                    hasNoChild
            );
        }
    }

    private PermissionGroupKey key;
    private PermissionGroupKey parentKey;
    private String name;
    private String remark;
    private Scope scope;
    private PermissionGroup parent;
    private boolean rootFlag;
    private boolean hasNoChild;

    public DispPermissionGroup() {
    }

    public DispPermissionGroup(
            PermissionGroupKey key, PermissionGroupKey parentKey, String name, String remark, Scope scope,
            PermissionGroup parent, boolean rootFlag, boolean hasNoChild
    ) {
        this.key = key;
        this.parentKey = parentKey;
        this.name = name;
        this.remark = remark;
        this.scope = scope;
        this.parent = parent;
        this.rootFlag = rootFlag;
        this.hasNoChild = hasNoChild;
    }

    public PermissionGroupKey getKey() {
        return key;
    }

    public void setKey(PermissionGroupKey key) {
        this.key = key;
    }

    public PermissionGroupKey getParentKey() {
        return parentKey;
    }

    public void setParentKey(PermissionGroupKey parentKey) {
        this.parentKey = parentKey;
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

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public PermissionGroup getParent() {
        return parent;
    }

    public void setParent(PermissionGroup parent) {
        this.parent = parent;
    }

    public boolean isRootFlag() {
        return rootFlag;
    }

    public void setRootFlag(boolean rootFlag) {
        this.rootFlag = rootFlag;
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
                ", parentKey=" + parentKey +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", scope=" + scope +
                ", parent=" + parent +
                ", rootFlag=" + rootFlag +
                ", hasNoChild=" + hasNoChild +
                '}';
    }
}
