package com.dwarfeng.familyhelper.webapi.stack.bean.audit.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

import java.util.List;

/**
 * 审计记录信息。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public class AuditRecordInfo implements Dto {

    private static final long serialVersionUID = 6836919274215283249L;

    private StringIdKey categoryKey;
    private List<PropertyItem> propertyItems;

    public AuditRecordInfo() {
    }

    public AuditRecordInfo(StringIdKey categoryKey, List<PropertyItem> propertyItems) {
        this.categoryKey = categoryKey;
        this.propertyItems = propertyItems;
    }

    public StringIdKey getCategoryKey() {
        return categoryKey;
    }

    public void setCategoryKey(StringIdKey categoryKey) {
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
        return "AuditRecordInfo{" +
                "categoryKey=" + categoryKey +
                ", propertyItems=" + propertyItems +
                '}';
    }

    /**
     * 审计记录属性项。
     *
     * @author DwArFeng
     * @since 2.2.0
     */
    public static class PropertyItem implements Dto {

        private static final long serialVersionUID = 1823879993116991364L;

        private String id;
        private int valueType;
        private String valueString;

        public PropertyItem() {
        }

        public PropertyItem(String id, int valueType, String valueString) {
            this.id = id;
            this.valueType = valueType;
            this.valueString = valueString;
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
