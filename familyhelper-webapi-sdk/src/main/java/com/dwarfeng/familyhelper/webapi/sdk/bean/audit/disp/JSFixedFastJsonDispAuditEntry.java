package com.dwarfeng.familyhelper.webapi.sdk.bean.audit.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.sdk.bean.entity.FastJsonAuditCategory;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp.DispAuditEntry;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 可展示审计条目。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public class JSFixedFastJsonDispAuditEntry implements Dto {

    private static final long serialVersionUID = -2664926775566845009L;

    public static JSFixedFastJsonDispAuditEntry of(DispAuditEntry dispAuditEntry) {
        if (Objects.isNull(dispAuditEntry)) {
            return null;
        } else {
            return new JSFixedFastJsonDispAuditEntry(
                    JSFixedFastJsonLongIdKey.of(dispAuditEntry.getKey()),
                    FastJsonStringIdKey.of(dispAuditEntry.getCategoryKey()),
                    dispAuditEntry.getCreatedDate(),
                    FastJsonAuditCategory.of(dispAuditEntry.getAuditCategory())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "category_key", ordinal = 2)
    private FastJsonStringIdKey categoryKey;

    @JSONField(name = "created_date", ordinal = 3)
    private Date createdDate;

    @JSONField(name = "audit_category", ordinal = 4)
    private FastJsonAuditCategory auditCategory;

    public JSFixedFastJsonDispAuditEntry() {
    }

    public JSFixedFastJsonDispAuditEntry(
            JSFixedFastJsonLongIdKey key, FastJsonStringIdKey categoryKey, Date createdDate,
            FastJsonAuditCategory auditCategory
    ) {
        this.key = key;
        this.categoryKey = categoryKey;
        this.createdDate = createdDate;
        this.auditCategory = auditCategory;
    }

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
        this.key = key;
    }

    public FastJsonStringIdKey getCategoryKey() {
        return categoryKey;
    }

    public void setCategoryKey(FastJsonStringIdKey categoryKey) {
        this.categoryKey = categoryKey;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public FastJsonAuditCategory getAuditCategory() {
        return auditCategory;
    }

    public void setAuditCategory(FastJsonAuditCategory auditCategory) {
        this.auditCategory = auditCategory;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispAuditEntry{" +
                "key=" + key +
                ", categoryKey=" + categoryKey +
                ", createdDate=" + createdDate +
                ", auditCategory=" + auditCategory +
                '}';
    }
}
