package com.dwarfeng.familyhelper.webapi.stack.bean.system.dto;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.List;

/**
 * 权限查看结果。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class PermissionInspectResult implements Dto {

    private static final long serialVersionUID = 6652431597837361890L;

    private List<Permission> permissions;

    public PermissionInspectResult() {
    }

    public PermissionInspectResult(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "PermissionInspectResult{" +
                "permissions=" + permissions +
                '}';
    }
}
