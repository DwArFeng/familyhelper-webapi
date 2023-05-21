package com.dwarfeng.familyhelper.webapi.sdk.bean.disp.life;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispActivityDataNode;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * JSFixed FastJson 可展示活动数据节点。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
public class JSFixedFastJsonDispActivityDataNode implements Dto {

    private static final long serialVersionUID = -1716009315208037769L;

    public static JSFixedFastJsonDispActivityDataNode of(DispActivityDataNode dispActivityDataNode) {
        if (Objects.isNull(dispActivityDataNode)) {
            return null;
        } else {
            return new JSFixedFastJsonDispActivityDataNode(
                    JSFixedFastJsonLongIdKey.of(dispActivityDataNode.getKey()),
                    JSFixedFastJsonLongIdKey.of(dispActivityDataNode.getParentKey()),
                    JSFixedFastJsonLongIdKey.of(dispActivityDataNode.getSetKey()),
                    dispActivityDataNode.getName(),
                    dispActivityDataNode.getRemark(),
                    JSFixedFastJsonDispActivityDataSet.of(dispActivityDataNode.getSet()),
                    dispActivityDataNode.isRootFlag(),
                    dispActivityDataNode.isHasNoChild()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "parent_key", ordinal = 2)
    private JSFixedFastJsonLongIdKey parentKey;

    @JSONField(name = "set_key", ordinal = 3)
    private JSFixedFastJsonLongIdKey setKey;

    @JSONField(name = "name", ordinal = 4)
    private String name;

    @JSONField(name = "remark", ordinal = 5)
    private String remark;

    @JSONField(name = "set", ordinal = 6)
    private JSFixedFastJsonDispActivityDataSet set;

    @JSONField(name = "root_flag", ordinal = 7)
    private boolean rootFlag;

    @JSONField(name = "has_no_child", ordinal = 8)
    private boolean hasNoChild;

    public JSFixedFastJsonDispActivityDataNode() {
    }

    public JSFixedFastJsonDispActivityDataNode(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey parentKey, JSFixedFastJsonLongIdKey setKey,
            String name, String remark, JSFixedFastJsonDispActivityDataSet set, boolean rootFlag, boolean hasNoChild
    ) {
        this.key = key;
        this.parentKey = parentKey;
        this.setKey = setKey;
        this.name = name;
        this.remark = remark;
        this.set = set;
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

    public JSFixedFastJsonLongIdKey getSetKey() {
        return setKey;
    }

    public void setSetKey(JSFixedFastJsonLongIdKey setKey) {
        this.setKey = setKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public JSFixedFastJsonDispActivityDataSet getSet() {
        return set;
    }

    public void setSet(JSFixedFastJsonDispActivityDataSet set) {
        this.set = set;
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
        return "JSFixedFastJsonDispActivityDataNode{" +
                "key=" + key +
                ", parentKey=" + parentKey +
                ", setKey=" + setKey +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", set=" + set +
                ", rootFlag=" + rootFlag +
                ", hasNoChild=" + hasNoChild +
                '}';
    }
}
