package com.dwarfeng.familyhelper.webapi.sdk.bean.settingrepo.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.disp.DispNavigationNodeItem;
import com.dwarfeng.settingrepo.sdk.bean.entity.JSFixedFastJsonNavigationNodeItem;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * JSFixed FastJson 可展示导航节点条目。
 *
 * @author DwArFeng
 * @since 1.8.2
 */
public class JSFixedFastJsonDispNavigationNodeItem implements Dto {

    private static final long serialVersionUID = -6477101998878617389L;

    public static JSFixedFastJsonDispNavigationNodeItem of(DispNavigationNodeItem dispNavigationNodeItem) {
        if (Objects.isNull(dispNavigationNodeItem)) {
            return null;
        } else {
            return new JSFixedFastJsonDispNavigationNodeItem(
                    JSFixedFastJsonLongIdKey.of(dispNavigationNodeItem.getKey()),
                    FastJsonStringIdKey.of(dispNavigationNodeItem.getNodeKey()),
                    JSFixedFastJsonLongIdKey.of(dispNavigationNodeItem.getParentKey()),
                    dispNavigationNodeItem.getIndex(),
                    dispNavigationNodeItem.getName(),
                    dispNavigationNodeItem.getContent(),
                    dispNavigationNodeItem.getRemark(),
                    JSFixedFastJsonNavigationNodeItem.of(dispNavigationNodeItem.getParentNavigationNodeItem()),
                    dispNavigationNodeItem.isHasNoChild()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "node_key", ordinal = 2)
    private FastJsonStringIdKey nodeKey;

    @JSONField(name = "parent_key", ordinal = 3)
    private JSFixedFastJsonLongIdKey parentKey;

    @JSONField(name = "index", ordinal = 4)
    private int index;

    @JSONField(name = "name", ordinal = 5)
    private String name;

    @JSONField(name = "content", ordinal = 6)
    private String content;

    @JSONField(name = "remark", ordinal = 7)
    private String remark;

    @JSONField(name = "parent_navigation_node_item", ordinal = 8)
    private JSFixedFastJsonNavigationNodeItem parentNavigationNodeItem;

    @JSONField(name = "has_no_child", ordinal = 9)
    private boolean hasNoChild;

    public JSFixedFastJsonDispNavigationNodeItem() {
    }

    public JSFixedFastJsonDispNavigationNodeItem(
            JSFixedFastJsonLongIdKey key, FastJsonStringIdKey nodeKey, JSFixedFastJsonLongIdKey parentKey, int index, String name,
            String content, String remark, JSFixedFastJsonNavigationNodeItem parentNavigationNodeItem, boolean hasNoChild
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

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
        this.key = key;
    }

    public FastJsonStringIdKey getNodeKey() {
        return nodeKey;
    }

    public void setNodeKey(FastJsonStringIdKey nodeKey) {
        this.nodeKey = nodeKey;
    }

    public JSFixedFastJsonLongIdKey getParentKey() {
        return parentKey;
    }

    public void setParentKey(JSFixedFastJsonLongIdKey parentKey) {
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

    public JSFixedFastJsonNavigationNodeItem getParentNavigationNodeItem() {
        return parentNavigationNodeItem;
    }

    public void setParentNavigationNodeItem(JSFixedFastJsonNavigationNodeItem parentNavigationNodeItem) {
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
        return "JSFixedFastJsonDispNavigationNodeItem{" +
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
