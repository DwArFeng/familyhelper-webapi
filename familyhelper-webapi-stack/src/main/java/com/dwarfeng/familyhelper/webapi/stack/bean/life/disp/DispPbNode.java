package com.dwarfeng.familyhelper.webapi.stack.bean.life.disp;

import com.dwarfeng.familyhelper.life.stack.bean.entity.PbNode;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Objects;

/**
 * 可展示个人最佳节点。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public class DispPbNode implements Dto {

    private static final long serialVersionUID = -6964392686298011777L;

    public static DispPbNode of(PbNode node, DispPbSet set, boolean hasNoChild) {
        if (Objects.isNull(node)) {
            return null;
        } else {
            return new DispPbNode(
                    node.getKey(), node.getParentKey(), node.getSetKey(), node.getName(), node.getRemark(), set,
                    Objects.isNull(node.getParentKey()), hasNoChild
            );
        }
    }

    private LongIdKey key;
    private LongIdKey parentKey;
    private LongIdKey setKey;
    private String name;
    private String remark;
    private DispPbSet set;
    private boolean rootFlag;
    private boolean hasNoChild;

    public DispPbNode() {
    }

    public DispPbNode(
            LongIdKey key, LongIdKey parentKey, LongIdKey setKey, String name, String remark, DispPbSet set,
            boolean rootFlag, boolean hasNoChild
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

    public LongIdKey getSetKey() {
        return setKey;
    }

    public void setSetKey(LongIdKey setKey) {
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

    public DispPbSet getSet() {
        return set;
    }

    public void setSet(DispPbSet set) {
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
        return "DispPbNode{" +
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
