package com.dwarfeng.familyhelper.webapi.sdk.bean.disp.system;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.system.DispPermissionGroup;
import com.dwarfeng.rbacds.sdk.bean.entity.FastJsonPermissionGroup;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * FastJson 可展示权限组。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonDispPermissionGroup implements Dto {

    private static final long serialVersionUID = 2245502541602823300L;

    public static FastJsonDispPermissionGroup of(DispPermissionGroup DispPermissionGroup) {
        if (Objects.isNull(DispPermissionGroup)) {
            return null;
        } else {
            return new FastJsonDispPermissionGroup(
                    FastJsonStringIdKey.of(DispPermissionGroup.getKey()),
                    FastJsonStringIdKey.of(DispPermissionGroup.getParentKey()),
                    DispPermissionGroup.getName(),
                    DispPermissionGroup.getRemark(),
                    FastJsonPermissionGroup.of(DispPermissionGroup.getParentPermissionGroup()),
                    DispPermissionGroup.isRootFlag(),
                    DispPermissionGroup.isHasNoChild()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonStringIdKey key;

    @JSONField(name = "parent_key", ordinal = 2)
    private FastJsonStringIdKey parentKey;

    @JSONField(name = "name", ordinal = 3)
    private String name;

    @JSONField(name = "remark", ordinal = 4)
    private String remark;

    @JSONField(name = "parent_permission_group", ordinal = 5)
    private FastJsonPermissionGroup parentPermissionGroup;

    @JSONField(name = "root_flag", ordinal = 6)
    private boolean rootFlag;

    @JSONField(name = "has_no_child", ordinal = 7)
    private boolean hasNoChild;

    public FastJsonDispPermissionGroup() {
    }

    public FastJsonDispPermissionGroup(
            FastJsonStringIdKey key, FastJsonStringIdKey parentKey, String name, String remark,
            FastJsonPermissionGroup parentPermissionGroup, boolean rootFlag, boolean hasNoChild
    ) {
        this.key = key;
        this.parentKey = parentKey;
        this.name = name;
        this.remark = remark;
        this.parentPermissionGroup = parentPermissionGroup;
        this.rootFlag = rootFlag;
        this.hasNoChild = hasNoChild;
    }

    public FastJsonStringIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonStringIdKey key) {
        this.key = key;
    }

    public FastJsonStringIdKey getParentKey() {
        return parentKey;
    }

    public void setParentKey(FastJsonStringIdKey parentKey) {
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

    public FastJsonPermissionGroup getParentPermissionGroup() {
        return parentPermissionGroup;
    }

    public void setParentPermissionGroup(FastJsonPermissionGroup parentPermissionGroup) {
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
        return "FastJsonDispPermissionGroup{" +
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
