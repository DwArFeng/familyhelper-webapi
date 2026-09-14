package com.dwarfeng.familyhelper.webapi.sdk.bean.audit.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.sdk.util.ValidAuditPropertyType;
import com.dwarfeng.audit.sdk.util.ValidLogicOperator;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.dto.AuditEntryGroupedLookupInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * WebInput 审计条目分组查询信息。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public class WebInputAuditEntryGroupedLookupInfo implements Bean {

    private static final long serialVersionUID = 4171070752658811781L;

    public static AuditEntryGroupedLookupInfo toStackBean(WebInputAuditEntryGroupedLookupInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new AuditEntryGroupedLookupInfo(
                    webInput.getPagingInfo(),
                    webInput.getLogicOperator(),
                    toStackLookupItems(webInput.getLookupItems()),
                    toStackQueryGroups(webInput.getQueryGroups())
            );
        }
    }

    private static List<AuditEntryGroupedLookupInfo.LookupItem> toStackLookupItems(
            List<WebInputLookupItem> webInputLookupItems
    ) {
        return Optional.ofNullable(webInputLookupItems).map(
                f -> f.stream().map(WebInputLookupItem::toStackBean).collect(Collectors.toList())
        ).orElse(null);
    }

    private static List<AuditEntryGroupedLookupInfo.QueryGroup> toStackQueryGroups(
            List<WebInputQueryGroup> webInputQueryGroups
    ) {
        return Optional.ofNullable(webInputQueryGroups).map(
                f -> f.stream().map(WebInputQueryGroup::toStackBean).collect(Collectors.toList())
        ).orElse(null);
    }

    @JSONField(name = "paging_info")
    private PagingInfo pagingInfo;

    @JSONField(name = "logic_operator")
    @ValidLogicOperator
    private int logicOperator;

    @JSONField(name = "lookup_items")
    @Valid
    private List<WebInputLookupItem> lookupItems;

    @JSONField(name = "query_groups")
    @Valid
    private List<WebInputQueryGroup> queryGroups;

    public WebInputAuditEntryGroupedLookupInfo() {
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

    public List<WebInputLookupItem> getLookupItems() {
        return lookupItems;
    }

    public void setLookupItems(List<WebInputLookupItem> lookupItems) {
        this.lookupItems = lookupItems;
    }

    public List<WebInputQueryGroup> getQueryGroups() {
        return queryGroups;
    }

    public void setQueryGroups(List<WebInputQueryGroup> queryGroups) {
        this.queryGroups = queryGroups;
    }

    @Override
    public String toString() {
        return "WebInputAuditEntryGroupedLookupInfo{" +
                "pagingInfo=" + pagingInfo +
                ", logicOperator=" + logicOperator +
                ", lookupItems=" + lookupItems +
                ", queryGroups=" + queryGroups +
                '}';
    }

    /**
     * WebInput 查询组。
     */
    public static class WebInputQueryGroup implements Bean {

        private static final long serialVersionUID = 298004215286870266L;

        public static AuditEntryGroupedLookupInfo.QueryGroup toStackBean(WebInputQueryGroup webInput) {
            if (Objects.isNull(webInput)) {
                return null;
            } else {
                return new AuditEntryGroupedLookupInfo.QueryGroup(
                        webInput.getLogicOperator(),
                        toStackLookupItems(webInput.getLookupItems()),
                        toStackQueryGroups(webInput.getQueryGroups()),
                        webInput.isEnabled()
                );
            }
        }

        @JSONField(name = "logic_operator")
        @ValidLogicOperator
        private int logicOperator;

        @JSONField(name = "lookup_items")
        @Valid
        private List<WebInputLookupItem> lookupItems;

        @JSONField(name = "query_groups")
        @Valid
        private List<WebInputQueryGroup> queryGroups;

        @JSONField(name = "enabled")
        private boolean enabled;

        public WebInputQueryGroup() {
        }

        public int getLogicOperator() {
            return logicOperator;
        }

        public void setLogicOperator(int logicOperator) {
            this.logicOperator = logicOperator;
        }

        public List<WebInputLookupItem> getLookupItems() {
            return lookupItems;
        }

        public void setLookupItems(List<WebInputLookupItem> lookupItems) {
            this.lookupItems = lookupItems;
        }

        public List<WebInputQueryGroup> getQueryGroups() {
            return queryGroups;
        }

        public void setQueryGroups(List<WebInputQueryGroup> queryGroups) {
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
            return "WebInputQueryGroup{" +
                    "logicOperator=" + logicOperator +
                    ", lookupItems=" + lookupItems +
                    ", queryGroups=" + queryGroups +
                    ", enabled=" + enabled +
                    '}';
        }
    }

    /**
     * WebInput 查询项。
     */
    public static class WebInputLookupItem implements Bean {

        private static final long serialVersionUID = 6497471968597843333L;

        public static AuditEntryGroupedLookupInfo.LookupItem toStackBean(WebInputLookupItem webInput) {
            if (Objects.isNull(webInput)) {
                return null;
            } else {
                return new AuditEntryGroupedLookupInfo.LookupItem(
                        WebInputStringIdKey.toStackBean(webInput.getCategoryKey()),
                        WebInputLongIdKey.toStackBean(webInput.getAuditEntryKey()),
                        webInput.getStartCreatedDate(),
                        webInput.getEndCreatedDate(),
                        Optional.ofNullable(webInput.getCompositeItems()).map(
                                f -> f.stream().map(WebInputCompositeItem::toStackBean).collect(Collectors.toList())
                        ).orElse(null),
                        webInput.isEnabled()
                );
            }
        }

        @JSONField(name = "category_key")
        @Valid
        private WebInputStringIdKey categoryKey;

        @JSONField(name = "audit_entry_key")
        @Valid
        private WebInputLongIdKey auditEntryKey;

        @JSONField(name = "start_created_date")
        private Date startCreatedDate;

        @JSONField(name = "end_created_date")
        private Date endCreatedDate;

        @JSONField(name = "composite_items")
        @Valid
        private List<WebInputCompositeItem> compositeItems;

        @JSONField(name = "enabled")
        private boolean enabled;

        public WebInputLookupItem() {
        }

        public WebInputStringIdKey getCategoryKey() {
            return categoryKey;
        }

        public void setCategoryKey(WebInputStringIdKey categoryKey) {
            this.categoryKey = categoryKey;
        }

        public WebInputLongIdKey getAuditEntryKey() {
            return auditEntryKey;
        }

        public void setAuditEntryKey(WebInputLongIdKey auditEntryKey) {
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

        public List<WebInputCompositeItem> getCompositeItems() {
            return compositeItems;
        }

        public void setCompositeItems(List<WebInputCompositeItem> compositeItems) {
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
            return "WebInputLookupItem{" +
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
     * WebInput 组合查询项。
     */
    public static class WebInputCompositeItem implements Bean {

        private static final long serialVersionUID = 1840577418800606798L;

        public static AuditEntryGroupedLookupInfo.CompositeItem toStackBean(WebInputCompositeItem webInput) {
            if (Objects.isNull(webInput)) {
                return null;
            } else {
                return new AuditEntryGroupedLookupInfo.CompositeItem(
                        webInput.getPropertyId(),
                        webInput.getPropertyType(),
                        webInput.getFirstConditionString(),
                        webInput.getSecondConditionString(),
                        webInput.isEnabled()
                );
            }
        }

        @JSONField(name = "property_id")
        @NotNull
        @NotEmpty
        private String propertyId;

        @JSONField(name = "property_type")
        @ValidAuditPropertyType
        private int propertyType;

        @JSONField(name = "first_condition")
        private String firstConditionString;

        @JSONField(name = "second_condition")
        private String secondConditionString;

        @JSONField(name = "enabled")
        private boolean enabled;

        public WebInputCompositeItem() {
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
            return "WebInputCompositeItem{" +
                    "propertyId='" + propertyId + '\'' +
                    ", propertyType=" + propertyType +
                    ", firstConditionString='" + firstConditionString + '\'' +
                    ", secondConditionString='" + secondConditionString + '\'' +
                    ", enabled=" + enabled +
                    '}';
        }
    }
}
