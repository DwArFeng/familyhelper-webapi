package com.dwarfeng.familyhelper.webapi.sdk.bean.disp.assets;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.assets.sdk.bean.entity.FastJsonItemLabel;
import com.dwarfeng.familyhelper.assets.sdk.bean.entity.FastJsonItemTypeIndicator;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.assets.DispItem;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 可展示项目。
 *
 * @author DwArFeng
 * @since 1.0.2
 */
public class JSFixedFastJsonDispItem implements Dto {

    private static final long serialVersionUID = 2599855457107982597L;

    public static JSFixedFastJsonDispItem of(DispItem dispItem) {
        if (Objects.isNull(dispItem)) {
            return null;
        } else {
            return new JSFixedFastJsonDispItem(
                    JSFixedFastJsonLongIdKey.of(dispItem.getKey()),
                    JSFixedFastJsonLongIdKey.of(dispItem.getParentKey()),
                    JSFixedFastJsonLongIdKey.of(dispItem.getAssetCatalogKey()),
                    Optional.ofNullable(dispItem.getLabelKeys()).map(
                            f -> f.stream().map(FastJsonStringIdKey::of).collect(Collectors.toList())
                    ).orElse(null),
                    dispItem.getName(), dispItem.getItemType(), dispItem.getCreatedDate(), dispItem.getModifiedDate(),
                    dispItem.getScrappedDate(), dispItem.getLifeCycle(), dispItem.getRemark(),
                    JSFixedFastJsonDispAssetCatalog.of(dispItem.getAssetCatalog()),
                    FastJsonItemTypeIndicator.of(dispItem.getTypeIndicator()),
                    Optional.ofNullable(dispItem.getLabels()).map(
                            f -> f.stream().map(FastJsonItemLabel::of).collect(Collectors.toList())
                    ).orElse(null),
                    dispItem.isRootFlag(),
                    dispItem.isHasNoChild()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "parent_key", ordinal = 2)
    private JSFixedFastJsonLongIdKey parentKey;

    @JSONField(name = "asset_catalog_key", ordinal = 3)
    private JSFixedFastJsonLongIdKey assetCatalogKey;

    @JSONField(name = "label_keys", ordinal = 4)
    private List<FastJsonStringIdKey> labelKeys;

    @JSONField(name = "name", ordinal = 5)
    private String name;

    @JSONField(name = "item_type", ordinal = 6)
    private String itemType;

    @JSONField(name = "created_date", ordinal = 7)
    private Date createdDate;

    @JSONField(name = "modified_date", ordinal = 8)
    private Date modifiedDate;

    @JSONField(name = "scrapped_date", ordinal = 9)
    private Date scrappedDate;

    @JSONField(name = "life_cycle", ordinal = 10)
    private Integer lifeCycle;

    @JSONField(name = "remark", ordinal = 11)
    private String remark;

    @JSONField(name = "asset_catalog", ordinal = 12)
    private JSFixedFastJsonDispAssetCatalog assetCatalog;

    @JSONField(name = "type_indicator", ordinal = 13)
    private FastJsonItemTypeIndicator typeIndicator;

    @JSONField(name = "labels", ordinal = 14)
    private List<FastJsonItemLabel> labels;

    @JSONField(name = "root_flag", ordinal = 15)
    private boolean rootFlag;

    @JSONField(name = "has_no_child", ordinal = 16)
    private boolean hasNoChild;

    public JSFixedFastJsonDispItem() {
    }

    public JSFixedFastJsonDispItem(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey parentKey, JSFixedFastJsonLongIdKey assetCatalogKey,
            List<FastJsonStringIdKey> labelKeys, String name, String itemType, Date createdDate, Date modifiedDate,
            Date scrappedDate, Integer lifeCycle, String remark, JSFixedFastJsonDispAssetCatalog assetCatalog,
            FastJsonItemTypeIndicator typeIndicator, List<FastJsonItemLabel> labels, boolean rootFlag,
            boolean hasNoChild
    ) {
        this.key = key;
        this.parentKey = parentKey;
        this.assetCatalogKey = assetCatalogKey;
        this.labelKeys = labelKeys;
        this.name = name;
        this.itemType = itemType;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.scrappedDate = scrappedDate;
        this.lifeCycle = lifeCycle;
        this.remark = remark;
        this.assetCatalog = assetCatalog;
        this.typeIndicator = typeIndicator;
        this.labels = labels;
        this.rootFlag = rootFlag;
        this.hasNoChild = hasNoChild;
    }

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
        this.key = key;
    }

    public JSFixedFastJsonLongIdKey getParentKey() {
        return parentKey;
    }

    public void setParentKey(JSFixedFastJsonLongIdKey parentKey) {
        this.parentKey = parentKey;
    }

    public JSFixedFastJsonLongIdKey getAssetCatalogKey() {
        return assetCatalogKey;
    }

    public void setAssetCatalogKey(JSFixedFastJsonLongIdKey assetCatalogKey) {
        this.assetCatalogKey = assetCatalogKey;
    }

    public List<FastJsonStringIdKey> getLabelKeys() {
        return labelKeys;
    }

    public void setLabelKeys(List<FastJsonStringIdKey> labelKeys) {
        this.labelKeys = labelKeys;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Date getScrappedDate() {
        return scrappedDate;
    }

    public void setScrappedDate(Date scrappedDate) {
        this.scrappedDate = scrappedDate;
    }

    public Integer getLifeCycle() {
        return lifeCycle;
    }

    public void setLifeCycle(Integer lifeCycle) {
        this.lifeCycle = lifeCycle;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public JSFixedFastJsonDispAssetCatalog getAssetCatalog() {
        return assetCatalog;
    }

    public void setAssetCatalog(JSFixedFastJsonDispAssetCatalog assetCatalog) {
        this.assetCatalog = assetCatalog;
    }

    public FastJsonItemTypeIndicator getTypeIndicator() {
        return typeIndicator;
    }

    public void setTypeIndicator(FastJsonItemTypeIndicator typeIndicator) {
        this.typeIndicator = typeIndicator;
    }

    public List<FastJsonItemLabel> getLabels() {
        return labels;
    }

    public void setLabels(List<FastJsonItemLabel> labels) {
        this.labels = labels;
    }

    public boolean isRootFlag() {
        return rootFlag;
    }

    public void setRootFlag(boolean rootFlag) {
        this.rootFlag = rootFlag;
    }

    public boolean isHasNoChild() {
        return hasNoChild;
    }

    public void setHasNoChild(boolean hasNoChild) {
        this.hasNoChild = hasNoChild;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispItem{" +
                "key=" + key +
                ", parentKey=" + parentKey +
                ", assetCatalogKey=" + assetCatalogKey +
                ", labelKeys=" + labelKeys +
                ", name='" + name + '\'' +
                ", itemType='" + itemType + '\'' +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                ", scrappedDate=" + scrappedDate +
                ", lifeCycle=" + lifeCycle +
                ", remark='" + remark + '\'' +
                ", assetCatalog=" + assetCatalog +
                ", typeIndicator=" + typeIndicator +
                ", labels=" + labels +
                ", rootFlag=" + rootFlag +
                ", hasNoChild=" + hasNoChild +
                '}';
    }
}
