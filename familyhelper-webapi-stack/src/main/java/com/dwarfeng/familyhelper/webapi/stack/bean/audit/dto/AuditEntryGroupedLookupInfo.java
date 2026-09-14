package com.dwarfeng.familyhelper.webapi.stack.bean.audit.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

import java.util.Date;
import java.util.List;

/**
 * 审计条目分组查询信息。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public class AuditEntryGroupedLookupInfo implements Dto {

    private static final long serialVersionUID = 7548720250616848462L;

    private PagingInfo pagingInfo;
    private int logicOperator;
    private List<LookupItem> lookupItems;
    private List<QueryGroup> queryGroups;

    public AuditEntryGroupedLookupInfo() {
    }

    public AuditEntryGroupedLookupInfo(
            PagingInfo pagingInfo, int logicOperator, List<LookupItem> lookupItems, List<QueryGroup> queryGroups
    ) {
        this.pagingInfo = pagingInfo;
        this.logicOperator = logicOperator;
        this.lookupItems = lookupItems;
        this.queryGroups = queryGroups;
    }

    public PagingInfo getPagingInfo() {
        return pagingInfo;
    }

    public void setPagingInfo(PagingInfo pagingInfo) {
        this.pagingInfo = pagingInfo;
    }

    public int getLogicOperator() {
        return logicOperator;
    }

    public void setLogicOperator(int logicOperator) {
        this.logicOperator = logicOperator;
    }

    public List<LookupItem> getLookupItems() {
        return lookupItems;
    }

    public void setLookupItems(List<LookupItem> lookupItems) {
        this.lookupItems = lookupItems;
    }

    public List<QueryGroup> getQueryGroups() {
        return queryGroups;
    }

    public void setQueryGroups(List<QueryGroup> queryGroups) {
        this.queryGroups = queryGroups;
    }

    @Override
    public String toString() {
        return "AuditEntryGroupedLookupInfo{" +
                "pagingInfo=" + pagingInfo +
                ", logicOperator=" + logicOperator +
                ", lookupItems=" + lookupItems +
                ", queryGroups=" + queryGroups +
                '}';
    }

    /**
     * 查询组。
     */
    public static class QueryGroup implements Dto {

        private static final long serialVersionUID = -8537903938939947152L;

        private int logicOperator;
        private List<LookupItem> lookupItems;
        private List<QueryGroup> queryGroups;
        private boolean enabled;

        public QueryGroup() {
        }

        public QueryGroup(
                int logicOperator, List<LookupItem> lookupItems, List<QueryGroup> queryGroups, boolean enabled
        ) {
            this.logicOperator = logicOperator;
            this.lookupItems = lookupItems;
            this.queryGroups = queryGroups;
            this.enabled = enabled;
        }

        public int getLogicOperator() {
            return logicOperator;
        }

        public void setLogicOperator(int logicOperator) {
            this.logicOperator = logicOperator;
        }

        public List<LookupItem> getLookupItems() {
            return lookupItems;
        }

        public void setLookupItems(List<LookupItem> lookupItems) {
            this.lookupItems = lookupItems;
        }

        public List<QueryGroup> getQueryGroups() {
            return queryGroups;
        }

        public void setQueryGroups(List<QueryGroup> queryGroups) {
            this.queryGroups = queryGroups;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        @Override
        public String toString() {
            return "QueryGroup{" +
                    "logicOperator=" + logicOperator +
                    ", lookupItems=" + lookupItems +
                    ", queryGroups=" + queryGroups +
                    ", enabled=" + enabled +
                    '}';
        }
    }

    /**
     * 查询项。
     */
    public static class LookupItem implements Dto {

        private static final long serialVersionUID = -1959492530818400250L;

        private StringIdKey categoryKey;
        private LongIdKey auditEntryKey;
        private Date startCreatedDate;
        private Date endCreatedDate;
        private List<CompositeItem> compositeItems;
        private boolean enabled;

        public LookupItem() {
        }

        public LookupItem(
                StringIdKey categoryKey, LongIdKey auditEntryKey, Date startCreatedDate, Date endCreatedDate,
                List<CompositeItem> compositeItems, boolean enabled
        ) {
            this.categoryKey = categoryKey;
            this.auditEntryKey = auditEntryKey;
            this.startCreatedDate = startCreatedDate;
            this.endCreatedDate = endCreatedDate;
            this.compositeItems = compositeItems;
            this.enabled = enabled;
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

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        @Override
        public String toString() {
            return "LookupItem{" +
                    "categoryKey=" + categoryKey +
                    ", auditEntryKey=" + auditEntryKey +
                    ", startCreatedDate=" + startCreatedDate +
                    ", endCreatedDate=" + endCreatedDate +
                    ", compositeItems=" + compositeItems +
                    ", enabled=" + enabled +
                    '}';
        }
    }

    /**
     * 组合查询项。
     */
    public static class CompositeItem implements Dto {

        private static final long serialVersionUID = -5541411743386161422L;

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
