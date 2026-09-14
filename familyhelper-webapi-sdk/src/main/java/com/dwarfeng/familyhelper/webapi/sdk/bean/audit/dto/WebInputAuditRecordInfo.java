package com.dwarfeng.familyhelper.webapi.sdk.bean.audit.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.sdk.util.ValidAuditPropertyType;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.dto.AuditRecordInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * WebInput 审计记录信息。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public class WebInputAuditRecordInfo implements Bean {

    private static final long serialVersionUID = 3245911747165303531L;

    public static AuditRecordInfo toStackBean(WebInputAuditRecordInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new AuditRecordInfo(
                    WebInputStringIdKey.toStackBean(webInput.getCategoryKey()),
                    Optional.ofNullable(webInput.getPropertyItems()).map(
                            f -> f.stream().map(PropertyItem::toStackBean).collect(Collectors.toList())
                    ).orElse(null)
            );
        }
    }

    @JSONField(name = "category_key")
    @Valid
    @NotNull
    private WebInputStringIdKey categoryKey;

    @JSONField(name = "property_items")
    @Valid
    @NotNull
    @NotEmpty
    private List<PropertyItem> propertyItems;

    public WebInputAuditRecordInfo() {
    }

    public WebInputStringIdKey getCategoryKey() {
        return categoryKey;
    }

    public void setCategoryKey(WebInputStringIdKey categoryKey) {
        this.categoryKey = categoryKey;
    }

    public List<PropertyItem> getPropertyItems() {
        return propertyItems;
    }

    public void setPropertyItems(List<PropertyItem> propertyItems) {
        this.propertyItems = propertyItems;
    }

    @Override
    public String toString() {
        return "WebInputAuditRecordInfo{" +
                "categoryKey=" + categoryKey +
                ", propertyItems=" + propertyItems +
                '}';
    }

    /**
     * WebInput 审计记录属性项。
     */
    public static class PropertyItem implements Bean {

        private static final long serialVersionUID = -8925699909323887467L;

        public static AuditRecordInfo.PropertyItem toStackBean(PropertyItem webInput) {
            if (Objects.isNull(webInput)) {
                return null;
            } else {
                return new AuditRecordInfo.PropertyItem(
                        webInput.getId(),
                        webInput.getValueType(),
                        webInput.getValueString()
                );
            }
        }

        @JSONField(name = "id")
        @NotNull
        @NotEmpty
        private String id;

        @JSONField(name = "value_type")
        @ValidAuditPropertyType
        private int valueType;

        @JSONField(name = "value_string")
        private String valueString;

        public PropertyItem() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getValueType() {
            return valueType;
        }

        public void setValueType(int valueType) {
            this.valueType = valueType;
        }

        public String getValueString() {
            return valueString;
        }

        public void setValueString(String valueString) {
            this.valueString = valueString;
        }

        @Override
        public String toString() {
            return "PropertyItem{" +
                    "id='" + id + '\'' +
                    ", valueType=" + valueType +
                    ", valueString='" + valueString + '\'' +
                    '}';
        }
    }
}
