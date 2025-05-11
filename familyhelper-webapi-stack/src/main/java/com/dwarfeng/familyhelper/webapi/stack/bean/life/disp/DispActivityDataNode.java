package com.dwarfeng.familyhelper.webapi.stack.bean.life.disp;

import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityDataNode;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Objects;

/**
 * 可展示活动数据节点。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
public class DispActivityDataNode implements Dto {

    private static final long serialVersionUID = 6404320523074140300L;

    public static DispActivityDataNode of(
            ActivityDataNode activityDataNode, DispActivityDataSet set, boolean hasNoChild
    ) {
        if (Objects.isNull(activityDataNode)) {
            return null;
        } else {
            return new DispActivityDataNode(
                    activityDataNode.getKey(), activityDataNode.getParentKey(), activityDataNode.getSetKey(),
                    activityDataNode.getName(), activityDataNode.getRemark(), set,
                    Objects.isNull(activityDataNode.getParentKey()), hasNoChild
            );
        }
    }

    private LongIdKey key;
    private LongIdKey parentKey;
    private LongIdKey setKey;
    private String name;
    private String remark;
    private DispActivityDataSet set;
    private boolean rootFlag;
    private boolean hasNoChild;

    public DispActivityDataNode() {
    }

    public DispActivityDataNode(
            LongIdKey key, LongIdKey parentKey, LongIdKey setKey, String name, String remark, DispActivityDataSet set,
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

    public DispActivityDataSet getSet() {
        return set;
    }

    public void setSet(DispActivityDataSet set) {
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
        return "DispActivityDataNode{" +
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
