package com.dwarfeng.familyhelper.webapi.sdk.bean.disp.life;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispPbItem;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * 可展示个人最佳项目。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public class JSFixedFastJsonDispPbItem implements Dto {

    private static final long serialVersionUID = -6550961362077070151L;

    public static JSFixedFastJsonDispPbItem of(DispPbItem dispPbItem) {
        if (Objects.isNull(dispPbItem)) {
            return null;
        } else {
            return new JSFixedFastJsonDispPbItem(
                    JSFixedFastJsonLongIdKey.of(dispPbItem.getKey()),
                    JSFixedFastJsonLongIdKey.of(dispPbItem.getNodeKey()),
                    dispPbItem.getName(), dispPbItem.getUnit(), dispPbItem.getComparator(), dispPbItem.getRemark(),
                    JSFixedFastJsonDispPbNode.of(dispPbItem.getNode())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "node_key", ordinal = 2)
    private JSFixedFastJsonLongIdKey nodeKey;

    @JSONField(name = "name", ordinal = 3)
    private String name;

    @JSONField(name = "unit", ordinal = 4)
    private String unit;

    @JSONField(name = "comparator", ordinal = 5)
    private Integer comparator;

    @JSONField(name = "remark", ordinal = 6)
    private String remark;

    @JSONField(name = "node", ordinal = 7)
    private JSFixedFastJsonDispPbNode node;

    public JSFixedFastJsonDispPbItem() {
    }

    public JSFixedFastJsonDispPbItem(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey nodeKey, String name, String unit,
            Integer comparator, String remark, JSFixedFastJsonDispPbNode node
    ) {
        this.key = key;
        this.nodeKey = nodeKey;
        this.name = name;
        this.unit = unit;
        this.comparator = comparator;
        this.remark = remark;
        this.node = node;
    }

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
        this.key = key;
    }

    public JSFixedFastJsonLongIdKey getNodeKey() {
        return nodeKey;
    }

    public void setNodeKey(JSFixedFastJsonLongIdKey nodeKey) {
        this.nodeKey = nodeKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getComparator() {
        return comparator;
    }

    public void setComparator(Integer comparator) {
        this.comparator = comparator;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public JSFixedFastJsonDispPbNode getNode() {
        return node;
    }

    public void setNode(JSFixedFastJsonDispPbNode node) {
        this.node = node;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispPbItem{" +
                "key=" + key +
                ", nodeKey=" + nodeKey +
                ", name='" + name + '\'' +
                ", unit='" + unit + '\'' +
                ", comparator=" + comparator +
                ", remark='" + remark + '\'' +
                ", node=" + node +
                '}';
    }
}
