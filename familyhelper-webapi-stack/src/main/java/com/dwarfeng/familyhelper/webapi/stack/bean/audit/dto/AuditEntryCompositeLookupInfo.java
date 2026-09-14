package com.dwarfeng.familyhelper.webapi.stack.bean.audit.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

import java.util.Date;
import java.util.List;

/**
 * 审计条目组合查询信息。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public class AuditEntryCompositeLookupInfo implements Dto {

    private static final long serialVersionUID = 1536251767003584075L;

    private PagingInfo pagingInfo;
    private StringIdKey categoryKey;
    private LongIdKey auditEntryKey;
    private Date startCreatedDate;
    private Date endCreatedDate;
    private List<CompositeItem> compositeItems;

    public AuditEntryCompositeLookupInfo() {
    }

    public AuditEntryCompositeLookupInfo(
            PagingInfo pagingInfo, StringIdKey categoryKey, LongIdKey auditEntryKey, Date startCreatedDate,
            Date endCreatedDate, List<CompositeItem> compositeItems
    ) {
        this.pagingInfo = pagingInfo;
        this.categoryKey = categoryKey;
        this.auditEntryKey = auditEntryKey;
        this.startCreatedDate = startCreatedDate;
        this.endCreatedDate = endCreatedDate;
        this.compositeItems = compositeItems;
    }

    public PagingInfo getPagingInfo() {
        return pagingInfo;
    }

    public void setPagingInfo(PagingInfo pagingInfo) {
        this.pagingInfo = pagingInfo;
    }

    public StringIdKey getCategoryKey() {
        return categoryKey;
    }

    public void setCategoryKey(StringIdKey categoryKey) {
        this.categoryKey = categoryKey;
    }

    public LongIdKey getAuditEntryKey() {
        return auditEntryKey;
    }

    public void setAuditEntryKey(LongIdKey auditEntryKey) {
        this.auditEntryKey = auditEntryKey;
    }

    public Date getStartCreatedDate() {
        return startCreatedDate;
    }

    public void setStartCreatedDate(Date startCreatedDate) {
        this.startCreatedDate = startCreatedDate;
    }

    public Date getEndCreatedDate() {
        return endCreatedDate;
    }

    public void setEndCreatedDate(Date endCreatedDate) {
        this.endCreatedDate = endCreatedDate;
    }

    public List<CompositeItem> getCompositeItems() {
        return compositeItems;
    }

    public void setCompositeItems(List<CompositeItem> compositeItems) {
        this.compositeItems = compositeItems;
    }

    @Override
    public String toString() {
        return "AuditEntryCompositeLookupInfo{" +
                "pagingInfo=" + pagingInfo +
                ", categoryKey=" + categoryKey +
                ", auditEntryKey=" + auditEntryKey +
                ", startCreatedDate=" + startCreatedDate +
                ", endCreatedDate=" + endCreatedDate +
                ", compositeItems=" + compositeItems +
                '}';
    }

    /**
     * 组合查询项。
     */
    public static class CompositeItem implements Dto {

        private static final long serialVersionUID = 7863963482174605783L;

        private String propertyId;
        private int propertyType;
        private String firstConditionString;
        private String secondConditionString;
        private boolean enabled;

        public CompositeItem() {
        }

        public CompositeItem(
                String propertyId, int propertyType, String firstConditionString, String secondConditionString,
                boolean enabled
        ) {
            this.propertyId = propertyId;
            this.propertyType = propertyType;
            this.firstConditionString = firstConditionString;
            this.secondConditionString = secondConditionString;
            this.enabled = enabled;
        }

        public String getPropertyId() {
            return propertyId;
        }

        public void setPropertyId(String propertyId) {
            this.propertyId = propertyId;
        }

        public int getPropertyType() {
            return propertyType;
        }

        public void setPropertyType(int propertyType) {
            this.propertyType = propertyType;
        }

        public String getFirstConditionString() {
            return firstConditionString;
        }

        public void setFirstConditionString(String firstConditionString) {
            this.firstConditionString = firstConditionString;
        }

        public String getSecondConditionString() {
            return secondConditionString;
        }

        public void setSecondConditionString(String secondConditionString) {
            this.secondConditionString = secondConditionString;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        @Override
        public String toString() {
            return "CompositeItem{" +
                    "propertyId='" + propertyId + '\'' +
                    ", propertyType=" + propertyType +
                    ", firstConditionString='" + firstConditionString + '\'' +
                    ", secondConditionString='" + secondConditionString + '\'' +
                    ", enabled=" + enabled +
                    '}';
        }
    }
}
