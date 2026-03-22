package com.dwarfeng.familyhelper.webapi.stack.bean.rbac.disp;

import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.bean.entity.Scope;
import com.dwarfeng.rbacds.stack.bean.key.PexpKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * 可展示的权限表达式。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class DispPexp implements Dto {

    private static final long serialVersionUID = -7437118774761073893L;

    public static DispPexp of(Pexp pexp, Scope scope, Role role) {
        if (Objects.isNull(pexp)) {
            return null;
        } else {
            return new DispPexp(
                    pexp.getKey(),
                    pexp.getContent(),
                    pexp.getRemark(),
                    scope,
                    role
            );
        }
    }

    private PexpKey key;
    private String content;
    private String remark;
    private Scope scope;
    private Role role;

    public DispPexp() {
    }

    public DispPexp(PexpKey key, String content, String remark, Scope scope, Role role) {
        this.key = key;
        this.content = content;
        this.remark = remark;
        this.scope = scope;
        this.role = role;
    }

    public PexpKey getKey() {
        return key;
    }

    public void setKey(PexpKey key) {
        this.key = key;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "DispPexp{" +
                "key=" + key +
                ", content='" + content + '\'' +
                ", remark='" + remark + '\'' +
                ", scope=" + scope +
                ", role=" + role +
                '}';
    }
}
