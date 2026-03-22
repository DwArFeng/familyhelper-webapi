package com.dwarfeng.familyhelper.webapi.sdk.bean.rbac.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.rbac.disp.DispRoleUserRelation;
import com.dwarfeng.rbacds.sdk.bean.entity.FastJsonRole;
import com.dwarfeng.rbacds.sdk.bean.entity.FastJsonUser;
import com.dwarfeng.rbacds.sdk.bean.key.FastJsonRoleUserRelationKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * FastJson 可展示的角色用户关系。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonDispRoleUserRelation implements Dto {

    private static final long serialVersionUID = 7924121348789644539L;

    public static FastJsonDispRoleUserRelation of(DispRoleUserRelation dispRoleUserRelation) {
        if (Objects.isNull(dispRoleUserRelation)) {
            return null;
        } else {
            return new FastJsonDispRoleUserRelation(
                    FastJsonRoleUserRelationKey.of(dispRoleUserRelation.getKey()),
                    dispRoleUserRelation.isEnabled(), dispRoleUserRelation.getRemark(),
                    FastJsonRole.of(dispRoleUserRelation.getRole()),
                    FastJsonUser.of(dispRoleUserRelation.getUser())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonRoleUserRelationKey key;

    @JSONField(name = "enabled", ordinal = 2)
    private boolean enabled;

    @JSONField(name = "remark", ordinal = 3)
    private String remark;

    @JSONField(name = "role", ordinal = 4)
    private FastJsonRole role;

    @JSONField(name = "user", ordinal = 5)
    private FastJsonUser user;

    public FastJsonDispRoleUserRelation() {
    }

    public FastJsonDispRoleUserRelation(
            FastJsonRoleUserRelationKey key, boolean enabled, String remark, FastJsonRole role, FastJsonUser user
    ) {
        this.key = key;
        this.enabled = enabled;
        this.remark = remark;
        this.role = role;
        this.user = user;
    }

    public FastJsonRoleUserRelationKey getKey() {
        return key;
    }

    public void setKey(FastJsonRoleUserRelationKey key) {
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

    public FastJsonRole getRole() {
        return role;
    }

    public void setRole(FastJsonRole role) {
        this.role = role;
    }

    public FastJsonUser getUser() {
        return user;
    }

    public void setUser(FastJsonUser user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "FastJsonDispRoleUserRelation{" +
                "key=" + key +
                ", enabled=" + enabled +
                ", remark='" + remark + '\'' +
                ", role=" + role +
                ", user=" + user +
                '}';
    }
}
