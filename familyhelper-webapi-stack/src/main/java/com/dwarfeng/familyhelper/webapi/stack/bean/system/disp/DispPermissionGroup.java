package com.dwarfeng.familyhelper.webapi.stack.bean.system.disp;

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

    private static final long serialVersionUID = -642998464144871502L;

    public static DispPermissionGroup of(
            PermissionGroup permissionGroup, PermissionGroup parentPermissionGroup, boolean hasNoChild) {
        if (Objects.isNull(permissionGroup)) {
            return null;
        } else {
            return new DispPermissionGroup(
                    permissionGroup.getKey(),
                    permissionGroup.getParentKey(),
                    permissionGroup.getName(),
                    permissionGroup.getRemark(),
                    parentPermissionGroup,
                    Objects.isNull(parentPermissionGroup),
                    hasNoChild
            );
        }
    }

    private StringIdKey key;

    /**
     * @since 1.4.2
     */
    private StringIdKey parentKey;
    private String name;
    private String remark;
    private PermissionGroup parentPermissionGroup;

    /**
     * @since 1.4.2
     */
    private boolean rootFlag;

    private boolean hasNoChild;

    public DispPermissionGroup() {
    }

    public DispPermissionGroup(
            StringIdKey key, StringIdKey parentKey, String name, String remark, PermissionGroup parentPermissionGroup,
            boolean rootFlag, boolean hasNoChild
    ) {
        this.key = key;
        this.parentKey = parentKey;
        this.name = name;
        this.remark = remark;
        this.parentPermissionGroup = parentPermissionGroup;
        this.rootFlag = rootFlag;
        this.hasNoChild = hasNoChild;
    }

    public StringIdKey getKey() {
        return key;
    }

    public void setKey(StringIdKey key) {
        this.key = key;
    }

    public StringIdKey getParentKey() {
        return parentKey;
    }

    public void setParentKey(StringIdKey parentKey) {
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

    public PermissionGroup getParentPermissionGroup() {
        return parentPermissionGroup;
    }

    public void setParentPermissionGroup(PermissionGroup parentPermissionGroup) {
        this.parentPermissionGroup = parentPermissionGroup;
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
                ", parentPermissionGroup=" + parentPermissionGroup +
                ", rootFlag=" + rootFlag +
                ", hasNoChild=" + hasNoChild +
                '}';
    }
}
