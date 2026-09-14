package com.dwarfeng.familyhelper.webapi.sdk.bean.audit.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.sdk.util.ValidAuditPropertyType;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.dto.AuditEntryCompositeLookupInfo;
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
 * WebInput 审计条目组合查询信息。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public class WebInputAuditEntryCompositeLookupInfo implements Bean {

    private static final long serialVersionUID = 7340606286780952129L;

    public static AuditEntryCompositeLookupInfo toStackBean(WebInputAuditEntryCompositeLookupInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new AuditEntryCompositeLookupInfo(
                    webInput.getPagingInfo(),
                    WebInputStringIdKey.toStackBean(webInput.getCategoryKey()),
                    WebInputLongIdKey.toStackBean(webInput.getAuditEntryKey()),
                    webInput.getStartCreatedDate(),
                    webInput.getEndCreatedDate(),
                    Optional.ofNullable(webInput.getCompositeItems()).map(
                            f -> f.stream().map(WebInputCompositeItem::toStackBean).collect(Collectors.toList())
                    ).orElse(null)
            );
        }
    }

    @JSONField(name = "paging_info")
    private PagingInfo pagingInfo;

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

    public WebInputAuditEntryCompositeLookupInfo() {
    }

    public PagingInfo getPagingInfo() {
        return pagingInfo;
    }

    public void setPagingInfo(PagingInfo pagingInfo) {
        this.pagingInfo = pagingInfo;
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

    @Override
    public String toString() {
        return "WebInputAuditEntryCompositeLookupInfo{" +
                "pagingInfo=" + pagingInfo +
                ", categoryKey=" + categoryKey +
                ", auditEntryKey=" + auditEntryKey +
                ", startCreatedDate=" + startCreatedDate +
                ", endCreatedDate=" + endCreatedDate +
                ", compositeItems=" + compositeItems +
                '}';
    }

    /**
     * WebInput 组合查询项。
     */
    public static class WebInputCompositeItem implements Bean {

        private static final long serialVersionUID = -5555242642439669934L;

        public static AuditEntryCompositeLookupInfo.CompositeItem toStackBean(WebInputCompositeItem webInput) {
            if (Objects.isNull(webInput)) {
                return null;
            } else {
                return new AuditEntryCompositeLookupInfo.CompositeItem(
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
