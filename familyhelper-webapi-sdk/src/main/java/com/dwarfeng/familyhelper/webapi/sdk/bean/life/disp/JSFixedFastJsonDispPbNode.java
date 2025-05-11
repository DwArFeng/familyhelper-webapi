package com.dwarfeng.familyhelper.webapi.sdk.bean.life.disp;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.life.disp.DispPbNode;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * JSFixedFastJson 可展示个人最佳节点。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public class JSFixedFastJsonDispPbNode implements Dto {

    private static final long serialVersionUID = -2609552392005417114L;

    public static JSFixedFastJsonDispPbNode of(DispPbNode dispPbNode) {
        if (Objects.isNull(dispPbNode)) {
            return null;
        } else {
            return new JSFixedFastJsonDispPbNode(
                    JSFixedFastJsonLongIdKey.of(dispPbNode.getKey()),
                    JSFixedFastJsonLongIdKey.of(dispPbNode.getParentKey()),
                    JSFixedFastJsonLongIdKey.of(dispPbNode.getSetKey()),
                    dispPbNode.getName(), dispPbNode.getRemark(),
                    JSFixedFastJsonDispPbSet.of(dispPbNode.getSet()),
                    dispPbNode.isRootFlag(), dispPbNode.isHasNoChild()
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
    private JSFixedFastJsonDispPbSet set;

    @JSONField(name = "root_flag", ordinal = 7)
    private boolean rootFlag;

    @JSONField(name = "has_no_child", ordinal = 8)
    private boolean hasNoChild;

    public JSFixedFastJsonDispPbNode() {
    }

    public JSFixedFastJsonDispPbNode(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey parentKey, JSFixedFastJsonLongIdKey setKey,
            String name, String remark, JSFixedFastJsonDispPbSet set, boolean rootFlag, boolean hasNoChild
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

    public JSFixedFastJsonDispPbSet getSet() {
        return set;
    }

    public void setSet(JSFixedFastJsonDispPbSet set) {
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
        return "JSFixedFastJsonDispPbNode{" +
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
