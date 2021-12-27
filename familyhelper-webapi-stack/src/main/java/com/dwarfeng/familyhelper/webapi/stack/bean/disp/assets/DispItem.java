package com.dwarfeng.familyhelper.webapi.stack.bean.disp.assets;

import com.dwarfeng.familyhelper.assets.stack.bean.entity.Item;
import com.dwarfeng.familyhelper.assets.stack.bean.entity.ItemLabel;
import com.dwarfeng.familyhelper.assets.stack.bean.entity.ItemTypeIndicator;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 可展示项目。
 *
 * @author DwArFeng
 * @since 1.0.2
 */
public class DispItem implements Dto {

    private static final long serialVersionUID = -1380817573285113246L;

    public static DispItem of(
            Item item, DispAssetCatalog assetCatalog, ItemTypeIndicator typeIndicator, List<ItemLabel> labels,
            boolean hasNoChild
    ) {
        if (Objects.isNull(assetCatalog)) {
            return null;
        } else {
            return new DispItem(
                    item.getKey(), item.getParentKey(), item.getAssetCatalogKey(),
                    labels.stream().map(ItemLabel::getKey).collect(Collectors.toList()),
                    item.getName(), item.getItemType(), item.getCreatedDate(), item.getModifiedDate(),
                    item.getScrappedDate(), item.getLifeCycle(), item.getRemark(), assetCatalog, typeIndicator, labels,
                    Objects.isNull(item.getParentKey()), hasNoChild
            );
        }
    }

    private LongIdKey key;
    private LongIdKey parentKey;
    private LongIdKey assetCatalogKey;
    private List<StringIdKey> labelKeys;
    private String name;
    private String itemType;
    private Date createdDate;
    private Date modifiedDate;
    private Date scrappedDate;
    private Integer lifeCycle;
    private String remark;
    private DispAssetCatalog assetCatalog;
    private ItemTypeIndicator typeIndicator;
    private List<ItemLabel> labels;
    private boolean rootFlag;
    private boolean hasNoChild;

    public DispItem() {
    }

    public DispItem(
            LongIdKey key, LongIdKey parentKey, LongIdKey assetCatalogKey, List<StringIdKey> labelKeys,
            String name, String itemType, Date createdDate, Date modifiedDate, Date scrappedDate, Integer lifeCycle,
            String remark, DispAssetCatalog assetCatalog, ItemTypeIndicator typeIndicator, List<ItemLabel> labels,
            boolean rootFlag, boolean hasNoChild
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

    public LongIdKey getKey() {
        return key;
    }

    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public LongIdKey getParentKey() {
        return parentKey;
    }

    public void setParentKey(LongIdKey parentKey) {
        this.parentKey = parentKey;
    }

    public LongIdKey getAssetCatalogKey() {
        return assetCatalogKey;
    }

    public void setAssetCatalogKey(LongIdKey assetCatalogKey) {
        this.assetCatalogKey = assetCatalogKey;
    }

    public List<StringIdKey> getLabelKeys() {
        return labelKeys;
    }

    public void setLabelKeys(List<StringIdKey> labelKeys) {
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

    public DispAssetCatalog getAssetCatalog() {
        return assetCatalog;
    }

    public void setAssetCatalog(DispAssetCatalog assetCatalog) {
        this.assetCatalog = assetCatalog;
    }

    public ItemTypeIndicator getTypeIndicator() {
        return typeIndicator;
    }

    public void setTypeIndicator(ItemTypeIndicator typeIndicator) {
        this.typeIndicator = typeIndicator;
    }

    public List<ItemLabel> getLabels() {
        return labels;
    }

    public void setLabels(List<ItemLabel> labels) {
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
        return "DispItem{" +
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
