package com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp;

import com.dwarfeng.audit.stack.bean.entity.AuditCategory;
import com.dwarfeng.audit.stack.bean.entity.AuditEntry;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

import java.util.Date;
import java.util.Objects;

/**
 * 可展示审计条目。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public class DispAuditEntry implements Dto {

    private static final long serialVersionUID = 2920166716321545908L;

    public static DispAuditEntry of(AuditEntry auditEntry, AuditCategory auditCategory) {
        if (Objects.isNull(auditEntry)) {
            return null;
        } else {
            return new DispAuditEntry(
                    auditEntry.getKey(),
                    auditEntry.getCategoryKey(),
                    auditEntry.getCreatedDate(),
                    auditCategory
            );
        }
    }

    private LongIdKey key;
    private StringIdKey categoryKey;
    private Date createdDate;
    private AuditCategory auditCategory;

    public DispAuditEntry() {
    }

    public DispAuditEntry(LongIdKey key, StringIdKey categoryKey, Date createdDate, AuditCategory auditCategory) {
        this.key = key;
        this.categoryKey = categoryKey;
        this.createdDate = createdDate;
        this.auditCategory = auditCategory;
    }

    public LongIdKey getKey() {
        return key;
    }

    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public StringIdKey getCategoryKey() {
        return categoryKey;
    }

    public void setCategoryKey(StringIdKey categoryKey) {
        this.categoryKey = categoryKey;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public AuditCategory getAuditCategory() {
        return auditCategory;
    }

    public void setAuditCategory(AuditCategory auditCategory) {
        this.auditCategory = auditCategory;
    }

    @Override
    public String toString() {
        return "DispAuditEntry{" +
                "key=" + key +
                ", categoryKey=" + categoryKey +
                ", createdDate=" + createdDate +
                ", auditCategory=" + auditCategory +
                '}';
    }
}
