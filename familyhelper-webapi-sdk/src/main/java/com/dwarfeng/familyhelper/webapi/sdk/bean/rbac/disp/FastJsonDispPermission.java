package com.dwarfeng.familyhelper.webapi.sdk.bean.rbac.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.rbac.disp.DispPermission;
import com.dwarfeng.rbacds.sdk.bean.entity.FastJsonPermissionGroup;
import com.dwarfeng.rbacds.sdk.bean.key.FastJsonPermissionGroupKey;
import com.dwarfeng.rbacds.sdk.bean.key.FastJsonPermissionKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Arrays;
import java.util.Objects;

/**
 * FastJson 可展示的权限。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonDispPermission implements Dto {

    private static final long serialVersionUID = -8789660252668466792L;

    public static FastJsonDispPermission of(DispPermission dispPermission) {
        if (Objects.isNull(dispPermission)) {
            return null;
        } else {
            return new FastJsonDispPermission(
                    FastJsonPermissionKey.of(dispPermission.getKey()),
                    FastJsonPermissionGroupKey.of(dispPermission.getGroupKey()),
                    dispPermission.getName(),
                    dispPermission.getRemark(),
                    dispPermission.getLevel(),
                    dispPermission.getGroupPath(),
                    FastJsonPermissionGroup.of(dispPermission.getGroup())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonPermissionKey key;

    @JSONField(name = "group_key", ordinal = 2)
    private FastJsonPermissionGroupKey groupKey;

    @JSONField(name = "name", ordinal = 3)
    private String name;

    @JSONField(name = "remark", ordinal = 4)
    private String remark;

    @JSONField(name = "level", ordinal = 5)
    private int level;

    @JSONField(name = "group_path", ordinal = 6)
    private String[] groupPath;

    @JSONField(name = "group", ordinal = 7)
    private FastJsonPermissionGroup group;

    public FastJsonDispPermission() {
    }

    public FastJsonDispPermission(
            FastJsonPermissionKey key, FastJsonPermissionGroupKey groupKey, String name, String remark, int level,
            String[] groupPath, FastJsonPermissionGroup group
    ) {
        this.key = key;
        this.groupKey = groupKey;
        this.name = name;
        this.remark = remark;
        this.level = level;
        this.groupPath = groupPath;
        this.group = group;
    }

    public FastJsonPermissionKey getKey() {
        return key;
    }

    public void setKey(FastJsonPermissionKey key) {
        this.key = key;
    }

    public FastJsonPermissionGroupKey getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(FastJsonPermissionGroupKey groupKey) {
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

    public FastJsonPermissionGroup getGroup() {
        return group;
    }

    public void setGroup(FastJsonPermissionGroup group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "FastJsonDispPermission{" +
                "key=" + key +
                ", groupKey=" + groupKey +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", level=" + level +
                ", groupPath=" + Arrays.toString(groupPath) +
                ", group=" + group +
                '}';
    }
}
