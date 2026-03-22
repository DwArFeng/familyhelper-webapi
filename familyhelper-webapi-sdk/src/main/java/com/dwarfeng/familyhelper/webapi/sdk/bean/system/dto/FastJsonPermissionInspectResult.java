package com.dwarfeng.familyhelper.webapi.sdk.bean.system.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.dto.PermissionInspectResult;
import com.dwarfeng.rbacds.sdk.bean.entity.FastJsonPermission;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * FastJson 权限查看结果。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonPermissionInspectResult implements Bean {

    private static final long serialVersionUID = 6676604425118342849L;

    public static FastJsonPermissionInspectResult of(PermissionInspectResult permissionInspectResult) {
        if (Objects.isNull(permissionInspectResult)) {
            return null;
        } else {
            return new FastJsonPermissionInspectResult(
                    Optional.ofNullable(permissionInspectResult.getPermissions()).map(
                            f -> f.stream().map(FastJsonPermission::of).collect(Collectors.toList())
                    ).orElse(null)
            );
        }
    }

    @JSONField(name = "permissions", ordinal = 1)
    private List<FastJsonPermission> permissions;

    public FastJsonPermissionInspectResult() {
    }

    public FastJsonPermissionInspectResult(List<FastJsonPermission> permissions) {
        this.permissions = permissions;
    }

    public List<FastJsonPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<FastJsonPermission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "FastJsonPermissionInspectResult{" +
                "permissions=" + permissions +
                '}';
    }
}
