package com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.disp;

import com.dwarfeng.settingrepo.stack.bean.entity.NavigationNodeItem;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

import java.util.Objects;

/**
 * 可展示导航节点条目。
 *
 * @author DwArFeng
 * @since 1.8.2
 */
public class DispNavigationNodeItem implements Dto {

    private static final long serialVersionUID = 8036978062036304919L;

    public static DispNavigationNodeItem of(
            NavigationNodeItem navigationNodeItem, NavigationNodeItem parentNavigationNodeItem, boolean hasNoChild
    ) {
        if (Objects.isNull(navigationNodeItem)) {
            return null;
        } else {
            return new DispNavigationNodeItem(
                    navigationNodeItem.getKey(),
                    navigationNodeItem.getNodeKey(),
                    navigationNodeItem.getParentKey(),
                    navigationNodeItem.getIndex(),
                    navigationNodeItem.getName(),
                    navigationNodeItem.getContent(),
                    navigationNodeItem.getRemark(),
                    parentNavigationNodeItem,
                    hasNoChild
            );
        }
    }

    private LongIdKey key;
    private StringIdKey nodeKey;
    private LongIdKey parentKey;
    private int index;
    private String name;
    private String content;
    private String remark;
    private NavigationNodeItem parentNavigationNodeItem;
    private boolean hasNoChild;

    public DispNavigationNodeItem() {
    }

    public DispNavigationNodeItem(
            LongIdKey key, StringIdKey nodeKey, LongIdKey parentKey, int index, String name, String content,
            String remark, NavigationNodeItem parentNavigationNodeItem, boolean hasNoChild
    ) {
        this.key = key;
        this.nodeKey = nodeKey;
        this.parentKey = parentKey;
        this.index = index;
        this.name = name;
        this.content = content;
        this.remark = remark;
        this.parentNavigationNodeItem = parentNavigationNodeItem;
        this.hasNoChild = hasNoChild;
    }

    public LongIdKey getKey() {
        return key;
    }

    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public StringIdKey getNodeKey() {
        return nodeKey;
    }

    public void setNodeKey(StringIdKey nodeKey) {
        this.nodeKey = nodeKey;
    }

    public LongIdKey getParentKey() {
        return parentKey;
    }

    public void setParentKey(LongIdKey parentKey) {
        this.parentKey = parentKey;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public NavigationNodeItem getParentNavigationNodeItem() {
        return parentNavigationNodeItem;
    }

    public void setParentNavigationNodeItem(NavigationNodeItem parentNavigationNodeItem) {
        this.parentNavigationNodeItem = parentNavigationNodeItem;
    }

    public boolean isHasNoChild() {
        return hasNoChild;
    }

    public void setHasNoChild(boolean hasNoChild) {
        this.hasNoChild = hasNoChild;
    }

    @Override
    public String toString() {
        return "DispNavigationNodeItem{" +
                "key=" + key +
                ", nodeKey=" + nodeKey +
                ", parentKey=" + parentKey +
                ", index=" + index +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", remark='" + remark + '\'' +
                ", parentNavigationNodeItem=" + parentNavigationNodeItem +
                ", hasNoChild=" + hasNoChild +
                '}';
    }
}
