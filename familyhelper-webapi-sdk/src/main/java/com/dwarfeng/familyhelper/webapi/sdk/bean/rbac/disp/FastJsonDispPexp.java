package com.dwarfeng.familyhelper.webapi.sdk.bean.rbac.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.rbac.disp.DispPexp;
import com.dwarfeng.rbacds.sdk.bean.entity.FastJsonRole;
import com.dwarfeng.rbacds.sdk.bean.entity.FastJsonScope;
import com.dwarfeng.rbacds.sdk.bean.key.FastJsonPexpKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * FastJson 可展示的权限表达式。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonDispPexp implements Dto {

    private static final long serialVersionUID = 1243806725733373782L;

    public static FastJsonDispPexp of(DispPexp dispPexp) {
        if (Objects.isNull(dispPexp)) {
            return null;
        } else {
            return new FastJsonDispPexp(
                    FastJsonPexpKey.of(dispPexp.getKey()),
                    dispPexp.getContent(),
                    dispPexp.getRemark(),
                    FastJsonScope.of(dispPexp.getScope()),
                    FastJsonRole.of(dispPexp.getRole())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonPexpKey key;

    @JSONField(name = "content", ordinal = 2)
    private String content;

    @JSONField(name = "remark", ordinal = 3)
    private String remark;

    @JSONField(name = "scope", ordinal = 4)
    private FastJsonScope scope;

    @JSONField(name = "role", ordinal = 5)
    private FastJsonRole role;

    public FastJsonDispPexp() {
    }

    public FastJsonDispPexp(
            FastJsonPexpKey key, String content, String remark, FastJsonScope scope, FastJsonRole role
    ) {
        this.key = key;
        this.content = content;
        this.remark = remark;
        this.scope = scope;
        this.role = role;
    }

    public FastJsonPexpKey getKey() {
        return key;
    }

    public void setKey(FastJsonPexpKey key) {
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

    public FastJsonScope getScope() {
        return scope;
    }

    public void setScope(FastJsonScope scope) {
        this.scope = scope;
    }

    public FastJsonRole getRole() {
        return role;
    }

    public void setRole(FastJsonRole role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "FastJsonDispPexp{" +
                "key=" + key +
                ", content='" + content + '\'' +
                ", remark='" + remark + '\'' +
                ", scope=" + scope +
                ", role=" + role +
                '}';
    }
}
