package com.dwarfeng.familyhelper.webapi.sdk.bean.rbac.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.rbac.disp.DispPermissionGroup;
import com.dwarfeng.rbacds.sdk.bean.entity.FastJsonPermissionGroup;
import com.dwarfeng.rbacds.sdk.bean.entity.FastJsonScope;
import com.dwarfeng.rbacds.sdk.bean.key.FastJsonPermissionGroupKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * FastJson 可展示的权限组。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonDispPermissionGroup implements Dto {

    private static final long serialVersionUID = 3300947158814908195L;

    public static FastJsonDispPermissionGroup of(DispPermissionGroup dispPermissionGroup) {
        if (Objects.isNull(dispPermissionGroup)) {
            return null;
        } else {
            return new FastJsonDispPermissionGroup(
                    FastJsonPermissionGroupKey.of(dispPermissionGroup.getKey()),
                    FastJsonPermissionGroupKey.of(dispPermissionGroup.getParentKey()),
                    dispPermissionGroup.getName(),
                    dispPermissionGroup.getRemark(),
                    FastJsonScope.of(dispPermissionGroup.getScope()),
                    FastJsonPermissionGroup.of(dispPermissionGroup.getParent()),
                    dispPermissionGroup.isRootFlag(),
                    dispPermissionGroup.isHasNoChild()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonPermissionGroupKey key;

    @JSONField(name = "parent_key", ordinal = 2)
    private FastJsonPermissionGroupKey parentKey;

    @JSONField(name = "name", ordinal = 3)
    private String name;

    @JSONField(name = "remark", ordinal = 4)
    private String remark;

    @JSONField(name = "scope", ordinal = 5)
    private FastJsonScope scope;

    @JSONField(name = "parent", ordinal = 6)
    private FastJsonPermissionGroup parent;

    @JSONField(name = "root_flag", ordinal = 7)
    private boolean rootFlag;

    @JSONField(name = "has_no_child", ordinal = 8)
    private boolean hasNoChild;

    public FastJsonDispPermissionGroup() {
    }

    public FastJsonDispPermissionGroup(
            FastJsonPermissionGroupKey key, FastJsonPermissionGroupKey parentKey, String name, String remark,
            FastJsonScope scope, FastJsonPermissionGroup parent, boolean rootFlag, boolean hasNoChild
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

    public FastJsonPermissionGroupKey getKey() {
        return key;
    }

    public void setKey(FastJsonPermissionGroupKey key) {
        this.key = key;
    }

    public FastJsonPermissionGroupKey getParentKey() {
        return parentKey;
    }

    public void setParentKey(FastJsonPermissionGroupKey parentKey) {
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

    public FastJsonScope getScope() {
        return scope;
    }

    public void setScope(FastJsonScope scope) {
        this.scope = scope;
    }

    public FastJsonPermissionGroup getParent() {
        return parent;
    }

    public void setParent(FastJsonPermissionGroup parent) {
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
        return "FastJsonDispPermissionGroup{" +
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
