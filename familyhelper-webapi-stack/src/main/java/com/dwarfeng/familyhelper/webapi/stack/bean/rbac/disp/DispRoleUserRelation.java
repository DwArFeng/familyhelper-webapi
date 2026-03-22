package com.dwarfeng.familyhelper.webapi.stack.bean.rbac.disp;

import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.bean.entity.RoleUserRelation;
import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.rbacds.stack.bean.key.RoleUserRelationKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * 可展示的角色用户关系。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class DispRoleUserRelation implements Dto {

    private static final long serialVersionUID = 973692342146908054L;

    public static DispRoleUserRelation of(RoleUserRelation roleUserRelation, Role role, User user) {
        if (Objects.isNull(roleUserRelation)) {
            return null;
        } else {
            return new DispRoleUserRelation(
                    roleUserRelation.getKey(),
                    roleUserRelation.isEnabled(),
                    roleUserRelation.getRemark(),
                    role,
                    user
            );
        }
    }

    private RoleUserRelationKey key;
    private boolean enabled;
    private String remark;
    private Role role;
    private User user;

    public DispRoleUserRelation() {
    }

    public DispRoleUserRelation(RoleUserRelationKey key, boolean enabled, String remark, Role role, User user) {
        this.key = key;
        this.enabled = enabled;
        this.remark = remark;
        this.role = role;
        this.user = user;
    }

    public RoleUserRelationKey getKey() {
        return key;
    }

    public void setKey(RoleUserRelationKey key) {
        this.key = key;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "DispRoleUserRelation{" +
                "key=" + key +
                ", enabled=" + enabled +
                ", remark='" + remark + '\'' +
                ", role=" + role +
                ", user=" + user +
                '}';
    }
}
