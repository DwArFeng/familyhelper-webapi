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

    private static final long serialVersionUID = -5050185170096578222L;

    public static FastJsonDispPermissionGroup of(DispPermissionGroup DispPermissionGroup) {
        if (Objects.isNull(DispPermissionGroup)) {
            return null;
        } else {
            return new FastJsonDispPermissionGroup(
                    FastJsonStringIdKey.of(DispPermissionGroup.getKey()),
                    FastJsonPermissionGroup.of(DispPermissionGroup.getParentPermissionGroup()),
                    DispPermissionGroup.getName(),
                    DispPermissionGroup.getRemark(),
                    DispPermissionGroup.isHasNoChild()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonStringIdKey key;

    @JSONField(name = "parent_permission_group", ordinal = 2)
    private FastJsonPermissionGroup parentPermissionGroup;

    @JSONField(name = "name", ordinal = 3)
    private String name;

    @JSONField(name = "remark", ordinal = 4)
    private String remark;

    @JSONField(name = "has_no_child", ordinal = 5)
    private boolean hasNoChild;

    public FastJsonDispPermissionGroup() {
    }

    public FastJsonDispPermissionGroup(
            FastJsonStringIdKey key, FastJsonPermissionGroup parentPermissionGroup, String name, String remark,
            boolean hasNoChild
    ) {
        this.key = key;
        this.parentPermissionGroup = parentPermissionGroup;
        this.name = name;
        this.remark = remark;
        this.hasNoChild = hasNoChild;
    }

    public FastJsonStringIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonStringIdKey key) {
        this.key = key;
    }

    public FastJsonPermissionGroup getParentPermissionGroup() {
        return parentPermissionGroup;
    }

    public void setParentPermissionGroup(FastJsonPermissionGroup parentPermissionGroup) {
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
        return "FastJsonDispPermissionGroup{" +
                "key=" + key +
                ", parentPermissionGroup=" + parentPermissionGroup +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", hasNoChild=" + hasNoChild +
                '}';
    }
}
